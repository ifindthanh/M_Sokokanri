package vn.com.nsmv.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import vn.com.nsmv.bean.ResponseResult;

@Controller
public class LoginController
{

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String login(Model model)
	{
		return "home";
	}
	
	@RequestMapping(value = "/session-timed-out", method = RequestMethod.GET)
    public String error(Model model)
    {
        return "/refresh";
    }
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
    public String errorLogin(Model model)
    {
	    return null;
    }
	
	@RequestMapping(value = "/user/dang-ky")
    public @ResponseBody ResponseEntity<ResponseResult<String>> register()
    {
        return new ResponseEntity<ResponseResult<String>>(new ResponseResult<String>(1, "Success", null), HttpStatus.OK);
    }
}