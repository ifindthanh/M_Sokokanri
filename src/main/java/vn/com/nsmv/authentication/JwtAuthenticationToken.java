package vn.com.nsmv.authentication;

import java.util.*;

import org.springframework.security.authentication.*;
import org.springframework.security.core.*;

import io.jsonwebtoken.*;
import vn.com.nsmv.entity.*;

public final class JwtAuthenticationToken extends AbstractAuthenticationToken
{
	private static final long serialVersionUID = 1L;
	private User user;
	public JwtAuthenticationToken(String token)
	{
		super(null);
		super.setAuthenticated(true);
		Claims claims;
		try
		{
			claims = new JwtUtil().getClaimsFromToken(token);
		}
		catch (Exception ex)
		{
			throw new JwtAuthencicationException("Token is not valid", ex);
		}
		this.user = new JwtUtil().parseToken(claims);
		/*if (user.getAuthority() != null && !user.getAuthority().equals("A"))//TODO use enumeration
		{
			throw new JwtAuthencicationException("You have no permisison to access resource");
		}*/
	}

	public Object getCredentials()
	{
		return "";
	}

	public Object getPrincipal()
	{
		return this.user.getEmail();
	}


	public Collection<GrantedAuthority> getAuthorities()
	{
		Collection<GrantedAuthority> result = new ArrayList<GrantedAuthority>();
		GrantedAuthority item = new GrantedAuthority()
		{
			public String getAuthority()
			{
				// TODO fix role
				return "A";
				//return user.getAuthority();
			}
		};
		result.add(item);
		return result;
	}

}
