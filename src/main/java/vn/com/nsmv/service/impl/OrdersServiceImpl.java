package vn.com.nsmv.service.impl;



import java.util.Iterator;
import java.util.List;

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
		for (Item item : category.getItems()) {
			if (item.ignore()) {
				category.removeItem(item);
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
	
}
