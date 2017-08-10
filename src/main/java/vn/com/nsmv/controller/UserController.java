package vn.com.nsmv.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import org.springframework.web.servlet.view.RedirectView;

import vn.com.nsmv.bean.CustomUser;
import vn.com.nsmv.bean.ResponseResult;
import vn.com.nsmv.common.Constants;
import vn.com.nsmv.common.SokokanriException;
import vn.com.nsmv.common.Utils;
import vn.com.nsmv.entity.Role;
import vn.com.nsmv.entity.Transaction;
import vn.com.nsmv.entity.User;
import vn.com.nsmv.i18n.SokokanriMessage;
import vn.com.nsmv.javabean.UserBean;
import vn.com.nsmv.javabean.UserRegistration;
import vn.com.nsmv.javabean.UserSearchCondition;
import vn.com.nsmv.service.UserService;

@Controller
@Scope("session")
public class UserController extends SokokanriCommonController
{

	@Autowired
	private UserService userService;

	private Integer offset;
    private Integer maxResults;
    private Set<Long> selectedItems = new HashSet<Long>();
    private UserSearchCondition searchCondition = new UserSearchCondition();

	@ModelAttribute("userBean")
	public UserBean createUserBeanModel()
	{
		return new UserBean();
	}

	@RequestMapping(value = "/user/tat-ca/0")
	public String init(HttpServletRequest request)
	{
	    this.offset = 0;
        this.maxResults = Constants.MAX_IMAGE_PER_PAGE;
        this.selectedItems.clear();
        this.searchCondition = new UserSearchCondition();
		return "redirect:../tat-ca";
	}
	
	@RequestMapping(value="/user/tat-ca", method = RequestMethod.GET)
	public ModelAndView list(
		HttpServletRequest request,
		Model model,
		Integer offset,
		Integer maxResults)
	{
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
        String message = request.getParameter("message");
        if (!Utils.isEmpty(message)) {
            model.addAttribute("message", message);
        }
        this.doBusiness(model);
        return new ModelAndView("/user/listUser");
	}

	private void doBusiness(Model model) {
	    if (this.searchCondition == null) {
            this.searchCondition = new UserSearchCondition();
        }
        try {
            List<User> allUsers = this.userService.getAllUsers(this.searchCondition, null, this.offset,
                    this.maxResults);
            int count = this.userService.countAllUsers(this.searchCondition);
            model.addAttribute("allUsers", allUsers);
            model.addAttribute("offset", this.offset);
            model.addAttribute("maxResult", this.maxResults);
            model.addAttribute("selectedItems", this.selectedItems);
            model.addAttribute("searchCondition", this.searchCondition);
            model.addAttribute("count", count);
        } catch (SokokanriException ex) {
            model.addAttribute("message", ex.getErrorMessage());
        }
    }
	@RequestMapping(value="/user/tat-ca", method = RequestMethod.POST)
	public RedirectView search(
		HttpServletRequest request,
		Model model,
		UserSearchCondition searchCondition,
		UserBean userBean,
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
		return new RedirectView("../user/searchUserResult");
	}
	@RequestMapping(value = "/user/searchUserResult", method = RequestMethod.GET)
	public ModelAndView searchResult(Model model, HttpServletRequest request)
	{
	    this.doBusiness(model);
	    return new ModelAndView("/user/listUser");
	}


	@RequestMapping(value = "/dang-ky", method = RequestMethod.POST, 
	    consumes = "application/json", produces = "application/json")
	public @ResponseBody ResponseEntity<ResponseResult<String>> register(
       @RequestBody UserRegistration userRegistration)
    {
	    try {
            this.userService.register(userRegistration);
        } catch (SokokanriException e) {
            return new ResponseEntity<ResponseResult<String>>(new ResponseResult<String>(0, e.getErrorMessage(), null), HttpStatus.OK);
        }
	    return new ResponseEntity<ResponseResult<String>>(new ResponseResult<String>(1, "Success", null), HttpStatus.OK);
    }
	
	@RequestMapping(value = "/quen-mat-khau", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<ResponseResult<String>> resetPassword(@RequestParam String email)
    {
        try {
            this.userService.resetPassword(email);
        } catch (SokokanriException e) {
            return new ResponseEntity<ResponseResult<String>>(new ResponseResult<String>(0, e.getErrorMessage(), null), HttpStatus.OK);
        }
        return new ResponseEntity<ResponseResult<String>>(new ResponseResult<String>(1, "Success", null), HttpStatus.OK);
    }
	
	@RequestMapping(value = "/quen-mat-khau", method = RequestMethod.GET)
    public String resetPassword(Model model, @RequestParam String email, @RequestParam String timestamp)
    {
        try {
            this.userService.resetPassword(email, timestamp);
        } catch (SokokanriException e) {
            model.addAttribute("message", e.getErrorMessage());
            return "/orders/error";
        }
        User user = new User();
        user.setEmail(email);
        user.setResetPwTimestamp(timestamp);
        model.addAttribute("user", user);
        return "/user/changePassword1";
    }
	
	@RequestMapping(value = "/doi-mat-khau", method = RequestMethod.POST)
    public String changePassword(Model model, @ModelAttribute User user)
    {
        try {
            this.userService.changePassword(user);
        } catch (SokokanriException e) {
            model.addAttribute("errorMessage", e.getErrorMessage());
            return "/user/changePassword1";
        }
        model.addAttribute("errorMessage", SokokanriMessage.getMessageInforChangePasswordSuccessfully(LocaleContextHolder.getLocale()));
        return "/user/changePassword1";
    }
	
	@RequestMapping(value = "/thay-doi-mat-khau", method = RequestMethod.GET)
    public String changePassword(Model model)
    {
        User user = new User();
        CustomUser customUser = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user.setEmail(customUser.getEmail());
        model.addAttribute("user", user);
        return "/user/userChangePassword";
    }
	
	@RequestMapping(value = "/thay-doi-mat-khau", method = RequestMethod.POST)
	public RedirectView updatePassword(Model model, @ModelAttribute User user, RedirectAttributes redirectAttributes)
    {
        try {
            this.userService.updatePassword(user);
        } catch (SokokanriException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getErrorMessage());
            return new RedirectView("thay-doi-mat-khau-kq");
        }
        redirectAttributes.addFlashAttribute("errorMessage", SokokanriMessage.getMessageInforChangePasswordSuccessfully(LocaleContextHolder.getLocale()));
        return new RedirectView("thay-doi-mat-khau-kq");
    }
	
	@RequestMapping(value = "/thay-doi-mat-khau-kq", method = RequestMethod.GET)
	public String updatePasswordResult(Model model){
	    User user = new User();
	    CustomUser customUser = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user.setEmail(customUser.getEmail());
        model.addAttribute("user", user);
	    return "/user/userChangePassword"; 
	}
	
	@RequestMapping(value = "/user/chon-don-hang", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseResult<String>> selectItem(@RequestParam Long id){
        this.selectedItems.add(id);
        return new ResponseEntity<ResponseResult<String>>(new ResponseResult<String>(1, "Success", null), HttpStatus.OK);
    }
    
	@RequestMapping(value = "/user/bo-chon-don-hang", method = RequestMethod.GET)
    protected @ResponseBody ResponseEntity<ResponseResult<String>> deSelectItem(@RequestParam Long id){
        this.selectedItems.remove(id);
        return new ResponseEntity<ResponseResult<String>>(new ResponseResult<String>(1, "Success", null), HttpStatus.OK);
    }
	
	@RequestMapping(value = "/user/chon-tat-ca", method = RequestMethod.GET)
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
    
	@RequestMapping(value = "/user/bo-chon-tat-ca", method = RequestMethod.GET)
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
    
	@RequestMapping(value="/user/xoa-user", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseResult<String>> delete(Model model) {
        try {
            this.userService.deleteUsers(this.selectedItems);
            this.selectedItems.clear();
        } catch (SokokanriException ex) {
            return new ResponseEntity<ResponseResult<String>>(new ResponseResult<String>(0, ex.getErrorMessage(), null), HttpStatus.OK);
        }
        return new ResponseEntity<ResponseResult<String>>(new ResponseResult<String>(1, "Success", null), HttpStatus.OK);
    }
	
	@RequestMapping(value="/user/{userId}", method = RequestMethod.GET)
    public ModelAndView getDetails(Model model, @PathVariable Long userId) {
        try {
            User user = this.userService.getUserByCode(userId);
            if (user == null) {
                throw new SokokanriException(SokokanriMessage.getMessageErrorUserNotExists(LocaleContextHolder.getLocale()));
            }
            model.addAttribute("user", user);
            model.addAttribute("roles", this.userService.getAllRoles(user.getId()));
        } catch (SokokanriException ex) {
            return new ModelAndView("/user/viewUser");
        }
        return new ModelAndView("/user/viewUser");
    }
	
	@RequestMapping(value="/editUser/{userId}", method = RequestMethod.GET)
    public ModelAndView editDetails(Model model, @PathVariable Long userId) {
        try {
            User user = this.userService.getUserByCode(userId);
            if (user == null) {
                throw new SokokanriException(SokokanriMessage.getMessageErrorUserNotExists(LocaleContextHolder.getLocale()));
            }
            model.addAttribute("user", user);
            model.addAttribute("roles", this.userService.getAllRoles(user.getId()));
        } catch (SokokanriException ex) {
            return new ModelAndView("/user/editUser");
        }
        return new ModelAndView("/user/editUser");
    }
	
	@RequestMapping(value="/editUser/luu-thong-tin", method = RequestMethod.POST)
    public RedirectView save(Model model, @ModelAttribute User userForm,  RedirectAttributes redirectAttributes) {
        try {
            User user = this.userService.saveInformation(userForm);
            redirectAttributes.addFlashAttribute("user", user);
        } catch (SokokanriException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getErrorMessage());
            redirectAttributes.addFlashAttribute("user", userForm);
        }
        return new RedirectView("../saveUser/" + userForm.getId());
    }
	
	@RequestMapping(value="/saveUser/{userId}", method = RequestMethod.GET)
	public String saveResult(Model model, @PathVariable Long userId){
        User user = (User) model.asMap().get("user");
        if (user == null) {
            try {
                user = this.userService.getUserByCode(userId);
                if (user == null) {
                    return "redirect:/editUser/" + userId; 
                }
            } catch (SokokanriException e) {
                model.addAttribute("errorMessage", e.getErrorMessage());
            }
        }
        model.addAttribute("user", user);
        model.addAttribute("roles", this.userService.getAllRoles(userId));
        return "/user/editUser"; 
        
    } 
	
	@RequestMapping(value="/user/them-moi", method = RequestMethod.GET)
    public ModelAndView moveToAddUserPage(Model model) {
	    User user = (User) model.asMap().get("user");
	    if (user == null) {
	        model.addAttribute("user", new User());
	        model.addAttribute("roles", new ArrayList<Role>());
	    } else {
	        model.addAttribute("user", user);
	        model.addAttribute("roles", user.getRoles());
	    }
        return new ModelAndView("user/addUser");
    }
	
	@RequestMapping(value="/user/them-moi", method = RequestMethod.POST)
    public RedirectView addUse(Model model, @ModelAttribute User userForm, RedirectAttributes redirectAttributes) {
	    try {
            Long userId = this.userService.addUser(userForm);
            return new RedirectView(String.valueOf(userId));
        } catch (SokokanriException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getErrorMessage());
            redirectAttributes.addFlashAttribute("user", userForm);
            return new RedirectView("them-moi");
        }
        
    }
	
	@RequestMapping(value="/user/vi-tien/{userId}", method = RequestMethod.GET)
    public ModelAndView transactionManagement(Model model, @PathVariable Long userId) {
        try {
            User user = this.userService.getUserByCode(userId);
            if (user == null) {
                throw new SokokanriException(SokokanriMessage.getMessageErrorUserNotExists(LocaleContextHolder.getLocale()));
            }
            if (!this.userService.getAllRoles(user.getId()).contains(new Role("U"))) {
                throw new SokokanriException(SokokanriMessage.getMessageErrorAddTransactionNotAllow(LocaleContextHolder.getLocale()));
            }
            Transaction transaction = new Transaction();
            transaction.setUser(user);
            model.addAttribute("transaction", transaction);
            return new ModelAndView("/user/walletManagement");
        } catch (SokokanriException e) {
            model.addAttribute("message", e.getErrorMessage());
            return new ModelAndView("/orders/error");
        }
        
    }
	
	@RequestMapping(value="/user/vi-tien/them-giao-dich", method = RequestMethod.POST)
    public RedirectView addTransaction(Model model, @ModelAttribute Transaction transaction, RedirectAttributes redirectAttributes) {
        try {
            this.userService.saveTransaction(transaction);
            redirectAttributes.addFlashAttribute("inforMessage", SokokanriMessage.getMessageInforAddTransactionSuccessfully(LocaleContextHolder.getLocale()));
        } catch (SokokanriException e) {
            redirectAttributes.addFlashAttribute("message", e.getErrorMessage());
        }
        redirectAttributes.addFlashAttribute("transaction", transaction);
        return new RedirectView("them-giao-dich");
    }
	
	@RequestMapping(value="/user/vi-tien/them-giao-dich", method = RequestMethod.GET)
    public ModelAndView addTransactionResult(Model model) {
        Transaction transaction = (Transaction) model.asMap().get("transaction");
        if (transaction == null) {
            model.addAttribute("message", SokokanriMessage.getMessageErrorInvalidRequest(LocaleContextHolder.getLocale()));
            return new ModelAndView("/orders/error");
        }
        model.addAttribute("transaction", transaction);
        return new ModelAndView("/user/walletManagement");
    }
	
	@RequestMapping(value="/user/vi-tien/tat-ca-giao-dich", method = RequestMethod.GET)
    public ModelAndView walletManagement(Model model, @PathVariable Long userId) {
        try {
            User user = this.userService.getUserByCode(userId);
            if (user == null) {
                throw new SokokanriException(SokokanriMessage.getMessageErrorUserNotExists(LocaleContextHolder.getLocale()));
            }
            if (!this.userService.getAllRoles(user.getId()).contains(new Role("U"))) {
                throw new SokokanriException(SokokanriMessage.getMessageErrorAddTransactionNotAllow(LocaleContextHolder.getLocale()));
            }
            Transaction transaction = new Transaction();
            transaction.setUser(user);
            model.addAttribute("transaction", transaction);
            return new ModelAndView("/user/walletManagement");
        } catch (SokokanriException e) {
            model.addAttribute("message", e.getErrorMessage());
            return new ModelAndView("/orders/error");
        }
        
    }
	
	@RequestMapping(value="/xem-thong-tin", method = RequestMethod.GET)
    public ModelAndView viewInformation(Model model) {
        try {
            User user = this.userService.getUserByCode(((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId());
            if (user == null) {
                throw new SokokanriException(SokokanriMessage.getMessageErrorUserNotExists(LocaleContextHolder.getLocale()));
            }
            model.addAttribute("user", user);
            model.addAttribute("selfEdit", Boolean.TRUE);
            model.addAttribute("roles", this.userService.getAllRoles(user.getId()));
        } catch (SokokanriException ex) {
            model.addAttribute("selfEdit", Boolean.TRUE);
            return new ModelAndView("/user/viewUser");
        }
        return new ModelAndView("/user/viewUser");
    }
	
	@RequestMapping(value="/sua-thong-tin", method = RequestMethod.GET)
    public ModelAndView editInformation(Model model) {
        try {
            User user = this.userService.getUserByCode(((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId());
            if (user == null) {
                throw new SokokanriException(SokokanriMessage.getMessageErrorUserNotExists(LocaleContextHolder.getLocale()));
            }
            model.addAttribute("user", user);
            model.addAttribute("roles", this.userService.getAllRoles(user.getId()));
            model.addAttribute("selfEdit", Boolean.TRUE);
        } catch (SokokanriException ex) {
            model.addAttribute("selfEdit", Boolean.TRUE);
            return new ModelAndView("/user/editUser");
        }
        return new ModelAndView("/user/editUser");
    }
}