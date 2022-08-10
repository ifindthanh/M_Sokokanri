package vn.com.nsmv.javabean;

import org.springframework.util.StringUtils;

import java.util.Map;

public class ProviderSearchCondition {
    private String email;
    private String name;
    private String phoneNumber;

    public ProviderSearchCondition() {

    }

    public ProviderSearchCondition(String email, String name, String phoneNumber) {
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSearching(Map<String, Object> params) {
        StringBuilder searching = new StringBuilder();
        if (StringUtils.hasText(this.name)) {
            searching.append(" and name = :name");
            params.put("name", "%" + this.name + "%");
        }
        if (StringUtils.hasText(this.email)) {
            searching.append(" and email = :email");
            params.put("email", "%" + this.email + "%");
        }
        if (StringUtils.hasText(this.phoneNumber)) {
            searching.append(" and phoneNumber = :phoneNumber");
            params.put("phoneNumber", "%" + this.phoneNumber + "%");
        }
        return searching.toString();
    }
}
