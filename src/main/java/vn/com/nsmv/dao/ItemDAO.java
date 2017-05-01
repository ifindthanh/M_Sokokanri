package vn.com.nsmv.dao;

import vn.com.nsmv.common.SokokanriException;
import vn.com.nsmv.entity.Item;

public interface ItemDAO {
	public void add(Item item) throws SokokanriException;
}
