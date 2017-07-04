package vn.com.nsmv.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.servlet.view.RedirectView;

import vn.com.nsmv.bean.CustomUser;
import vn.com.nsmv.bean.ResponseResult;
import vn.com.nsmv.common.Constants;
import vn.com.nsmv.common.SokokanriException;
import vn.com.nsmv.common.Utils;
import vn.com.nsmv.entity.Bill;
import vn.com.nsmv.javabean.SearchCondition;
import vn.com.nsmv.service.OrdersService;

@Controller
@Scope("session")
public class StorageController extends AbstractController {
	
	private SearchCondition searchCondition = new SearchCondition(5);
	
	@Autowired
	private OrdersService ordersService;
	
	
	@RequestMapping(value = "/donhang/da-nhap-kho/0", method=RequestMethod.GET)
	public String init()
	{
		this.searchCondition =  new SearchCondition(5);
		super.initController();
		return "redirect:/donhang/da-nhap-kho";
	}
	
	@RequestMapping(value = "/donhang/da-nhap-kho", method=RequestMethod.GET)
	public ModelAndView listAllOrders(HttpServletRequest request, Model model, Integer offset, Integer maxResults) {
	    request.getSession().setAttribute("listType", 7);
		super.listAllOrdersInPage(request, model, offset, maxResults);
		return new ModelAndView("/orders/storagedItems");
	}
	
	
	@RequestMapping(value = "/donhang/da-nhap-kho", method = RequestMethod.POST)
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
		return new RedirectView("da-nhap-kho-tim-kiem");
	}

	
	@RequestMapping(value = "/donhang/da-nhap-kho-tim-kiem", method = RequestMethod.GET)
	public String searchResult(HttpServletRequest request, Model model)
	{
		request.getSession().setAttribute("listType", 7);
		this.doBusiness(model);
		return "/orders/storagedItems";
	}
	
	
	public void doBusiness(Model model) {
		if (this.searchCondition == null) {
			this.searchCondition = new SearchCondition(5);
		}
		try {
		    Long userId = null;
			if (Utils.isUser()) {
			    userId = ((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
				this.searchCondition.setUserId(userId);
			}
			List<Bill> allBills = this.ordersService.getAllBills(this.searchCondition, this.getOffset(),
					this.getMaxResults());
			int count = this.ordersService.countAllBills(this.searchCondition);
			model.addAttribute("allBills", allBills);
			model.addAttribute("offset", this.getOffset());
			model.addAttribute("maxResult", this.getMaxResults());
			model.addAttribute("searchCondition", this.searchCondition);
			model.addAttribute("count", count);
			model.addAttribute("selectedItems", this.getSelectedItems());
		} catch (SokokanriException ex) {
			model.addAttribute("message", ex.getErrorMessage());
		}
	}
	
	@RequestMapping(value = "/donhang/xuat-hoa-don", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseResult<String>> exportBill(Model model, @RequestParam Long id) {
		try {
			String billContent = this.ordersService.exportBill(id, true);
			return new ResponseEntity<ResponseResult<String>>(new ResponseResult<String>(1, billContent, billContent), HttpStatus.OK);
		} catch (SokokanriException e) {
			return new ResponseEntity<ResponseResult<String>>(new ResponseResult<String>(0, e.getErrorMessage(), e.getErrorMessage()), HttpStatus.OK);
		}
		
	}
	
	@RequestMapping(value = "/donhang/da-xuat-hoa-don", method=RequestMethod.GET)
	public String exportBillAction(Model model) {
		if (!Utils.hasRole(Constants.ROLE_BG) && !Utils.hasRole(Constants.ROLE_A)) {
			model.addAttribute("message", "Bạn không có quyền chuyển trạng thái của đơn hàng này");
		}
		try {
			this.ordersService.exportBill(this.getSelectedItems(), true);
			this.getSelectedItems().clear();
			return "redirect:da-nhap-kho";
		} catch (SokokanriException e) {
			return "redirect:da-nhap-kho";
		}
		
	}
	
	@RequestMapping(value = "/donhang/xuat-hoa-don-file", method=RequestMethod.GET)
	public void exportBillToFile(Model model, HttpServletRequest request, HttpServletResponse response, @RequestParam Long id) {
		try {
			String billContent = this.ordersService.exportBill(id, false);
			try
			{
				// set file as attached data and copy file data to response output stream
				String mimeType = "application/octet-stream";

				response.setContentType(mimeType);

				/* "Content-Disposition : attachment" will be directly download, may provide save as popup, based on your browser setting*/
				response.setHeader(
					"Content-Disposition",
					String.format("attachment; filename=\"chi_tiet_hoa_don.txt\""));
				//Copy bytes from source to destination, closes both streams.
				// reads the file's bytes and writes them to the response stream
				response.setCharacterEncoding("UTF-8");
				ServletOutputStream outStream = response.getOutputStream();
				try
				{
					outStream.write(billContent.getBytes());
				}
				catch (Exception ex)
				{
					outStream.close();
					response.flushBuffer();
				}
			}
			catch (IOException ex)
			{
			}
			
		} catch (SokokanriException e) {
		}
		
	}
	
	@RequestMapping(value = "/donhang/nhap-kho/chon-don-hang", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseResult<String>> selectItem(@RequestParam Long id){
		return super.selectItem(id);
	}
	
	@RequestMapping(value = "/donhang/nhap-kho/bo-chon-don-hang", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseResult<String>> deSelectItem(@RequestParam Long id){
		return super.deSelectItem(id);
	}
	
	@RequestMapping(value = "/donhang/nhap-kho/chon-tat-ca", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseResult<String>> selectAllItems(@RequestParam String ids){
		return super.selectAllItems(ids);
	}
	
	@RequestMapping(value = "/donhang/nhap-kho/bo-chon-tat-ca", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseResult<String>> deSelectAllItems(@RequestParam String ids){
		return super.deSelectAllItems(ids);
	}
}
