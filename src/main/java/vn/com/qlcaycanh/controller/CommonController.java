package vn.com.qlcaycanh.controller;

import org.springframework.stereotype.*;
import org.springframework.web.bind.*;
import org.springframework.web.bind.annotation.*;

import vn.com.qlcaycanh.common.*;

@Controller
public class CommonController
{
	@InitBinder
	public void initBinder(WebDataBinder binder)
	{
		binder.registerCustomEditor(String.class, new StringEditor());
		//we set false because if user do not input the value => we will get empty string instead of null => we can avoid NullPointerException
	}

}
