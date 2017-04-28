package vn.com.nsmv.validator;

import org.springframework.validation.*;

import vn.com.nsmv.javabean.*;

public class RegisterValidator implements Validator
{

	public boolean supports(Class clazz)
	{
		//just validate the UserBean instances
		return UserBean.class.isAssignableFrom(clazz);

	}

	public void validate(Object target, Errors errors)
	{

		//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userCd",
		//			"required.userName", "Field name bbb is required.");
	}
}