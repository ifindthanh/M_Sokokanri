package vn.com.nsmv.dao;

import java.util.*;

import vn.com.nsmv.common.*;
import vn.com.nsmv.entity.*;
import vn.com.nsmv.javabean.*;

/**
 */
public interface NyukoDAO
{
	public List<Nyuko> getAllImportingItems(long lastRetrieveTime, String seizoshoKbn);

	public boolean add(Nyuko nyuko);

	public List<Nyuko> getAllImportedItems(long fromDate, long toDate, String seizoshoKbn);

	public List<Nyuko> getImportingItems(
		SearchCondition searchCondition,
		SortCondition sortCondition,
		Integer offset,
		Integer maxResult,
		Integer status,
		String seizoshoKbn);

	public int getCountImportingItems(
		SearchCondition searchCondition,
		SortCondition sortCondition,
		Integer status,
		String seizoshoKbn);

	public void update(Nyuko nyuko) throws SokokanriException;

	public Nyuko getItemById(String id, String sokoCd) throws SokokanriException;

	public List<VBuzaiKigo> getAllBuzaiKigo(String sokoCd);

	public List<VKakoMongon> getAllKakoMongon(String sokoCd);

	public List<VZaisitu> getAllZaisitu(String sokoCd);
	public Long countImportedItems(String sokoCd) throws SokokanriException;

	public Long countImportingItems(String sokoCd) throws SokokanriException;

	public Long countImportedToday(String sokoCd) throws SokokanriException;

	public void deleteAll();

	public void delete(Nyuko item);

	public List<Nyuko> getItemsToExport(SearchCondition searchCondition, String seizoshoKbn);
}
