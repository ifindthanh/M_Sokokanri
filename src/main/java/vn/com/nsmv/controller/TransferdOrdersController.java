package vn.com.nsmv.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import vn.com.nsmv.bean.CustomUser;
import vn.com.nsmv.bean.ResponseResult;
import vn.com.nsmv.common.Constants;
import vn.com.nsmv.common.SokokanriException;
import vn.com.nsmv.common.Utils;
import vn.com.nsmv.entity.Category;
import vn.com.nsmv.javabean.SearchCondition;
import vn.com.nsmv.service.OrdersService;

@Controller
@Scope("session")
public class TransferdOrdersController {
	
	private SearchCondition searchCondition = new SearchCondition(3);
	private Integer offset;
	private Integer maxResults;
	
	@Autowired
	private OrdersService ordersService;
	
	private Set<Long> selectedItems = new HashSet<Long>();
	
	@RequestMapping(value = "/donhang/da-chuyen/0", method=RequestMethod.GET)
	public String init()
	{
		this.offset = 0;
		this.maxResults = Constants.MAX_IMAGE_PER_PAGE;
		this.selectedItems.clear();
		this.searchCondition = new SearchCondition(3);
		return "redirect:/donhang/da-chuyen";
	}
	
	@RequestMapping(value = "/donhang/da-chuyen", method=RequestMethod.GET)
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
		return new ModelAndView("/orders/transferedOrders");
	}
	
	private void doBusiness(Model model) {
		if (this.searchCondition == null) {
			this.searchCondition = new SearchCondition(3);
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
			model.addAttribute("selectedItems", this.selectedItems);
		} catch (SokokanriException ex) {
			model.addAttribute("message", ex.getErrorMessage());
		}
	}
	
	@RequestMapping(value = "/donhang/da-chuyen/chon-don-hang", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseResult<String>> selectItem(@RequestParam Long id){
		this.selectedItems.add(id);
		return new ResponseEntity<ResponseResult<String>>(new ResponseResult<String>(1, "Success", null), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/donhang/da-chuyen/bo-chon-don-hang", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseResult<String>> deSelectItem(@RequestParam Long id){
		this.selectedItems.remove(id);
		return new ResponseEntity<ResponseResult<String>>(new ResponseResult<String>(1, "Success", null), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/donhang/da-chuyen/chon-tat-ca", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseResult<String>> selectAllItems(@RequestParam String ids){
		String[] allIds = ids.split(",");
		for (String item : allIds) {
			if (Utils.isEmpty(item)) {
				continue;
			}
			try {
				Long id = Long.parseLong(item);
				this.selectedItems.add(id);
			} catch (Exception ex) {
				continue;
			}
		}
		return new ResponseEntity<ResponseResult<String>>(new ResponseResult<String>(1, "Success", null), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/donhang/da-chuyen/bo-chon-tat-ca", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseResult<String>> deSelectAllItems(@RequestParam String ids){
		String[] allIds = ids.split(",");
		for (String item : allIds) {
			if (Utils.isEmpty(item)) {
				continue;
			}
			try {
				Long id = Long.parseLong(item);
				this.selectedItems.remove(id);
			} catch (Exception ex) {
				continue;
			}
		}
		return new ResponseEntity<ResponseResult<String>>(new ResponseResult<String>(1, "Success", null), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/donhang/chuyen-nhieu-don-hang-vn", method=RequestMethod.GET)
	public ModelAndView approvalOrders(Model model){
		if (!Utils.hasRole(Constants.ROLE_T2) && !Utils.hasRole(Constants.ROLE_A)) {
			model.addAttribute("message", "Bạn không có quyền chuyển trạng thái của đơn hàng này");
		}
		try {
			this.ordersService.transferOrdersToVN(this.selectedItems);
			this.selectedItems.clear();
		} catch (SokokanriException e) {
			model.addAttribute("message", e.getErrorMessage());
		}
		return new ModelAndView("redirect:da-chuyen");
	}
	
	@RequestMapping(value = "/donhang/chuyen-don-hang-vn", method=RequestMethod.GET)
	public ModelAndView approval(@RequestParam Long id, Model model){
		if (!Utils.hasRole(Constants.ROLE_T2) && !Utils.hasRole(Constants.ROLE_A)) {
			model.addAttribute("message", "Bạn không có quyền chuyển trạng thái của đơn hàng này");
		}
		try {
			this.ordersService.transferOrderToVN(id);
			
		} catch (SokokanriException e) {
			model.addAttribute("message", e.getErrorMessage());
		}
		return new ModelAndView("redirect:da-chuyen");
	}
}
