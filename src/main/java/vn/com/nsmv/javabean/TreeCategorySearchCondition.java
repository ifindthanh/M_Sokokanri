package vn.com.nsmv.javabean;

import org.springframework.util.StringUtils;

import java.util.Map;

public class TreeCategorySearchCondition {

    private String name;

    private Long treeId;

    public TreeCategorySearchCondition() {

    }

    public TreeCategorySearchCondition(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTreeId() {
        return treeId;
    }

    public void setTreeId(Long treeId) {
        this.treeId = treeId;
    }

    public String getSearching(Map<String, Object> params) {
        StringBuilder searching = new StringBuilder();
        if (StringUtils.hasText(this.name)) {
            searching.append(" and name like :name");
            params.put("name", "%" + this.name + "%");
        }
        if (this.treeId != null && this.treeId != 0) {
            searching.append(" and treeGroup.id = :id");
            params.put("id", this.treeId);
        }
        return searching.toString();
    }
}
