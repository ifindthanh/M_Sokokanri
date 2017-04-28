package vn.com.nsmv.service.impl;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.context.*;
import org.springframework.context.i18n.*;
import org.springframework.context.support.*;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;

import vn.com.nsmv.common.*;
import vn.com.nsmv.dao.*;
import vn.com.nsmv.entity.*;
import vn.com.nsmv.i18n.*;
import vn.com.nsmv.javabean.*;
import vn.com.nsmv.service.*;

@Service("userService")
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
	 * Validate userCd
	 *
	 * @param userBean
	 * @return
	 * @throws SokokanriException
	 ******************************************/
	public void validate(UserBean userBean) throws SokokanriException
	{
		String userCd = userBean.getUserCd();
		if (Utils.isEmpty(userCd))
		{
			throw new SokokanriException(
				SokokanriMessage.getUserCdRequired(LocaleContextHolder.getLocale()));
		}
		userCd = userCd.trim();
		if (userDAO.isExists(userBean.getUserCd().trim()))
		{
			throw new SokokanriException(
				SokokanriMessage.getUserCdExists(LocaleContextHolder.getLocale()));
		}

		PriShainBas shainBas = priShainBasDAO.getPriShainBas(userCd);
		if (shainBas == null)
		{
			throw new SokokanriException(
				SokokanriMessage.getStfNoNotExists(LocaleContextHolder.getLocale()));
		}
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
	public void checkOldPassword(String oldPassword, String inputPassword) throws SokokanriException
	{

		if (!Utils.isValidPassword(oldPassword, inputPassword))
		{
			throw new SokokanriException(
				SokokanriMessage.getMSG06_006(LocaleContextHolder.getLocale()));
		}
	}

	// check 2 password moi
	public void checkNewPassword(String newPassword, String confirmNewPassword)
		throws SokokanriException
	{
		if (newPassword.length() > 7 && newPassword.length() < 21)
		{
			if (newPassword.compareTo(confirmNewPassword) != 0)
			{
				throw new SokokanriException(
					SokokanriMessage.getMSG06_007(LocaleContextHolder.getLocale()));
			}
		}
		else
		{
			throw new SokokanriException(
				SokokanriMessage.getMSG06_008(LocaleContextHolder.getLocale()));
		}
	}

	// check pass moi khong duoc trung voi pass dang co trong DB
	public void checkComparePasswordDB(String inputPassword, String oldPassword, String password)
		throws SokokanriException
	{

		if (Utils.isValidPassword(password, inputPassword)
			|| Utils.isValidPassword(oldPassword, inputPassword))
		{
			throw new SokokanriException(
				SokokanriMessage.getMSG06_010(LocaleContextHolder.getLocale()));
		}
	}

	public User getUserByUserCd(String userCd)
	{
		User userEntity;
		try
		{
			userEntity = userDAO.getUserByCd(userCd);
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
		SokokanriException sokokanriException = new SokokanriException();
		boolean hasError = false;
		// validate Email
		if (!Utils.isEmpty(userBean.getEmail()))
		{
			Utils.checkEmail(userBean.getEmail());
			User userEntity = userDAO.getUserByCd(userBean.getUserCd());
			if (!userBean.getEmail().equals(userEntity.getEmail()))
			{
				Long count = this.countUserByEmail(userBean.getEmail());
				if (count >= 1)
				{
					hasError = true;
					sokokanriException.addErrorMessage(
						SokokanriMessage.getMSG13_002(LocaleContextHolder.getLocale()));
				}
			}
		}
		// validate Phone
		if (!Utils.isEmpty(userBean.getPhone()))
		{
			try
			{
				Utils.checkPhone(userBean.getPhone());
			}
			catch (SokokanriException ex)
			{
				sokokanriException.addErrorMessage(ex.getErrorMessage());
				hasError = true;
			}
		}

		// validate Name
		if (!Utils.isEmpty(userBean.getFirstName()))
		{
			try
			{
				Utils.checkSpecialCharacters(userBean.getFirstName());
			}
			catch (SokokanriException ex)
			{
				sokokanriException.addErrorMessage(
					SokokanriMessage
						.getSPECIAL_CHARACTERS_FIRSTNAME(LocaleContextHolder.getLocale()));
				hasError = true;
			}
		}

		if (!Utils.isEmpty(userBean.getLastName()))
		{
			try
			{
				Utils.checkSpecialCharacters(userBean.getLastName());
			}
			catch (SokokanriException ex)
			{
				sokokanriException.addErrorMessage(
					SokokanriMessage
						.getSPECIAL_CHARACTERS_LASTNAME(LocaleContextHolder.getLocale()));
				hasError = true;
			}
		}
		if (hasError)
		{
			throw sokokanriException;
		}
	}

	public void register(UserBean userBean) throws SokokanriException {
		// TODO Auto-generated method stub
		
	}

	public void changePassword(UserBean userBean) throws SokokanriException {
		// TODO Auto-generated method stub
		
	}

	public boolean login(String username, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean resetUser(String userCd) {
		// TODO Auto-generated method stub
		return false;
	}
}
