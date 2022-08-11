package vn.com.qlcaycanh.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import vn.com.qlcaycanh.common.MyException;
import vn.com.qlcaycanh.dao.TreeCategoryDAO;
import vn.com.qlcaycanh.entity.TreeCategory;
import vn.com.qlcaycanh.i18n.IMessage;
import vn.com.qlcaycanh.javabean.SortCondition;
import vn.com.qlcaycanh.javabean.TreeCategorySearchCondition;
import vn.com.qlcaycanh.service.TreeCategoryService;

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
            throws MyException {
        return this.treeCategoryDAO.getAllItems(searchCondition, sortCondition, offset, maxResults);
    }

    @Transactional
    public int countAll(TreeCategorySearchCondition searchCondition) throws MyException {
        return this.treeCategoryDAO.countAllItems(searchCondition);
    }

    @Transactional
    public void delete(Set<Long> selectedItems) throws MyException {
        treeCategoryDAO.deleteSelected(selectedItems);
    }

    public TreeCategory getById(Long userCd) throws MyException {
        return treeCategoryDAO.getById(userCd);
    }


    @Transactional
    public TreeCategory saveInformation(TreeCategory itemToUpdate) throws MyException {

        Long userId = itemToUpdate.getId();
        TreeCategory tree = this.treeCategoryDAO.getById(userId);
        if (tree == null) {
            throw new MyException(IMessage.getMessageErrorUserNotExists(LocaleContextHolder.getLocale()));
        }
        tree.setTreeGroup(itemToUpdate.getTreeGroup());
        tree.setName(itemToUpdate.getName());
        tree.setAge(itemToUpdate.getAge());
        tree.setDescription(itemToUpdate.getDescription());
        this.treeCategoryDAO.saveOrUpdate(tree);
        return tree;
    }

    @Transactional
    public Long add(TreeCategory treeCategory) throws MyException {
        return this.treeCategoryDAO.add(treeCategory);
    }

}
