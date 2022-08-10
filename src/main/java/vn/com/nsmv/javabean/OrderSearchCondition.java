package vn.com.nsmv.javabean;

import java.util.Map;

public class OrderSearchCondition {
    private Long categoryId;
    private Long providerId;
    private Long treeId;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getProviderId() {
        return providerId;
    }

    public void setProviderId(Long providerId) {
        this.providerId = providerId;
    }

    public Long getTreeId() {
        return treeId;
    }

    public void setTreeId(Long treeId) {
        this.treeId = treeId;
    }

    public String getSearching(Map<String, Object> params) {
        StringBuilder searching = new StringBuilder();
        if (this.categoryId != null && this.categoryId != 0) {
            searching.append(" and category.id = :category");
            params.put("category", this.categoryId);
        }
        if (this.providerId != null && this.providerId != 0) {
            searching.append(" and provider.id = :provider");
            params.put("provider", this.providerId);
        }
        if (this.treeId != null && this.treeId != 0) {
            searching.append(" and tree.id = :tree");
            params.put("tree", this.treeId);
        }
        return searching.toString();
    }
}
