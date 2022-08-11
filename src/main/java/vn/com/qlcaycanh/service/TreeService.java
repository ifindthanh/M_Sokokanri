package vn.com.qlcaycanh.service;

import org.springframework.transaction.annotation.Transactional;
import vn.com.qlcaycanh.common.MyException;
import vn.com.qlcaycanh.entity.Tree;
import vn.com.qlcaycanh.javabean.TreeSearchCondition;
import vn.com.qlcaycanh.javabean.SortCondition;

import java.util.List;
import java.util.Set;

public interface TreeService {
    public void updateTree(Tree Tree);

    public List<Tree> getAll(
            TreeSearchCondition searchCondition, SortCondition sortCondition, Integer offset, Integer maxResults)
            throws MyException;

    @Transactional
    public int countAll(TreeSearchCondition searchCondition) throws MyException;

    @Transactional
    public void delete(Set<Long> selectedItems) throws MyException;

    public Tree getById(Long userCd) throws MyException;


    @Transactional
    public Tree saveInformation(Tree TreeToUpdate) throws MyException;

    @Transactional
    public Long add(Tree Tree) throws MyException;

}
