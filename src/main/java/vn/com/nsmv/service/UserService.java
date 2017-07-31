package vn.com.nsmv.service;

import java.util.*;

import vn.com.nsmv.common.*;
import vn.com.nsmv.entity.*;
import vn.com.nsmv.javabean.*;

public interface UserService
{

	public List<User> listUser(Integer offset, Integer maxResults);

	public void register(UserRegistration userRegistration) throws SokokanriException;

	public void changePassword(User user) throws SokokanriException;
	public String getUserName(String userCd);
	public String getEmail(String userCd);
	public boolean login(String username, String password);
	public User getUserByUserCd(String userCd);
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
	 * @param String listId
	 * @return List<User>
	 ******************************************/
	public List<User> getListUserById(String[] listId);

	/******************************************
	 * Delete user by table id column
	 *
	 * @param String listId
	 * @return List<User>
	 ******************************************/
	public boolean deleteUserById(String[] listId);

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

	/******************************************
	 * Reset user
	 *
	 * @param String userCd
	 * @return List<BusinessCard>
	 ******************************************/
	public boolean resetUser(String userCd);

	/******************************************
	 * Update user's information
	 *
	 * @param String userCd
	 * @return boolean
	 ******************************************/
	public boolean updateUser(User userEntity);

	/******************************************
	 * Validate user's information
	 *
	 * @param String userCd
	 * @return boolean
	 * @throws SokokanriException
	 ******************************************/
	public void validateUserInfo(UserBean userBean) throws SokokanriException;

    public void resetPassword(String email) throws SokokanriException;

    public void resetPassword(String email, String timestamp) throws SokokanriException;

    public void updatePassword(User user) throws SokokanriException;

}
