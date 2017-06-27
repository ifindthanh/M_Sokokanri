package vn.com.nsmv.controller;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.com.nsmv.bean.CustomUser;
import vn.com.nsmv.bean.ResponseResult;
import vn.com.nsmv.common.Constants;
import vn.com.nsmv.common.SokokanriException;
import vn.com.nsmv.common.Utils;
import vn.com.nsmv.entity.Item;
import vn.com.nsmv.javabean.SearchCondition;
import vn.com.nsmv.service.OrdersService;

@Controller
@Scope("session")
public class WaitingToBuyController {
	
	private SearchCondition searchCondition = new SearchCondition(1);
	private Integer offset;
	private Integer maxResults;
	
	@Autowired
	private OrdersService ordersService;
	
	private Set<Long> selectedItems = new HashSet<Long>();
	
	@RequestMapping(value = "/donhang/cho-mua/0", method=RequestMethod.GET)
	public String init()
	{
		this.offset = 0;
		this.maxResults = Constants.MAX_IMAGE_PER_PAGE;
		this.selectedItems.clear();
		this.searchCondition = new SearchCondition(1);
		return "redirect:/donhang/cho-mua";
	}
	
	@RequestMapping(value = "/donhang/cho-mua", method=RequestMethod.GET)
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
		request.getSession().setAttribute("listType", 3);
		this.doBusiness(model);
		return new ModelAndView("/orders/buyingOrders");
	}
	
	private void doBusiness(Model model) {
		if (this.searchCondition == null) {
			this.searchCondition = new SearchCondition(1);
		}
		try {
			if (Utils.isUser()) {
				Long userId = ((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
				this.searchCondition.setUserId(userId);
			}
			List<Item> allOrders = this.ordersService.getAllOrders(this.searchCondition, null, this.offset,
					this.maxResults);
			int count = this.ordersService.countAllItems(this.searchCondition);
			model.addAttribute("allOrders", allOrders);
			model.addAttribute("offset", this.offset);
			model.addAttribute("maxResult", this.maxResults);
			model.addAttribute("searchCondition", this.searchCondition);
			model.addAttribute("count", count);
		} catch (SokokanriException ex) {
			model.addAttribute("message", ex.getErrorMessage());
		}
	}
	
	
	@RequestMapping(value = "/donhang/da-mua-nhieu-don-hang", method=RequestMethod.GET)
	public ModelAndView approvalOrders(Model model){
		if (!Utils.hasRole(Constants.ROLE_C) && !Utils.hasRole(Constants.ROLE_A)) {
			model.addAttribute("message", "Bạn không có quyền duyệt đơn hàng");
		}
		try {
			this.ordersService.approveOrders(this.selectedItems);
			this.selectedItems.clear();
		} catch (SokokanriException e) {
			model.addAttribute("message", e.getErrorMessage());
		}
		return new ModelAndView("redirect:cho-mua");
	}
	
	@RequestMapping(value = "/donhang/mua-hang", method=RequestMethod.POST)
	public ModelAndView approvalBuy(@ModelAttribute("order")Item item, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes){
		try {
			item.setStatus(2);
			item.setBuyer(((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId());
			item.setBoughtDate(new Date());
			item.setBuyNote("");
			this.ordersService.saveOrder(item);
			return new ModelAndView("redirect:/donhang/" + item.getId());
		} catch (SokokanriException ex) {
			redirectAttributes.addFlashAttribute(
					"message", ex.getErrorMessage());
			redirectAttributes.addFlashAttribute(
					"order", item);
			return new ModelAndView("redirect:/donhang/" + item.getId());
		}
	}
	
	@RequestMapping(value = "/donhang/ghi-chu-mua", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseResult<String>> noteAnOrder(@RequestParam Long id, @RequestParam String content, Model model){
		try {
			this.ordersService.noteABuyingOrder(id, content);
		} catch (SokokanriException e) {
			model.addAttribute("message", e.getErrorMessage());
		}
		return new ResponseEntity<ResponseResult<String>>(new ResponseResult<String>(1, "Success", null), HttpStatus.OK);
	}
}
