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
import vn.com.nsmv.common.SokokanriException;
import vn.com.nsmv.common.Utils;
import vn.com.nsmv.entity.Category;
import vn.com.nsmv.entity.Item;
import vn.com.nsmv.javabean.SearchCondition;
import vn.com.nsmv.service.OrdersService;

@Controller
@Scope("session")
public class AllOrdersController extends AbstractController{
	private SearchCondition searchCondition = new SearchCondition();
	
	@Autowired
	private OrdersService ordersService;
	
	@RequestMapping(value = "/donhang/tat-ca/0", method=RequestMethod.GET)
	public String init()
	{
	    this.searchCondition = new SearchCondition();
	    super.initController();
		return "redirect:/donhang/tat-ca";
	}
	
	@RequestMapping(value = "/donhang/tat-ca", method=RequestMethod.GET)
	public ModelAndView listAllOrders(HttpServletRequest request, Model model, Integer offset, Integer maxResults) {
		request.getSession().setAttribute("listType", 1);
		super.listAllOrdersInPage(request, model, offset, maxResults);
		return new ModelAndView("/orders/allOrders");
	}
	
	@RequestMapping(value = "/donhang/tat-ca", method=RequestMethod.POST)
	public RedirectView listAllOrdersPOST(HttpServletRequest request, Model model, 
			Integer offset, Integer maxResults, SearchCondition searchCondition) {
	    
	    this.reset(offset, maxResults);
		if (searchCondition != null) 
		{
			this.searchCondition = searchCondition;
		}
		
		request.getSession().setAttribute("listType", 1);
		
		return new RedirectView("tat-ca-tim-kiem");
	}
	
	@RequestMapping(value = "/donhang/tat-ca-tim-kiem", method = RequestMethod.GET)
	public String searchResult(HttpServletRequest request, Model model)
	{
		request.getSession().setAttribute("listType", 1);
		this.doBusiness(model);
		return "/orders/allOrders";
	}

	public void doBusiness(Model model) {
		if (this.searchCondition == null) {
			this.searchCondition = new SearchCondition();
		}
		Long userId = null;
		try {
			if (Utils.isUser()) {
			    userId = ((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
				this.searchCondition.setUserId(userId);
			}
			List<Item> allOrders = this.ordersService.getAllOrders(this.searchCondition, null, this.getOffset(),
					this.getMaxResults());
			int count = this.ordersService.countAllItems(this.searchCondition);
			List<String> allBrands = this.ordersService.getAllBrands(userId, this.searchCondition.getStatus());
			this.checkSearching(searchCondition.getBrands(), allBrands);
			model.addAttribute("allBrands", allBrands);
			model.addAttribute("items", allOrders);
			model.addAttribute("offset", this.getOffset());
			model.addAttribute("maxResult", this.getMaxResults());
			model.addAttribute("searchCondition", this.searchCondition);
			model.addAttribute("count", count);
		} catch (SokokanriException ex) {
			model.addAttribute("message", ex.getErrorMessage());
		}
	}
	
	@RequestMapping(value = "/donhang/admin/{orderId}")
	public ModelAndView viewAnOrder(HttpServletRequest request, Model model, @PathVariable Long orderId) {
		try {
			Item item = this.ordersService.getItem(orderId);
			if (item == null) {
				throw new SokokanriException("Đơn hàng không tồn tại");
			}
			model.addAttribute("category", item);
			return new ModelAndView("/admin/orderDetail");
		} catch (SokokanriException ex) {
			model.addAttribute("message", ex.getErrorMessage());
			model.addAttribute("category",  new Category());
			return new ModelAndView("/orders/error");
		}
		
	}
}
