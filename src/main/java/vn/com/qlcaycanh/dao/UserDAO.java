package vn.com.qlcaycanh.dao;

import java.util.*;

import vn.com.qlcaycanh.common.MyException;
import vn.com.qlcaycanh.entity.*;
import vn.com.qlcaycanh.javabean.SortCondition;
import vn.com.qlcaycanh.javabean.UserSearchCondition;

public interface UserDAO
{


	public long add(User user) throws MyException;

	public boolean update(User user);

	public User getUserByEmail(String userCd);

	public boolean deleteUser(Set<Long> ids);

	public Set<UserRole> getRoles(Long userID);
	
	public void addUserRole(UserRole userRole) throws MyException;
	
	public void saveUser(User user) throws MyException;

    public List<User> getAllUsers(
        UserSearchCondition searchCondition, SortCondition sortCondition, Integer offset, Integer maxResults) throws MyException;

    public User getUserByCode(Long userCd) throws MyException;
    
    public Role getRoleById(String roleId);
    
    public List<Role> getAllRoles();
    
    public void deleteAllOtherRoles(Long userId, List<String> roles) throws MyException;
    
    public UserRole getUserRole(Long userId, String roleId) throws MyException;

    public int countAllUsers(UserSearchCondition searchCondition);
    
}

