package vn.com.nsmv.service.impl;



import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import vn.com.nsmv.common.SokokanriException;
import vn.com.nsmv.dao.CategoryDAO;
import vn.com.nsmv.dao.ItemDAO;
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
	
	@Transactional(rollbackFor={SokokanriException.class})
	public Long createOrder(Category category) throws SokokanriException {
		User user = new User();
		user.setId(category.getUserId());
		category.setUser(user);
		category.setStatus(0);
		Long categoryId = categoryDAO.add(category);
		
		Iterator<Item> iterator = category.getItems().iterator();
		while (iterator.hasNext()) {
			Item item = iterator.next();
			if (item == null) {
				continue;
			}
			if (item.ignore()) {
				iterator.remove();
				continue;
			}
			item.setCategory(category);
			this.itemDAO.add(item);
		}
		return categoryId;
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


	
}
