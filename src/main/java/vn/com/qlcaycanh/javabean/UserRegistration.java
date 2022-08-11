package vn.com.qlcaycanh.javabean;

import java.util.Locale;

import org.springframework.context.i18n.LocaleContextHolder;

import vn.com.qlcaycanh.common.MyException;
import vn.com.qlcaycanh.common.Utils;
import vn.com.qlcaycanh.i18n.IMessage;

public class UserRegistration {
    private String email;
    private String password;
    private String confirmPassword;
    private String sex;
    private String fullName;
    private String phone;
    private final Locale locale = LocaleContextHolder.getLocale();
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getConfirmPassword() {
        return confirmPassword;
    }
    
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
    
    public String getSex() {
        return sex;
    }
    
    public void setSex(String sex) {
        this.sex = sex;
    }
    
    public String getFullName() {
        return fullName;
    }
    
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public String getPhone() {
        return phone;
    }

    
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void validateUser() throws MyException {
        Utils.checkEmail(this.email);
        this.checkPhoneNumber();
        this.checkNewPassword();
        
    }
    
    private void checkNewPassword() throws MyException
    {
        if (Utils.isEmpty(this.password) || this.password.length() < 6)
        {
            throw new MyException(
                IMessage.getMessageErrorInvalidPasswordLength(this.locale));
        }
        
        if (this.password.compareTo(this.confirmPassword) != 0)
        {
            throw new MyException(
                IMessage.getMessageErrorInvalidConfirmPassword(this.locale));
        }
    }
    
    public void checkPhoneNumber() throws MyException {
        if (Utils.isEmpty(this.phone)) {
            throw new MyException(IMessage.getMessageErrorPhoneCannotBeEmpty(locale));
        }
        
        if (!this.phone.matches("(\\+84|0)\\d{9,10}")) {
                                 
            throw new MyException(IMessage.getMessageErrorInvalidPhone(locale));
        }
    }
}
