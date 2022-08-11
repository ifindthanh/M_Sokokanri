package vn.com.qlcaycanh.service;

import org.springframework.transaction.annotation.Transactional;
import vn.com.qlcaycanh.common.MyException;
import vn.com.qlcaycanh.entity.TreeCategory;
import vn.com.qlcaycanh.javabean.SortCondition;
import vn.com.qlcaycanh.javabean.TreeCategorySearchCondition;

import java.util.List;
import java.util.Set;

public interface TreeCategoryService {
    public void updateTree(TreeCategory TreeCategory);

    public List<TreeCategory> getAll(
            TreeCategorySearchCondition
                    searchCondition, SortCondition sortCondition, Integer offset, Integer maxResults)
            throws MyException;

    @Transactional
    public int countAll(TreeCategorySearchCondition searchCondition) throws MyException;

    @Transactional
    public void delete(Set<Long> selectedItems) throws MyException;

    public TreeCategory getById(Long userCd) throws MyException;


    @Transactional
    public TreeCategory saveInformation(TreeCategory TreeToUpdate) throws MyException;

    @Transactional
    public Long add(TreeCategory TreeCategory) throws MyException;

}
