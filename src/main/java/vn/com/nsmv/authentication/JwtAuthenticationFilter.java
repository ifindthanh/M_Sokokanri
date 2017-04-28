package vn.com.nsmv.authentication;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.springframework.security.core.*;
import org.springframework.security.web.authentication.*;

import vn.com.nsmv.common.*;

public final class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter
{

	public JwtAuthenticationFilter()
	{
		super("/**");
		setAuthenticationManager(new MeishiAuthenticationManager());
		setAuthenticationSuccessHandler(new JwtAuthenticationSuccessHandler());
	}
	/**
	 * Attempt to authenticate request - basically just pass over to another method to authenticate request headers
	 */

	public Authentication attemptAuthentication(
		HttpServletRequest request,
		HttpServletResponse response) throws AuthenticationException, IOException, ServletException
	{
		String token = request.getHeader("authorization");//TODO use constant
		if (Utils.isEmpty(token))
		{
			throw new JwtAuthencicationException("Login first");
		}
		JwtAuthenticationToken authRequest = new JwtAuthenticationToken(token);
		return this.getAuthenticationManager().authenticate(authRequest);
	}

}