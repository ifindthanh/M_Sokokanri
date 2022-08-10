package vn.com.nsmv.service;

import vn.com.nsmv.common.SokokanriException;
import vn.com.nsmv.entity.Role;
import vn.com.nsmv.entity.User;
import vn.com.nsmv.javabean.SortCondition;
import vn.com.nsmv.javabean.UserRegistration;
import vn.com.nsmv.javabean.UserSearchCondition;

import java.util.List;
import java.util.Set;

public interface UserService {


    public void register(UserRegistration userRegistration) throws SokokanriException;

    public void changePassword(User user) throws SokokanriException;

    public User getUserByEmail(String email) throws SokokanriException;

    public User getUserByCode(Long userCd) throws SokokanriException;

    public void resetPassword(String email) throws SokokanriException;

    public void resetPassword(String email, String timestamp) throws SokokanriException;

    public void updatePassword(User user) throws SokokanriException;

    public List<User> getAllUsers(
            UserSearchCondition searchCondition, SortCondition sortCondition, Integer offset, Integer maxResults) throws SokokanriException;

    public int countAllUsers(UserSearchCondition searchCondition) throws SokokanriException;

    public void deleteUsers(Set<Long> selectedItems) throws SokokanriException;

    public List<Role> getAllRoles(Long userId);

    public User saveInformation(User user, boolean canEditRole) throws SokokanriException;

    public Long addUser(User userForm) throws SokokanriException;

}
