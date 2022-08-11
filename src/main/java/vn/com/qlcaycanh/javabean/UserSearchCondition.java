package vn.com.qlcaycanh.javabean;

import java.util.Map;

import vn.com.qlcaycanh.common.Utils;

public class UserSearchCondition {
    private String email;

    
    public String getEmail() {
        return email;
    }

    
    public void setEmail(String email) {
        this.email = email;
    }


    public String getSearching(UserSearchCondition searchCondition, Map<String, Object> params) {
        StringBuilder searching = new StringBuilder();
        if (!Utils.isEmpty(this.email)) {
            searching.append(" and email = :email");
            params.put("email", this.email);
        }
        return searching.toString();
    }
    
    
}
