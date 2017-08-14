package vn.com.nsmv.javabean;

import java.util.Map;

public class TransactionSearchCondition {
    
    private String email;

    private Long userId;
    
    private String fromDate;
    
    private String toDate;
    
    public String getEmail() {
        return email;
    }

    
    public void setEmail(String email) {
        this.email = email;
    }


    public String getSearching(TransactionSearchCondition searchCondition, Map<String, Object> params) {
        StringBuilder searching = new StringBuilder();
        if (this.userId != null) {
            searching.append(" and userId = :userId");
            params.put("userId", this.userId);
        }
        return searching.toString();
    }


    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public String getFromDate() {
        return fromDate;
    }
    
    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }
    
    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }
    
}
