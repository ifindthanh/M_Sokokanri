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
public class TransferdOrdersController extends AbstractController{
	
	private SearchCondition searchCondition = new SearchCondition(3);
	
	@Autowired
	private OrdersService ordersService;
	
	@RequestMapping(value = "/donhang/da-chuyen/0", method=RequestMethod.GET)
	public String init()
	{
	    this.initController();
		this.searchCondition = new SearchCondition(3);
		return "redirect:/donhang/da-chuyen";
	}
	
	@RequestMapping(value = "/donhang/da-chuyen", method=RequestMethod.GET)
	public ModelAndView listAllOrders(HttpServletRequest request, Model model, Integer offset, Integer maxResults) {
		
		request.getSession().setAttribute("listType", 5);
		this.listAllOrdersInPage(request, model, offset, maxResults);
		return new ModelAndView("/orders/transferedOrders");
	}
	
	@RequestMapping(value = "/donhang/da-chuyen", method = RequestMethod.POST)
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
        
        return new RedirectView("da-chuyen-tim-kiem");
    }
    
    @RequestMapping(value = "/donhang/da-chuyen-tim-kiem", method = RequestMethod.GET)
    public String searchResult(HttpServletRequest request, Model model)
    {
        request.getSession().setAttribute("listType", 5);
        this.doBusiness(model);
        return "/orders/transferedOrders";
    }
	
	public void doBusiness(Model model) {
		if (this.searchCondition == null) {
			this.searchCondition = new SearchCondition(3);
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
	
	@RequestMapping(value = "/donhang/da-chuyen/chon-don-hang", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseResult<String>> selectItem(@RequestParam Long id){
		return super.selectItem(id);
	}
	
	@RequestMapping(value = "/donhang/da-chuyen/bo-chon-don-hang", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseResult<String>> deSelectItem(@RequestParam Long id){
		return super.deSelectItem(id);
	}
	
	@RequestMapping(value = "/donhang/da-chuyen/chon-tat-ca", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseResult<String>> selectAllItems(@RequestParam String ids){
		return super.selectAllItems(ids);
	}
	
	@RequestMapping(value = "/donhang/da-chuyen/bo-chon-tat-ca", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseResult<String>> deSelectAllItems(@RequestParam String ids){
		return super.deSelectAllItems(ids);
	}
	
	@RequestMapping(value = "/donhang/chuyen-nhieu-don-hang-vn", method=RequestMethod.GET)
	public ModelAndView approvalOrders(@RequestParam String tranferID, Model model){
		if (!Utils.hasRole(Constants.ROLE_T2) && !Utils.hasRole(Constants.ROLE_A)) {
			model.addAttribute("message", "Bạn không có quyền chuyển trạng thái của đơn hàng này");
		}
		try {
		    if (Utils.isEmpty(tranferID)) {
                throw new SokokanriException("Vui lòng ghi chú vận đơn");
            }
			this.ordersService.transferOrdersToVN(this.getSelectedItems(), tranferID);
			this.getSelectedItems().clear();
		} catch (SokokanriException e) {
			model.addAttribute("message", e.getErrorMessage());
		}
		return new ModelAndView("redirect:da-chuyen");
	}
	
	@RequestMapping(value = "/donhang/da-chuyen/xoa-don-hang", method=RequestMethod.GET)
    public String deleteOrders(Model model){
        this.delete(model, this.ordersService);
        return "redirect:/donhang/da-chuyen";
    }
    
    @RequestMapping(value = "/donhang/da-chuyen/huy-don-hang", method=RequestMethod.GET)
    public String cancelOrders(Model model){
        this.cancel(model, this.ordersService);
        return "redirect:/donhang/da-chuyen";
    }
	
}
