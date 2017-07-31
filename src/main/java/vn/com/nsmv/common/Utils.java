package vn.com.nsmv.common;

import java.text.*;
import java.util.*;
import java.util.regex.*;

import org.apache.commons.lang.*;
import org.springframework.context.i18n.*;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.*;
import org.springframework.security.core.context.*;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.password.*;

import vn.com.nsmv.authentication.*;
import vn.com.nsmv.bean.*;
import vn.com.nsmv.i18n.*;
import vn.com.nsmv.javabean.*;

public class Utils
{
    public static void validateUser(UserRegistration userRegistration) throws SokokanriException{
        Utils.checkEmail(userRegistration.getEmail());
        Utils.checkNewPassword(userRegistration.getPassword(), userRegistration.getConfirmPassword());
    }
    
    private static void checkNewPassword(String newPassword, String confirmNewPassword)
        throws SokokanriException
    {
        if (newPassword == null || newPassword.length() < 6)
        {
            throw new SokokanriException(
                SokokanriMessage.getMessageErrorInvalidPasswordLength(LocaleContextHolder.getLocale()));
            
        }
        
        if (newPassword.compareTo(confirmNewPassword) != 0)
        {
            throw new SokokanriException(
                SokokanriMessage.getMessageErrorInvalidConfirmPassword(LocaleContextHolder.getLocale()));
        }
    }
    
	public static boolean isEmpty(String str)
	{
		if (null == str)
			return true;
		if (str.trim().isEmpty())
			return true;
		return false;
	}

	public static String formatDate(Date date)
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		String str = format.format(date);
		return str;
	}

	public static Date stringToDate(String str)
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		Date date = null;
		try
		{
			date = format.parse(str);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return date;
	}
	
	// check email
	public static void checkEmail(String email) throws SokokanriException
	{
	    if (Utils.isEmpty(email)) {
            throw new SokokanriException(SokokanriMessage.getMessageErrorEmailCannotBeEmpty(LocaleContextHolder.getLocale()));
        }

		Pattern mail = Pattern
			.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
		Matcher m = mail.matcher(email);

		if (!m.find())
		{
			throw new SokokanriException(
				SokokanriMessage.getMessageErrorInvalidEmail(LocaleContextHolder.getLocale()));
		}

	}

	public static String genPassword()
	{
		return RandomStringUtils.randomAlphabetic(8);
	}

	public static String encode(String str)
	{
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(str);//
	}

	public static boolean isValidPassword(String enPass, String rawPass)
	{
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.matches(rawPass, enPass);
	}

	public static boolean stringCompare(String str1, String str2)
	{

		if (isEmpty(str1))
		{
			if (isEmpty(str2))
				return true;
			else
				return false;
		}
		else
		{
			if (isEmpty(str2))
				return false;
			else if (str1.trim().equals(str2.trim()))
				return true;
			else
				return false;
		}
	}


	//check characters

	public static StringBuilder getOrdering(SortCondition sortCondition)
	{
		StringBuilder ordering = new StringBuilder();
		if (sortCondition == null)
		{
			return ordering;
		}
		if (!Utils.isEmpty(sortCondition.getSortField()))
		{
			ordering.append(" order by ");
			ordering.append(sortCondition.getSortField());
			ordering.append(Constants.SPACE);
			ordering.append(sortCondition.getSortOrder());
			ordering.append(Constants.SPACE);
		}
		return ordering;
	}

	public static String getSokoCd(String sokoCd)
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null)
		{
			return Constants.BLANK;
		}
		String result = "";
		if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_S")))
		{
			//return sokoCd that is selected by super admin user
			return sokoCd;
		}

		// return sokoCd of current user
		return result;
	}

	public static String getSokoCdFromJwt()
	{
		JwtAuthenticationToken authentication = (JwtAuthenticationToken) SecurityContextHolder
			.getContext().getAuthentication();
		if (authentication == null)
		{
			return Constants.BLANK;
		}
		return "";
	}
	
	public static boolean isUser(){
		Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		for (GrantedAuthority item : authorities) {
			if (Constants.ROLE_U.equals(item.getAuthority())) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean hasRole(String role){
		if (Utils.isEmpty(role)) {
			return false;
		}
		Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		for (GrantedAuthority item : authorities) {
			if (role.equals(item.getAuthority())) {
				return true;
			}
		}
		return false;
	}
	
	public static String getFormattedId (Object id, int nbOfDigit) {
		return String.format("%0" + nbOfDigit + "d", id);
	}
}
