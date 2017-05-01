package vn.com.nsmv.service;

import vn.com.nsmv.common.SokokanriException;
import vn.com.nsmv.javabean.Order;

public interface OrdersService {
	public void createOrder(Order order) throws SokokanriException;
}
