package vn.com.nsmv.validator;

import org.springframework.context.i18n.*;
import org.springframework.stereotype.*;
import org.springframework.validation.*;

import vn.com.nsmv.common.*;
import vn.com.nsmv.i18n.*;
import vn.com.nsmv.javabean.*;

@Component
public class UserValidator implements Validator
{

	public boolean supports(Class<?> clazz)
	{
		return UserBean.class.isAssignableFrom(clazz);
	}

	public void validate(Object object, Errors errors)
	{
		UserBean userBean = (UserBean) object;
		// validate Email
		if (!Utils.isEmpty(userBean.getEmail()))
		{
			try
			{
				Utils.checkEmail(userBean.getEmail());
			}
			catch (SokokanriException e)
			{
				errors.reject(e.getErrorMessage());
			}
		}
		// validate Phone
		if (!Utils.isEmpty(userBean.getPhone()))
		{
			try
			{
				Utils.checkPhone(userBean.getPhone());
			}
			catch (SokokanriException e)
			{
				errors.reject(e.getErrorMessage());
			}
		}

		// validate Name
		if (!Utils.isEmpty(userBean.getFirstName()))
		{
			try
			{
				Utils.checkSpecialCharacters(userBean.getFirstName());
			}
			catch (SokokanriException e)
			{
				errors.reject(
					SokokanriMessage
						.getSPECIAL_CHARACTERS_FIRSTNAME(LocaleContextHolder.getLocale()));
			}
		}
		if (!Utils.isEmpty(userBean.getLastName()))
		{
			try
			{
				Utils.checkSpecialCharacters(userBean.getLastName());
			}
			catch (SokokanriException e)
			{
				errors.reject(
					SokokanriMessage
						.getSPECIAL_CHARACTERS_LASTNAME(LocaleContextHolder.getLocale()));
			}
		}

	}

}
