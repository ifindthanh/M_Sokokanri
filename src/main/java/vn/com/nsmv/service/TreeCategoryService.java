package vn.com.nsmv.service;

import org.springframework.transaction.annotation.Transactional;
import vn.com.nsmv.common.SokokanriException;
import vn.com.nsmv.entity.TreeCategory;
import vn.com.nsmv.javabean.SortCondition;
import vn.com.nsmv.javabean.TreeCategorySearchCondition;
import vn.com.nsmv.javabean.TreeSearchCondition;

import java.util.List;
import java.util.Set;

public interface TreeCategoryService {
    public void updateTree(TreeCategory TreeCategory);

    public List<TreeCategory> getAll(
            TreeCategorySearchCondition
                    searchCondition, SortCondition sortCondition, Integer offset, Integer maxResults)
            throws SokokanriException;

    @Transactional
    public int countAll(TreeCategorySearchCondition searchCondition) throws SokokanriException;

    @Transactional
    public void delete(Set<Long> selectedItems) throws SokokanriException;

    public TreeCategory getById(Long userCd) throws SokokanriException;


    @Transactional
    public TreeCategory saveInformation(TreeCategory TreeToUpdate) throws SokokanriException;

    @Transactional
    public Long add(TreeCategory TreeCategory) throws SokokanriException;

}
