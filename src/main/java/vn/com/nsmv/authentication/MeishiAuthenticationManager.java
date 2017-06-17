package vn.com.nsmv.authentication;

import org.springframework.security.authentication.*;
import org.springframework.security.core.*;

public class MeishiAuthenticationManager implements AuthenticationManager
{

	public Authentication authenticate(Authentication authentication) throws AuthenticationException
	{
		return authentication;
	}

}
