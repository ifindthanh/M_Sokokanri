package vn.com.qlcaycanh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.com.qlcaycanh.bean.CustomUser;
import vn.com.qlcaycanh.bean.ResponseResult;
import vn.com.qlcaycanh.common.Constants;
import vn.com.qlcaycanh.common.MyException;
import vn.com.qlcaycanh.common.Utils;
import vn.com.qlcaycanh.entity.Category;
import vn.com.qlcaycanh.entity.ImportingEntity;
import vn.com.qlcaycanh.entity.Provider;
import vn.com.qlcaycanh.entity.TreeCategory;
import vn.com.qlcaycanh.i18n.IMessage;
import vn.com.qlcaycanh.service.ImportingService;
import vn.com.qlcaycanh.service.ProviderService;
import vn.com.qlcaycanh.service.TreeCategoryService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@Scope("session")
public class ItemsController {

    @Autowired
    private ImportingService importingService;

    @Autowired
    private ProviderService providerService;

    @Autowired
    private TreeCategoryService treeService;

    @RequestMapping(value = "/donhang/xem-don-hang/{orderId}", method = RequestMethod.GET)
    public ModelAndView viewOrderInformation(HttpServletRequest request, Model model, @PathVariable Long orderId) {
        try {
            ImportingEntity item = this.importingService.getById(orderId);
            if (item == null) {
                throw new MyException(IMessage.getMessageErrorOrderNotExist(LocaleContextHolder.getLocale()));
            }
            model.addAttribute("item", item);
            return new ModelAndView("/orders/itemDetails");
        } catch (MyException ex) {
            model.addAttribute("message", ex.getErrorMessage());
            return new ModelAndView("/orders/errorInAjax");
        }

    }

    @RequestMapping(value = "/donhang/{orderId}", method = RequestMethod.GET)
    public ModelAndView viewAnOrder(HttpServletRequest request, Model model, @PathVariable Long orderId) {
        try {
            Category category = this.importingService.getCategoryById(orderId);
            if (category == null) {
                throw new MyException(IMessage.getMessageErrorOrderNotExist(LocaleContextHolder.getLocale()));
            }
            if (Utils.isUser()) {
                if (!category.getUser().getId().equals(((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId())) {
                    throw new MyException(IMessage.getMessageErrorNoPermissionInOrder(LocaleContextHolder.getLocale()));
                }
            }
            List<TreeCategory> allTreeGroups = treeService.getAll(null, null, null, null);
            model.addAttribute("allTrees", allTreeGroups);
            List<Provider> allProviders = providerService.getAll(null, null, null, null);
            model.addAttribute("allProviders", allProviders);

            boolean readOnly = !(Utils.hasRole(Constants.ROLE_A) || Utils.hasRole(Constants.ROLE_C) || Utils.hasRole(Constants.ROLE_U)
                    || (Utils.isUser() && category.getStatus() != null && category.getStatus() == 0));

            model.addAttribute("read_only", Boolean.valueOf(readOnly));
            model.addAttribute("category", category);
            return new ModelAndView("/orders/orderDetail");
        } catch (MyException ex) {
            model.addAttribute("message", ex.getErrorMessage());
            model.addAttribute("category", new Category());
            return new ModelAndView("/orders/error");
        }

    }


    @RequestMapping(value = "/donhang/xoa-item", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<ResponseResult<String>> deleteItem(HttpServletRequest request, Model model, @RequestParam Long id) {
        try {
            this.importingService.delete(id);
            return new ResponseEntity<ResponseResult<String>>(new ResponseResult<String>(1, "Success", null), HttpStatus.OK);
        } catch (MyException e) {
            return new ResponseEntity<ResponseResult<String>>(new ResponseResult<String>(0, e.getErrorMessage(), null), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/donhang/luu-item", method = RequestMethod.POST,
            consumes = "application/json", produces = "application/json")
    public @ResponseBody ResponseEntity<ResponseResult<String>> saveItem(HttpServletRequest request, @RequestBody ImportingEntity item) throws MyException {
        this.importingService.updateTree(item);
        return new ResponseEntity<ResponseResult<String>>(new ResponseResult<String>(1, "Success", null), HttpStatus.OK);
    }

    @RequestMapping(value = "/donhang/tao-moi", method = RequestMethod.GET)
    public ModelAndView createNew(HttpServletRequest request, Model model) throws MyException {
        Category category = createInitOrder();
        model.addAttribute("order", category);
        List<TreeCategory> allTreeGroups = treeService.getAll(null, null, null, null);
        model.addAttribute("allTrees", allTreeGroups);
        List<Provider> allProviders = providerService.getAll(null, null, null, null);
        model.addAttribute("allProviders", allProviders);
        return new ModelAndView("/orders/createNew");
    }



    private Category createInitOrder() {
        Category category = new Category();
        List<ImportingEntity> items = new ArrayList<ImportingEntity>();
        items.add(new ImportingEntity());
        items.add(new ImportingEntity());
        category.setItems(items);
		CustomUser customUser = (CustomUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		category.setFullName(customUser.getDisplayName());
		category.setPhone(customUser.getPhone());
		category.setEmail(customUser.getEmail());
		category.setUserId(customUser.getUserId());
        return category;
    }

    @RequestMapping(value = "/donhang/tao-moi", method = RequestMethod.POST)
    public ModelAndView createOrder(@ModelAttribute("order") Category category, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        try {
            Long createOrder = this.importingService.createOrder(category);
            return new ModelAndView("redirect:/donhang/" + createOrder);
        } catch (MyException ex) {
            redirectAttributes.addFlashAttribute(
                    "message", ex.getErrorMessage());
            redirectAttributes.addFlashAttribute(
                    "order", category);
            return new ModelAndView("redirect:/donhang/tao-moi-error");
        }
    }

    @RequestMapping(value = "/donhang/tao-moi-error", method = RequestMethod.GET)
    public String createError(Model model) {
        if (!model.containsAttribute("message")) {
            return "redirect:tao-moi";
        }
        model.addAttribute("order", model.asMap().get("order"));
        return "/orders/createNew";
    }

    @RequestMapping(value = "/donhang/luu-don-hang", method = RequestMethod.POST)
    public ModelAndView saveOrder(@ModelAttribute("order") Category category, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        try {
            this.importingService.saveOrder(category);
        } catch (MyException e) {
            redirectAttributes.addFlashAttribute(
                    "message", e.getErrorMessage());
            redirectAttributes.addFlashAttribute(
                    "order", category);
            return new ModelAndView("redirect:/donhang/tao-moi-error");
        }
        return new ModelAndView("redirect:/donhang/" + category.getId());
    }

}
