package vn.com.nsmv.dao;

import java.util.*;

import vn.com.nsmv.common.SokokanriException;
import vn.com.nsmv.entity.*;
import vn.com.nsmv.javabean.SortCondition;
import vn.com.nsmv.javabean.UserSearchCondition;

public interface UserDAO
{


	public long add(User user) throws SokokanriException;

	public boolean update(User user);

	public User getUserByEmail(String userCd);

	public boolean deleteUser(String listId);

	public Set<UserRole> getRoles(Long userID);
	
	public void addUserRole(UserRole userRole) throws SokokanriException;
	
	public void saveUser(User user) throws SokokanriException;

    public List<User> getAllUsers(
        UserSearchCondition searchCondition, SortCondition sortCondition, Integer offset, Integer maxResults) throws SokokanriException;

    public User getUserByCode(Long userCd) throws SokokanriException;
    
    public Role getRoleById(String roleId);
    
    public List<Role> getAllRoles();
    
    public void deleteAllOtherRoles(Long userId, List<String> roles) throws SokokanriException;
    
    public UserRole getUserRole(Long userId, String roleId) throws SokokanriException;

    public int countAllUsers(UserSearchCondition searchCondition);
    
}

