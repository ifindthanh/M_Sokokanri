package vn.com.nsmv.dao;

import vn.com.nsmv.common.SokokanriException;
import vn.com.nsmv.entity.TreeCategory;
import vn.com.nsmv.javabean.SortCondition;
import vn.com.nsmv.javabean.TreeCategorySearchCondition;
import vn.com.nsmv.javabean.TreeSearchCondition;

import java.util.List;
import java.util.Set;

public interface TreeCategoryDAO {
    public Long add(TreeCategory item) throws SokokanriException;

    public void saveOrUpdate(TreeCategory item) throws SokokanriException;

    public void deleteById(Long id) throws SokokanriException;

    public TreeCategory findById(Long id) throws SokokanriException;

    public List<TreeCategory> getAllItems(TreeCategorySearchCondition searchCondition, SortCondition sortCondition, Integer offset,
                                          Integer maxResults) throws SokokanriException;

    public int countAllItems(TreeCategorySearchCondition searchCondition) throws SokokanriException;

    public boolean deleteSelected(Set<Long> ids);

    TreeCategory getById(Long userCd);
}
