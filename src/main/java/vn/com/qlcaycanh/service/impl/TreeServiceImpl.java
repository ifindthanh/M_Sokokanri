package vn.com.qlcaycanh.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import vn.com.qlcaycanh.common.MyException;
import vn.com.qlcaycanh.dao.TreeDAO;
import vn.com.qlcaycanh.entity.Tree;
import vn.com.qlcaycanh.i18n.IMessage;
import vn.com.qlcaycanh.javabean.TreeSearchCondition;
import vn.com.qlcaycanh.javabean.SortCondition;
import vn.com.qlcaycanh.service.TreeService;

import java.util.List;
import java.util.Set;

@Service("treeService")
@EnableTransactionManagement
public class TreeServiceImpl implements TreeService {

    @Autowired
    private TreeDAO treeDAO;


    public void updateTree(Tree tree) {
        try {
            treeDAO.saveOrUpdate(tree);
        } catch (Exception e) {
            return;
        }
    }

    @Transactional
    public List<Tree> getAll(
            TreeSearchCondition searchCondition, SortCondition sortCondition, Integer offset, Integer maxResults)
            throws MyException {
        return this.treeDAO.getAllItems(searchCondition, sortCondition, offset, maxResults);
    }

    @Transactional
    public int countAll(TreeSearchCondition searchCondition) throws MyException {
        return this.treeDAO.countAllItems(searchCondition);
    }

    @Transactional
    public void delete(Set<Long> selectedItems) throws MyException {
        treeDAO.deleteSelected(selectedItems);
    }

    public Tree getById(Long userCd) throws MyException {
        return treeDAO.getById(userCd);
    }


    @Transactional
    public Tree saveInformation(Tree providerToUpdate) throws MyException {

        Long userId = providerToUpdate.getId();
        Tree tree = this.treeDAO.getById(userId);
        if (tree == null) {
            throw new MyException(IMessage.getMessageErrorUserNotExists(LocaleContextHolder.getLocale()));
        }
        tree.setName(providerToUpdate.getName());
        tree.setDescription(providerToUpdate.getDescription());
        this.treeDAO.saveOrUpdate(tree);
        return tree;
    }

    @Transactional
    public Long add(Tree tree) throws MyException {
        return this.treeDAO.add(tree);
    }

}
