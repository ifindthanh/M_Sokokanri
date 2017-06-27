package vn.com.nsmv.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import vn.com.nsmv.common.SokokanriException;
import vn.com.nsmv.entity.Bill;
import vn.com.nsmv.entity.Category;
import vn.com.nsmv.entity.Item;
import vn.com.nsmv.javabean.SearchCondition;
import vn.com.nsmv.javabean.SortCondition;
import vn.com.nsmv.javabean.UploadBean;

public interface OrdersService {
	public Long createOrder(Category order) throws SokokanriException;
	public List<Item> getItemsInCategory(Long categoryId) throws SokokanriException;
	public Category getCategory(Long categoryId) throws SokokanriException;
	public void saveOrder(Category category) throws SokokanriException;
	public void deleteItemById(Long id) throws SokokanriException;
	public List<Item> getAllOrders(SearchCondition searchCondition, SortCondition sortCondition, Integer offset, Integer maxResults) throws SokokanriException;
	public List<Bill> getAllBills(SearchCondition searchCondition, Integer offset, Integer maxResults) throws SokokanriException;
	public int countAllItems(SearchCondition searchCondition) throws SokokanriException;
	public int countAllBills(SearchCondition searchCondition) throws SokokanriException;
	public void approve(Long id) throws SokokanriException;
	public void approveOrders(Set<Long> selectedItems) throws SokokanriException;
	public void noteAnOrder(Long id, String content) throws SokokanriException;
	public void saveRealPrice(Long id, Double value) throws SokokanriException;
	public void noteABuyingOrder(Long id, String content) throws SokokanriException;
	public void transferOrder(Long id, String tranferID) throws SokokanriException;
	public void transferOrders(Set<Long> selectedItems, String tranferID) throws SokokanriException;
	public void transferOrdersToVN(Set<Long> selectedItems) throws SokokanriException;
	public void transferOrderToVN(Long id) throws SokokanriException;
	public void importToStorage(Map<Long, List<Item>> classificationOrders) throws SokokanriException;
	public String exportBill(Long selectedItem, boolean toWeb) throws SokokanriException;
	public void exportBill(Set<Long> selectedItems, boolean toWeb) throws SokokanriException;
	public Long doUpload(UploadBean uploadFile) throws SokokanriException;
	public void alreadyToSend(Set<Long> selectedItems) throws SokokanriException;
	public void sendOrders(Set<Long> selectedItems) throws SokokanriException;
	public void deleteOrders(Set<Long> selectedItems) throws SokokanriException;
	public void saveOrder(Item item) throws SokokanriException;
	public Item getItem(Long id) throws SokokanriException;
    public void saveItem(Item item) throws SokokanriException;
	
}
