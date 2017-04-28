package vn.com.nsmv.bean;

import java.util.*;

import org.springframework.security.core.*;
import org.springframework.security.core.authority.*;
import org.springframework.security.core.userdetails.*;

import vn.com.nsmv.dao.*;
import vn.com.nsmv.entity.User;

public class CustomUserService implements UserDetailsService
{
	private UserDAO userDAO;
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{
		User user = this.userDAO.getUserByCd(username);
		if (user == null)
		{
			throw new UsernameNotFoundException("");
		}
		user.getUserRole().size();
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority(
			"ROLE_A");
		// TODO get Role from Role table
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(authority);
		return new CustomUser(
			user.getFullname(),
			authorities,
			user.getPassword(),
			username,
			"",
			true,
			true,
			true,
			true);
	}
	public UserDAO getUserDAO()
	{
		return userDAO;
	}
	public void setUserDAO(UserDAO userDAO)
	{
		this.userDAO = userDAO;
	}

}
