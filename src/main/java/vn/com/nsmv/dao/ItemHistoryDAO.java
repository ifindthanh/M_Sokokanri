package vn.com.nsmv.dao;

import java.util.List;

import vn.com.nsmv.common.SokokanriException;
import vn.com.nsmv.entity.ItemHistory;
import vn.com.nsmv.javabean.SearchCondition;

public interface ItemHistoryDAO {
    public void add(ItemHistory itemHistory) throws SokokanriException;

    public List<ItemHistory> getAllHistory(SearchCondition searchCondition, Integer maxResults, Integer offset) throws SokokanriException;
}
