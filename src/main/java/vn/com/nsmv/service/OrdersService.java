package vn.com.nsmv.service;

import java.util.List;

import vn.com.nsmv.common.SokokanriException;
import vn.com.nsmv.entity.Category;
import vn.com.nsmv.entity.Item;
import vn.com.nsmv.javabean.SearchCondition;
import vn.com.nsmv.javabean.SortCondition;

public interface OrdersService {
	public Long createOrder(Category order) throws SokokanriException;
	public List<Item> getItemsInCategory(Long categoryId) throws SokokanriException;
	public Category getCategory(Long categoryId) throws SokokanriException;
	public void saveOrder(Category category) throws SokokanriException;
	public void deleteItemById(Long id) throws SokokanriException;
	public List<Category> getAllOrders(SearchCondition searchCondition, SortCondition sortCondition, Integer offset, Integer maxResults) throws SokokanriException;
	public int countAllOrders(SearchCondition searchCondition) throws SokokanriException;
}
