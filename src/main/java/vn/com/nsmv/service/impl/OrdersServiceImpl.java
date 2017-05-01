package vn.com.nsmv.service.impl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import vn.com.nsmv.common.SokokanriException;
import vn.com.nsmv.dao.ItemDAO;
import vn.com.nsmv.entity.Item;
import vn.com.nsmv.javabean.Order;
import vn.com.nsmv.service.OrdersService;

@Service("ordersService")
@EnableTransactionManagement
public class OrdersServiceImpl implements OrdersService {

	@Autowired
	private ItemDAO itemDAO;
	
	@Transactional(rollbackFor={SokokanriException.class})
	public void createOrder(Order order) throws SokokanriException {
		for (Item item : order.getAllItems()) {
			if (item.getName().equals("a")) {
				throw new SokokanriException("Break");
			}
			this.itemDAO.add(item);
		}
	}
	
}
