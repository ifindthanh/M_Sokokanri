package vn.com.nsmv.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import vn.com.nsmv.dao.UserDAO;
import vn.com.nsmv.entity.User;
import vn.com.nsmv.entity.UserRole;

public class CustomUserService implements UserDetailsService
{
	private UserDAO userDAO;
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{
		User user = this.userDAO.getUserByCd(username);
		if (user == null)
		{
			throw new UsernameNotFoundException("");
		}
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		Set<UserRole> roles = userDAO.getRoles(user.getId());
		if (roles != null) {
			for (UserRole item : roles) {
				SimpleGrantedAuthority authority = new SimpleGrantedAuthority(
						"ROLE_" + item.getId().getRoleID());
				authorities.add(authority);
			}
		}
		
		return new CustomUser(user.getFullname(), authorities, user.getPassword(), username, true, true, true, true,
				user.getId(), user.getPhone(), user.getEmail());
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
