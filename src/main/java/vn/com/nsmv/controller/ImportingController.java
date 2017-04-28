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
import vn.com.nsmv.entity.*;
import vn.com.nsmv.i18n.*;
import vn.com.nsmv.javabean.*;
import vn.com.nsmv.service.*;

@Controller
@Scope("session")
public class ImportingController
{
	@Autowired
	private ImportingService importingService;
	@Autowired
	private SokoService sokoService;

	private Integer _offset;
	private Integer _maxResults;
	private SearchCondition _searchCondition = new SearchCondition();

	@RequestMapping(value = "/import/items/0", method = RequestMethod.GET)
	public String init(HttpServletRequest request, Model model, Integer offset, Integer maxResults)
	{
		this._offset = 0;
		this._maxResults = Constants.MAX_IMAGE_PER_PAGE;
		this._searchCondition = new SearchCondition();
		this._searchCondition
			.setSokoCd((String) request.getSession().getAttribute(Constants.SESSION_PARAM.SOKO_CD));
		return "redirect:/import/items";
	}

	//	listImprotingItems
	@RequestMapping(value = "/import/items", method = RequestMethod.GET)
	public ModelAndView listImprotingItems(
		HttpServletRequest request,
		Model model,
		Integer offset,
		Integer maxResults)
	{
		request.getSession().setAttribute("selectedMenu", 1);
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
		processListImport(model);
		return new ModelAndView("/import/listImportingItems");
	}

	@RequestMapping(value = "/import/items", method = RequestMethod.POST)
	public RedirectView search(
		HttpServletRequest request,
		Model model,
		SearchCondition searchCondition,
		Integer offset,
		Integer maxResults)
	{
		request.getSession().setAttribute("selectedMenu", 1);
		if (offset != null)
			_offset = offset;
		if (maxResults != null)
			_maxResults = maxResults;
		if (searchCondition != null)
			_searchCondition = searchCondition;
		request.getSession().setAttribute(SESSION_PARAM.SOKO_CD, _searchCondition.getSokoCd());

		return new RedirectView("searchResult");
	}

	@RequestMapping(value = "/import/searchResult", method = RequestMethod.GET)
	public String searchResult(HttpServletRequest request, Model model)
	{
		processListImport(model);
		return "/import/listImportingItems";
	}

	private void processListImport(Model model)
	{
		String sokoCd = Utils.getSokoCd(this._searchCondition.getSokoCd());
		List<Nyuko> importItems = this.importingService.getImportingItems(
			_searchCondition,
			null,
			_offset == null ? 0 : _offset,
			_maxResults == null ? 0 : _maxResults,
			0,
			sokoCd);
		int count = this.importingService.getCountImportingItems(_searchCondition, null, 0, sokoCd);

		List<VBuzaiKigo> buzaiKigos = this.importingService.getAllBuzaiKigo(sokoCd);
		List<VKakoMongon> kakoMongons = this.importingService.getAllKakoMongon(sokoCd);
		List<VZaisitu> zaisitus = this.importingService.getAllZaisitu(sokoCd);
		model.addAttribute("importItems", importItems);
		model.addAttribute("offset", _offset);
		model.addAttribute("maxResult", _maxResults);
		model.addAttribute("searchCondition", _searchCondition);
		model.addAttribute("count", count);
		model.addAttribute("buzaiKigos", buzaiKigos);
		model.addAttribute("kakoMongons", kakoMongons);
		model.addAttribute("zaisitus", zaisitus);
		model.addAttribute("sokoCds", this.sokoService.getAllSokoes());
	}

	@RequestMapping(value = "/import/upload", method = RequestMethod.GET)
	public ModelAndView displayUpload(HttpServletRequest request, Model model)
	{
		request.getSession().setAttribute("selectedMenu", 1);
		UploadBean uploadBean = new UploadBean();
		model.addAttribute("uploadBean", uploadBean);
		return new ModelAndView("/common/upload");
	}

	@RequestMapping(value = "/import/upload", method = RequestMethod.POST)
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
			//we can think about do not allow user with unknown sokoCd
			if (Utils.isEmpty(sokoCd))
			{
				throw new SokokanriException(
					SokokanriMessage.getErrorSelectSokoCd(LocaleContextHolder.getLocale()));
			}
			this.importingService.doUpload(myUpload.getUploadFile(), Utils.getSokoCd(sokoCd));
		}
		catch (SokokanriException ex)
		{
			redirectAttributes.addFlashAttribute("message", ex.getErrorMessage());
			return new RedirectView("uploadResult");
		}
		redirectAttributes.addFlashAttribute(
			"message",
			SokokanriMessage.getMSG05_001(LocaleContextHolder.getLocale()));
		return new RedirectView("uploadResult");
	}

	@RequestMapping(value = "/import/uploadResult", method = RequestMethod.GET)
	public String uploadResult(HttpServletRequest request, Model model)
	{
		request.getSession().setAttribute("selectedMenu", 1);
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
