package vn.com.nsmv.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import vn.com.nsmv.entity.Item;
import vn.com.nsmv.javabean.SearchCondition;
import vn.com.nsmv.service.OrdersService;

@Controller
@Scope("session")
public class CheckingOrdersController extends AbstractController{
	
	private SearchCondition searchCondition = new SearchCondition(4);
	
	@Autowired
	private OrdersService ordersService;
	
	@RequestMapping(value = "/donhang/da-chuyen-vn/0", method=RequestMethod.GET)
	public String init()
	{
		this.initController();
		this.searchCondition =  new SearchCondition(4);
		return "redirect:/donhang/da-chuyen-vn";
	}
	
	@RequestMapping(value = "/donhang/da-chuyen-vn", method=RequestMethod.GET)
	public ModelAndView listAllOrders(HttpServletRequest request, Model model, Integer offset, Integer maxResults) {

	    this.listAllOrdersInPage(request, model, offset, maxResults);
		request.getSession().setAttribute("listType", 6);
		return new ModelAndView("/orders/transferedToVNOrders");
	}
	
	
	@RequestMapping(value = "/donhang/da-chuyen-vn", method = RequestMethod.POST)
	public RedirectView search(
		SearchCondition searchCondition,
		Integer offset,
		Integer maxResults)
	{
		this.reset(offset, maxResults);
		
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
	
	
	public void doBusiness(Model model) {
		if (this.searchCondition == null) {
			this.searchCondition = new SearchCondition(4);
		}
		try {
		    Long userId = null;
			if (Utils.isUser()) {
			    userId = ((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
				this.searchCondition.setUserId(userId);
			}
			List<Item> allOrders = this.ordersService.getAllOrders(this.searchCondition, null, this.getOffset(),
					this.getMaxResults());
			int count = this.ordersService.countAllItems(this.searchCondition);
			model.addAttribute("allOrders", allOrders);
			model.addAttribute("offset", this.getOffset());
			model.addAttribute("maxResult", this.getMaxResults());
			model.addAttribute("searchCondition", this.searchCondition);
			model.addAttribute("selectedItems", this.getSelectedItems());
			model.addAttribute("allBrands", this.ordersService.getAllBrands(userId, searchCondition.getStatus()));
            model.addAttribute("allBuyingCodes", this.ordersService.getAllBuyingCodes(userId, searchCondition.getStatus()));
            model.addAttribute("allTransferIds", this.ordersService.getAllTransferIds(userId, searchCondition.getStatus()));
			model.addAttribute("count", count);
		} catch (SokokanriException ex) {
			model.addAttribute("message", ex.getErrorMessage());
		}
	}
	
	@RequestMapping(value = "/donhang/nhap-kho", method=RequestMethod.GET)
	public ModelAndView importToStorage(Model model) {
		try {
			List<Item> allOrders = this.ordersService.getAllOrders(new SearchCondition(4), null, null,
					null);
			Map<Long, List<Item>> classificationOrders = new HashMap<Long, List<Item>>();
			for (Item item : allOrders) {
				if (!this.getSelectedItems().contains(item.getId())) {
					continue;
				}
				Long userID = item.getUser().getId();
				if (classificationOrders.containsKey(userID)) {
					classificationOrders.get(userID).add(item);
				} else {
					ArrayList<Item> orders = new ArrayList<Item>();
					orders.add(item);
					classificationOrders.put(userID, orders);
				}
			}
			this.ordersService.importToStorage(classificationOrders);
		} catch (SokokanriException e) {
			model.addAttribute("message", e.getErrorMessage());
		}
		return new ModelAndView("redirect:/donhang/da-chuyen-vn/0");
	}
	
	@RequestMapping(value = "/donhang/da-chuyen-vn/chon-don-hang", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseResult<String>> selectItem(@RequestParam Long id){
		return super.selectItem(id);
	}
	
	@RequestMapping(value = "/donhang/da-chuyen-vn/bo-chon-don-hang", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseResult<String>> deSelectItem(@RequestParam Long id){
		return super.deSelectItem(id);
	}
	
	@RequestMapping(value = "/donhang/da-chuyen-vn/chon-tat-ca", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseResult<String>> selectAllItems(@RequestParam String ids){
		return super.selectAllItems(ids);
	}
	
	@RequestMapping(value = "/donhang/da-chuyen-vn/bo-chon-tat-ca", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseResult<String>> deSelectAllItems(@RequestParam String ids){
		return super.deSelectAllItems(ids);
	}
	
	@RequestMapping(value = "/donhang/da-chuyen-vn/xoa-don-hang", method=RequestMethod.GET)
    public String deleteOrders(Model model){
        try {
            this.ordersService.deleteItems(this.getSelectedItems());
            this.getSelectedItems().clear();
        } catch (SokokanriException ex) {
            model.addAttribute("message", ex.getErrorMessage());
        }
        return "redirect:/donhang/da-chuyen-vn";
    }
    
    @RequestMapping(value = "/donhang/da-chuyen-vn/huy-don-hang", method=RequestMethod.GET)
    public String cancelOrders(Model model){
        try {
            this.ordersService.cancelItems(this.getSelectedItems());
            this.getSelectedItems().clear();
        } catch (SokokanriException ex) {
            model.addAttribute("message", ex.getErrorMessage());
        }
        return "redirect:/donhang/da-chuyen-vn";
    }
	
}
