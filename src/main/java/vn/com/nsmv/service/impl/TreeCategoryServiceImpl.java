package vn.com.nsmv.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import vn.com.nsmv.common.SokokanriException;
import vn.com.nsmv.dao.TreeCategoryDAO;
import vn.com.nsmv.entity.TreeCategory;
import vn.com.nsmv.i18n.SokokanriMessage;
import vn.com.nsmv.javabean.SortCondition;
import vn.com.nsmv.javabean.TreeCategorySearchCondition;
import vn.com.nsmv.javabean.TreeSearchCondition;
import vn.com.nsmv.service.TreeCategoryService;

import java.util.List;
import java.util.Set;

@Service("treeCartService")
@EnableTransactionManagement
public class TreeCategoryServiceImpl implements TreeCategoryService {

    @Autowired
    private TreeCategoryDAO treeCategoryDAO;


    public void updateTree(TreeCategory item) {
        try {
            treeCategoryDAO.saveOrUpdate(item);
        } catch (Exception e) {
            return;
        }
    }

    @Transactional
    public List<TreeCategory> getAll(
            TreeCategorySearchCondition searchCondition, SortCondition sortCondition, Integer offset, Integer maxResults)
            throws SokokanriException {
        return this.treeCategoryDAO.getAllItems(searchCondition, sortCondition, offset, maxResults);
    }

    @Transactional
    public int countAll(TreeCategorySearchCondition searchCondition) throws SokokanriException {
        return this.treeCategoryDAO.countAllItems(searchCondition);
    }

    @Transactional
    public void delete(Set<Long> selectedItems) throws SokokanriException {
        treeCategoryDAO.deleteSelected(selectedItems);
    }

    public TreeCategory getById(Long userCd) throws SokokanriException {
        return treeCategoryDAO.getById(userCd);
    }


    @Transactional
    public TreeCategory saveInformation(TreeCategory itemToUpdate) throws SokokanriException {

        Long userId = itemToUpdate.getId();
        TreeCategory tree = this.treeCategoryDAO.getById(userId);
        if (tree == null) {
            throw new SokokanriException(SokokanriMessage.getMessageErrorUserNotExists(LocaleContextHolder.getLocale()));
        }
        tree.setTreeGroup(itemToUpdate.getTreeGroup());
        tree.setName(itemToUpdate.getName());
        tree.setAge(itemToUpdate.getAge());
        tree.setDescription(itemToUpdate.getDescription());
        this.treeCategoryDAO.saveOrUpdate(tree);
        return tree;
    }

    @Transactional
    public Long add(TreeCategory treeCategory) throws SokokanriException {
        return this.treeCategoryDAO.add(treeCategory);
    }

}
