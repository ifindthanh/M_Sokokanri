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
import org.springframework.web.servlet.view.RedirectView;

import vn.com.nsmv.bean.CustomUser;
import vn.com.nsmv.bean.ResponseResult;
import vn.com.nsmv.common.Constants;
import vn.com.nsmv.common.SokokanriException;
import vn.com.nsmv.common.Utils;
import vn.com.nsmv.entity.Category;
import vn.com.nsmv.entity.Item;
import vn.com.nsmv.javabean.SearchCondition;
import vn.com.nsmv.service.OrdersService;

@Controller
@Scope("session")
public class BoughtOrdersController {
	
	private SearchCondition searchCondition = new SearchCondition(2);
	private Integer offset;
	private Integer maxResults;
	
	@Autowired
	private OrdersService ordersService;
	
	private Set<Long> selectedItems = new HashSet<Long>();
	
	@RequestMapping(value = "/donhang/da-mua/0", method=RequestMethod.GET)
	public String init()
	{
		this.offset = 0;
		this.maxResults = Constants.MAX_IMAGE_PER_PAGE;
		this.selectedItems.clear();
		searchCondition = new SearchCondition(2);
		return "redirect:/donhang/da-mua";
	}
	
	@RequestMapping(value = "/donhang/da-mua", method=RequestMethod.GET)
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
		request.getSession().setAttribute("listType", 4);
		this.doBusiness(model);
		return new ModelAndView("/orders/boughtOrders");
	}
	
	
	@RequestMapping(value = "/donhang/da-mua", method = RequestMethod.POST)
    public RedirectView search(
        HttpServletRequest request,
        Model model,
        SearchCondition searchCondition,
        Integer offset,
        Integer maxResults)
    {
        this.selectedItems.clear();
        
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
        
        return new RedirectView("da-mua-tim-kiem");
    }
    
    @RequestMapping(value = "/donhang/da-mua-tim-kiem", method = RequestMethod.GET)
    public String searchResult(HttpServletRequest request, Model model)
    {
        request.getSession().setAttribute("listType", 4);
        this.doBusiness(model);
        return "/orders/boughtOrders";
    }
	
	
	private void doBusiness(Model model) {
		if (this.searchCondition == null) {
			this.searchCondition = new SearchCondition(2);
		}
		try {
		    Long userId = null;
			if (Utils.isUser()) {
				userId = ((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
				this.searchCondition.setUserId(userId);
			}
			List<Item> allOrders = this.ordersService.getAllOrders(this.searchCondition, null, this.offset,
					this.maxResults);
			int count = this.ordersService.countAllItems(this.searchCondition);
			model.addAttribute("allBrands", this.ordersService.getAllBrands(userId, searchCondition.getStatus()));
			model.addAttribute("allBuyingCodes", this.ordersService.getAllBuyingCodes(userId, searchCondition.getStatus()));
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
	
	
	@RequestMapping(value = "/donhang/chuyen-don-hang", method=RequestMethod.GET)
	public ModelAndView approval(Model model){
		if (!Utils.hasRole(Constants.ROLE_T1) && !Utils.hasRole(Constants.ROLE_A)) {
			model.addAttribute("message", "Bạn không có quyền chuyển trạng thái của đơn hàng này");
		}
		try {
			this.ordersService.transferOrders(this.selectedItems);
			this.selectedItems.clear();
			
		} catch (SokokanriException e) {
			model.addAttribute("message", e.getErrorMessage());
		}
		return new ModelAndView("redirect:da-mua");
	}
	
	
	@RequestMapping(value = "/donhang/da-mua/chon-don-hang", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseResult<String>> selectItem(@RequestParam Long id){
		this.selectedItems.add(id);
		return new ResponseEntity<ResponseResult<String>>(new ResponseResult<String>(1, "Success", null), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/donhang/da-mua/bo-chon-don-hang", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseResult<String>> deSelectItem(@RequestParam Long id){
		this.selectedItems.remove(id);
		return new ResponseEntity<ResponseResult<String>>(new ResponseResult<String>(1, "Success", null), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/donhang/da-mua/chon-tat-ca", method=RequestMethod.GET)
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
	
	@RequestMapping(value = "/donhang/da-mua/bo-chon-tat-ca", method=RequestMethod.GET)
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
	
}
