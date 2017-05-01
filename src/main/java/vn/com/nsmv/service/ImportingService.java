package vn.com.nsmv.service;

import java.util.*;

import org.springframework.web.multipart.*;

import vn.com.nsmv.common.*;
import vn.com.nsmv.entity.*;
import vn.com.nsmv.javabean.*;

public interface ImportingService
{
	public List<NyukoAPI> getAllImportingItems(long lastRetrieveTime, String sokoCd);
	public boolean updateStatusItem(String id);
	public List<NyukoAPI> getAllImportedItems(long fromDate, long toDate, String sokoCd);
	public void doUpload(MultipartFile uploadFile, String sokoCd) throws SokokanriException;
	public List<Nyuko> getImportingItems(
		SearchCondition searchCondition,
		SortCondition sortCondition,
		Integer offset,
		Integer maxResult,
		Integer status,
		String sokoCd);
	public int getCountImportingItems(
		SearchCondition searchCondition,
		SortCondition sortCondition,
		Integer status,
		String sokoCd);
	public void importItem(String id, String sokoCd) throws SokokanriException;
	public List<VBuzaiKigo> getAllBuzaiKigo(String sokoCd);
	public List<VKakoMongon> getAllKakoMongon(String sokoCd);
	public List<VZaisitu> getAllZaisitu(String sokoCd);
	public void setExportedFlg(Nyuko item);
	public List<Nyuko> getItemsToExport(SearchCondition filter, String sokoCd);
}
