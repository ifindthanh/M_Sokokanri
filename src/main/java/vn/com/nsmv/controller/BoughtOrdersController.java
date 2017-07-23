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
import vn.com.nsmv.common.Constants;
import vn.com.nsmv.common.SokokanriException;
import vn.com.nsmv.common.Utils;
import vn.com.nsmv.entity.Item;
import vn.com.nsmv.javabean.SearchCondition;
import vn.com.nsmv.service.OrdersService;

@Controller
@Scope("session")
public class BoughtOrdersController extends AbstractController{
	
	private SearchCondition searchCondition = new SearchCondition(2);
	
	@Autowired
	private OrdersService ordersService;
	
	@RequestMapping(value = "/donhang/da-mua/0", method=RequestMethod.GET)
	public String init()
	{
	    super.initController();
		searchCondition = new SearchCondition(2);
		return "redirect:/donhang/da-mua";
	}
	
	@RequestMapping(value = "/donhang/da-mua", method=RequestMethod.GET)
	public ModelAndView listAllOrders(HttpServletRequest request, Model model, Integer offset, Integer maxResults) {
		request.getSession().setAttribute("listType", 4);
		super.listAllOrdersInPage(request, model, offset, maxResults);
		return new ModelAndView("/orders/boughtOrders");
	}
	
	
	@RequestMapping(value = "/donhang/da-mua", method = RequestMethod.POST)
    public RedirectView search(
        SearchCondition searchCondition,
        Integer offset,
        Integer maxResults)
    {
	    super.reset(offset, maxResults);
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
	
	
	public void doBusiness(Model model) {
		if (this.searchCondition == null) {
			this.searchCondition = new SearchCondition(2);
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
			List<String> allBrands = this.ordersService.getAllBrands(userId, searchCondition.getStatus());
			this.checkSearching(this.searchCondition.getBrands(), allBrands);
            model.addAttribute("allBrands", allBrands);
			List<String> allBuyingCodes = this.ordersService.getAllBuyingCodes(userId, searchCondition.getStatus());
			this.checkSearching(this.searchCondition.getBuyingCodes(), allBuyingCodes);
            model.addAttribute("allBuyingCodes", allBuyingCodes);
			model.addAttribute("allOrders", allOrders);
			model.addAttribute("offset", this.getOffset());
			model.addAttribute("maxResult", this.getMaxResults());
			model.addAttribute("searchCondition", this.searchCondition);
			model.addAttribute("count", count);
			model.addAttribute("selectedItems", this.getSelectedItems());
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
			this.ordersService.transferOrders(this.getSelectedItems());
			this.getSelectedItems().clear();
			
		} catch (SokokanriException e) {
			model.addAttribute("message", e.getErrorMessage());
		}
		return new ModelAndView("redirect:da-mua");
	}
	
	
	@RequestMapping(value = "/donhang/da-mua/chon-don-hang", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseResult<String>> selectItem(@RequestParam Long id){
		return super.selectItem(id);
	}
	
	@RequestMapping(value = "/donhang/da-mua/bo-chon-don-hang", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseResult<String>> deSelectItem(@RequestParam Long id){
		return super.deSelectItem(id);
	}
	
	@RequestMapping(value = "/donhang/da-mua/chon-tat-ca", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseResult<String>> selectAllItems(@RequestParam String ids){
		return super.selectAllItems(ids);
	}
	
	@RequestMapping(value = "/donhang/da-mua/bo-chon-tat-ca", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseResult<String>> deSelectAllItems(@RequestParam String ids){
		return super.deSelectAllItems(ids);
	}
	
	@RequestMapping(value = "/donhang/da-mua/xoa-don-hang", method=RequestMethod.GET)
    public String deleteOrders(Model model){
	    this.delete(model, this.ordersService);
        return "redirect:/donhang/da-mua";
    }
    
    @RequestMapping(value = "/donhang/da-mua/huy-don-hang", method=RequestMethod.GET)
    public String cancelOrders(Model model){
        this.cancel(model, this.ordersService);
        return "redirect:/donhang/da-mua";
    }
}
