package vn.com.nsmv.dao;

import vn.com.nsmv.common.*;
import vn.com.nsmv.entity.*;

/**
 */
public interface TransDAO
{
	public boolean add(Trans trans);
	public Trans existInTempTable(String id, String sokoCd);
	public void deleteInTempTable(String id, String sokoCd) throws SokokanriException;
}
