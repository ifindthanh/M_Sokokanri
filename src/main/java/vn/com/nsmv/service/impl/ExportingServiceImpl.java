package vn.com.nsmv.service.impl;

import java.io.*;
import java.text.*;
import java.util.*;
import java.util.Map.*;

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

/**
 */
@Service("exportingService")
public class ExportingServiceImpl implements ExportingService
{
	@Autowired
	private ShukkaBodyDAO shukkaBodyDAO;
	@Autowired
	private ShukkaHeadDAO shukkaHeadDAO;
	@Autowired
	private NyukoDAO nyukoDAO;

	private static final Logger logger = Logger.getLogger(ImportingServiceImpl.class);
	private static final int UPLOAD_ORDER_INDEX = 1;
	private static final int SAGYO_DATE_INDEX = 2;
	private static final int SOJO_NO_INDEX = 3;
	private static final int DATA_KBN_INDEX = 4;
	// Head
	private static final int SUKKA_YOTEIBI_INDEX = 5;
	private static final int JYUOKA_INDEX = 6;
	private static final int YOBI_HEAD_INDEX = 7;
	// Body
	private static final int BKN09_INDEX = 5;
	private static final int BUZAI_KIGO_INDEX = 6;
	private static final int ZAISITU_INDEX = 7;
	private static final int DANSUN_MONGON_INDEX = 8;
	private static final int NAGASA_INDEX = 9;
	private static final int ZAIKO_INZU_INDEX = 10;
	private static final int NAGASA_TANI_INDEX = 11;
	private static final int KENSYO_INDEX = 12;

	private static final int PRI_BKN09_INDEX = 13;
	private static final int PRI_BUZAI_KIGO_INDEX = 14;
	private static final int PRI_DANSUN_MONGON_INDEX = 15;
	private static final int KAKO_MEISYO_INDEX = 16;
	private static final int YOBI_INDEX = 17;

	public List<ShukkaAPI> getAllExportingBody(long lastRetrieveTime, String sokoCd)
	{
		List<ShukkaBody> shukkaBodys = this.shukkaBodyDAO
			.getAllExportingItems(lastRetrieveTime, sokoCd);
		return this.getShukkaAPI(shukkaBodys, sokoCd);
	}
	public List<ShukkaAPI> getAllExportedBody(long fromDate, long toDate, String sokoCd)
	{
		List<ShukkaBody> shukkaBodys = this.shukkaBodyDAO
			.getAllExportedItems(fromDate, toDate, sokoCd);
		return this.getShukkaAPI(shukkaBodys, sokoCd);
	}

	private List<ShukkaAPI> getShukkaAPI(List<ShukkaBody> shukkaBodys, String sokoCd)
	{
		List<ShukkaAPI> result = new ArrayList<ShukkaAPI>();
		//use caching mechanism to reduce the number of times that we request from DB
		for (ShukkaBody body : shukkaBodys)
		{
			ShukkaHeadId headId = new ShukkaHeadId(body.getSagyoDate(), body.getSojoNo(), sokoCd);
			ShukkaAPI shukkaAPI = this.getHead(result, headId);
			if (shukkaAPI == null)
			{
				ShukkaHead shukkaHead = shukkaHeadDAO.getShukkaHeadById(headId);
				if (shukkaHead == null)
				{
					continue;
				}
				shukkaAPI = new ShukkaAPI(new ShukkaHeadBean(shukkaHead));
				result.add(shukkaAPI);
			}
			shukkaAPI.addBody(new ShukkaBodyBean(body));
		}
		return result;
	}

	private final ShukkaAPI getHead(List<ShukkaAPI> shukkaAPIs, ShukkaHeadId headId)
	{
		for (ShukkaAPI item : shukkaAPIs)
		{
			if (item.getShukkaHeadBean().getId().equals(headId))
			{
				return item;
			}
		}
		return null;
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
			throw new SokokanriException("");
		}
		List<ShukkaBody> updateBodies = new ArrayList<ShukkaBody>();
		List<ShukkaBody> insertBodies = new ArrayList<ShukkaBody>();
		List<ShukkaBody> deleteBodies = new ArrayList<ShukkaBody>();
		List<ShukkaHead> updateHeads = new ArrayList<ShukkaHead>();
		List<ShukkaHead> insertHeads = new ArrayList<ShukkaHead>();
		List<ShukkaHead> deleteHeads = new ArrayList<ShukkaHead>();
		Set<String> existed = new HashSet<String>();
		Set<String> nonExisted = new HashSet<String>();
		Set<String> notFoundOrder = new HashSet<String>();
		String uploadOrder = "";
		String line;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		try
		{
			while ((line = bufferedReader.readLine()) != null)
			{
				if (line.length() != 160)
				{
					logger.debug("All lines must length 160 characters");
					throw new SokokanriException();
				}
				Date currentDate = new Date();
				if ("H1".equals(line.substring(21, 23)))
				{
					//head
					ShukkaHead shukkaHead = new ShukkaHead();
					try
					{
						List<String> listWord = this.splitQS2Head(line);
						uploadOrder = listWord.get(UPLOAD_ORDER_INDEX);
						String sagyoDate = listWord.get(SAGYO_DATE_INDEX);
						String sojoNo = listWord.get(SOJO_NO_INDEX);
						ShukkaHeadId shukkaHeadId = new ShukkaHeadId(sagyoDate, sojoNo, sokoCd);

						shukkaHead.setId(shukkaHeadId);
						shukkaHead.setDataKbn(listWord.get(DATA_KBN_INDEX));
						String sukkaYoteibi = listWord.get(SUKKA_YOTEIBI_INDEX);
						//	 if sukkaYoteibi = "00000000", we do not set the value of sukkaYoteibi
						if (!Constants.NON_SET_SUKKAYOTEIBI.equals(sukkaYoteibi))
						{
							try
							{
								shukkaHead.setSukkaYoteibi(simpleDateFormat.parse(sukkaYoteibi));
							}
							catch (Exception ex)
							{
								//do nothing
								logger.debug(ex);
							}
						}
						shukkaHead.setJyuoka(listWord.get(JYUOKA_INDEX));
						shukkaHead.setYobi(listWord.get(YOBI_HEAD_INDEX));
						ShukkaHead temp = this.shukkaHeadDAO.getShukkaHeadById(shukkaHeadId);
						//we compare with cache first
						if (UPLOAD_ORDER.INSERT.equals(uploadOrder))
						{
							if (temp == null)
							{
								//if it is not available in db -> insert to DB
								shukkaHead.setCreateDate(currentDate);
								shukkaHead.setStatus(0);
								shukkaHead.setDeleteFlg(0);
								insertHeads.add(shukkaHead);
								continue;
							}
							existed.add(sagyoDate + "-" + sojoNo);
						}
						else if (UPLOAD_ORDER.UPDATE.equals(uploadOrder))
						{
							if (temp == null
								|| (temp.getDeleteFlg() != null && temp.getDeleteFlg() == 1))
							{
								nonExisted.add(sagyoDate + "-" + sojoNo);
								continue;
							}
							if ((temp.getStatus() != null && temp.getStatus() == 1))
							{
								existed.add(sagyoDate + "-" + sojoNo);
								continue;
							}
							// need to update
							shukkaHead.setCreateDate(temp.getCreateDate());
							shukkaHead.setStatus(temp.getStatus());
							shukkaHead.setDeleteFlg(temp.getDeleteFlg());
							updateHeads.add(shukkaHead);
						}
						else if (UPLOAD_ORDER.DELETE.equals(uploadOrder))
						{
							if (temp == null
								|| (temp.getDeleteFlg() != null && temp.getDeleteFlg() == 1))
							{
								nonExisted.add(sagyoDate + "-" + sojoNo);
								continue;
							}
							if ((temp.getStatus() != null && temp.getStatus() == 1))
							{
								existed.add(sagyoDate + "-" + sojoNo);
								continue;
							}
							deleteHeads.add(shukkaHead);
						}
						else
						{
							notFoundOrder.add(uploadOrder);
						}
					}
					catch (Exception ex)
					{
						logger.debug(ex.getMessage());
						if (SokokanriException.class.isInstance(ex))
						{
							throw new SokokanriException(ex);
						}
						throw new SokokanriException();
					}
				}
				else
				{
					//body
					String sagyoDate;
					String sojoNo;
					String dataKbn;
					Integer zaikoInzu;
					try
					{
						List<String> listWord = this.splitQS2Body(line);
						zaikoInzu = Integer.valueOf(listWord.get(ZAIKO_INZU_INDEX));
						for (int renban = 1; renban <= zaikoInzu; renban++)
						{
							ShukkaBody shukkaBody = new ShukkaBody();
							shukkaBody.setSokoCd(sokoCd);
							uploadOrder = listWord.get(UPLOAD_ORDER_INDEX);
							sagyoDate = listWord.get(SAGYO_DATE_INDEX);
							shukkaBody.setSagyoDate(sagyoDate);
							sojoNo = listWord.get(SOJO_NO_INDEX);
							shukkaBody.setSojoNo(sojoNo);
							dataKbn = listWord.get(DATA_KBN_INDEX);
							shukkaBody.setDataKbn(dataKbn);
							shukkaBody.setBkn09(listWord.get(BKN09_INDEX));
							shukkaBody.setBuzaiKigo(listWord.get(BUZAI_KIGO_INDEX));
							shukkaBody.setZaisitu(listWord.get(ZAISITU_INDEX));
							shukkaBody.setDansunMongon(listWord.get(DANSUN_MONGON_INDEX));
							shukkaBody.setNagasa(Integer.valueOf(listWord.get(NAGASA_INDEX)));
							shukkaBody.setZaikoInzu(zaikoInzu);
							shukkaBody.setNagasaTani(listWord.get(NAGASA_TANI_INDEX));
							shukkaBody.setKensyo(listWord.get(KENSYO_INDEX));
							shukkaBody.setPriBkn09(listWord.get(PRI_BKN09_INDEX));
							shukkaBody.setPriBuzaiKigo(listWord.get(PRI_BUZAI_KIGO_INDEX));
							shukkaBody.setPriDansunMongon(listWord.get(PRI_DANSUN_MONGON_INDEX));
							shukkaBody.setKakoMeisyo(listWord.get(KAKO_MEISYO_INDEX));
							shukkaBody.setYobi(listWord.get(YOBI_INDEX));
							shukkaBody.setRenban(renban);
							// check if exist sagyoDate, sojoNo, dataKbn
							ShukkaBody temp = this.shukkaBodyDAO
								.getShukkaBody(sagyoDate, sojoNo, dataKbn, renban, sokoCd);
							if (UPLOAD_ORDER.INSERT.equals(uploadOrder))
							{
								if (temp == null)
								{
									shukkaBody.setStatus(0);
									shukkaBody.setDeleteFlg(0);
									shukkaBody.setCreateDate(currentDate);
									insertBodies.add(shukkaBody);
									continue;
								}
								existed
									.add(sagyoDate + "-" + sojoNo + "-" + dataKbn + "-" + renban);
							}
							else if (UPLOAD_ORDER.UPDATE.equals(uploadOrder))
							{
								if (temp == null
									|| (temp.getDeleteFlg() != null && temp.getDeleteFlg() == 1))
								{
									nonExisted.add(
										sagyoDate + "-" + sojoNo + "-" + dataKbn + "-" + renban);
									continue;
								}
								if ((temp.getStatus() != null && temp.getStatus() == 1))
								{
									existed.add(
										sagyoDate + "-" + sojoNo + "-" + dataKbn + "-" + renban);
									continue;
								}
								//update instead
								shukkaBody.setCreateDate(temp.getCreateDate());
								shukkaBody.setStatus(temp.getStatus());
								shukkaBody.setDeleteFlg(temp.getDeleteFlg());
								shukkaBody.setId(temp.getId());
								shukkaBody.setUpdateDate(currentDate);
								updateBodies.add(shukkaBody);
							}
							else if (UPLOAD_ORDER.DELETE.equals(uploadOrder))
							{
								if (temp == null
									|| (temp.getDeleteFlg() != null && temp.getDeleteFlg() == 1))
								{
									nonExisted.add(
										sagyoDate + "-" + sojoNo + "-" + dataKbn + "-" + renban);
									continue;
								}
								if ((temp.getStatus() != null && temp.getStatus() == 1))
								{
									existed.add(
										sagyoDate + "-" + sojoNo + "-" + dataKbn + "-" + renban);
									continue;
								}
								deleteBodies.add(temp);
							}
							else
							{
								notFoundOrder.add(uploadOrder);
							}
						}
					}
					catch (Exception ex)
					{
						logger.debug(ex.getMessage());
						if (SokokanriException.class.isInstance(ex))
						{
							throw new SokokanriException(ex);
						}
						throw new SokokanriException();
					}
				}
			}
		}
		catch (IOException ex)
		{
			logger.debug(ex);
			throw new SokokanriException();
		}
		try
		{
			this.updateToDB(
				updateBodies,
				insertBodies,
				deleteBodies,
				updateHeads,
				insertHeads,
				deleteHeads);
		}
		catch (Exception ex)
		{
			logger.debug(ex);
			throw new SokokanriException();
		}
		//we store the items successfully, but still show the errors if exist
		this.handleError(locale, existed, nonExisted, notFoundOrder);
	}
	private void updateToDB(
		List<ShukkaBody> updateBodies,
		List<ShukkaBody> insertBodies,
		List<ShukkaBody> deleteBodies,
		List<ShukkaHead> updateHeads,
		List<ShukkaHead> insertHeads,
		List<ShukkaHead> deleteHeads) throws SokokanriException
	{
		for (ShukkaHead head : updateHeads)
		{
			this.shukkaHeadDAO.update(head);
		}
		for (ShukkaHead head : insertHeads)
		{
			this.shukkaHeadDAO.add(head);
		}
		for (ShukkaHead head : deleteHeads)
		{
			this.shukkaHeadDAO.delete(head);
		}
		for (ShukkaBody body : updateBodies)
		{
			this.shukkaBodyDAO.update(body);
		}
		for (ShukkaBody body : insertBodies)
		{
			body.setId(null);
			this.shukkaBodyDAO.add(body);
		}
		for (ShukkaBody body : deleteBodies)
		{
			this.shukkaBodyDAO.delete(body);
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
			errorMesssages.append("");
		}
		if (!nonExisted.isEmpty())
		{
			hasError = true;
			errorMesssages.append("<br/>");
			errorMesssages.append("");
		}
		if (!notFoundOrder.isEmpty())
		{
			hasError = true;
			errorMesssages.append("<br/>");
			errorMesssages
				.append("");
		}
		if (hasError)
		{
			throw new SokokanriException(errorMesssages.toString());
		}
	}
	public List<ShukkaAPI> getExportingItems(
		ExportingSearchCondition searchCondition,
		SortCondition sortCondition,
		Integer offset,
		Integer maxResults,
		int status,
		String sokoCd)
	{
		Set<ShukkaHeadId> shukkaHeadIds = this.shukkaBodyDAO
			.getShukkaHeadIds(searchCondition, sortCondition, offset, maxResults, status, sokoCd);
		return this.getShukkaAPIs(shukkaHeadIds, status, true, sokoCd);
	}

	public int getCountExportingItems(
		ExportingSearchCondition searchCondition,
		SortCondition sortCondition,
		int status,
		String sokoCd)
	{
		return this.shukkaBodyDAO
			.countExportingItems(searchCondition, sortCondition, status, sokoCd);
	}

	private List<String> splitQS2Head(String line)
	{
		List<String> list = new ArrayList<String>();

		char[] arr = line.toCharArray();
		List<String> listChar = new ArrayList<String>();
		for (char c : arr)
		{
			listChar.add(c + "");
		}
		int[] arrNumber = { 3, 1, 8, 9, 2, 8, 20, 109 };// QS2-head
		for (int number : arrNumber)
		{
			addString(number, listChar, list);
		}
		return list;

	}

	private List<String> splitQS2Body(String line)
	{
		List<String> list = new ArrayList<String>();

		char[] arr = line.toCharArray();
		List<String> listChar = new ArrayList<String>();
		for (char c : arr)
		{
			listChar.add(c + "");
		}
		int[] arrNumber = { 3, 1, 8, 9, 2, 10, 12, 12, 20, 5, 5, 1, 1, 10, 12, 20, 4, 25 };// QS2-body
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
	public void exportItems(ExportParameter exportParameter, String sokoCd)
		throws SokokanriException
	{
		Map<ShukkaBody, Nyuko> exportingBody = new HashMap<ShukkaBody, Nyuko>();
		int count = 0;
		ShukkaHead shukkaHead = this.shukkaHeadDAO.getShukkaHeadById(
			new ShukkaHeadId(exportParameter.getSagyoDate(), exportParameter.getSojoNo(), sokoCd));
		Locale locale = LocaleContextHolder.getLocale();
		if (shukkaHead == null)
		{
			throw new SokokanriException();
		}
		if (shukkaHead.getStatus() != null && shukkaHead.getStatus() == 1)
		{
			throw new SokokanriException();
		}
		List<ShukkaBody> shukkaBodys = this.shukkaBodyDAO
			.getBodyByHeader(exportParameter.getSagyoDate(), exportParameter.getSojoNo(), sokoCd);

		Map<String, String> exportingNyuko = new HashMap<String, String>();
		for (ExportItem item : exportParameter.getItems())
		{
			count++;
			String bodyId = item.getBodyId();
			if (!this.containsInHeader(bodyId, shukkaBodys))
			{
				throw new SokokanriException();
			}
			ShukkaBody shukkaBody = this.shukkaBodyDAO.getById(bodyId);
			if (shukkaBody == null
				|| (shukkaBody.getDeleteFlg() != null && shukkaBody.getDeleteFlg() == 1))
			{
				throw new SokokanriException();
			}

			String szNo = item.getSzNo();
			if (exportingNyuko.containsKey(szNo)
				&& !Utils.stringCompare(exportingNyuko.get(szNo), bodyId))
			{
				throw new SokokanriException();
			}
			exportingNyuko.put(szNo, bodyId);

			if (shukkaBody.getStatus() == 1)
			{
				continue;
			}
			//nyuko must be imported and not exported
			Nyuko nyuko = this.nyukoDAO.getItemById(szNo, sokoCd);
			if (nyuko == null || nyuko.getStatus() != 1)
			{
				//note that Nyuko can be in Trans table, but we do not handle now.
				throw new SokokanriException();
			}

			//it is validated (by client) => change state to 1.
			shukkaBody.setSzNo(szNo);
			shukkaBody.setUpdateDate(new Date());
			shukkaBody.setShukkaDate(new Date());
			shukkaBody.setStatus(1);
			exportingBody.put(shukkaBody, nyuko);

		}
		if (this.shukkaBodyDAO.countItemByHeader(
			exportParameter.getSagyoDate(),
			exportParameter.getSojoNo(),
			sokoCd) != count)
		{
			throw new SokokanriException();
		}
		try
		{
			Iterator<Entry<ShukkaBody, Nyuko>> iterator = exportingBody.entrySet().iterator();
			while (iterator.hasNext())
			{
				Entry<ShukkaBody, Nyuko> entry = iterator.next();
				ShukkaBody shukkaBody = entry.getKey();
				//update status of shukka body
				this.shukkaBodyDAO.update(shukkaBody);
				//change status of Nyuko to 2
				Nyuko nyuko = entry.getValue();
				nyuko.setStatus(2);
				nyuko.setUpdateDate(new Date());
				this.nyukoDAO.update(nyuko);
			}
			//update status of shukka header
			shukkaHead.setStatus(1);
			shukkaHead.setUpdateDate(new Date());
			this.shukkaHeadDAO.update(shukkaHead);
		}
		catch (Exception ex)
		{
			throw new SokokanriException();
		}
	}
	private boolean containsInHeader(String bodyId, List<ShukkaBody> shukkaBodies)
	{
		for (ShukkaBody shukkaBody : shukkaBodies)
		{
			if (Utils.stringCompare(bodyId, String.valueOf(shukkaBody.getId())))
			{
				shukkaBodies.remove(shukkaBody);
				return true;
			}
		}
		return false;
	}

	public SokokanriInfor getInfor(String sokoCd) throws SokokanriException
	{
		SokokanriInfor infor = new SokokanriInfor();
		infor.setZaiko(this.nyukoDAO.countImportedItems(sokoCd));
		infor.setNyukoYotei(this.nyukoDAO.countImportingItems(sokoCd));
		infor.setNyukoJiseki(this.nyukoDAO.countImportedToday(sokoCd));
		infor.setShukkaYotei(this.shukkaBodyDAO.countExportingItems(sokoCd));
		infor.setShkkaJiseki(this.shukkaBodyDAO.countExportedToday(sokoCd));
		return infor;
	}
	public List<ShukkaBody> getBodyByHeader(ShukkaHeadId shukkaHeadId, String sokoCd)
	{
		return this.shukkaBodyDAO
			.getBodyByHeader(shukkaHeadId.getSagyoDate(), shukkaHeadId.getSojoNo(), sokoCd);
	}

	public List<ShukkaAPI> getShukkaAPIs(
		Set<ShukkaHeadId> shukkaHeadIds,
		int status,
		boolean includeExported,
		String sokoCd)
	{
		List<ShukkaAPI> result = new ArrayList<ShukkaAPI>();
		for (ShukkaHeadId shukkaHeadId : shukkaHeadIds)
		{
			ShukkaHead shukkaHead = this.shukkaHeadDAO.getShukkaHeadById(shukkaHeadId);
			if (shukkaHead == null)
			{
				continue;
			}
			if (shukkaHead.getExportedFlg() != null && shukkaHead.getExportedFlg() == 1
				&& !includeExported)
			{
				continue;
			}
			List<ShukkaBody> exportedItems = this.shukkaBodyDAO
				.getBodyByHeader(shukkaHeadId.getSagyoDate(), shukkaHeadId.getSojoNo(), sokoCd);
			List<ShukkaBodyBean> bodyBeans = new ArrayList<ShukkaBodyBean>();
			for (ShukkaBody item : exportedItems)
			{
				if (item.getStatus() == null || item.getStatus() != status)
				{
					continue;
				}
				ShukkaBodyBean shukkaBodyBean = new ShukkaBodyBean(item);
				try
				{
					Nyuko nyuko = this.nyukoDAO.getItemById(item.getSzNo(), sokoCd);
					if (nyuko != null)
					{
						shukkaBodyBean.setSeq(nyuko.getSeq());
						shukkaBodyBean.setInzuToExport(nyuko.getZaikoInzu());
					}
				}
				catch (SokokanriException ex)
				{
					logger.debug(ex);
				}
				bodyBeans.add(shukkaBodyBean);
			}
			result.add(new ShukkaAPI(new ShukkaHeadBean(shukkaHead), bodyBeans));
		}
		return result;

	}
	public void deleteAll()
	{
		this.nyukoDAO.deleteAll();
	}
	public void setExportedFlg(ShukkaHeadId id, String sokoCd)
	{
		ShukkaHead shukkaHead = this.shukkaHeadDAO.getShukkaHeadById(id);
		if (shukkaHead == null)
		{
			return;
		}
		shukkaHead.setExportedFlg(1);
		this.shukkaHeadDAO.update(shukkaHead);
	}
}
