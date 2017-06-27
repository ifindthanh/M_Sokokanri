package vn.com.nsmv.dao;

import java.util.List;

import vn.com.nsmv.common.SokokanriException;
import vn.com.nsmv.entity.Item;
import vn.com.nsmv.javabean.SearchCondition;
import vn.com.nsmv.javabean.SortCondition;

public interface ItemDAO {
	public void add(Item item) throws SokokanriException;
	public List<Item> getItemsInCategory(Long categoryId) throws SokokanriException;
	public void saveOrUpdate(Item item) throws SokokanriException;
	public void deleteById(Long id) throws SokokanriException;
	public Item findById(Long id) throws SokokanriException;
	public List<Item> getAllItems(SearchCondition searchCondition, SortCondition sortCondition, Integer offset,
        Integer maxResults) throws SokokanriException;
	public int countAllItems(SearchCondition searchCondition) throws SokokanriException;
}
