package vn.com.nsmv.controller;

import java.io.*;
import java.nio.charset.*;
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
import vn.com.nsmv.entity.*;
import vn.com.nsmv.javabean.*;
import vn.com.nsmv.service.*;

/**
 */
@Controller
@Scope("session")
public class ImportedController
{
	@Autowired
	private ImportingService importingService;
	@Autowired
	private SokoService sokoService;

	private Integer _offset;
	private Integer _maxResults;
	private SearchCondition _searchCondition = new SearchCondition();
	private boolean includeExportedItems;
	private final String SPACE_8 = "        ";
	private static final Logger logger = Logger.getLogger(ImportedController.class);

	@RequestMapping(value = "/imported/items/0", method = RequestMethod.GET)
	public String init(HttpServletRequest request, Model model, Integer offset, Integer maxResults)
	{
		this._offset = 0;
		this._maxResults = Constants.MAX_IMAGE_PER_PAGE;
		this._searchCondition = new SearchCondition();
		this._searchCondition
			.setSokoCd((String) request.getSession().getAttribute(Constants.SESSION_PARAM.SOKO_CD));
		this.setDefaultDate();
		this.includeExportedItems = false;
		return "redirect:/imported/items";
	}

	@RequestMapping(value = "/imported/items", method = RequestMethod.GET)
	public ModelAndView listImprotingItems(
		HttpServletRequest request,
		Model model,
		Integer offset,
		Integer maxResults)
	{
		request.getSession().setAttribute("selectedMenu", 2);
		if (_maxResults == null)
		{
			_maxResults = Constants.MAX_IMAGE_PER_PAGE;
		}
		if (this._searchCondition == null)
		{
			this._searchCondition = new SearchCondition();
		}
		if (Utils.isEmpty(this._searchCondition.getSokoCd()))
		{
			this._searchCondition.setSokoCd(
				(String) request.getSession().getAttribute(Constants.SESSION_PARAM.SOKO_CD));
		}
		if (offset != null)
			_offset = offset;
		if (maxResults != null)
			_maxResults = maxResults;

		processImportedList(model, Utils.getSokoCd(this._searchCondition.getSokoCd()));
		return new ModelAndView("/import/listImportedItems");
	}

	private void setDefaultDate()
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();

		// Get 2 weeks before
		Calendar c = Calendar.getInstance();
		c.setTime(new Date()); // Now use today date.
		c.add(Calendar.DATE, -14); // Adding 5 days

		_searchCondition.setDateStart(format.format(c.getTime()));
		_searchCondition.setDateEnd(format.format(date));
	}

	private void processImportedList(Model model, String sokoCd)
	{
		List<Nyuko> importItems = this.importingService.getImportingItems(
			_searchCondition,
			null,
			_offset == null ? 0 : _offset,
			_maxResults == null ? 0 : _maxResults,
			1,
			sokoCd);
		int count = this.importingService.getCountImportingItems(_searchCondition, null, 1, sokoCd);

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
		model.addAttribute("includeExportedItems", includeExportedItems);
		model.addAttribute("sokoCds", this.sokoService.getAllSokoes());
	}

	@RequestMapping(value = "/imported/items", method = RequestMethod.POST)
	public RedirectView search(
		HttpServletRequest request,
		Model model,
		SearchCondition searchCondition,
		Integer offset,
		Integer maxResults)
	{
		request.getSession().setAttribute("selectedMenu", 2);

		if (offset != null)
			_offset = offset;
		if (maxResults != null)
			_maxResults = maxResults;
		if (searchCondition != null)
			_searchCondition = searchCondition;

		request.getSession().setAttribute(SESSION_PARAM.SOKO_CD, _searchCondition.getSokoCd());

		return new RedirectView("searchResult");
	}

	@RequestMapping(value = "/imported/searchResult", method = RequestMethod.GET)
	public String searchResult(HttpServletRequest request, Model model)
	{
		processImportedList(model, Utils.getSokoCd(this._searchCondition.getSokoCd()));
		return "/import/listImportedItems";
	}
	@RequestMapping(value = "/imported/includeExportedItems")
	public ResponseEntity<Integer> includeExportedItems()
	{
		this.includeExportedItems = true;
		return new ResponseEntity<Integer>(1, HttpStatus.OK);
	}

	@RequestMapping(value = "/imported/excludeExportedItems")
	public ResponseEntity<Integer> excludeExportedItems()
	{
		this.includeExportedItems = false;
		return new ResponseEntity<Integer>(1, HttpStatus.OK);
	}

	//handle export QJ1 action
	@RequestMapping(value = "/imported/download")
	public void download(
		HttpServletRequest request,
		HttpServletResponse response,
		@RequestParam String fromDate,
		@RequestParam String toDate)
	{
		//we only filter the export result by from date and to date only. the other searching conditions are ignored
		SearchCondition filter = new SearchCondition();
		filter.setDateStart(fromDate);
		filter.setDateEnd(toDate);
		filter.setIncludeExportedItems(this.includeExportedItems);
		List<Nyuko> importItems = this.importingService
			.getItemsToExport(filter, Utils.getSokoCd(this._searchCondition.getSokoCd()));
		try
		{
			// set file as attached data and copy file data to response output stream
			String mimeType = "application/octet-stream";

			response.setContentType(mimeType);
			response.setCharacterEncoding("SJIS");

			/* "Content-Disposition : attachment" will be directly download, may provide save as popup, based on your browser setting*/
			response.setHeader(
				"Content-Disposition",
				String.format("attachment; filename=\"QJ1.txt\""));
			byte[] byteBuffer = new byte[4096];
			//Copy bytes from source to destination(outputstream in this example), closes both streams.
			// reads the file's bytes and writes them to the response stream
			ServletOutputStream outStream = response.getOutputStream();
			InputStream in = null;
			try
			{
				SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
				String sojoNo = "";
				int seqIndex = 1;
				for (Nyuko item : importItems)
				{
					// if sojoNo equals previous one -> increment sojoNo by 1
					if (sojoNo.equals(item.getSojoNo()))
					{
						seqIndex++;
					}
					else
					{
						//  sojoNo does not equals previous one ->
						// it means that this is another shipping cost -> we update the sojoNo and reset the seqIndex
						sojoNo = item.getSojoNo();
						seqIndex = 1;
					}
					StringBuilder stringBuilder = new StringBuilder();
					stringBuilder.append("QJ1");
					String sagyoDate = format.format(item.getNykoDate());
					stringBuilder.append(sagyoDate);
					stringBuilder.append(item.getSojoNo());
					stringBuilder.append(String.format("%02d", seqIndex));
					stringBuilder.append(item.getId().getSzNo());
					String zaikoInzu = String.format("%05d", item.getZaikoInzu());
					stringBuilder.append(zaikoInzu);
					stringBuilder.append(item.getBkn09());
					stringBuilder.append(item.getBuzaiKigo() + SPACE_8);
					stringBuilder.append(item.getDansunMongon());
					stringBuilder.append(Constants.SPACE_68);
					in = new DataInputStream(
						new ByteArrayInputStream(
							stringBuilder.toString().getBytes(Charset.forName("SJIS"))));
					int length = 0;
					while (in != null && (length = in.read(byteBuffer)) != -1)
					{
						outStream.write(byteBuffer, 0, length);
					}
					outStream.println();
					if (item.getExportedFlg() == null || item.getExportedFlg() != 1)
					{
						this.importingService.setExportedFlg(item);
					}
				}
			}
			finally
			{
				if (in != null)
				{
					in.close();
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
