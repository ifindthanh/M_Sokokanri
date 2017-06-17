package vn.com.nsmv.authentication;

import org.springframework.security.core.*;

/**
 */
public final class JwtAuthencicationException extends AuthenticationException
{

	public JwtAuthencicationException(String msg, Throwable t)
	{
		super(msg, t);
	}

	public JwtAuthencicationException(String msg)
	{
		super(msg);
	}

}
