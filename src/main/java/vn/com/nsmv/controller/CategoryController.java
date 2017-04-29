package vn.com.nsmv.controller;

import org.springframework.web.bind.annotation.*;
import vn.com.nsmv.javabean.*;

@RestController
@RequestMapping(name = "donhang")
public class CategoryController
{
	private SearchCondition searchCondition = new SearchCondition();
	@RequestMapping
	public void listAllOrders()
	{
		
	}

}
