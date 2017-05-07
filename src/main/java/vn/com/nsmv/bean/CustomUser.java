package vn.com.nsmv.bean;

import java.util.*;

import org.springframework.security.core.*;
import org.springframework.security.core.userdetails.*;

public class CustomUser implements UserDetails
{
	private static final long serialVersionUID = 1L;

	private final String displayName;
	private final Collection<? extends GrantedAuthority> authorities;
	private final String password;
	private final String username;
	private final boolean accountNonExpired;
	private final boolean accountNonLocked;
	private final boolean credentialsNonExpired;
	private final boolean enabled;
	private final Long userId;
	private final String phone;
	private final String email;

	public CustomUser(
		String displayName,
		Collection<? extends GrantedAuthority> authorities,
		String password,
		String username,
		boolean accountNonExpired,
		boolean accountNonLocked,
		boolean credentialsNonExpired,
		boolean enabled, Long userId,
		String phone,
		String email)
	{
		this.displayName = displayName;
		this.authorities = authorities;
		this.password = password;
		this.username = username;
		this.accountNonExpired = accountNonExpired;
		this.accountNonLocked = accountNonLocked;
		this.credentialsNonExpired = credentialsNonExpired;
		this.enabled = enabled;
		this.userId = userId;
		this.phone = phone;
		this.email = email;
	}

	public Collection<? extends GrantedAuthority> getAuthorities()
	{
		return this.authorities;
	}

	public String getPassword()
	{
		return this.password;
	}

	public String getUsername()
	{
		return this.username;
	}

	public boolean isAccountNonExpired()
	{
		return this.accountNonExpired;
	}

	public boolean isAccountNonLocked()
	{
		return this.accountNonLocked;
	}

	public boolean isCredentialsNonExpired()
	{
		return this.credentialsNonExpired;
	}

	public boolean isEnabled()
	{
		return this.enabled;
	}

	public String getDisplayName()
	{
		return displayName;
	}

	public Long getUserId() {
		return userId;
	}

	public String getPhone() {
		return phone;
	}

	public String getEmail() {
		return email;
	}

	
}
