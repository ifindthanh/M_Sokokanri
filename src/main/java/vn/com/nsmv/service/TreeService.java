package vn.com.nsmv.service;

import org.springframework.transaction.annotation.Transactional;
import vn.com.nsmv.common.SokokanriException;
import vn.com.nsmv.entity.Tree;
import vn.com.nsmv.javabean.TreeSearchCondition;
import vn.com.nsmv.javabean.SortCondition;

import java.util.List;
import java.util.Set;

public interface TreeService {
    public void updateTree(Tree Tree);

    public List<Tree> getAll(
            TreeSearchCondition searchCondition, SortCondition sortCondition, Integer offset, Integer maxResults)
            throws SokokanriException;

    @Transactional
    public int countAll(TreeSearchCondition searchCondition) throws SokokanriException;

    @Transactional
    public void delete(Set<Long> selectedItems) throws SokokanriException;

    public Tree getById(Long userCd) throws SokokanriException;


    @Transactional
    public Tree saveInformation(Tree TreeToUpdate) throws SokokanriException;

    @Transactional
    public Long add(Tree Tree) throws SokokanriException;

}
