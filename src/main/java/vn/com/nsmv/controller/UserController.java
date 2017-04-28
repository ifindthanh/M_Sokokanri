package vn.com.nsmv.controller;

import java.util.*;

import javax.servlet.http.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.context.i18n.*;
import org.springframework.http.*;
import org.springframework.security.core.*;
import org.springframework.security.core.context.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;
import org.springframework.web.servlet.mvc.support.*;
import org.springframework.web.servlet.view.*;

import vn.com.nsmv.common.*;
import vn.com.nsmv.entity.*;
import vn.com.nsmv.i18n.*;
import vn.com.nsmv.javabean.*;
import vn.com.nsmv.service.*;

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


	
}