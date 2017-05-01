package vn.com.nsmv.service;

import java.util.*;

import org.springframework.web.multipart.*;

import vn.com.nsmv.common.*;
import vn.com.nsmv.entity.*;
import vn.com.nsmv.javabean.*;

/**
 */
public interface ExportingService
{
	public List<ShukkaAPI> getAllExportingBody(long lastRetrieveTime, String sokoCd);
	public List<ShukkaAPI> getAllExportedBody(long fromDate, long toDate, String sokoCd);
	public void doUpload(MultipartFile uploadFile, String sokoCd) throws SokokanriException;
	public List<ShukkaAPI> getExportingItems(
		ExportingSearchCondition searchCondition,
		SortCondition sortCondition,
		Integer offset,
		Integer maxResults,
		int status,
		String sokoCd);
	public int getCountExportingItems(
		ExportingSearchCondition searchCondition,
		SortCondition sortCondition,
		int status,
		String sokoCd);
	public void exportItems(ExportParameter exportParameter, String sokoCd)
		throws SokokanriException;
	public SokokanriInfor getInfor(String sokoCd) throws SokokanriException;
	public List<ShukkaAPI> getShukkaAPIs(
		Set<ShukkaHeadId> shukkaHeadIds,
		int status,
		boolean includeExported,
		String sokoCd);
	public void deleteAll();
	public void setExportedFlg(ShukkaHeadId id, String sokoCd);
}
