package vn.com.nsmv.controller;

import java.io.*;
import java.text.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.log4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;
import org.springframework.web.servlet.view.*;

import vn.com.nsmv.common.*;
import vn.com.nsmv.common.Constants.*;
import vn.com.nsmv.javabean.*;
import vn.com.nsmv.service.*;

/**
 */
@Controller
@Scope("session")
public class ExportedController
{
	@Autowired
	private ExportingService exportingService;
	@Autowired
	private SokoService sokoService;

	private Integer _offset;
	private Integer _maxResults;
	private ExportingSearchCondition _searchCondition = new ExportingSearchCondition();
	private boolean includeExportedItems;

	private final String SPACE_2 = "  ";
	private final String SPACE_8 = "        ";
	private final String SPACE_9 = "         ";
	private final String SPACE_15 = "               ";
	private final String SPACE_21 = "                     ";
	private final String ZERO5 = "00000";
	private static final Logger logger = Logger.getLogger(ExportedController.class);

	@RequestMapping(value = "/exported/items/0", method = RequestMethod.GET)
	public String init(HttpServletRequest request, Model model, Integer offset, Integer maxResults)
	{
		this._offset = 0;
		this._maxResults = Constants.MAX_IMAGE_PER_PAGE;
		this._searchCondition = new ExportingSearchCondition();
		this.includeExportedItems = false;
		this._searchCondition
			.setSokoCd((String) request.getSession().getAttribute(Constants.SESSION_PARAM.SOKO_CD));
		return "redirect:/exported/items";
	}

	@RequestMapping(value = "/exported/items", method = RequestMethod.GET)
	public ModelAndView listExportingItems(
		HttpServletRequest request,
		Model model,
		Integer offset,
		Integer maxResults)
	{
		request.getSession().setAttribute("selectedMenu", 4);
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
		this.doBusiness(model, Utils.getSokoCd(this._searchCondition.getSokoCd()));
		return new ModelAndView("/export/listExportedItems");
	}

	private void doBusiness(Model model, String sokoCd)
	{
		List<ShukkaAPI> exportItems = this.exportingService
			.getExportingItems(_searchCondition, null, _offset, _maxResults, 1, sokoCd);
		int count = this.exportingService.getCountExportingItems(_searchCondition, null, 1, sokoCd);
		model.addAttribute("exportItems", exportItems);
		model.addAttribute("offset", _offset);
		model.addAttribute("maxResult", _maxResults);
		model.addAttribute("searchCondition", _searchCondition);
		model.addAttribute("count", count);
		model.addAttribute("includeExportedItems", includeExportedItems);
		model.addAttribute("sokoCds", this.sokoService.getAllSokoes());
	}

	@RequestMapping(value = "/exported/items", method = RequestMethod.POST)
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
	@RequestMapping(value = "/exported/searchResult", method = RequestMethod.GET)
	public String searchResult(HttpServletRequest request, Model model)
	{
		request.getSession().setAttribute("selectedMenu", 4);
		this.doBusiness(model, Utils.getSokoCd(this._searchCondition.getSokoCd()));
		return "/export/listExportedItems";
	}

	@RequestMapping(value = "/exported/includeExportedItems")
	public ResponseEntity<Integer> includeExportedItems()
	{
		this.includeExportedItems = true;
		return new ResponseEntity<Integer>(1, HttpStatus.OK);
	}

	@RequestMapping(value = "/exported/excludeExportedItems")
	public ResponseEntity<Integer> excludeExportedItems()
	{
		this.includeExportedItems = false;
		return new ResponseEntity<Integer>(1, HttpStatus.OK);
	}

	//handle export QJ2 action
	@RequestMapping(value = "/exported/download", method = RequestMethod.GET)
	public void download(HttpServletRequest request, HttpServletResponse response)
	{
		InputStream inputStream = null;
		try
		{
			// set file as attached data and copy file data to response output stream
			String mimeType = "application/octet-stream";

			response.setContentType(mimeType);

			/* "Content-Disposition : attachment" will be directly download, may provide save as popup, based on your browser setting*/
			response.setHeader(
				"Content-Disposition",
				String.format("attachment; filename=\"QJ2.txt\""));
			byte[] byteBuffer = new byte[4096];
			//Copy bytes from source to destination, closes both streams.
			// reads the file's bytes and writes them to the response stream
			response.setCharacterEncoding("SJIS");
			ServletOutputStream outStream = response.getOutputStream();
			String sokoCd = Utils.getSokoCd(this._searchCondition.getSokoCd());
			List<ShukkaAPI> shukkaAPIs = this.exportingService
				.getExportingItems(null, null, null, null, 1, sokoCd);
			try
			{
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				for (ShukkaAPI item : shukkaAPIs)
				{
					ShukkaHeadBean shukkaHead = item.getShukkaHeadBean();
					if (shukkaHead.getExportedFlg() != null && shukkaHead.getExportedFlg() == 1
						&& !this.includeExportedItems)
					{
						continue;
					}
					List<ShukkaBodyBean> exportedItems = item.getShukkaBodyBeans();
					for (ShukkaBodyBean body : exportedItems)
					{
						StringBuilder stringBuilder = new StringBuilder();
						stringBuilder.append("QJ2");
						if (body.getSagyoDate() == null)
						{
							stringBuilder.append(SPACE_8);
						}
						else
						{
							stringBuilder.append(sdf.format(body.getShukkaDate()));
						}
						if (body.getSojoNo() == null)
						{
							stringBuilder.append(SPACE_9);
						}
						else
						{
							stringBuilder.append(body.getSojoNo());
						}
						String dataKbn = String.format(body.getDataKbn());
						stringBuilder.append(dataKbn);
						if (body.getRenban() == null)
						{
							stringBuilder.append(SPACE_2);
						}
						else
						{
							stringBuilder.append(String.format("%02d", body.getRenban()));
						}
						if (body.getSzNo() == null)
						{
							stringBuilder.append(SPACE_15);
						}
						else
						{
							stringBuilder.append(body.getSzNo());
						}
						if (body.getInzuToExport() == null)
						{
							stringBuilder.append(ZERO5);
						}
						else
						{
							String zaikoInzu = String.format("%05d", body.getInzuToExport());
							stringBuilder.append(zaikoInzu);
						}
						if (body.getSzNo() == null)
						{
							stringBuilder.append(SPACE_15);
						}
						else
						{
							stringBuilder.append(body.getSzNo());
						}
						stringBuilder.append(SPACE_21);
						inputStream = new DataInputStream(
							new ByteArrayInputStream(stringBuilder.toString().getBytes()));
						int length = 0;
						while (inputStream != null && (length = inputStream.read(byteBuffer)) != -1)
						{
							outStream.write(byteBuffer, 0, length);
						}
						outStream.println();
					}
					if (shukkaHead.getExportedFlg() == null || shukkaHead.getExportedFlg() != 1)
					{
						this.exportingService.setExportedFlg(shukkaHead.getId(), sokoCd);
					}
				}
			}
			catch (Exception ex)
			{
				if (inputStream != null)
				{
					inputStream.close();
				}
				outStream.close();
				response.flushBuffer();
			}

		}
		catch (IOException ex)
		{
			logger.debug(ex);
		}
	}

	@InitBinder
	public void initBinder(WebDataBinder binder)
	{
		binder.registerCustomEditor(String.class, new SokokanriStringEditor());
		//we set false because if user do not input the value => we will get empty string instead of null => we can avoid NullPointerException
	}
}
