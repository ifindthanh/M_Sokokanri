package vn.com.nsmv.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import vn.com.nsmv.common.Constants;
import vn.com.nsmv.common.Mail;
import vn.com.nsmv.common.MailForm;
import vn.com.nsmv.common.SokokanriException;
import vn.com.nsmv.common.Utils;
import vn.com.nsmv.dao.PriShainBasDAO;
import vn.com.nsmv.dao.UserDAO;
import vn.com.nsmv.entity.PriShainBas;
import vn.com.nsmv.entity.User;
import vn.com.nsmv.entity.UserRole;
import vn.com.nsmv.entity.UserRoleID;
import vn.com.nsmv.i18n.SokokanriMessage;
import vn.com.nsmv.javabean.UserBean;
import vn.com.nsmv.javabean.UserRegistration;
import vn.com.nsmv.service.UserService;

@Service("userService")
@EnableTransactionManagement
public class UserServiceImpl implements UserService
{
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private PriShainBasDAO priShainBasDAO;

	public List<User> listUser(Integer offset, Integer maxResults)
	{
		List<User> list = userDAO.listAll(offset, maxResults);
		return list;
	}

	/******************************************
	 * get user name from userCd
	 *
	 * @param userCd
	 * @return
	 ******************************************/
	public String getUserName(String userCd)
	{
		PriShainBas shainBas = priShainBasDAO.getPriShainBas(userCd);
		if (shainBas != null)
		{
			return shainBas.getDuNameKnj();
		}
		return null;
	}

	/******************************************
	 * get email from userCd
	 *
	 * @param userCd
	 * @return
	 ******************************************/
	public String getEmail(String userCd)
	{
		PriShainBas shainBas = priShainBasDAO.getPriShainBas(userCd);
		if (shainBas != null)
		{
			return shainBas.getDuMailaddress();
		}
		return null;
	}

	
	/**
	 * Send account active information to user
	 *
	 * @param userCd
	 * @param password
	 * @param userName
	 * @param email
	 * @param mode
	 *            = 0(register), 1(reset)
	 * @return
	 */
	private boolean sendMail(
		String userCd,
		String password,
		String userName,
		String email,
		int mode)
	{
		try
		{
			ApplicationContext context = new ClassPathXmlApplicationContext("spring-mail.xml");

			String linkActive = Constants.LINK_ACTIVE_ACCOUNT + userCd;

			Mail mail = (Mail) context.getBean("sendMail");
			MailForm form = new MailForm();
			// form.setFrom(Constants.EMAIL_FROM);
			form.setTo(email);
			form.setPassword(password);
			form.setUserName(userName);
			form.setUserCd(userCd);
			form.setUrl(linkActive);
			form.setSubject(Constants.EMAIL_SUBJECT);

			mail.sendMail(form, mode);
			((ConfigurableApplicationContext) context).close();

		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}

	

	// check OldPassword nhap vao voi OldPassword trong DB

	// check 2 password moi
	


	public User getUserByUserCd(String userCd)
	{
		User userEntity;
		try
		{
			userEntity = userDAO.getUserByEmail(userCd);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
		return userEntity;
	}

	public User getUser(String userCd, String password)
	{
		User userEntity;
		try
		{
			userEntity = userDAO.getUser(userCd, password);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
		return userEntity;
	}

	/******************************************
	 * count list user in user table
	 *
	 * @param
	 * @return Long
	 ******************************************/
	public Long countListUser()
	{
		Long count;
		try
		{
			count = userDAO.countListUser();
		}
		catch (Exception e)
		{
			return null;
		}
		return count;
	}

	public Long countUserByEmail(String email)
	{
		Long count;
		try
		{
			count = userDAO.countUserByEmail(email);
		}
		catch (Exception e)
		{
			return null;
		}
		return count;
	}

	/******************************************
	 * get list user by table id column
	 *
	 * @param String
	 *            listId
	 * @return List<User>
	 ******************************************/
	public List<User> getListUserById(String[] listId)
	{
		List<User> listUser = new ArrayList<User>();
		try
		{
			// TODO use StringBuilder
			String strListId = "";
			for (String id : listId)
			{
				strListId = strListId + id + ",";
			}
			strListId = strListId.substring(0, strListId.length() - 1);

			listUser = userDAO.getListUserById(strListId);
		}
		catch (Exception e)
		{
			return listUser;
		}
		return listUser;
	}

	/******************************************
	 * Delete user by table id column
	 *
	 * @param String
	 *            listId
	 * @return List<User>
	 ******************************************/
	public boolean deleteUserById(String[] listId)
	{

		try
		{
			String strListId = "";
			for (String id : listId)
			{
				strListId = strListId + id + ",";
			}
			strListId = strListId.substring(0, strListId.length() - 1);
			// Delete User
			userDAO.deleteUser(strListId);
		}
		catch (Exception e)
		{
			return false;
		}
		return true;
	}

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
		Integer maxResults)
	{
		List<User> listUser = new ArrayList<User>();
		try
		{
			listUser = userDAO.getListUserByCondition(searchCondition, offset, maxResults);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return listUser;
	}

	/******************************************
	 * Get the number of user by search condition
	 *
	 * @param String
	 *            searchCondition
	 * @return List<BusinessCard>
	 ******************************************/
	public Long countUserBySearchCondition(String searchCondition)
	{
		try
		{
			return userDAO.countUserBySearchCondition(searchCondition);
		}
		catch (Exception e)
		{
			return null;
		}
	}

	

	/******************************************
	 * Update user's information
	 *
	 * @param String
	 *            userCd
	 * @return boolean
	 ******************************************/
	public boolean updateUser(User userEntity)
	{
		boolean result = false;
		try
		{
			result = userDAO.update(userEntity);
		}
		catch (Exception e)
		{
			return result;
		}
		return result;
	}

	/******************************************
	 * Validate user's information
	 *
	 * @param String
	 *            userCd
	 * @return boolean
	 * @throws SokokanriException
	 ******************************************/
	public void validateUserInfo(UserBean userBean) throws SokokanriException
	{
	}

	@Transactional
	public void register(UserRegistration userBean) throws SokokanriException {
	    userBean.validateUser();
	    if (!Utils.stringCompare(userBean.getPassword(), userBean.getConfirmPassword())) {
	        throw new SokokanriException(SokokanriMessage.getMessageErrorEmailExisted(LocaleContextHolder.getLocale()));
	    } 
	    User user = this.userDAO.getUserByEmail(userBean.getEmail());
	    if (user != null) {
	        throw new SokokanriException(SokokanriMessage.getMessageErrorEmailExisted(LocaleContextHolder.getLocale()));
	    }
	    user = new User();
	    user.setEmail(userBean.getEmail());
	    user.setFullname(userBean.getFullName());
//	    user.setPassword(Utils.encode(userBean.getPassword()));
	    user.setPassword(userBean.getPassword());
	    user.setGender(userBean.getSex());
	    long userId = this.userDAO.add(user);
	    UserRole role = new UserRole();
	    UserRoleID roleId = new UserRoleID(userId, "U");
	    role.setId(roleId);
	    this.userDAO.addUserRole(role);
	}

	@Transactional
	public void changePassword(User user) throws SokokanriException {
	    this.checkPassword(user, LocaleContextHolder.getLocale());
	    if (!Utils.stringCompare(user.getConfirmPassword(), user.getPassword())) {
	        throw new SokokanriException(SokokanriMessage.getMessageErrorInvalidConfirmPassword(LocaleContextHolder.getLocale()));
	    }
		User currentUser = this.userDAO.getUserByEmail(user.getEmail());
		if (currentUser == null) {
		    throw new SokokanriException(SokokanriMessage.getMessageErrorEmailNotExists(LocaleContextHolder.getLocale()));
		}
		
		if (!Utils.stringCompare(currentUser.getResetPwTimestamp(), user.getResetPwTimestamp())) {
		    throw new SokokanriException(SokokanriMessage.getMessageErrorInvalidRequest(LocaleContextHolder.getLocale()));
		}
		currentUser.setPassword(user.getPassword());
		//generante a random value
		currentUser.setResetPwTimestamp(Utils.genPassword());
		this.userDAO.saveUser(currentUser);
	}

	public boolean login(String username, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean resetUser(String userCd) {
		// TODO Auto-generated method stub
		return false;
	}

	@Transactional
    public void resetPassword(String email) throws SokokanriException {
        User user = this.userDAO.getUserByEmail(email);
        if (user == null) {
            throw new SokokanriException(SokokanriMessage.getMessageErrorEmailNotExists(LocaleContextHolder.getLocale()));
        }
        
        String timestamp = String.valueOf(new Date().getTime());
        user.setResetPwTimestamp(timestamp);
        this.userDAO.saveUser(user);
        try
        {
            ApplicationContext context = new ClassPathXmlApplicationContext("spring-mail.xml");
            String linkActive = Constants.LINK_ACTIVE_ACCOUNT + "email=" + email+"&timestamp=" + timestamp;

            Mail mail = (Mail) context.getBean("sendMail");
            MailForm form = new MailForm();
            // form.setFrom(Constants.EMAIL_FROM);
            form.setTo(user.getEmail());
            form.setUserName(user.getFullname());
            form.setUrl(linkActive);
            form.setSubject(Constants.EMAIL_SUBJECT);

            mail.sendMail(form, 1);
            ((ConfigurableApplicationContext) context).close();

        }
        catch (Exception e)
        {
            throw new SokokanriException(e);
        }
        
    }

	@Transactional
    public void resetPassword(String email, String timestamp) throws SokokanriException {
	   
        User user = this.userDAO.getUserByEmail(email);
        if (user == null) {
            throw new SokokanriException(SokokanriMessage.getMessageErrorEmailNotExists(LocaleContextHolder.getLocale()));
        }
        
        if (!Utils.stringCompare(timestamp, user.getResetPwTimestamp())) {
            throw new SokokanriException(SokokanriMessage.getMessageErrorInvalidRequest(LocaleContextHolder.getLocale()));
        }
        
    }
	
	
	@Transactional
    public void updatePassword(User user) throws SokokanriException {
	    this.checkPassword(user, LocaleContextHolder.getLocale());
        if (!Utils.stringCompare(user.getConfirmPassword(), user.getPassword())) {
            throw new SokokanriException(SokokanriMessage.getMessageErrorInvalidConfirmPassword(LocaleContextHolder.getLocale()));
        }
        User currentUser = this.userDAO.getUserByEmail(user.getEmail());
        if (currentUser == null) {
            throw new SokokanriException(SokokanriMessage.getMessageErrorEmailNotExists(LocaleContextHolder.getLocale()));
        }
        
        if (!Utils.stringCompare(currentUser.getPassword(), user.getOldPassword())) {
            throw new SokokanriException(SokokanriMessage.getMessageErrorInvalidPassword(LocaleContextHolder.getLocale()));
        }
        currentUser.setPassword(user.getPassword());
        this.userDAO.saveUser(currentUser);
    }
    
    private void checkPassword(User user, Locale locale) throws SokokanriException
    {
        if (Utils.isEmpty(user.getPassword()) || user.getPassword().length() < 6)
        {
            throw new SokokanriException(
                SokokanriMessage.getMessageErrorInvalidPasswordLength(locale));
        }
        
        if (user.getPassword().compareTo(user.getConfirmPassword()) != 0)
        {
            throw new SokokanriException(
                SokokanriMessage.getMessageErrorInvalidConfirmPassword(locale));
        }
    }
    
}
