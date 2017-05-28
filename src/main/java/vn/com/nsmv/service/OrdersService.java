package vn.com.nsmv.service;

import java.util.List;
import java.util.Set;

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
	public void approve(Long id) throws SokokanriException;
	public void approveOrders(Set<Long> selectedItems) throws SokokanriException;
	public void noteAnOrder(Long id, String content) throws SokokanriException;
	public void saveRealPrice(Long id, Double value) throws SokokanriException;
	public void noteABuyingOrder(Long id, String content) throws SokokanriException;
	public void transferOrder(Long id, String tranferID) throws SokokanriException;
	public void transferOrders(Set<Long> selectedItems, String tranferID) throws SokokanriException;
}
