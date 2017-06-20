package vn.com.nsmv.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import vn.com.nsmv.bean.CustomUser;
import vn.com.nsmv.common.Constants;
import vn.com.nsmv.common.SokokanriException;
import vn.com.nsmv.common.Utils;
import vn.com.nsmv.entity.Category;
import vn.com.nsmv.javabean.SearchCondition;
import vn.com.nsmv.service.OrdersService;

@Controller
@Scope("session")
public class AllOrdersController {
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
		request.getSession().setAttribute("listType", 1);
		this.doBusiness(model);
		return new ModelAndView("/orders/allOrders");
	}
	
	@RequestMapping(value = "/donhang/tat-ca", method=RequestMethod.POST)
	public RedirectView listAllOrdersPOST(HttpServletRequest request, Model model, 
			Integer offset, Integer maxResults, SearchCondition searchCondition) {
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
		request.getSession().setAttribute("listType", 1);
		if (maxResults != null)
		{
			this.maxResults = maxResults;
		}
		
		if (searchCondition != null) 
		{
			this.searchCondition = searchCondition;
		}
		
		return new RedirectView("tat-ca-tim-kiem");
	}
	
	@RequestMapping(value = "/donhang/tat-ca-tim-kiem", method = RequestMethod.GET)
	public String searchResult(HttpServletRequest request, Model model)
	{
		request.getSession().setAttribute("listType", 1);
		this.doBusiness(model);
		return "/orders/allOrders";
	}

	private void doBusiness(Model model) {
		if (this.searchCondition == null) {
			this.searchCondition = new SearchCondition();
		}
		try {
			if (Utils.isUser()) {
				Long userId = ((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
				this.searchCondition.setUserId(userId);
			}
			List<Category> allOrders = this.ordersService.getAllOrders(this.searchCondition, null, this.offset,
					this.maxResults);
			int count = this.ordersService.countAllOrders(this.searchCondition);
			model.addAttribute("allOrders", allOrders);
			model.addAttribute("offset", this.offset);
			model.addAttribute("maxResult", this.maxResults);
			model.addAttribute("searchCondition", this.searchCondition);
			model.addAttribute("count", count);
		} catch (SokokanriException ex) {
			model.addAttribute("message", ex.getErrorMessage());
		}
	}
	
	@RequestMapping(value = "/donhang/admin/{orderId}")
	public ModelAndView viewAnOrder(HttpServletRequest request, Model model, @PathVariable Long orderId) {
		try {
			Category category = this.ordersService.getCategory(orderId);
			if (category == null) {
				throw new SokokanriException("Đơn hàng không tồn tại");
			}
			model.addAttribute("category", category);
			return new ModelAndView("/admin/orderDetail");
		} catch (SokokanriException ex) {
			model.addAttribute("message", ex.getErrorMessage());
			model.addAttribute("category",  new Category());
			return new ModelAndView("/orders/error");
		}
		
	}
}
