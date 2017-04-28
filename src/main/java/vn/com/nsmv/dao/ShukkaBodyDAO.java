package vn.com.nsmv.dao;

import java.util.*;

import vn.com.nsmv.common.*;
import vn.com.nsmv.entity.*;
import vn.com.nsmv.javabean.*;

/**
 */
public interface ShukkaBodyDAO
{
	public List<ShukkaBody> getAllExportingItems(long lastRetrieveTime, String sokoCd);

	public List<ShukkaBody> getAllExportedItems(long fromDate, long toDate, String sokoCd);

	public void update(ShukkaBody shukkaBody) throws SokokanriException;

	public int countExportingItems(
		ExportingSearchCondition searchCondition,
		SortCondition sortCondition,
		int status,
		String sokoCd);

	public void add(ShukkaBody shukkaBody);

	public ShukkaBody getById(String bodyId);

	public List<ShukkaBody> getBodyByHeader(String sagyoDate, String sojoNo, String sokoCd);
	public Long countItemByHeader(String sagyoDate, String sojoNo, String sokoCd);
	public Long countExportingItems(String sokoCd) throws SokokanriException;
	public Long countExportedToday(String sokoCd) throws SokokanriException;
	public Set<ShukkaHeadId> getShukkaHeadIds(
		ExportingSearchCondition searchCondition,
		SortCondition sortCondition,
		Integer offset,
		Integer maxResults,
		int status,
		String sokoCd);
	public ShukkaBody getShukkaBody(
		String sagyoDate,
		String sojoNo,
		String dataKbn,
		Integer renban,
		String sokoCd);

	public void delete(ShukkaBody body);

}
