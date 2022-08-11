package vn.com.qlcaycanh.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import vn.com.qlcaycanh.javabean.UserBean;

@Component
public class UserValidator implements Validator
{

	public boolean supports(Class<?> clazz)
	{
		return UserBean.class.isAssignableFrom(clazz);
	}

    public void validate(Object arg0, Errors arg1) {
        
    }

}
