package vn.com.nsmv.authentication;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.springframework.security.core.*;
import org.springframework.security.web.authentication.*;

public final class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler
{

	public void onAuthenticationSuccess(
		HttpServletRequest request,
		HttpServletResponse response,
		Authentication authentication) throws ServletException, IOException
	{
		String url = determineTargetUrl(request);
		request.getRequestDispatcher(url).forward(request, response);
	}
	private String determineTargetUrl(HttpServletRequest request)
	{
		String context = request.getContextPath();
		String fullURL = request.getRequestURI();
		String url = fullURL.substring(fullURL.indexOf(context) + context.length());
		return url;
	}

}
