package vn.com.qlcaycanh.service;

import vn.com.qlcaycanh.common.MyException;
import vn.com.qlcaycanh.entity.Role;
import vn.com.qlcaycanh.entity.User;
import vn.com.qlcaycanh.javabean.SortCondition;
import vn.com.qlcaycanh.javabean.UserRegistration;
import vn.com.qlcaycanh.javabean.UserSearchCondition;

import java.util.List;
import java.util.Set;

public interface UserService {


    public void register(UserRegistration userRegistration) throws MyException;

    public void changePassword(User user) throws MyException;

    public User getUserByEmail(String email) throws MyException;

    public User getUserByCode(Long userCd) throws MyException;

    public void resetPassword(String email) throws MyException;

    public void resetPassword(String email, String timestamp) throws MyException;

    public void updatePassword(User user) throws MyException;

    public List<User> getAllUsers(
            UserSearchCondition searchCondition, SortCondition sortCondition, Integer offset, Integer maxResults) throws MyException;

    public int countAllUsers(UserSearchCondition searchCondition) throws MyException;

    public void deleteUsers(Set<Long> selectedItems) throws MyException;

    public List<Role> getAllRoles(Long userId);

    public User saveInformation(User user, boolean canEditRole) throws MyException;

    public Long addUser(User userForm) throws MyException;

}
