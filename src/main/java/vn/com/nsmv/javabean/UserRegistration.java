package vn.com.nsmv.javabean;

import java.util.Locale;

import org.springframework.context.i18n.LocaleContextHolder;

import vn.com.nsmv.common.SokokanriException;
import vn.com.nsmv.common.Utils;
import vn.com.nsmv.i18n.SokokanriMessage;

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

    public void validateUser() throws SokokanriException{
        Utils.checkEmail(this.email);
        this.checkPhoneNumber();
        this.checkNewPassword();
        
    }
    
    private void checkNewPassword() throws SokokanriException
    {
        if (Utils.isEmpty(this.password) || this.password.length() < 6)
        {
            throw new SokokanriException(
                SokokanriMessage.getMessageErrorInvalidPasswordLength(this.locale));
        }
        
        if (this.password.compareTo(this.confirmPassword) != 0)
        {
            throw new SokokanriException(
                SokokanriMessage.getMessageErrorInvalidConfirmPassword(this.locale));
        }
    }
    
    public void checkPhoneNumber() throws SokokanriException {
        if (Utils.isEmpty(this.phone)) {
            throw new SokokanriException(SokokanriMessage.getMessageErrorPhoneCannotBeEmpty(locale));
        }
        
        if (!this.phone.matches("^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$")) {
                                 
            throw new SokokanriException(SokokanriMessage.getMessageErrorInvalidPhone(locale));
        }
    }
}
