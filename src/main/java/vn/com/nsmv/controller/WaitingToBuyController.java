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
import org.springframework.web.servlet.view.RedirectView;

import vn.com.nsmv.bean.CustomUser;
import vn.com.nsmv.bean.ResponseResult;
import vn.com.nsmv.common.Constants;
import vn.com.nsmv.common.SokokanriException;
import vn.com.nsmv.common.Utils;
import vn.com.nsmv.entity.Item;
import vn.com.nsmv.entity.VBrand;
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
	
	@RequestMapping(value = "/donhang/cho-mua", method = RequestMethod.POST)
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
        
        return new RedirectView("cho-mua-tim-kiem");
    }
	
	@RequestMapping(value = "/donhang/cho-mua-tim-kiem", method = RequestMethod.GET)
    public String searchResult(HttpServletRequest request, Model model)
    {
        request.getSession().setAttribute("listType", 3);
        this.doBusiness(model);
        return "/orders/buyingOrders";
    }
	
	private void doBusiness(Model model) {
		if (this.searchCondition == null) {
			this.searchCondition = new SearchCondition(1);
		}
		Long userId = null;
		try {
			if (Utils.isUser()) {
				userId = ((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
				this.searchCondition.setUserId(userId);
			}
			List<Item> allOrders = this.ordersService.getAllOrders(this.searchCondition, null, this.offset,
					this.maxResults);
			List<String> allBrands = this.ordersService.getAllBrands(userId, 1);
			int count = this.ordersService.countAllItems(this.searchCondition);
			model.addAttribute("allOrders", allOrders);
			model.addAttribute("allBrands", allBrands);
			model.addAttribute("offset", this.offset);
			model.addAttribute("maxResult", this.maxResults);
			model.addAttribute("searchCondition", this.searchCondition);
			model.addAttribute("selectedItems", this.selectedItems);
			model.addAttribute("count", count);
		} catch (SokokanriException ex) {
			model.addAttribute("message", ex.getErrorMessage());
		}
	}
	
	
	@RequestMapping(value = "/donhang/mua-nhieu-don-hang", method=RequestMethod.GET)
	public ModelAndView approvalOrders(Model model){
		if (!Utils.hasRole(Constants.ROLE_B) && !Utils.hasRole(Constants.ROLE_A)) {
			model.addAttribute("message", "Bạn không có quyền duyệt đơn hàng");
		}
		try {
			this.ordersService.buyOrders(this.selectedItems);
			this.selectedItems.clear();
		} catch (SokokanriException e) {
			model.addAttribute("message", e.getErrorMessage());
		}
		this.doBusiness(model);
		return new ModelAndView("/orders/buyingOrders");
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
	public @ResponseBody ResponseEntity<ResponseResult<String>> noteAnOrder(@RequestParam String content, Model model){
		try {
		    if (this.selectedItems.size() != 1) {
                throw new SokokanriException("Vui lòng chọn một đơn hàng.");
            }
			this.ordersService.noteABuyingOrder(this.selectedItems.iterator().next(), content);
		} catch (SokokanriException e) {
			model.addAttribute("message", e.getErrorMessage());
		}
		return new ResponseEntity<ResponseResult<String>>(new ResponseResult<String>(1, "Success", null), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/donhang/cho-mua/chon-don-hang", method=RequestMethod.GET)
    public @ResponseBody ResponseEntity<ResponseResult<String>> selectItem(@RequestParam Long id){
        this.selectedItems.add(id);
        return new ResponseEntity<ResponseResult<String>>(new ResponseResult<String>(1, "Success", null), HttpStatus.OK);
    }
	
	@RequestMapping(value = "/donhang/cho-mua/bo-chon-don-hang", method=RequestMethod.GET)
    public @ResponseBody ResponseEntity<ResponseResult<String>> deSelectItem(@RequestParam Long id){
        this.selectedItems.remove(id);
        return new ResponseEntity<ResponseResult<String>>(new ResponseResult<String>(1, "Success", null), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/donhang/cho-mua/chon-tat-ca", method=RequestMethod.GET)
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
    
    @RequestMapping(value = "/donhang/cho-mua/bo-chon-tat-ca", method=RequestMethod.GET)
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
    
    @RequestMapping(value = "/donhang/cho-mua/xoa-don-hang", method=RequestMethod.GET)
    public String deleteOrders(Model model){
        try {
            this.ordersService.deleteItems(this.selectedItems);
            this.selectedItems.clear();
        } catch (SokokanriException ex) {
            model.addAttribute("message", ex.getErrorMessage());
        }
        return "redirect:/donhang/cho-duyet";
    }
    
    @RequestMapping(value = "/donhang/cho-mua/huy-don-hang", method=RequestMethod.GET)
    public String cancelOrders(Model model){
        try {
            this.ordersService.cancelItems(this.selectedItems);
            this.selectedItems.clear();
        } catch (SokokanriException ex) {
            model.addAttribute("message", ex.getErrorMessage());
        }
        return "redirect:/donhang/cho-duyet";
    }
}
