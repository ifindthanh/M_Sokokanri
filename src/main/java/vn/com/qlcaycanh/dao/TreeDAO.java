package vn.com.qlcaycanh.dao;

import vn.com.qlcaycanh.common.MyException;
import vn.com.qlcaycanh.entity.Tree;
import vn.com.qlcaycanh.javabean.SortCondition;
import vn.com.qlcaycanh.javabean.TreeSearchCondition;

import java.util.List;
import java.util.Set;

public interface TreeDAO {
    public Long add(Tree item) throws MyException;

    public void saveOrUpdate(Tree item) throws MyException;

    public void deleteById(Long id) throws MyException;

    public Tree findById(Long id) throws MyException;

    public List<Tree> getAllItems(TreeSearchCondition searchCondition, SortCondition sortCondition, Integer offset,
                                  Integer maxResults) throws MyException;

    public int countAllItems(TreeSearchCondition searchCondition) throws MyException;

    public boolean deleteSelected(Set<Long> ids);

    Tree getById(Long userCd);
}
