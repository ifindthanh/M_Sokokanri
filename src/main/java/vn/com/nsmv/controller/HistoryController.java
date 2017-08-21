package vn.com.nsmv.controller;

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

import vn.com.nsmv.common.SokokanriException;
import vn.com.nsmv.entity.ItemHistory;
import vn.com.nsmv.javabean.SearchCondition;
import vn.com.nsmv.service.OrdersService;

@Controller
@Scope("session")
public class HistoryController extends AbstractController{
	
	@Autowired
	private OrdersService ordersService;
	
	private SearchCondition searchCondition = new SearchCondition();
	
	@RequestMapping(value = "/donhang/xem-lich-su/{orderId}", method=RequestMethod.GET)
    public ModelAndView viewOrderHistory(HttpServletRequest request, Model model, @PathVariable Long orderId, Integer offset, Integer maxResults) {
	    this.searchCondition = new SearchCondition();
	    searchCondition.setOrderId(orderId);
	    super.listAllOrdersInPage(request, model, offset, maxResults);
	    return new ModelAndView("/orders/orderHistory");
    }

    @Override
    protected void doBusiness(Model model) {
        model.addAttribute("orderId", this.searchCondition.getOrderId());
        List<ItemHistory> allHistory;
        try {
            allHistory = this.ordersService.getAllHistory(this.searchCondition, this.getMaxResults(), this.getOffset());
            model.addAttribute("allOrders", allHistory);
        } catch (SokokanriException ex) {
            model.addAttribute("message", ex.getErrorMessage());
        }
        
    }
	
}
