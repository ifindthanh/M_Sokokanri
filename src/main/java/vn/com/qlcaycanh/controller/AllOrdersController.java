package vn.com.qlcaycanh.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import vn.com.qlcaycanh.common.MyException;
import vn.com.qlcaycanh.entity.*;
import vn.com.qlcaycanh.javabean.OrderSearchCondition;
import vn.com.qlcaycanh.service.*;

@Controller
@Scope("session")
public class AllOrdersController extends AbstractController{

	private OrderSearchCondition searchCondition = new OrderSearchCondition();
	@Autowired
	private ImportingService ordersService;

	@Autowired
	private TreeCategoryService treeCategoryService;

	@Autowired
	private ProviderService providerService;
	@Autowired UserService userService;

	@RequestMapping(value = "/donhang/tat-ca/0", method=RequestMethod.GET)
	public String init()
	{
	    super.initController();
		this.searchCondition = new OrderSearchCondition();
		return "redirect:/donhang/tat-ca";
	}

	@RequestMapping(value = "/donhang/tat-ca", method=RequestMethod.GET)
	public ModelAndView listAllOrders(HttpServletRequest request, Model model, Integer offset, Integer maxResults) {
		request.getSession().setAttribute("listType", 1);
		super.settingUpParams(request, model, offset, maxResults);
		return new ModelAndView("/orders/allOrders");
	}

	@RequestMapping(value = "/donhang/tat-ca", method=RequestMethod.POST)
	public RedirectView listAllOrdersPOST(HttpServletRequest request, Model model,
			Integer offset, Integer maxResults, OrderSearchCondition searchCondition) {

	    this.reset(offset, maxResults);
		if (searchCondition != null)
		{
			this.searchCondition = searchCondition;
		}
		request.getSession().setAttribute("listType", 1);

		return new RedirectView("tat-ca-tim-kiem");
	}

	@RequestMapping(value = "/donhang/tat-ca-tim-kiem", method = RequestMethod.GET)
	public String searchResult(HttpServletRequest request, Model model)
	{
		request.getSession().setAttribute("listType", 1);
		this.listItems(model);
		return "/orders/allOrders";
	}

	public void listItems(Model model) {
		if (this.searchCondition == null) {
			this.searchCondition = new OrderSearchCondition();
		}
		try {

			List<ImportingEntity> allOrders = this.ordersService.getAll(this.searchCondition, null, this.getOffset(),
					this.getMaxResults());
			int count = this.ordersService.countAll(this.searchCondition);
			List<Provider> allBrands = this.providerService.getAll(null, null, null, null);
			List<TreeCategory> allTrees = this.treeCategoryService.getAll(null, null, null, null);
			model.addAttribute("allBrands", allBrands);
			model.addAttribute("searchCondition", searchCondition);
			model.addAttribute("allTrees", allTrees);
			model.addAttribute("items", allOrders);
			model.addAttribute("offset", this.getOffset());
			model.addAttribute("maxResult", this.getMaxResults());
			model.addAttribute("count", count);
		} catch (MyException ex) {
			model.addAttribute("message", ex.getErrorMessage());
		}
	}

	@RequestMapping(value = "/donhang/admin/{orderId}")
	public ModelAndView viewAnOrder(HttpServletRequest request, Model model, @PathVariable Long orderId) {
		try {
			ImportingEntity item = this.ordersService.getById(orderId);
			if (item == null) {
				throw new MyException("Phiếu thu không tồn tại");
			}

			model.addAttribute("category", item);
			return new ModelAndView("/admin/orderDetail");
		} catch (MyException ex) {
			model.addAttribute("message", ex.getErrorMessage());
			model.addAttribute("category",  new Category());
			return new ModelAndView("/orders/error");
		}

	}
}
