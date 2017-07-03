package vn.com.nsmv.service.impl;

import java.io.*;
import java.text.*;
import java.util.*;

import org.apache.log4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.i18n.*;
import org.springframework.stereotype.*;
import org.springframework.web.multipart.*;

import vn.com.nsmv.common.*;
import vn.com.nsmv.common.Constants.*;
import vn.com.nsmv.dao.*;
import vn.com.nsmv.entity.*;
import vn.com.nsmv.i18n.*;
import vn.com.nsmv.javabean.*;
import vn.com.nsmv.service.*;

@Service("importingService")
public class ImportingServiceImpl implements ImportingService
{
	@Autowired
	private NyukoDAO nyukoDAO;
	@Autowired
	private TransDAO transDAO;
	private static final Logger logger = Logger.getLogger(ImportingServiceImpl.class);
	private static final int UPLOAD_ORDER_INDEX = 1;
	private static final int SAGYO_DATE_INDEX = 2;
	private static final int SOJO_NO_INDEX = 3;
	private static final int SEQ_INDEX = 4;
	private static final int SZ_NO_INDEX = 5;
	private static final int BKN09_INDEX = 6;
	private static final int BUZAI_KIGO_INDEX = 7;
	private static final int ZAISITU_INDEX = 8;
	private static final int DANSUN_MONGON_INDEX = 9;
	private static final int NAGASA_INDEX = 10;
	private static final int ZAIKO_INZU_INDEX = 11;
	private static final int NAGASA_TANI_INDEX = 12;
	private static final int KAKO_MONGON_INDEX = 13;
	private static final int PRI_BKN09_INDEX = 14;
	private static final int PRI_BUZAI_KIGO_INDEX = 15;
	private static final int PRI_DANSUN_MONGON_INDEX = 16;

	private static final int YOBI_INDEX = 17;

	public List<NyukoAPI> getAllImportingItems(long lastRetrieveTime, String sokoCd)
	{
		List<Nyuko> allImportingItems = this.nyukoDAO
			.getAllImportingItems(lastRetrieveTime, sokoCd);
		List<NyukoAPI> result = new ArrayList<NyukoAPI>();
		for (Nyuko item : allImportingItems)
		{
			result.add(new NyukoAPI(item));
		}
		return result;
	}

	public boolean updateStatusItem(String id)
	{
		return false;
	}

	public List<NyukoAPI> getAllImportedItems(long fromDate, long toDate, String sokoCd)
	{
		List<Nyuko> allImportedItems = this.nyukoDAO.getAllImportedItems(fromDate, toDate, sokoCd);
		List<NyukoAPI> result = new ArrayList<NyukoAPI>();
		for (Nyuko item : allImportedItems)
		{
			result.add(new NyukoAPI(item));
		}
		return result;
	}

	public void doUpload(MultipartFile uploadFile, String sokoCd) throws SokokanriException
	{
		byte[] byteArray;
		BufferedReader bufferedReader;
		try
		{
			byteArray = uploadFile.getBytes();
			bufferedReader = new BufferedReader(
				new InputStreamReader(new ByteArrayInputStream(byteArray), "SJIS"));
		}
		catch (IOException ex)
		{
			throw new SokokanriException(ex);
		}
		Locale locale = LocaleContextHolder.getLocale();
		if (byteArray.length == 0)
		{
			throw new SokokanriException(SokokanriMessage.getErrorSelectFileToUpload(locale));
		}
		List<Nyuko> updates = new ArrayList<Nyuko>();
		List<Nyuko> adds = new ArrayList<Nyuko>();
		List<Nyuko> deletes = new ArrayList<Nyuko>();
		List<String> deleteSzNos = new ArrayList<String>();
		Set<String> existed = new HashSet<String>();
		Set<String> nonExisted = new HashSet<String>();
		Set<String> notFoundOrder = new HashSet<String>();
		String line;
		try
		{
			while ((line = bufferedReader.readLine()) != null)
			{
				if (line.length() != 240)
				{
					logger.debug("All lines must length 240 characters");
					throw new SokokanriException(
						SokokanriMessage.getErrorUnexpectedUploadError(locale));
				}
				Nyuko nyuko = new Nyuko();
				NyukoId id = new NyukoId();
				id.setSokoCd(sokoCd);
				nyuko.setId(id);
				String szNo;
				String uploadOrder;
				try
				{
					List<String> listWord = this.splitQS1(line);
					uploadOrder = listWord.get(UPLOAD_ORDER_INDEX);
					nyuko.setSagyoDate(ConvertDate(listWord.get(SAGYO_DATE_INDEX)));
					nyuko.setSojoNo(listWord.get(SOJO_NO_INDEX));
					nyuko.setSeq(listWord.get(SEQ_INDEX));
					szNo = listWord.get(SZ_NO_INDEX);
					id.setSzNo(szNo);
					nyuko.setBkn09(listWord.get(BKN09_INDEX));
					nyuko.setBuzaiKigo(listWord.get(BUZAI_KIGO_INDEX));
					nyuko.setZaisitu(listWord.get(ZAISITU_INDEX));
					nyuko.setDansunMongon(listWord.get(DANSUN_MONGON_INDEX));
					nyuko.setNagasa(Integer.parseInt(listWord.get(NAGASA_INDEX)));
					nyuko.setZaikoInzu(Integer.parseInt(listWord.get(ZAIKO_INZU_INDEX)));
					nyuko.setNagasaTani(listWord.get(NAGASA_TANI_INDEX));
					nyuko.setKakoMongon(listWord.get(KAKO_MONGON_INDEX));
					nyuko.setPriBkn09(listWord.get(PRI_BKN09_INDEX));
					nyuko.setPriBuzaiKigo(listWord.get(PRI_BUZAI_KIGO_INDEX));
					nyuko.setPriDansunMongon(listWord.get(PRI_DANSUN_MONGON_INDEX));
					nyuko.setYobi(listWord.get(YOBI_INDEX));
				}
				catch (Exception ex)
				{
					logger.debug(ex);
					throw new SokokanriException(
						SokokanriMessage.getErrorUnexpectedUploadError(locale));
				}
				Nyuko item = this.nyukoDAO.getItemById(szNo, sokoCd);
				if (UPLOAD_ORDER.INSERT.equals(uploadOrder))
				{
					if (item == null)
					{
						// find in the temp table and delete corresponding record
						Trans tran = this.transDAO.existInTempTable(szNo, sokoCd);
						if (tran != null)
						{
							// this nyuko is already imported -> set status of
							// current nyuko to 1
							deleteSzNos.add(szNo);
							if (nyuko.existInList(adds))
							{
								//duplicated in file
								existed.add(szNo);
							}
							else
							{
								nyuko.setStatus(1);
								nyuko.setNykoDate(tran.getNykoDate());
								nyuko.setCreateDate(new Date());
								adds.add(nyuko);
							}
						}
						else
						{
							if (nyuko.existInList(adds))
							{
								//duplicated in file
								existed.add(szNo);
							}
							else
							{
								nyuko.setCreateDate(new Date());
								adds.add(nyuko);
							}
						}

						continue;
					}
					//duplicated in DB
					existed.add(szNo);
				}
				else if (UPLOAD_ORDER.DELETE.equals(uploadOrder))
				{
					if (item == null || item.getDeleteFlg() == 1)
					{
						nonExisted.add(szNo);
						continue;
					}
					if (item.getStatus() == 1 || item.getStatus() == 2)
					{
						existed.add(szNo);
						continue;
					}
					deletes.add(item);
				}
				else if (UPLOAD_ORDER.UPDATE.equals(uploadOrder))
				{
					if (item == null || item.getDeleteFlg() == 1)
					{
						nonExisted.add(szNo);
						continue;
					}
					if (item.getStatus() == 1 || item.getStatus() == 2)
					{
						existed.add(szNo);
						continue;
					}
					nyuko.setUpdateDate(new Date());
					updates.add(nyuko);
				}
				else
				{
					notFoundOrder.add(uploadOrder);
				}
			}
		}
		catch (IOException ex)
		{
			throw new SokokanriException(SokokanriMessage.getErrorUnexpectedUploadError(locale));
		}

		// process update data (insert, update, delete)
		try
		{
			this.updateToDB(updates, adds, deletes, deleteSzNos, sokoCd);
		}
		catch (Exception ex)
		{
			logger.debug(ex);
			throw new SokokanriException(SokokanriMessage.getErrorUnexpectedUploadError(locale));
		}
		//we store the items successfully, but still show the errors if exist
		this.handleError(locale, existed, nonExisted, notFoundOrder);
	}

	private void updateToDB(
		List<Nyuko> updates,
		List<Nyuko> adds,
		List<Nyuko> deletes,
		List<String> deleteSzNos,
		String sokoCd) throws SokokanriException
	{
		for (Nyuko item : adds)
		{
			this.nyukoDAO.add(item);
		}
		for (Nyuko item : updates)
		{
			this.nyukoDAO.update(item);
		}
		for (Nyuko item : deletes)
		{
			this.nyukoDAO.delete(item);
		}
		for (String item : deleteSzNos)
		{
			this.transDAO.deleteInTempTable(item, sokoCd);
		}
	}

	private void handleError(
		Locale locale,
		Set<String> existed,
		Set<String> nonExisted,
		Set<String> notFoundOrder) throws SokokanriException
	{
		StringBuilder errorMesssages = new StringBuilder();
		boolean hasError = false;
		if (!existed.isEmpty())
		{
			hasError = true;
			errorMesssages.append(SokokanriMessage.getItemAlreadyExist(existed, locale));
		}
		if (!nonExisted.isEmpty())
		{
			hasError = true;
			errorMesssages.append("<br/>");
			errorMesssages.append(SokokanriMessage.getItemNotExist(nonExisted, locale));
		}
		if (!notFoundOrder.isEmpty())
		{
			hasError = true;
			errorMesssages.append("<br/>");
			errorMesssages
				.append(SokokanriMessage.getErrorNotFoundUploadOrder(notFoundOrder, locale));
		}
		if (hasError)
		{
			throw new SokokanriException(errorMesssages.toString());
		}
	}

	private Date ConvertDate(String dateString) throws SokokanriException
	{
		Date date = null;
		if (dateString.length() == 8)
		{
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
			try
			{
				date = simpleDateFormat.parse(dateString);
			}
			catch (ParseException ex)
			{
				logger.debug(ex.getMessage());
				throw new SokokanriException(
					SokokanriMessage
						.getErrorUnexpectedUploadError(LocaleContextHolder.getLocale()));
			}
		}
		return date;
	}

	private List<String> splitQS1(String line)
	{
		List<String> list = new ArrayList<String>();

		char[] arr = line.toCharArray();
		List<String> listChar = new ArrayList<String>();
		for (char c : arr)
		{
			listChar.add(c + "");
		}
		int[] arrNumber = { 3, 1, 8, 9, 2, 15, 10, 12, 12, 20, 5, 5, 1, 30, 10, 12, 20, 65 };// QS1
		for (int number : arrNumber)
		{
			addString(number, listChar, list);
		}
		return list;

	}

	public void addString(int length, List<String> listChar, List<String> listWord)
	{
		String s = "";
		for (int i = 0; i < length; i++)
		{
			if (listChar.isEmpty())
			{
				break;
			}
			s += listChar.get(0);
			listChar.remove(0);
		}
		listWord.add(s);
	}

	public List<Nyuko> getImportingItems(
		SearchCondition searchCondition,
		SortCondition sortCondition,
		Integer offset,
		Integer maxResult,
		Integer status,
		String sokoCd)
	{
		return this.nyukoDAO
			.getImportingItems(searchCondition, sortCondition, offset, maxResult, status, sokoCd);
	}

	public int getCountImportingItems(
		SearchCondition searchCondition,
		SortCondition sortCondition,
		Integer status,
		String sokoCd)
	{
		return this.nyukoDAO.getCountImportingItems(searchCondition, sortCondition, status, sokoCd);
	}

	public void importItem(String id, String sokoCd) throws SokokanriException
	{
		Nyuko item = this.nyukoDAO.getItemById(id, sokoCd);
		// no item with passed id
		if (item == null)
		{
			Trans trans = new Trans();
			trans.setId(new NyukoId(id, sokoCd));
			Date currentDate = new Date();
			trans.setCreateDate(currentDate);
			trans.setNykoDate(currentDate);
			trans.setStatus(1);
			trans.setDeleteFlg(0);
			try
			{
				this.transDAO.add(trans);
			}
			catch (Exception ex)
			{
				logger.debug("Cannot import the item");
				logger.debug(ex);
				throw new SokokanriException(
					SokokanriMessage.getItemImportedBefore(id, LocaleContextHolder.getLocale()));
			}
			return;
		}
		// this item is already imported or exported
		if (item.getStatus() == 1 || item.getStatus() == 2)
		{
			throw new SokokanriException(
				SokokanriMessage.getItemImportedBefore(id, LocaleContextHolder.getLocale()));
		}
		item.setNykoDate(new Date());
		item.setStatus(1);
		this.nyukoDAO.update(item);
	}

	public List<VBrand> getAllBuzaiKigo(String sokoCd)
	{
		return this.nyukoDAO.getAllBuzaiKigo(sokoCd);
	}

	public List<VKakoMongon> getAllKakoMongon(String sokoCd)
	{
		return this.nyukoDAO.getAllKakoMongon(sokoCd);
	}

	public List<VZaisitu> getAllZaisitu(String sokoCd)
	{
		return this.nyukoDAO.getAllZaisitu(sokoCd);
	}

	public void setExportedFlg(Nyuko item)
	{
		item.setExportedFlg(1);
		try
		{
			this.nyukoDAO.update(item);
		}
		catch (SokokanriException ex)
		{
			logger.debug("Cannot set exported flag: ");
			logger.debug(ex);
		}
	}

	public List<Nyuko> getItemsToExport(SearchCondition searchCondition, String sokoCd)
	{
		return this.nyukoDAO.getItemsToExport(searchCondition, sokoCd);
	}

}
