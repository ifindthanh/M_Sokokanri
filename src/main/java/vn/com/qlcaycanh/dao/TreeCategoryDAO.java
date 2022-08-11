package vn.com.qlcaycanh.dao;

import vn.com.qlcaycanh.common.MyException;
import vn.com.qlcaycanh.entity.TreeCategory;
import vn.com.qlcaycanh.javabean.SortCondition;
import vn.com.qlcaycanh.javabean.TreeCategorySearchCondition;

import java.util.List;
import java.util.Set;

public interface TreeCategoryDAO {
    public Long add(TreeCategory item) throws MyException;

    public void saveOrUpdate(TreeCategory item) throws MyException;

    public void deleteById(Long id) throws MyException;

    public TreeCategory findById(Long id) throws MyException;

    public List<TreeCategory> getAllItems(TreeCategorySearchCondition searchCondition, SortCondition sortCondition, Integer offset,
                                          Integer maxResults) throws MyException;

    public int countAllItems(TreeCategorySearchCondition searchCondition) throws MyException;

    public boolean deleteSelected(Set<Long> ids);

    TreeCategory getById(Long userCd);
}
