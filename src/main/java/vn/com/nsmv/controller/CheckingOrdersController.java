package vn.com.nsmv.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import vn.com.nsmv.bean.CustomUser;
import vn.com.nsmv.bean.ResponseResult;
import vn.com.nsmv.common.Constants;
import vn.com.nsmv.common.SokokanriException;
import vn.com.nsmv.common.Utils;
import vn.com.nsmv.common.Constants.SESSION_PARAM;
import vn.com.nsmv.entity.Category;
import vn.com.nsmv.javabean.ExportingSearchCondition;
import vn.com.nsmv.javabean.SearchCondition;
import vn.com.nsmv.service.OrdersService;

@Controller
@Scope("session")
public class CheckingOrdersController {
	
	private SearchCondition searchCondition = new SearchCondition(4);
	private Integer offset;
	private Integer maxResults;
	
	@Autowired
	private OrdersService ordersService;
	
	@RequestMapping(value = "/donhang/da-chuyen-vn/0", method=RequestMethod.GET)
	public String init()
	{
		this.offset = 0;
		this.maxResults = Constants.MAX_IMAGE_PER_PAGE;
		this.searchCondition =  new SearchCondition(4);
		return "redirect:/donhang/da-chuyen-vn";
	}
	
	@RequestMapping(value = "/donhang/da-chuyen-vn", method=RequestMethod.GET)
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
		
		this.doBusiness(model);
		return new ModelAndView("/orders/transferedToVNOrders");
	}
	
	
	@RequestMapping(value = "/donhang/da-chuyen-vn", method = RequestMethod.POST)
	public RedirectView search(
		HttpServletRequest request,
		Model model,
		SearchCondition searchCondition,
		Integer offset,
		Integer maxResults)
	{
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
		
		if (searchCondition != null) 
		{
			this.searchCondition = searchCondition;
		}
		
		return new RedirectView("da-chuyen-vn-tim-kiem");
	}
	
	@RequestMapping(value = "/donhang/da-chuyen-vn-tim-kiem", method = RequestMethod.GET)
	public String searchResult(HttpServletRequest request, Model model)
	{
		this.doBusiness(model);
		return "/orders/transferedToVNOrders";
	}
	
	
	private void doBusiness(Model model) {
		if (this.searchCondition == null) {
			this.searchCondition = new SearchCondition(4);
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
	
	@RequestMapping(value = "/donhang/nhap-kho", method=RequestMethod.GET)
	public ModelAndView importToStorage(Model model) {
		try {
			List<Category> allOrders = this.ordersService.getAllOrders(new SearchCondition(4), null, null,
					null);
			Map<Long, List<Category>> classificationOrders = new HashMap<Long, List<Category>>();
			for (Category item : allOrders) {
				Long id = item.getUser().getId();
				if (classificationOrders.containsKey(id)) {
					classificationOrders.get(id).add(item);
				} else {
					ArrayList<Category> orders = new ArrayList<Category>();
					orders.add(item);
					classificationOrders.put(id, orders);
				}
			}
			this.ordersService.importToStorage(classificationOrders);
		} catch (SokokanriException e) {
			model.addAttribute("message", e.getErrorMessage());
		}
		return new ModelAndView("redirect:/donhang/da-chuyen-vn/0");
	}
	
}
