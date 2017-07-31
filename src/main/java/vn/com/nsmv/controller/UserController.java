package vn.com.nsmv.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
import vn.com.nsmv.common.HTMLUtils;
import vn.com.nsmv.common.SokokanriException;
import vn.com.nsmv.common.Utils;
import vn.com.nsmv.entity.User;
import vn.com.nsmv.i18n.SokokanriMessage;
import vn.com.nsmv.javabean.UserBean;
import vn.com.nsmv.javabean.UserRegistration;
import vn.com.nsmv.service.UserService;

@Controller
public class UserController extends SokokanriCommonController
{

	@Autowired
	private UserService userService;

	//TODO fix this issue
	private String userCd_CP;

	@ModelAttribute("userBean")
	public UserBean createUserBeanModel()
	{
		return new UserBean();
	}

	@RequestMapping(value = "/user/list/0")
	public String listUsers(HttpServletRequest request)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null || Utils.isEmpty(auth.getName()))
		{
			return "redirect:../../login";
		}
		request.getSession().setAttribute("selectedMenu", 5);
		request.getSession().setAttribute("userBean", new UserBean());
		return "redirect:../list";
	}
	@RequestMapping({ "/user/list" })
	public ModelAndView list(
		HttpServletRequest request,
		Model model,
		Integer offset,
		Integer maxResults)
	{
		HttpSession session = request.getSession();
		session.setAttribute("selectedMenu", 5);
		session.setAttribute("offsetInUser", offset);
		session.setAttribute("maxResultsInUser", maxResults);
		return this.getListUser(model, offset, maxResults);
	}

	/******************************************
	 * get list user
	 *
	 * @param Model model,Integer offset, Integer maxResults
	 * @return ModelAndView
	 ******************************************/
	public ModelAndView getListUser(Model model, Integer offset, Integer maxResults)
	{
		List<User> list = userService.listUser(offset, maxResults);
		Long count = userService.countListUser();
		model.addAttribute("list", list);
		model.addAttribute("count", count);
		model.addAttribute("offset", offset);
		model.addAttribute("maxResult", maxResults);
		model.addAttribute("mode", 1);
		return new ModelAndView("user/listUser", "command", new UserBean());
	}

	/******************************************
	 * Handle action register user button click
	 *
	 * @param Model
	 * @return ModelAndView
	 ******************************************/
	@RequestMapping(value = "/user/registerAnUser")
	public ModelAndView registerUser(HttpServletRequest request, Model model)
	{
		return new ModelAndView("user/register1", "command", new UserBean());
	}

	/******************************************
	 * Handle action delete user button click
	 *
	 * @param Model
	 * @return ModelAndView
	 ******************************************/
	@RequestMapping(value = "/user/list", params = "deleteUser", method = RequestMethod.POST)
	public ModelAndView deleteUser(
		Model model,
		HttpServletRequest request,
		@RequestParam(value = "table_records", required = false) String[] listId,
		Integer offset,
		Integer maxResults)
	{
		userService.deleteUserById(listId);
		return this.getListUser(model, offset, maxResults);
	}

	/******************************************
	 * Handle action search button click
	 *
	 * @param HttpServletRequest,
	 *            Model, UserBean, Integer, Integer
	 * @return ModelAndView
	 ******************************************/
	@RequestMapping(value = "/user/list", params = "search")
	public RedirectView search1(
		HttpServletRequest request,
		Model model,
		RedirectAttributes redirectAttributes,
		UserBean userBean,
		Integer offset,
		Integer maxResults)
	{
		String txtSearch = HTMLUtils.escapeForJavascriptString(userBean.getTxtSearch());
		if (Utils.isEmpty(txtSearch))
		{
			request.getSession().setAttribute("userBean", userBean);
			return new RedirectView("../user/list/0");
		}
		request.getSession().setAttribute("offsetInUser", offset);
		userBean.setTxtSearch(txtSearch);
		redirectAttributes.addFlashAttribute("userBean", userBean);
		redirectAttributes.addAttribute("txtSearch", txtSearch);
		redirectAttributes.addAttribute("offset", offset);
		redirectAttributes.addAttribute("maxResults", maxResults);
		return new RedirectView("../user/searchUserResult");
	}
	@RequestMapping(value = "/user/searchUserResult", method = RequestMethod.GET)
	public ModelAndView searchResult(Model model, HttpServletRequest request)
	{
		String txtSearch = request.getParameter("txtSearch");
		String offsetStr = request.getParameter("offset");
		String maxResultsStr = request.getParameter("maxResultsStr");
		UserBean userBean = (UserBean) model.asMap().get("userBean");
		if (userBean == null)
		{
			userBean = new UserBean();
		}
		userBean.setTxtSearch(txtSearch);
		Integer offset = null;
		Integer maxResults = null;
		try
		{
			if (!Utils.isEmpty(offsetStr))
			{
				offset = Integer.valueOf(offsetStr);
			}
			if (!Utils.isEmpty(maxResultsStr))
			{
				maxResults = Integer.valueOf(maxResultsStr);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return this.getListUserBySearchCondition(request, model, userBean, offset, maxResults);
	}

	/******************************************
	 * Get business card deleted by search condition
	 *
	 * @param HttpServletRequest,
	 *            Model,BusinessCardBean,Integer,Integer
	 * @return ModelAndView
	 ******************************************/
	private ModelAndView getListUserBySearchCondition(
		HttpServletRequest request,
		Model model,
		UserBean userBean,
		Integer offset,
		Integer maxResults)
	{

		List<User> list = new ArrayList<User>();
		long count = 0;
		try
		{
			list = userService
				.getListUserByCondition(userBean.getTxtSearch().trim(), offset, maxResults);
			if (list != null)
			{
				count = userService.countUserBySearchCondition(userBean.getTxtSearch().trim());
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		request.getSession().setAttribute("userBean", userBean);
		model.addAttribute("userBean", userBean);
		model.addAttribute("list", list);
		model.addAttribute("count", count);
		model.addAttribute("offset", offset);
		model.addAttribute("mode", 2);

		return new ModelAndView("user/listUser", "command", userBean);
	}

	/******************************************
	 * Handle action search paging button click
	 *
	 * @param HttpServletRequest,
	 *            HttpSession, Model, BusinessCardBean, Integer, Integer
	 * @return ModelAndView
	 ******************************************/
	@RequestMapping(value = "/user/user_search")
	public ModelAndView searchUserPaging(
		HttpServletRequest request,
		HttpSession session,
		UserBean userBean,
		Model model,
		Integer offset,
		Integer maxResults)
	{
		userBean = (UserBean) session.getAttribute("userBean");
		request.getSession().setAttribute("offsetInUser", offset);
		if (Utils.isEmpty(userBean.getTxtSearch().trim()))
		{
			return this.getListUser(model, offset, maxResults);
		}
		return this.getListUserBySearchCondition(request, model, userBean, offset, maxResults);
	}

	/******************************************
	 * Handle action reset button click
	 *
	 * @param HttpServletRequest,
	 *            HttpSession, Model, BusinessCardBean, Integer, Integer
	 * @return ModelAndView
	 ******************************************/
	@RequestMapping(value = "/user/resetUser/{userCd}")
	public String restoreCard(
		Model model,
		HttpServletRequest request,
		@PathVariable("userCd") String userCd,
		Integer offset,
		Integer maxResults)
	{
		try
		{
			userService.resetUser(userCd);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return "redirect:../list";
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
            return null;
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
	
}