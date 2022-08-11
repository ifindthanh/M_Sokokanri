package vn.com.qlcaycanh.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import vn.com.qlcaycanh.common.MyException;
import vn.com.qlcaycanh.common.Utils;
import vn.com.qlcaycanh.dao.UserDAO;
import vn.com.qlcaycanh.entity.Role;
import vn.com.qlcaycanh.entity.User;
import vn.com.qlcaycanh.entity.UserRole;
import vn.com.qlcaycanh.entity.UserRoleID;
import vn.com.qlcaycanh.i18n.IMessage;
import vn.com.qlcaycanh.javabean.SortCondition;
import vn.com.qlcaycanh.javabean.UserRegistration;
import vn.com.qlcaycanh.javabean.UserSearchCondition;
import vn.com.qlcaycanh.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@Service("userService")
@EnableTransactionManagement
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    public User getUserByEmail(String userCd) throws MyException {
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
    public void register(UserRegistration userBean) throws MyException {
        userBean.validateUser();
        if (!Utils.stringCompare(userBean.getPassword(), userBean.getConfirmPassword())) {
            throw new MyException(IMessage.getMessageErrorEmailExisted(LocaleContextHolder.getLocale()));
        }
        User user = this.userDAO.getUserByEmail(userBean.getEmail());
        if (user != null) {
            throw new MyException(IMessage.getMessageErrorEmailExisted(LocaleContextHolder.getLocale()));
        }
        user = new User();
        user.setEmail(userBean.getEmail());
        user.setPhone(userBean.getPhone());
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
    public void changePassword(User user) throws MyException {
        this.checkPassword(user, LocaleContextHolder.getLocale());
        if (!Utils.stringCompare(user.getConfirmPassword(), user.getPassword())) {
            throw new MyException(
                    IMessage.getMessageErrorInvalidConfirmPassword(LocaleContextHolder.getLocale()));
        }
        User currentUser = this.userDAO.getUserByEmail(user.getEmail());
        if (currentUser == null) {
            throw new MyException(
                    IMessage.getMessageErrorEmailNotExists(LocaleContextHolder.getLocale()));
        }

        if (!Utils.stringCompare(currentUser.getResetPwTimestamp(), user.getResetPwTimestamp())) {
            throw new MyException(
                    IMessage.getMessageErrorInvalidRequest(LocaleContextHolder.getLocale()));
        }
        currentUser.setPassword(Utils.encode(user.getPassword()));
        // generante a random value
        currentUser.setResetPwTimestamp(Utils.genPassword());
        this.userDAO.saveUser(currentUser);
    }

    @Transactional
    public void resetPassword(String email) throws MyException {
        User user = this.userDAO.getUserByEmail(email);
        if (user == null) {
            throw new MyException(
                    IMessage.getMessageErrorEmailNotExists(LocaleContextHolder.getLocale()));
        }

        String timestamp = String.valueOf(System.currentTimeMillis());
        user.setResetPwTimestamp(timestamp);
        this.userDAO.saveUser(user);
    }

    @Transactional
    public void resetPassword(String email, String timestamp) throws MyException {

        User user = this.userDAO.getUserByEmail(email);
        if (user == null) {
            throw new MyException(
                    IMessage.getMessageErrorEmailNotExists(LocaleContextHolder.getLocale()));
        }

        if (!Utils.stringCompare(timestamp, user.getResetPwTimestamp())) {
            throw new MyException(
                    IMessage.getMessageErrorInvalidRequest(LocaleContextHolder.getLocale()));
        }

        if (System.currentTimeMillis() - Long.valueOf(user.getResetPwTimestamp()) >= 24 * 60 * 60 * 1000) {
            throw new MyException(
                    IMessage.getMessageErrorInvalidRequest(LocaleContextHolder.getLocale()));
        }
    }

    @Transactional
    public void updatePassword(User user) throws MyException {
        this.checkPassword(user, LocaleContextHolder.getLocale());
        if (!Utils.stringCompare(user.getConfirmPassword(), user.getPassword())) {
            throw new MyException(
                    IMessage.getMessageErrorInvalidConfirmPassword(LocaleContextHolder.getLocale()));
        }
        User currentUser = this.userDAO.getUserByEmail(user.getEmail());
        if (currentUser == null) {
            throw new MyException(
                    IMessage.getMessageErrorEmailNotExists(LocaleContextHolder.getLocale()));
        }

        if (!Utils.isValidPassword(currentUser.getPassword(), user.getOldPassword())) {
            throw new MyException(
                    IMessage.getMessageErrorInvalidPassword(LocaleContextHolder.getLocale()));
        }
        currentUser.setPassword(Utils.encode(user.getPassword()));
        this.userDAO.saveUser(currentUser);
    }

    private void checkPassword(User user, Locale locale) throws MyException {
        if (Utils.isEmpty(user.getPassword()) || user.getPassword().length() < 6) {
            throw new MyException(IMessage.getMessageErrorInvalidPasswordLength(locale));
        }

        if (user.getPassword().compareTo(user.getConfirmPassword()) != 0) {
            throw new MyException(IMessage.getMessageErrorInvalidConfirmPassword(locale));
        }
    }

    @Transactional
    public List<User> getAllUsers(
            UserSearchCondition searchCondition, SortCondition sortCondition, Integer offset, Integer maxResults)
            throws MyException {
        return this.userDAO.getAllUsers(searchCondition, sortCondition, offset, maxResults);
    }

    @Transactional
    public int countAllUsers(UserSearchCondition searchCondition) throws MyException {
        return this.userDAO.countAllUsers(searchCondition);
    }

    @Transactional
    public void deleteUsers(Set<Long> selectedItems) throws MyException {
        userDAO.deleteUser(selectedItems);
    }

    public User getUserByCode(Long userCd) throws MyException {
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
    public User saveInformation(User user, boolean canEditRole) throws MyException {
        if (canEditRole && (user.getRoles() == null || user.getRoles().isEmpty())) {
            throw new MyException(
                    IMessage.getMessageErrorUserRoleEmpty(LocaleContextHolder.getLocale()));
        }
        UserRegistration userRegistration = new UserRegistration();
        userRegistration.setPhone(user.getPhone());
        userRegistration.checkPhoneNumber();

        Long userId = user.getId();
        User userByCode = this.userDAO.getUserByCode(userId);
        if (userByCode == null) {
            throw new MyException(IMessage.getMessageErrorUserNotExists(LocaleContextHolder.getLocale()));
        }
        userByCode.setFullname(user.getFullname());
        userByCode.setAddress(user.getAddress());
        userByCode.setGender(user.getGender());
        userByCode.setPhone(user.getPhone());
        this.userDAO.update(userByCode);
        if (!canEditRole) {
            return userByCode;
        }
        this.userDAO.deleteAllOtherRoles(userId, user.getRoles());
        for (String role : user.getRoles()) {
            UserRole userRole = this.userDAO.getUserRole(userId, role);
            if (userRole == null) {
                this.userDAO.addUserRole(new UserRole(new UserRoleID(userId, role)));
            }
        }
        return userByCode;
    }

    @Transactional
    public Long addUser(User userForm) throws MyException {
        Utils.checkEmail(userForm.getEmail());
        UserRegistration userRegistration = new UserRegistration();
        userRegistration.setPhone(userForm.getPhone());
        userRegistration.checkPhoneNumber();
        if (userForm.getRoles() == null || userForm.getRoles().isEmpty()) {
            throw new MyException(IMessage.getMessageErrorUserRoleEmpty(LocaleContextHolder.getLocale()));
        }

        User user = this.userDAO.getUserByEmail(userForm.getEmail());
        if (user != null) {
            throw new MyException(IMessage.getMessageErrorEmailExisted(LocaleContextHolder.getLocale()));
        }
        //default password
        userForm.setPassword(Utils.encode("12345678"));

        long userID = this.userDAO.add(userForm);
        for (String roleID : userForm.getRoles()) {
            this.userDAO.addUserRole(new UserRole(new UserRoleID(userID, roleID)));
        }
        return userID;
    }

}
