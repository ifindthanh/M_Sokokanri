package vn.com.nsmv.dao;

import java.util.*;

import vn.com.nsmv.common.SokokanriException;
import vn.com.nsmv.entity.*;

public interface UserDAO
{

	public List<User> listAll(Integer offset, Integer maxResults);

	public long add(User user) throws SokokanriException;

	public boolean isExists(String userCd);

	public boolean update(User user);

	public User getUserByEmail(String userCd);

	public User getUser(String userCd, String password);

	/******************************************
	 * count list user in user table
	 *
	 * @param
	 * @return Long
	 ******************************************/
	public Long countListUser();
	public Long countUserByEmail(String email);

	/******************************************
	 * get list user by table id column
	 *
	 * @param String listIs
	 * @return List<User>
	 ******************************************/
	public List<User> getListUserById(String listId);

	/******************************************
	 * Delete user
	 *
	 * @param String listId
	 * @return boolean
	 ******************************************/
	public boolean deleteUser(String listId);

	/******************************************
	 * Get list user by search condition
	 *
	 * @param String
	 *            searchCondition, Integer offset, Integer maxResults
	 * @return List<BusinessCard>
	 ******************************************/
	public List<User> getListUserByCondition(
		String searchCondition,
		Integer offset,
		Integer maxResults);

	/******************************************
	 * Get the number of user by search condition
	 *
	 * @param String
	 *            searchCondition
	 * @return List<BusinessCard>
	 ******************************************/
	public Long countUserBySearchCondition(String searchCondition);
	
	public Set<UserRole> getRoles(Long userID);
	
	public void addUserRole(UserRole userRole) throws SokokanriException;
	
	public void saveUser(User user) throws SokokanriException;
}

