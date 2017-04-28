package vn.com.nsmv.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.context.i18n.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import vn.com.nsmv.common.*;
import vn.com.nsmv.entity.*;
import vn.com.nsmv.i18n.*;
import vn.com.nsmv.javabean.*;
import vn.com.nsmv.service.*;

/**
 */
@RestController
public class ImportingAPIController
{
	@Autowired
	private ImportingService importingService;
	@Autowired
	private LogRequestService logRequestService;

	@RequestMapping(value = "/api/getAllImportingItems", method = RequestMethod.GET)
	public ResponseEntity<List<NyukoAPI>> getAllImportingItems(@RequestParam long lastRetrieveTime)
	{
		logRequestService.add(
			new LogRequest(
				"Request ==== Get importing items API, lastRetrieveTime = "
					+ new Date(lastRetrieveTime),
				new Date(),
				0,
				Utils.getSokoCdFromJwt()));
		List<NyukoAPI> importingItems = this.importingService
			.getAllImportingItems(lastRetrieveTime, Utils.getSokoCdFromJwt());
		logRequestService.add(
			new LogRequest(
				"Response ==== Get importing items API - " + importingItems.size() + " items",
				new Date(),
				1,
				Utils.getSokoCdFromJwt()));
		return new ResponseEntity<List<NyukoAPI>>(importingItems, HttpStatus.OK);
	}

	@RequestMapping(value = "/api/getAllImportedItems", method = RequestMethod.GET)
	public ResponseEntity<List<NyukoAPI>> getAllImportedItems(
		@RequestParam long fromDate,
		@RequestParam long toDate)
	{
		logRequestService.add(
			new LogRequest(
				"Request ==== Get imported items API: from " + new Date(fromDate) + " to "
					+ new Date(toDate),
				new Date(),
				0,
				Utils.getSokoCdFromJwt()));
		List<NyukoAPI> result = this.importingService
			.getAllImportedItems(fromDate, toDate, Utils.getSokoCdFromJwt());
		logRequestService.add(
			new LogRequest(
				"Response ==== Get imported items API - " + result.size() + " items",
				new Date(),
				1,
				Utils.getSokoCdFromJwt()));
		return new ResponseEntity<List<NyukoAPI>>(result, HttpStatus.OK);
	}

	@RequestMapping(value = "/api/importItem", method = RequestMethod.GET)
	public ResponseEntity<String> importItem(@RequestParam String id)
	{
		logRequestService.add(
			new LogRequest(
				"Request ==== Import one item API: id = " + id,
				new Date(),
				0,
				Utils.getSokoCdFromJwt()));
		try
		{
			this.importingService.importItem(id, Utils.getSokoCdFromJwt());
		}
		catch (SokokanriException ex)
		{
			logRequestService.add(
				new LogRequest(
					"Request ==== ERROR Import one item API" + ex.getErrorMessage(),
					new Date(),
					1,
					Utils.getSokoCdFromJwt()));
			return new ResponseEntity<String>(ex.getErrorMessage(), HttpStatus.CONFLICT);
		}
		logRequestService.add(
			new LogRequest(
				"Response ==== Import one item API - " + HttpStatus.OK,
				new Date(),
				1,
				Utils.getSokoCdFromJwt()));
		return new ResponseEntity<String>(
			SokokanriMessage.getItemImportSuccessfully(id, LocaleContextHolder.getLocale()),
			HttpStatus.OK);

	}

	@RequestMapping(value = "/api/importMultiItem", method = RequestMethod.POST)
	public ResponseEntity<List<ImportResult>> importMultiItem(@RequestParam String ids)
	{
		logRequestService.add(
			new LogRequest(
				"Request ==== Import many items API: ids = " + ids,
				new Date(),
				0,
				Utils.getSokoCdFromJwt()));
		List<ImportResult> result = new ArrayList<ImportResult>();
		for (String id : ids.split(","))
		{
			try
			{
				this.importingService.importItem(id, Utils.getSokoCdFromJwt());
				result.add(new ImportResult(id, 0));
			}
			catch (SokokanriException ex)
			{
				result.add(new ImportResult(id, 1));
			}
		}
		logRequestService.add(
			new LogRequest(
				"Response ==== Import many items API - " + result.size() + " items",
				new Date(),
				1,
				Utils.getSokoCdFromJwt()));
		return new ResponseEntity<List<ImportResult>>(result, HttpStatus.OK);
	}

}
