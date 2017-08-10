package vn.com.nsmv.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;

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
import vn.com.nsmv.dao.TransactionDAO;
import vn.com.nsmv.dao.UserDAO;
import vn.com.nsmv.entity.Role;
import vn.com.nsmv.entity.Transaction;
import vn.com.nsmv.entity.User;
import vn.com.nsmv.entity.UserRole;
import vn.com.nsmv.entity.UserRoleID;
import vn.com.nsmv.i18n.SokokanriMessage;
import vn.com.nsmv.javabean.SortCondition;
import vn.com.nsmv.javabean.TransactionTypeEnum;
import vn.com.nsmv.javabean.UserRegistration;
import vn.com.nsmv.javabean.UserSearchCondition;
import vn.com.nsmv.service.UserService;

@Service("userService")
@EnableTransactionManagement
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private TransactionDAO transactionDAO;

    public User getUserByEmail(String userCd) throws SokokanriException {
        return userDAO.getUserByEmail(userCd);
    }

    public boolean updateUser(User userEntity) {
        boolean result = false;
        try {
            result = userDAO.update(userEntity);
        } catch (Exception e) {
            return result;
        }
        return result;
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
        user.setPassword(Utils.encode(userBean.getPassword()));
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
            throw new SokokanriException(
                SokokanriMessage.getMessageErrorInvalidConfirmPassword(LocaleContextHolder.getLocale()));
        }
        User currentUser = this.userDAO.getUserByEmail(user.getEmail());
        if (currentUser == null) {
            throw new SokokanriException(
                SokokanriMessage.getMessageErrorEmailNotExists(LocaleContextHolder.getLocale()));
        }

        if (!Utils.stringCompare(currentUser.getResetPwTimestamp(), user.getResetPwTimestamp())) {
            throw new SokokanriException(
                SokokanriMessage.getMessageErrorInvalidRequest(LocaleContextHolder.getLocale()));
        }
        currentUser.setPassword(Utils.encode(user.getPassword()));
        // generante a random value
        currentUser.setResetPwTimestamp(Utils.genPassword());
        this.userDAO.saveUser(currentUser);
    }

    @Transactional
    public void resetPassword(String email) throws SokokanriException {
        User user = this.userDAO.getUserByEmail(email);
        if (user == null) {
            throw new SokokanriException(
                SokokanriMessage.getMessageErrorEmailNotExists(LocaleContextHolder.getLocale()));
        }

        String timestamp = String.valueOf(System.currentTimeMillis());
        user.setResetPwTimestamp(timestamp);
        this.userDAO.saveUser(user);
        try {
            ApplicationContext context = new ClassPathXmlApplicationContext("spring-mail.xml");
            String linkActive = Constants.LINK_ACTIVE_ACCOUNT + "email=" + email + "&timestamp=" + timestamp;

            Mail mail = (Mail) context.getBean("sendMail");
            MailForm form = new MailForm();
            // form.setFrom(Constants.EMAIL_FROM);
            form.setTo(user.getEmail());
            form.setUserName(user.getFullname());
            form.setUrl(linkActive);
            form.setSubject(Constants.EMAIL_SUBJECT);

            mail.sendMail(form, 1);
            ((ConfigurableApplicationContext) context).close();

        } catch (Exception e) {
            throw new SokokanriException(e);
        }

    }

    @Transactional
    public void resetPassword(String email, String timestamp) throws SokokanriException {

        User user = this.userDAO.getUserByEmail(email);
        if (user == null) {
            throw new SokokanriException(
                SokokanriMessage.getMessageErrorEmailNotExists(LocaleContextHolder.getLocale()));
        }

        if (!Utils.stringCompare(timestamp, user.getResetPwTimestamp())) {
            throw new SokokanriException(
                SokokanriMessage.getMessageErrorInvalidRequest(LocaleContextHolder.getLocale()));
        }
        
        if (System.currentTimeMillis() - Long.valueOf(user.getResetPwTimestamp()) >= 24*60*60*1000) {
            throw new SokokanriException(
                SokokanriMessage.getMessageErrorInvalidRequest(LocaleContextHolder.getLocale()));
        }
    }

    @Transactional
    public void updatePassword(User user) throws SokokanriException {
        this.checkPassword(user, LocaleContextHolder.getLocale());
        if (!Utils.stringCompare(user.getConfirmPassword(), user.getPassword())) {
            throw new SokokanriException(
                SokokanriMessage.getMessageErrorInvalidConfirmPassword(LocaleContextHolder.getLocale()));
        }
        User currentUser = this.userDAO.getUserByEmail(user.getEmail());
        if (currentUser == null) {
            throw new SokokanriException(
                SokokanriMessage.getMessageErrorEmailNotExists(LocaleContextHolder.getLocale()));
        }

        if (!Utils.isValidPassword(currentUser.getPassword(), user.getOldPassword())) {
            throw new SokokanriException(
                SokokanriMessage.getMessageErrorInvalidPassword(LocaleContextHolder.getLocale()));
        }
        currentUser.setPassword(Utils.encode(user.getPassword()));
        this.userDAO.saveUser(currentUser);
    }

    private void checkPassword(User user, Locale locale) throws SokokanriException {
        if (Utils.isEmpty(user.getPassword()) || user.getPassword().length() < 6) {
            throw new SokokanriException(SokokanriMessage.getMessageErrorInvalidPasswordLength(locale));
        }

        if (user.getPassword().compareTo(user.getConfirmPassword()) != 0) {
            throw new SokokanriException(SokokanriMessage.getMessageErrorInvalidConfirmPassword(locale));
        }
    }

    @Transactional
    public List<User> getAllUsers(
        UserSearchCondition searchCondition, SortCondition sortCondition, Integer offset, Integer maxResults)
        throws SokokanriException {
        return this.userDAO.getAllUsers(searchCondition, sortCondition, offset, maxResults);
    }

    @Transactional
    public int countAllUsers(UserSearchCondition searchCondition) throws SokokanriException {
        return this.userDAO.countAllUsers(searchCondition);
    }

    public void deleteUsers(Set<Long> selectedItems) throws SokokanriException {
        String strListId = "";
        for (Long id : selectedItems) {
            strListId = strListId + id + ",";
        }
        strListId = strListId.substring(0, strListId.length() - 1);
        userDAO.deleteUser(strListId);
    }

    public User getUserByCode(Long userCd) throws SokokanriException {
        return userDAO.getUserByCode(userCd);
    }
    
    public List<Role> getAllRoles(Long userId) {
        List<Role> result = new ArrayList<Role>();
        Set<UserRole> roles = this.userDAO.getRoles(userId);
        for (UserRole item : roles) {
            Role role = this.userDAO.getRoleById(item.getId().getRoleID());
            if (role == null) {
                continue;
            }
            result.add(role);
        }
        return result;
    }

    @Transactional
    public User saveInformation(User user) throws SokokanriException {
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            throw new SokokanriException(SokokanriMessage.getMessageErrorUserRoleEmpty(LocaleContextHolder.getLocale()));
        }
        Long userId = user.getId();
        User userByCode = this.userDAO.getUserByCode(userId);
        if (userByCode == null) {
            throw new SokokanriException(SokokanriMessage.getMessageErrorUserNotExists(LocaleContextHolder.getLocale()));
        }
        userByCode.setFullname(user.getFullname());
        userByCode.setAddress(user.getAddress());
        userByCode.setGender(user.getGender());
        userByCode.setPhone(user.getPhone());
        this.userDAO.update(userByCode);
        this.userDAO.deleteAllOtherRoles(userId, user.getRoles());
        for (String role : user.getRoles()) {
            UserRole userRole = this.userDAO.getUserRole(userId, role);
            if (userRole == null) {
                this.userDAO.addUserRole(new UserRole(new UserRoleID(userId, role)));
            }
        }
        return userByCode;
    }

    public Long addUser(User userForm) throws SokokanriException {
        Utils.checkEmail(userForm.getEmail());
        UserRegistration userRegistration = new UserRegistration();
        userRegistration.setPhone(userForm.getPhone());
        userRegistration.checkPhoneNumber();
        if (userForm.getRoles() == null || userForm.getRoles().isEmpty()) {
            throw new SokokanriException(SokokanriMessage.getMessageErrorUserRoleEmpty(LocaleContextHolder.getLocale()));
        }
        
        User user = this.userDAO.getUserByEmail(userForm.getEmail());
        if (user != null) {
            throw new SokokanriException(SokokanriMessage.getMessageErrorEmailExisted(LocaleContextHolder.getLocale()));
        }
        //default password
        userForm.setPassword(Utils.encode("12345678"));
        return this.userDAO.add(userForm);
    }

    public User getWallet(Long userId) throws SokokanriException {
        
        return null;
    }

    @Transactional
    public void saveTransaction(Transaction transaction) throws SokokanriException {
        transaction.validate();
        User user = this.userDAO.getUserByCode(transaction.getUserId());
        if (user == null) {
            throw new SokokanriException(SokokanriMessage.getMessageErrorUserNotExists(LocaleContextHolder.getLocale()));
        }
        if (!this.getAllRoles(user.getId()).contains(new Role("U"))) {
            throw new SokokanriException(SokokanriMessage.getMessageErrorAddTransactionNotAllow(LocaleContextHolder.getLocale()));
        }
        TransactionTypeEnum transactionType = TransactionTypeEnum.valueOf(transaction.getTransactionType());
        if (user.getAccountBalance() == null) {
            user.setAccountBalance(0);
        }
        switch (transactionType) {
            case RECHARGE:
                user.setAccountBalance(user.getAccountBalance() + transaction.getAmount());
                break;
            case PAY:
                if (user.getAccountBalance() < transaction.getAmount()) {
                    throw new SokokanriException(SokokanriMessage.getMessageErrorAddAccountBalanceCannotLessThanAmount(LocaleContextHolder.getLocale()));
                }
                user.setAccountBalance(user.getAccountBalance() - transaction.getAmount());
                break;
            case REFUND:
                user.setAccountBalance(user.getAccountBalance() + transaction.getAmount());
                user.setAccountBalance(user.getAccountBalance() + transaction.getAmount());
                break;

            default:
                break;
        }
        this.userDAO.saveUser(user);
        this.transactionDAO.addTransaction(transaction);
        transaction.getUser().setAccountBalance(user.getAccountBalance());
    }

}
