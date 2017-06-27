package vn.com.nsmv.dao;

import vn.com.nsmv.common.SokokanriException;
import vn.com.nsmv.entity.ItemHistory;

public interface ItemHistoryDAO {
    public void add(ItemHistory itemHistory) throws SokokanriException;
}
