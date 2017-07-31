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
public class ExportingAPIController
{
	@Autowired
	private ExportingService exportingService;
	@Autowired
	private LogRequestService logRequestService;

	@RequestMapping(value = "/api/getAllExportingItems", method = RequestMethod.GET)
	public ResponseEntity<List<ShukkaAPI>> getAllExportingItems(@RequestParam long lastRetrieveTime)
	{
		logRequestService.add(
			new LogRequest(
				"Request ==== Getting all exporting items + lastRetrieveTime = "
					+ new Date(lastRetrieveTime),
				new Date(),
				0,
				Utils.getSokoCdFromJwt()));
		List<ShukkaAPI> exportingItems = this.exportingService
			.getAllExportingBody(lastRetrieveTime, Utils.getSokoCdFromJwt());

		logRequestService.add(
			new LogRequest(
				"Response ==== Getting all exporting items - " + exportingItems.size() + " items"
					+ HttpStatus.OK,
				new Date(),
				1,
				Utils.getSokoCdFromJwt()));
		return new ResponseEntity<List<ShukkaAPI>>(exportingItems, HttpStatus.OK);
	}

	@RequestMapping(value = "/api/getAllExportedItems", method = RequestMethod.GET)
	public ResponseEntity<List<ShukkaAPI>> getAllExportedItems(
		@RequestParam long fromDate,
		@RequestParam long toDate)
	{
		logRequestService.add(
			new LogRequest(
				"Request ==== Getting all exported items from " + new Date(fromDate) + " to "
					+ new Date(toDate),
				new Date(),
				0,
				Utils.getSokoCdFromJwt()));
		List<ShukkaAPI> result = this.exportingService
			.getAllExportedBody(fromDate, toDate, Utils.getSokoCdFromJwt());
		logRequestService.add(
			new LogRequest(
				"Response ==== Getting all exported items - " + result.size() + " items",
				new Date(),
				1,
				Utils.getSokoCdFromJwt()));
		return new ResponseEntity<List<ShukkaAPI>>(result, HttpStatus.OK);
	}

	@RequestMapping(value = "/api/exportMultiItem", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public ResponseEntity<String> exportMultiItem(@RequestBody ExportParameter exportParameter)
	{
		logRequestService.add(
			new LogRequest(
				"Request ==== Export an order: sagyoDate = " + exportParameter.getSagyoDate()
					+ ", sojoNo = " + exportParameter.getSojoNo() + " contains "
					+ exportParameter.getItems().size() + " items",
				new Date(),
				0,
				Utils.getSokoCdFromJwt()));
		try
		{
			this.exportingService.exportItems(exportParameter, Utils.getSokoCdFromJwt());
		}
		catch (SokokanriException ex)
		{
			logRequestService.add(
				new LogRequest(
					"Response ==== Export an order, Result =  " + ex.getErrorMessage(),
					new Date(),
					1,
					Utils.getSokoCdFromJwt()));
			return new ResponseEntity<String>(ex.getErrorMessage(), HttpStatus.BAD_REQUEST);
		}
		logRequestService.add(
			new LogRequest(
				"Response ==== Export an order " + HttpStatus.OK,
				new Date(),
				1,
				Utils.getSokoCdFromJwt()));
		return new ResponseEntity<String>(
			"",
			HttpStatus.OK);
	}

	@RequestMapping(value = "/api/getInfor", method = RequestMethod.GET)
	public ResponseEntity<SokokanriInfor> getInfor()
	{
		try
		{
			logRequestService.add(
				new LogRequest(
					"Request ==== Get infor API",
					new Date(),
					0,
					Utils.getSokoCdFromJwt()));
			SokokanriInfor result = this.exportingService.getInfor(Utils.getSokoCdFromJwt());
			logRequestService.add(
				new LogRequest(
					"Response ==== Get infor API - " + HttpStatus.OK,
					new Date(),
					1,
					Utils.getSokoCdFromJwt()));
			return new ResponseEntity<SokokanriInfor>(result, HttpStatus.OK);
		}
		catch (SokokanriException ex)
		{
			logRequestService.add(
				new LogRequest(
					"Response ==== ERROR - Get infor API" + ex.getErrorMessage(),
					new Date(),
					1,
					Utils.getSokoCdFromJwt()));
			return new ResponseEntity<SokokanriInfor>(HttpStatus.NO_CONTENT);
		}
	}

	/*@RequestMapping(value = "/test/test_delete", method = RequestMethod.GET)
	public ModelAndView deleteAll(Model model)
	{
		String userCd = SecurityContextHolder.getContext().getAuthentication().getName();
		if ("00002".equals(userCd))
		{
			this.exportingService.deleteAll();
		}
		return new ModelAndView("redirect:../login");
	}*/
}
