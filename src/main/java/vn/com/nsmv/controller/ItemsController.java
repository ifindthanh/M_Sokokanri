package vn.com.nsmv.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
import vn.com.nsmv.entity.Category;
import vn.com.nsmv.entity.Item;
import vn.com.nsmv.i18n.SokokanriMessage;
import vn.com.nsmv.javabean.UploadBean;
import vn.com.nsmv.service.OrdersService;

@Controller
@Scope("session")
public class ItemsController {
	
	@Autowired
	private OrdersService ordersService;
	
	@RequestMapping(value = "/donhang/xem-don-hang/{orderId}", method=RequestMethod.GET)
    public ModelAndView viewOrderInformation(HttpServletRequest request, Model model, @PathVariable Long orderId) {
        try {
            Item item = this.ordersService.getItem(orderId);
            if (item == null) {
                throw new SokokanriException(SokokanriMessage.getMessageErrorOrderNotExist(LocaleContextHolder.getLocale()));
            }
            if (Utils.isUser()) {
                if (!item.getUser().getId().equals(((CustomUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId())) {
                    throw new SokokanriException(SokokanriMessage.getMessageErrorNoPermissionInOrder(LocaleContextHolder.getLocale()));
                }
            }
            
            boolean readOnly = !(Utils.hasRole(Constants.ROLE_A) || Utils.hasRole(Constants.ROLE_C) || Utils.hasRole(Constants.ROLE_U) 
                || (Utils.isUser() && item.getStatus() != null && item.getStatus() == 0 ));
            
            model.addAttribute("read_only", Boolean.valueOf(readOnly));
            model.addAttribute("item", item);
            return new ModelAndView("/orders/itemDetails");
        } catch (SokokanriException ex) {
            model.addAttribute("message", ex.getErrorMessage());
            return new ModelAndView("/orders/errorInAjax");
        }
        
    }
	
	@RequestMapping(value = "/donhang/{orderId}", method=RequestMethod.GET)
	public ModelAndView viewAnOrder(HttpServletRequest request, Model model, @PathVariable Long orderId) {
		try {
			Category category = this.ordersService.getCategory(orderId);
			if (category == null) {
				throw new SokokanriException(SokokanriMessage.getMessageErrorOrderNotExist(LocaleContextHolder.getLocale()));
			}
			if (Utils.isUser()) {
				if (!category.getUser().getId().equals(((CustomUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId())) {
				    throw new SokokanriException(SokokanriMessage.getMessageErrorNoPermissionInOrder(LocaleContextHolder.getLocale()));
				}
			}
			
			boolean readOnly = !(Utils.hasRole(Constants.ROLE_A) || Utils.hasRole(Constants.ROLE_C) || Utils.hasRole(Constants.ROLE_U) 
			    || (Utils.isUser() && category.getStatus() != null && category.getStatus() == 0 ));
			
			model.addAttribute("read_only", Boolean.valueOf(readOnly));
			model.addAttribute("category", category);
			return new ModelAndView("/orders/orderDetail");
		} catch (SokokanriException ex) {
			model.addAttribute("message", ex.getErrorMessage());
			model.addAttribute("category",  new Category());
			return new ModelAndView("/orders/error");
		}
		
	}
	
	
	@RequestMapping(value = "/donhang/xoa-item", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseResult<String>> deleteItem(HttpServletRequest request, Model model, @RequestParam Long id) {
		try {
			this.ordersService.deleteItemById(id);
			return new ResponseEntity<ResponseResult<String>>(new ResponseResult<String>(1, "Success", null), HttpStatus.OK);
		} catch (SokokanriException e) {
			return new ResponseEntity<ResponseResult<String>>(new ResponseResult<String>(0, e.getErrorMessage(), null), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/donhang/luu-item", method=RequestMethod.POST,  
	    consumes = "application/json", produces = "application/json")
    public @ResponseBody ResponseEntity<ResponseResult<String>> saveItem(HttpServletRequest request, @RequestBody Item item) {
        try {
            this.ordersService.saveItem(item);
            return new ResponseEntity<ResponseResult<String>>(new ResponseResult<String>(1, "Success", null), HttpStatus.OK);
        } catch (SokokanriException e) {
            return new ResponseEntity<ResponseResult<String>>(new ResponseResult<String>(0, e.getErrorMessage(), null), HttpStatus.OK);
        }
    }
	
	@RequestMapping(value = "/donhang/tao-moi", method=RequestMethod.GET)
	public ModelAndView createNew(HttpServletRequest request, Model model) {
		Category category = createInitOrder();
		model.addAttribute("order", category);
		return new ModelAndView("/orders/createNew");
	}
	
	@RequestMapping(value = "/donhang/tao-tu-file", method=RequestMethod.GET)
	public ModelAndView createFromFile(HttpServletRequest request, Model model) {
		UploadBean uploadBean = new UploadBean();
		CustomUser customUser = (CustomUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		uploadBean.setFullName(customUser.getDisplayName());
		uploadBean.setPhone(customUser.getPhone());
		uploadBean.setEmail(customUser.getEmail());
		model.addAttribute("uploadBean", uploadBean);
		return new ModelAndView("/common/upload");
	}
	
	@RequestMapping(value = "/donhang/tao-tu-file", method=RequestMethod.POST)
	public ModelAndView uploadOneFileHandlerPOST(
			HttpServletRequest request,
			Model model, RedirectAttributes redirectAttributes, 
			@ModelAttribute("uploadFile") UploadBean myUpload)
		{
		try
		{
			Long orderId = this.ordersService.doUpload(myUpload);
			return new ModelAndView("redirect:/donhang/" + orderId);
		}
		catch (SokokanriException ex)
		{
			redirectAttributes.addFlashAttribute(
					"message", ex.getErrorMessage());
			return new ModelAndView("redirect:/donhang/tao-tu-file");
		}
	}
	
	private Category createInitOrder() {
		Category category = new Category();
		List<Item> items = new  ArrayList<Item>();
		items.add(new Item());
		items.add(new Item());
		category.setItems(items);
		CustomUser customUser = (CustomUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		category.setFullName(customUser.getDisplayName());
		category.setPhone(customUser.getPhone());
		category.setEmail(customUser.getEmail());
		category.setUserId(customUser.getUserId());
		return category;
	}
	
	@RequestMapping(value = "/donhang/tao-moi", method=RequestMethod.POST)
	public ModelAndView createOrder(@ModelAttribute("order")Category category, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		try {
			Long createOrder = this.ordersService.createOrder(category);
			return new ModelAndView("redirect:/donhang/" + createOrder);
		} catch (SokokanriException ex) {
			redirectAttributes.addFlashAttribute(
					"message", ex.getErrorMessage());
			redirectAttributes.addFlashAttribute(
					"order", category);
			return new ModelAndView("redirect:/donhang/tao-moi-error");
		}
	}
	
	@RequestMapping(value = "/donhang/tao-moi-error", method=RequestMethod.GET)
	public String createError(Model model){
		if (!model.containsAttribute("message"))
		{
			return "redirect:tao-moi";
		}
		model.addAttribute("order", model.asMap().get("order"));
		return "/orders/createNew";
	}
	
	@RequestMapping(value = "/donhang/luu-don-hang", method=RequestMethod.POST)
	public ModelAndView saveOrder(@ModelAttribute("order")Category category, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		try {
			this.ordersService.saveOrder(category);
			return new ModelAndView("redirect:/donhang/" + category.getId());
		} catch (SokokanriException ex) {
			redirectAttributes.addFlashAttribute(
					"message", ex.getErrorMessage());
			redirectAttributes.addFlashAttribute(
					"order", category);
			return new ModelAndView("redirect:/donhang/" + category.getId());
		}
	}
	
}
