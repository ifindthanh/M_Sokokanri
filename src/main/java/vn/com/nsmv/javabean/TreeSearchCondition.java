package vn.com.nsmv.javabean;

import org.springframework.util.StringUtils;

import java.util.Map;

public class TreeSearchCondition {

    private String name;


    public TreeSearchCondition() {

    }

    public TreeSearchCondition(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSearching(Map<String, Object> params) {
        StringBuilder searching = new StringBuilder();
        if (StringUtils.hasText(this.name)) {
            searching.append(" and name like :name");
            params.put("name", "%" + this.name + "%");
        }
        return searching.toString();
    }
}
