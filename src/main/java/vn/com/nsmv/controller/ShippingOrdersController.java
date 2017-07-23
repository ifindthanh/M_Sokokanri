package vn.com.nsmv.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
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
import vn.com.nsmv.common.SokokanriException;
import vn.com.nsmv.common.Utils;
import vn.com.nsmv.entity.Bill;
import vn.com.nsmv.javabean.SearchCondition;
import vn.com.nsmv.service.OrdersService;

@Controller
@Scope("session")
public class ShippingOrdersController extends AbstractController {
	
	private SearchCondition searchCondition = new SearchCondition(7);
	
	@Autowired
	private OrdersService ordersService;
	
	@RequestMapping(value = "/donhang/giao-hang/0", method=RequestMethod.GET)
	public String init()
	{
		this.searchCondition =  new SearchCondition(7);
		this.initController();
		return "redirect:/donhang/giao-hang";
	}
	
	@RequestMapping(value = "/donhang/giao-hang", method=RequestMethod.GET)
	public ModelAndView listAllOrders(HttpServletRequest request, Model model, Integer offset, Integer maxResults) {
		request.getSession().setAttribute("listType", 9);
		this.listAllOrdersInPage(request, model, offset, maxResults);
		return new ModelAndView("/orders/shippingOrders");
	}
	
	
	@RequestMapping(value = "/donhang/giao-hang", method = RequestMethod.POST)
	public RedirectView search(
		HttpServletRequest request,
		Model model,
		SearchCondition searchCondition,
		Integer offset,
		Integer maxResults)
	{
		this.reset(offset, maxResults);
		
		if (searchCondition != null) 
		{
			this.searchCondition = searchCondition;
		}
		
		return new RedirectView("giao-hang-tim-kiem");
	}
	
	@RequestMapping(value = "/donhang/giao-hang-tim-kiem", method = RequestMethod.GET)
	public String searchResult(HttpServletRequest request, Model model)
	{
		request.getSession().setAttribute("listType", 9);
		this.doBusiness(model);
		return "/orders/shippingOrders";
	}
	
	
	protected void doBusiness(Model model) {
		if (this.searchCondition == null) {
			this.searchCondition = new SearchCondition(7);
		}
		try {
			if (Utils.isUser()) {
				Long userId = ((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
				this.searchCondition.setUserId(userId);
			}
			List<Bill> allBills = this.ordersService.getAllBills(this.searchCondition, this.getOffset(),
					this.getMaxResults());
			int count = this.ordersService.countAllBills(this.searchCondition);
			List<Long> allBillIDs = this.ordersService.getAllBillIDs(searchCondition);
            this.checkSearchingBills(this.searchCondition.getBills(), allBillIDs);
            model.addAttribute("billIDs", allBillIDs);
			model.addAttribute("allBills", allBills);
			model.addAttribute("offset", this.getOffset());
			model.addAttribute("maxResult", this.getMaxResults());
			model.addAttribute("searchCondition", this.searchCondition);
			model.addAttribute("count", count);
			model.addAttribute("selectedItems", this.getSelectedItems());
		} catch (SokokanriException ex) {
			model.addAttribute("message", ex.getErrorMessage());
		}
	}
	
	
	@RequestMapping(value = "/donhang/giao-hang/chon-don-hang", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseResult<String>> selectItem(@RequestParam Long id){
	    return super.selectItem(id);
	}
	
	@RequestMapping(value = "/donhang/giao-hang/bo-chon-don-hang", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseResult<String>> deSelectItem(@RequestParam Long id){
	    return super.deSelectItem(id);
	}
	
	@RequestMapping(value = "/donhang/giao-hang/chon-tat-ca", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseResult<String>> selectAllItems(@RequestParam String ids){
	    return super.selectAllItems(ids);
	}
	
	@RequestMapping(value = "/donhang/giao-hang/bo-chon-tat-ca", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseResult<String>> deSelectAllItems(@RequestParam String ids){
	    return super.deSelectAllItems(ids);
	}
	
	@RequestMapping(value = "/donhang/da-giao-hang", method=RequestMethod.GET)
	public ModelAndView alreadyToSend(Model model) {
		try {
			this.ordersService.sendOrders(this.getSelectedItems());
		} catch (SokokanriException ex) {
			model.addAttribute("message", ex.getErrorMessage());
		}
		return new ModelAndView("redirect:giao-hang");
	}
}
