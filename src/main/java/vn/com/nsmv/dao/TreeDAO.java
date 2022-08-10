package vn.com.nsmv.dao;

import vn.com.nsmv.common.SokokanriException;
import vn.com.nsmv.entity.Tree;
import vn.com.nsmv.javabean.SortCondition;
import vn.com.nsmv.javabean.TreeSearchCondition;

import java.util.List;
import java.util.Set;

public interface TreeDAO {
    public Long add(Tree item) throws SokokanriException;

    public void saveOrUpdate(Tree item) throws SokokanriException;

    public void deleteById(Long id) throws SokokanriException;

    public Tree findById(Long id) throws SokokanriException;

    public List<Tree> getAllItems(TreeSearchCondition searchCondition, SortCondition sortCondition, Integer offset,
                                  Integer maxResults) throws SokokanriException;

    public int countAllItems(TreeSearchCondition searchCondition) throws SokokanriException;

    public boolean deleteSelected(Set<Long> ids);

    Tree getById(Long userCd);
}
