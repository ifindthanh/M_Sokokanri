package vn.com.nsmv.controller;

import java.util.*;

import javax.servlet.http.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.context.i18n.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.validation.*;
import org.springframework.web.bind.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;
import org.springframework.web.servlet.mvc.support.*;
import org.springframework.web.servlet.view.*;

import vn.com.nsmv.common.*;
import vn.com.nsmv.common.Constants.*;
import vn.com.nsmv.i18n.*;
import vn.com.nsmv.javabean.*;
import vn.com.nsmv.service.*;

/**
 */
@Controller
@Scope("session")
public class ExportingController
{
	@Autowired
	private ExportingService exportingService;
	@Autowired
	private SokoService sokoService;

	private Integer _offset;
	private Integer _maxResults;
	private ExportingSearchCondition _searchCondition = new ExportingSearchCondition();

	@RequestMapping(value = "/export/items/0", method = RequestMethod.GET)
	public String init(HttpServletRequest request, Model model, Integer offset, Integer maxResults)
	{
		this._offset = 0;
		this._maxResults = Constants.MAX_IMAGE_PER_PAGE;
		this._searchCondition = new ExportingSearchCondition();
		this._searchCondition
			.setSokoCd((String) request.getSession().getAttribute(Constants.SESSION_PARAM.SOKO_CD));
		return "redirect:/export/items";
	}

	@RequestMapping(value = "/export/items", method = RequestMethod.GET)
	public ModelAndView listExportingItems(
		HttpServletRequest request,
		Model model,
		Integer offset,
		Integer maxResults)
	{
		request.getSession().setAttribute("selectedMenu", 3);
		if (_maxResults == null)
		{
			_maxResults = Constants.MAX_IMAGE_PER_PAGE;
		}
		if (offset != null)
			_offset = offset;
		if (maxResults != null)
			_maxResults = maxResults;
		if (Utils.isEmpty(this._searchCondition.getSokoCd()))
		{
			this._searchCondition.setSokoCd(
				(String) request.getSession().getAttribute(Constants.SESSION_PARAM.SOKO_CD));
		}

		this.processExporting(model, Utils.getSokoCd(this._searchCondition.getSokoCd()));
		return new ModelAndView("/export/listExportingItems");
	}

	private void processExporting(Model model, String sokoCd)
	{
		List<ShukkaAPI> exportItems = this.exportingService
			.getExportingItems(_searchCondition, null, _offset, _maxResults, 0, sokoCd);
		int count = this.exportingService.getCountExportingItems(_searchCondition, null, 0, sokoCd);
		model.addAttribute("exportItems", exportItems);
		model.addAttribute("offset", _offset);
		model.addAttribute("maxResult", _maxResults);
		model.addAttribute("searchCondition", _searchCondition);
		model.addAttribute("count", count);
		model.addAttribute("sokoCds", this.sokoService.getAllSokoes());
	}

	@RequestMapping(value = "/export/items", method = RequestMethod.POST)
	public RedirectView search(
		HttpServletRequest request,
		Model model,
		ExportingSearchCondition searchCondition,
		Integer offset,
		Integer maxResults)
	{
		if (offset != null)
			_offset = offset;
		if (maxResults != null)
			_maxResults = maxResults;
		if (searchCondition != null)
			_searchCondition = searchCondition;
		request.getSession().setAttribute(SESSION_PARAM.SOKO_CD, _searchCondition.getSokoCd());
		return new RedirectView("searchResult");
	}
	@RequestMapping(value = "/export/searchResult", method = RequestMethod.GET)
	public String searchResult(HttpServletRequest request, Model model)
	{
		request.getSession().setAttribute("selectedMenu", 3);
		this.processExporting(model, Utils.getSokoCd(this._searchCondition.getSokoCd()));
		return "/export/listExportingItems";
	}
	@RequestMapping(value = "/export/upload", method = RequestMethod.GET)
	public ModelAndView displayUpload(Model model)
	{
		UploadBean uploadBean = new UploadBean();
		model.addAttribute("uploadBean", uploadBean);
		model.addAttribute("export", true);
		return new ModelAndView("/common/upload");
	}

	@RequestMapping(value = "/export/upload", method = RequestMethod.POST)
	public RedirectView uploadOneFileHandlerPOST(
		HttpServletRequest request,
		Model model,
		RedirectAttributes redirectAttributes,
		@ModelAttribute("uploadFile") UploadBean myUpload,
		BindingResult bindingResult)
	{
		try
		{
			String sokoCd = Utils.getSokoCd(this._searchCondition.getSokoCd());
			if (Utils.isEmpty(sokoCd))
			{
				throw new SokokanriException();
			}
			this.exportingService.doUpload(myUpload.getUploadFile(), Utils.getSokoCd(sokoCd));
		}
		catch (SokokanriException ex)
		{
			redirectAttributes.addFlashAttribute("message", ex.getErrorMessage());
			return new RedirectView("uploadResult");
		}
		redirectAttributes.addFlashAttribute(
			"message", null);
		return new RedirectView("uploadResult");
	}

	@RequestMapping(value = "/export/uploadResult", method = RequestMethod.GET)
	public String uploadResult(Model model)
	{
		if (!model.containsAttribute("message"))
		{
			return "redirect:upload";
		}
		model.addAttribute("uploadBean", new UploadBean());
		return "/common/upload";
	}
	@InitBinder
	public void initBinder(WebDataBinder binder)
	{
		binder.registerCustomEditor(String.class, new SokokanriStringEditor());
		//we set false because if user do not input the value => we will get empty string instead of null => we can avoid NullPointerException
	}
}
