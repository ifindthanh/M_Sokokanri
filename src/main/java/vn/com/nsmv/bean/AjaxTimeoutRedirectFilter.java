package vn.com.nsmv.bean;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.springframework.security.access.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.security.core.context.*;
import org.springframework.security.web.util.*;
import org.springframework.web.filter.*;

public class AjaxTimeoutRedirectFilter extends GenericFilterBean
{

	private ThrowableAnalyzer throwableAnalyzer = new DefaultThrowableAnalyzer();
	private AuthenticationTrustResolver authenticationTrustResolver = new AuthenticationTrustResolverImpl();

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
		throws IOException, ServletException
	{
		try
		{
			chain.doFilter(request, response);

			logger.debug("Chain processed normally");
		}
		catch (IOException ex)
		{
			throw ex;
		}
		catch (Exception ex)
		{
			Throwable[] causeChain = throwableAnalyzer.determineCauseChain(ex);
			RuntimeException ase = (AuthenticationException) throwableAnalyzer
				.getFirstThrowableOfType(AuthenticationException.class, causeChain);

			if (ase == null)
			{
				ase = (AccessDeniedException) throwableAnalyzer
					.getFirstThrowableOfType(AccessDeniedException.class, causeChain);
			}

			if (ase != null)
			{
				if (ase instanceof AuthenticationException)
				{
					throw ase;
				}
				else if (ase instanceof AccessDeniedException)
				{

					if (authenticationTrustResolver
						.isAnonymous(SecurityContextHolder.getContext().getAuthentication()))
					{
						logger.info("User session expired or not logged in yet");
						String ajaxHeader = ((HttpServletRequest) request)
							.getHeader("X-Requested-With");

						if ("XMLHttpRequest".equals(ajaxHeader))
						{
							request.getRequestDispatcher("/WEB-INF/pages/refresh.jsp")
								.forward(request, response);

						}
						else
						{
							logger.info("Redirect to login page");
							throw ase;
						}
					}
					else
					{
						throw ase;
					}
				}
			}

		}
	}

	private static final class DefaultThrowableAnalyzer extends ThrowableAnalyzer
	{
		/**
		 * @see org.springframework.security.web.util.ThrowableAnalyzer#initExtractorMap()
		 */
		protected void initExtractorMap()
		{
			super.initExtractorMap();

			registerExtractor(ServletException.class, new ThrowableCauseExtractor()
			{
				public Throwable extractCause(Throwable throwable)
				{
					ThrowableAnalyzer.verifyThrowableHierarchy(throwable, ServletException.class);
					return ((ServletException) throwable).getRootCause();
				}
			});
		}

	}

}
