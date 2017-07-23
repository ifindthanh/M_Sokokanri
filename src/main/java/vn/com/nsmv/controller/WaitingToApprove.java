package vn.com.nsmv.controller;

import java.util.List;

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
import vn.com.nsmv.entity.Item;
import vn.com.nsmv.javabean.SearchCondition;
import vn.com.nsmv.service.OrdersService;

@Controller
@Scope("session")
public class WaitingToApprove extends AbstractController{
	
	private SearchCondition searchCondition = new SearchCondition(0);
	
	@Autowired
	private OrdersService ordersService;
	
	@RequestMapping(value = "/donhang/cho-duyet/0", method=RequestMethod.GET)
	public String init()
	{
	    this.searchCondition =  new SearchCondition(0);
        super.initController();
		return "redirect:/donhang/cho-duyet";
	}
	
	@RequestMapping(value = "/donhang/cho-duyet", method=RequestMethod.GET)
	public ModelAndView listAllOrders(HttpServletRequest request, Model model, Integer offset, Integer maxResults) {
		
		request.getSession().setAttribute("listType", 2);
		super.listAllOrdersInPage(request, model, offset, maxResults);
		return new ModelAndView("/orders/allWaitingOrders");
	}
	
	public void doBusiness(Model model) {
		if (this.searchCondition == null) {
			this.searchCondition = new SearchCondition(0);
		}
		try {
			if (Utils.isUser()) {
				Long userId = ((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
				this.searchCondition.setUserId(userId);
			}
			List<Item> allOrders = this.ordersService.getAllOrders(this.searchCondition, null, this.getOffset(),
					this.getMaxResults());
			int count = this.ordersService.countAllItems(this.searchCondition);
			model.addAttribute("allOrders", allOrders);
			model.addAttribute("offset", this.getOffset());
			model.addAttribute("maxResult", this.getMaxResults());
			model.addAttribute("selectedItems", this.getSelectedItems());
			model.addAttribute("searchCondition", this.searchCondition);
			model.addAttribute("count", count);
		} catch (SokokanriException ex) {
			model.addAttribute("message", ex.getErrorMessage());
		}
	}
	
	@RequestMapping(value = "/donhang/cho-duyet/chon-don-hang", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseResult<String>> selectItem(@RequestParam Long id){
	    return super.selectItem(id);
	}
	
	@RequestMapping(value = "/donhang/cho-duyet/bo-chon-don-hang", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseResult<String>> deSelectItem(@RequestParam Long id){
	    return super.deSelectItem(id);
	}
	
	@RequestMapping(value = "/donhang/cho-duyet/chon-tat-ca", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseResult<String>> selectAllItems(@RequestParam String ids){
		return super.selectAllItems(ids);
	}
	
	@RequestMapping(value = "/donhang/cho-duyet/bo-chon-tat-ca", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseResult<String>> deSelectAllItems(@RequestParam String ids){
		return super.deSelectAllItems(ids);
	}
	
	@RequestMapping(value = "/donhang/duyet-nhieu-don-hang", method=RequestMethod.GET)
	public ModelAndView approvalOrders(Model model){
		if (!Utils.hasRole(Constants.ROLE_C) && !Utils.hasRole(Constants.ROLE_A)) {
			model.addAttribute("message", "Bạn không có quyền duyệt đơn hàng");
		}
		try {
			this.ordersService.approveOrders(this.getSelectedItems());
			this.getSelectedItems().clear();
		} catch (SokokanriException e) {
			model.addAttribute("message", e.getErrorMessage());
		}
		return new ModelAndView("redirect:cho-duyet");
	}
	
	@RequestMapping(value = "/donhang/duyet-don-hang", method=RequestMethod.GET)
	public ModelAndView approval(@RequestParam Long id, Model model){
		//TODO check money, permission
		if (!Utils.hasRole(Constants.ROLE_C) && !Utils.hasRole(Constants.ROLE_A)) {
			model.addAttribute("message", "Bạn không có quyền duyệt đơn hàng");
		}
		try {
			this.ordersService.approve(id);
		} catch (SokokanriException e) {
			model.addAttribute("message", e.getErrorMessage());
		}
		return new ModelAndView("redirect:cho-duyet");
	}
	
	@RequestMapping(value = "/donhang/ghi-chu", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseResult<String>> noteAnOrder(@RequestParam String content, Model model){
		try {
		    if (this.getSelectedItems().size() != 1) {
		        throw new SokokanriException("Vui lòng chọn một đơn hàng.");
		    }
			this.ordersService.noteAnOrder(this.getSelectedItems().iterator().next(), content);
		} catch (SokokanriException e) {
			model.addAttribute("message", e.getErrorMessage());
		}
		return new ResponseEntity<ResponseResult<String>>(new ResponseResult<String>(1, "Success", null), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/donhang/mua-mat-hang", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseResult<String>> saveRealPrice(@RequestParam Long id, @RequestParam Double value){
		try {
			this.ordersService.saveRealPrice(id, value);
		} catch (SokokanriException ex) {
			return new ResponseEntity<ResponseResult<String>>(new ResponseResult<String>(0, ex.getErrorMessage(), null), HttpStatus.OK);
		}
		return new ResponseEntity<ResponseResult<String>>(new ResponseResult<String>(1, "Success", null), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/donhang/cho-duyet/xoa-don-hang", method=RequestMethod.GET)
    public String deleteOrders(Model model){
        this.delete(model, this.ordersService);
        return "redirect:/donhang/cho-duyet";
    }
	
	@RequestMapping(value = "/donhang/cho-duyet/huy-don-hang", method=RequestMethod.GET)
    public String cancelOrders(Model model){
	    this.cancel(model, this.ordersService);
        return "redirect:/donhang/cho-duyet";
    }
	
	@RequestMapping(value = "/donhang/cho-duyet/bo-ghi-chu", method=RequestMethod.GET)
    public String removeNote(Model model, @RequestParam Long id){
        try {
            this.ordersService.removeNote(id);
        } catch (SokokanriException ex) {
            model.addAttribute("message", ex.getErrorMessage());
        }
        return "redirect:/donhang/cho-duyet";
    }
}
