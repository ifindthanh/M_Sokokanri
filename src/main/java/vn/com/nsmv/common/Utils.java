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
	public static void checkEmail(String str) throws SokokanriException
	{
		if (Utils.isEmpty(str.trim()))
		{
			return;
		}

		Pattern mail = Pattern
			.compile("^\\w+\\.?\\w*\\.?\\w*\\@\\w*\\-?\\w*\\.\\w+\\.?\\w+\\.?\\w*\\.?\\w*$");
		Matcher m = mail.matcher(str);

		if (!m.find())
		{
			throw new SokokanriException(
				SokokanriMessage.getMSG07_002(LocaleContextHolder.getLocale()));
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

	/**
	 * split fullName to firstName and lastName
	 *
	 * @param fullName
	 * @return
	 */
	public static List<String> splitFullName(String fullName)
	{
		List<String> list = new LinkedList<String>();
		if (!isEmpty(fullName))
		{
			String[] names;
			fullName = fullName.trim();
			if (fullName.contains(" "))
			{
				names = fullName.split(" ");
				list.add(names[0].trim());
				list.add(names[1].trim());
			}
			else if (fullName.contains("ã€€"))
			{
				names = fullName.split("ã€€");
				list.add(names[0].trim());
				list.add(names[1].trim());
			}
			else
			{
				list.add(fullName);
			}
		}
		return list;
	}
	// check phone
	public static void checkPhone(String str) throws SokokanriException
	{
		if (Utils.isEmpty(str.trim()))
		{
			return;
		}

		Matcher m1 = null;
		Matcher m2 = null;
		Pattern phone1 = Pattern.compile(
			"^\\(?\\+?\\d{0,2}[\\-\\.\\s]?\\d{0,2}\\)?[\\-\\.\\s]?\\d{0,4}[\\-\\.\\s]\\d{0,4}[\\-\\.\\s]\\d{1,4}[\\-\\.\\s]?\\(?\\w*[\\-\\.\\s\\/\\:]*\\d{0,4}\\)?$");
		Pattern phone2 = Pattern.compile(
			"^\\(?\\+?\\d{0,2}[\\-\\.\\s]?\\d?\\)?[\\-\\.\\s]?\\d*\\-?\\d{8,11}[\\-\\.\\s]?\\(?\\w*[\\-\\.\\s\\/\\:]*\\d{0,4}\\)?$");

		if (str.length() > 9 && str.length() <= 30)
		{

			m1 = phone1.matcher(str);
			m2 = phone2.matcher(str);

			if (!m1.find() && !m2.find())
			{
				throw new SokokanriException(
					SokokanriMessage.getMSG13_006(LocaleContextHolder.getLocale()));
			}
			else
			{
				m1 = phone1.matcher(str);
				m2 = phone2.matcher(str);
			}

			if (m1.find() || m2.find())
			{
				return;
			}
			else
			{
				throw new SokokanriException(
					SokokanriMessage.getMSG13_006(LocaleContextHolder.getLocale()));
			}
		}
		else
		{
			throw new SokokanriException(
				SokokanriMessage.getMSG13_006(LocaleContextHolder.getLocale()));
		}

	}

	// check web
	public static void checkWeb(String str) throws SokokanriException
	{

		if (Utils.isEmpty(str.trim()))
		{
			return;
		}
		else if (str.startsWith("http://") || str.startsWith("www.") || str.startsWith("https://"))
		{
			Pattern web1 = Pattern.compile(
				"\\w{0,5}\\:?\\/?\\/?\\www\\.?\\w+\\-?\\w*\\.\\w*\\-?\\w*\\.?\\w{0,4}\\/?\\w*");
			Pattern web2 = Pattern
				.compile("^\\w{4,5}\\:\\/\\/\\w+\\.?\\w+\\-?\\w+\\.\\w{0,4}\\.?\\w{0,4}\\/?\\w*$");

			Matcher m1 = web1.matcher(str);
			Matcher m2 = web2.matcher(str);

			if (!m1.find() && !m2.find())
			{
				throw new SokokanriException(
					SokokanriMessage.getMSG04_014(LocaleContextHolder.getLocale()));
			}
			else
			{
				return;
			}
		}
		else
		{
			throw new SokokanriException(
				SokokanriMessage.getMSG04_014(LocaleContextHolder.getLocale()));
		}
	}
	//check characters
	public static void checkSpecialCharacters(String str) throws SokokanriException
	{

		if (Utils.isEmpty(str.trim()))
		{
			return;
		}
		Pattern p = Pattern.compile("[^0-9a-zA-Zä¸€-é¾ ã��-ã‚”ã‚¡-ãƒ´ãƒ¼ã€…ã€†ã€¤ï½�-ï½šï¼¡-ï¼ºï¼�-ï¼™\\s]+");
		Matcher m = p.matcher(str.trim());

		if (m.find())
		{
			throw new SokokanriException(
				SokokanriMessage.getSPECIAL_CHARACTERS(LocaleContextHolder.getLocale()));
		}
	}

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
