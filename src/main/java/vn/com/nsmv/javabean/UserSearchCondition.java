package vn.com.nsmv.javabean;

import java.util.Map;

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
        if (this.email != null) {
            searching.append(" and email = :email");
            params.put("email", this.email);
        }
        return searching.toString();
    }
    
    
}
