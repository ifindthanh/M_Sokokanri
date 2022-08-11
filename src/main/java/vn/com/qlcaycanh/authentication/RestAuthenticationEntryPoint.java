package vn.com.qlcaycanh.authentication;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.springframework.security.core.*;
import org.springframework.security.web.*;

public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint
{

	public void commence(
		HttpServletRequest request,
		HttpServletResponse response,
		AuthenticationException authenticationException) throws IOException, ServletException
	{
		response.sendError(
			HttpServletResponse.SC_UNAUTHORIZED,
			authenticationException.getLocalizedMessage());
	}

}
