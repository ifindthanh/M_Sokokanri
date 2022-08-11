package vn.com.qlcaycanh.bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vn.com.qlcaycanh.dao.UserDAO;
import vn.com.qlcaycanh.entity.Role;

@Component
public class RoleEnum {
    
    public static List<Role> ALL_ROLES = new ArrayList<Role>();
    @Autowired
    UserDAO userDAO;
    @PostConstruct
    public void getAllRoles(){
        List<Role> allRoles = this.userDAO.getAllRoles();
        for (Role item: allRoles) {
            if ("U".equals(item.getRoleId())) {
                continue;
            }
            ALL_ROLES.add(item);
        }
    }
    
}
