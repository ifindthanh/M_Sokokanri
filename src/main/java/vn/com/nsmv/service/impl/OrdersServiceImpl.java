package vn.com.nsmv.service.impl;



import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import vn.com.nsmv.common.SokokanriException;
import vn.com.nsmv.common.Utils;
import vn.com.nsmv.dao.BillDAO;
import vn.com.nsmv.dao.CategoryDAO;
import vn.com.nsmv.dao.ItemDAO;
import vn.com.nsmv.entity.Bill;
import vn.com.nsmv.entity.Category;
import vn.com.nsmv.entity.Item;
import vn.com.nsmv.entity.User;
import vn.com.nsmv.javabean.SearchCondition;
import vn.com.nsmv.javabean.SortCondition;
import vn.com.nsmv.service.OrdersService;

@Service("ordersService")
@EnableTransactionManagement
public class OrdersServiceImpl implements OrdersService {

	@Autowired
	private ItemDAO itemDAO;
	@Autowired
	private CategoryDAO categoryDAO;
	@Autowired
	private BillDAO billDAO;
	
	@Transactional(rollbackFor={SokokanriException.class})
	public Long createOrder(Category category) throws SokokanriException {
		User user = new User();
		user.setId(category.getUserId());
		category.setUser(user);
		category.setStatus(0);
		Long categoryId = categoryDAO.add(category);
		
		Iterator<Item> iterator = category.getItems().iterator();
		boolean hasRecord = false;
		while (iterator.hasNext()) {
			Item item = iterator.next();
			
			if (item == null) {
				continue;
			}
			item.validate();
			if (item.ignore()) {
				iterator.remove();
				continue;
			}
			hasRecord= true;
			item.setCategory(category);
			this.itemDAO.add(item);
		}
		if (hasRecord) {
			return categoryId;
		}
		throw new SokokanriException("Đơn hàng phải có ít nhất 1 sản phẩm");
	}

	@Transactional
	public List<Item> getItemsInCategory(Long categoryId) throws SokokanriException {
		return this.itemDAO.getItemsInCategory(categoryId);
	}

	@Transactional
	public Category getCategory(Long categoryId) throws SokokanriException {
		return this.categoryDAO.getById(categoryId);
	}

	@Transactional
	public void saveOrder(Category category) throws SokokanriException {
		User user = new User();
		user.setId(category.getUserId());
		category.setUser(user);
		this.categoryDAO.saveCategory(category);
		Iterator<Item> iterator = category.getItems().iterator();
		while (iterator.hasNext()) {
			Item item = iterator.next();
			if (item == null) {
				continue;
			}
			item.validate();
			if (item.ignore()) {
				iterator.remove();
				continue;
			}
			item.setCategory(category);
			if (item.getId() == null) {
				this.itemDAO.add(item);
			} else {
				this.itemDAO.saveOrUpdate(item);
			}
		}
	}

	public void deleteItemById(Long id) throws SokokanriException {
		this.itemDAO.deleteById(id);
	}

	@Transactional
	public List<Category> getAllOrders(SearchCondition searchCondition, SortCondition sortCondition, Integer offset, Integer maxResults) throws SokokanriException {
		return this.categoryDAO.getAllOrders(searchCondition, sortCondition, offset, maxResults);
	}
	
	@Transactional
	public List<Bill> getAllBills(SearchCondition searchCondition, Integer offset, Integer maxResults) throws SokokanriException {
		return this.billDAO.getAllBills(searchCondition, offset, maxResults);
	}
	
	@Transactional
	public int countAllBills(SearchCondition searchCondition) throws SokokanriException {
		return this.billDAO.countAllBills(searchCondition);
	}

	@Transactional
	public int countAllOrders(SearchCondition searchCondition) throws SokokanriException {
		return this.categoryDAO.countAllOrders(searchCondition);
	}

	@Transactional
	public void approve(Long id) throws SokokanriException {
		this.approveAnOrder(id);
	}

	private void approveAnOrder(Long id) throws SokokanriException {
		Category category = this.categoryDAO.getById(id);
		if (category == null) {
			throw new SokokanriException("Đơn hàng không tồn tại");
		}
		if (category.getStatus() == null || (category.getStatus() != 0 && category.getStatus() != -1)) {
			throw new SokokanriException("Không thể duyệt đơn hàng đã chọn.");
		}
		category.setStatus(1);
		this.categoryDAO.saveCategory(category);
	}

	@Transactional
	public void approveOrders(Set<Long> selectedItems) throws SokokanriException {
		for (Long id : selectedItems) {
			this.approveAnOrder(id);
		}
	}

	@Transactional
	public void noteAnOrder(Long id, String content) throws SokokanriException {
		Category category = this.categoryDAO.getById(id);
		if (category == null) {
			throw new SokokanriException("Đơn hàng không tồn tại");
		}
		if (category.getStatus() == null || (category.getStatus() != 0 && category.getStatus() != -1)) {
			throw new SokokanriException("Không thể ghi chú đơn hàng đã chọn.");
		}
		category.setStatus(-1);
		this.categoryDAO.saveCategory(category);
	}

	@Transactional
	public void saveRealPrice(Long id, Double value) throws SokokanriException {
		Item item = this.itemDAO.findById(id);
		if (item == null) {
			return;
		}
		item.setRealPrice(value);
		this.itemDAO.saveOrUpdate(item);
	}

	@Transactional
	public void noteABuyingOrder(Long id, String content) throws SokokanriException {
		Category category = this.categoryDAO.getById(id);
		if (category == null) {
			throw new SokokanriException("Đơn hàng không tồn tại");
		}
		if (category.getStatus() == null || (category.getStatus() != 1 && category.getStatus() != -2)) {
			throw new SokokanriException("Không thể ghi chú đơn hàng đã chọn.");
		}
		category.setStatus(-2);
		this.categoryDAO.saveCategory(category);
		
	}

	@Transactional
	public void transferOrder(Long id, String tranferID) throws SokokanriException {
		this.transferAnOrder(id, tranferID);
	}

	private void transferAnOrder(Long id, String tranferID) throws SokokanriException {
		Category category = this.categoryDAO.getById(id);
		if (category == null) {
			throw new SokokanriException("Đơn hàng không tồn tại");
		}
		if (category.getStatus() == null || (category.getStatus() != 2)) {
			throw new SokokanriException("Không thể chuyển trạng thái đơn hàng đã chọn.");
		}
		category.setTransferId(tranferID);
		category.setStatus(3);
		this.categoryDAO.saveCategory(category);
	}

	@Transactional
	public void transferOrders(Set<Long> selectedItems, String tranferID) throws SokokanriException {
		for (Long id : selectedItems) {
			this.transferAnOrder(id, tranferID);
		}
		
	}

	@Transactional
	public void transferOrdersToVN(Set<Long> selectedItems) throws SokokanriException {
		for (Long id : selectedItems) {
			this.transferAnOrderToVN(id);
		}
		
	}

	@Transactional
	public void transferOrderToVN(Long id) throws SokokanriException {
		this.transferAnOrderToVN(id);
	}

	private void transferAnOrderToVN(Long id) throws SokokanriException {
		Category category = this.categoryDAO.getById(id);
		if (category == null) {
			throw new SokokanriException("Đơn hàng không tồn tại");
		}
		if (category.getStatus() == null || (category.getStatus() != 3)) {
			throw new SokokanriException("Không thể chuyển trạng thái đơn hàng đã chọn.");
		}
		category.setStatus(4);
		this.categoryDAO.saveCategory(category);
	}

	@Transactional
	public void importToStorage(Map<Long, List<Category>> classificationOrders) throws SokokanriException {
		Bill bill = new Bill();
		bill.setStatus(1);
		Iterator<Entry<Long, List<Category>>> iterator = classificationOrders.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<Long, List<Category>> entry = iterator.next();
			Long billId = this.billDAO.add(bill);
			for (Category item : entry.getValue()) {
				item.setBill(bill);
				item.setStatus(5);
				this.categoryDAO.saveCategory(item);
			}
		}
	}

	@Transactional
	public String exportBill(Long selectedItem, boolean toWeb) throws SokokanriException {
		Bill bill = this.billDAO.getById(selectedItem);
		if (bill == null || bill.getCategories() == null || bill.getCategories().isEmpty()) {
			throw new SokokanriException("Hóa đơn không tồn tại");
		}
		StringBuilder content = new StringBuilder("Chi tiết hóa đơn :" + Utils.getFormattedId(bill.getId(), 7));
		String breakLine = System.lineSeparator();
		if (toWeb) {
			breakLine = "<br />";
		}
		content.append(breakLine);
		content.append("Tên khách hàng: " + bill.getCategories().get(0).getUser().getFullname());
		content.append(breakLine);
		content.append(String.format("%70s", "").replaceAll(" ", "="));
		content.append(breakLine);
		double total = 0;
		for (Category category : bill.getCategories()) {
			content.append("Đơn hàng: " + category.getFormattedId());
			content.append(breakLine);
			for (Item item : category.getItems()) {
				if (toWeb) {
					content.append(String.format("%-50s", item.getName() + ":").replaceAll(" ", "&nbsp;"));
				} else {
					content.append(String.format("%-50s", item.getName() + ":"));
				}
				content.append(item.getRealPrice());
				content.append(breakLine);
				total += item.getRealPrice();
			}
			content.append(breakLine);
			content.append(String.format("%70s", "").replaceAll(" ", "-"));
			content.append(breakLine);
		}
		content.append(String.format("%70s", "").replaceAll(" ", "="));
		content.append(breakLine);
		if (toWeb) {
			content.append(String.format("%-50s", "Tổng tiền:").replaceAll(" ", "&nbsp;"));
		} else {
			content.append(String.format("%-50s", "Tổng tiền:"));
		}
		content.append(total);
		content.append(breakLine);
		return content.toString();
	}

	@Transactional
	public void exportBill(Set<Long> selectedItems, boolean toWeb) throws SokokanriException {
		for (Long billId : selectedItems) {
			Bill bill = this.billDAO.getById(billId);
			if (bill == null) {
				throw new SokokanriException("Hóa đơn không tồn tại");
			}
			for (Category cart : bill.getCategories()) {
				if (cart.getStatus() == null || (cart.getStatus() != 5)) {
					throw new SokokanriException("Không thể chuyển trạng thái đơn hàng đã chọn.");
				}
				cart.setStatus(6);
				this.categoryDAO.saveCategory(cart);

			}
		}

	}
}
