package vn.com.nsmv.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
	    return "redirect:/";
    }
}