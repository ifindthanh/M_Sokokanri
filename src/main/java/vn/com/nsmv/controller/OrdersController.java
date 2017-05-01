package vn.com.nsmv.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import vn.com.nsmv.common.Constants;
import vn.com.nsmv.common.SokokanriException;
import vn.com.nsmv.entity.Item;
import vn.com.nsmv.javabean.Order;
import vn.com.nsmv.javabean.SearchCondition;
import vn.com.nsmv.service.OrdersService;

@Controller
public class OrdersController {
	private SearchCondition searchCondition = new SearchCondition();
	private Integer offset;
	private Integer maxResults;
	
	@Autowired
	private OrdersService ordersService;
	
	@RequestMapping(value = "/donhang/tat-ca/0", method=RequestMethod.GET)
	public String init()
	{
		this.offset = 0;
		this.maxResults = Constants.MAX_IMAGE_PER_PAGE;
		return "redirect:/donhang/tat-ca";
	}
	
	@RequestMapping(value = "/donhang/tat-ca", method=RequestMethod.GET)
	public ModelAndView listAllOrders(HttpServletRequest request, Model model, Integer offset, Integer maxResults) {
		if (this.maxResults == null)
		{
			this.maxResults = Constants.MAX_IMAGE_PER_PAGE;
		}
		
		if (offset != null)
		{
			this.offset = offset;
		}
		
		if (maxResults != null)
		{
			this.maxResults = maxResults;
		}
		
		return new ModelAndView("/orders/allOrders");
	}
	
	@RequestMapping(value = "/donhang/{orderId}", method=RequestMethod.GET)
	public ModelAndView viewAnOrder(HttpServletRequest request, Model model, String orderId) {
		return new ModelAndView("/orders/orderDetail");
	}
	
	@RequestMapping(value = "/donhang/tao-moi", method=RequestMethod.GET)
	public ModelAndView createNew(HttpServletRequest request, Model model) {
		Order order = new Order();
		order.addItem(new Item());
		order.addItem(new Item());
		model.addAttribute("order", order);
		return new ModelAndView("/orders/createNew");
	}
	
	@RequestMapping(value = "/donhang/tao-moi", method=RequestMethod.POST)
	public ModelAndView createOrder(@ModelAttribute("order")Order order, HttpServletRequest request, Model model) {
		try {
			this.ordersService.createOrder(order);
		} catch (SokokanriException ex) {
			model.addAttribute("error", ex.getErrorMessage());
		}
		return new ModelAndView("/orders/createNew");
	}
	
}
