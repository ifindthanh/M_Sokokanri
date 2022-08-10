package vn.com.nsmv.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import vn.com.nsmv.common.SokokanriException;
import vn.com.nsmv.dao.TreeDAO;
import vn.com.nsmv.entity.Tree;
import vn.com.nsmv.i18n.SokokanriMessage;
import vn.com.nsmv.javabean.TreeSearchCondition;
import vn.com.nsmv.javabean.SortCondition;
import vn.com.nsmv.javabean.TreeSearchCondition;
import vn.com.nsmv.service.TreeService;

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
            throws SokokanriException {
        return this.treeDAO.getAllItems(searchCondition, sortCondition, offset, maxResults);
    }

    @Transactional
    public int countAll(TreeSearchCondition searchCondition) throws SokokanriException {
        return this.treeDAO.countAllItems(searchCondition);
    }

    @Transactional
    public void delete(Set<Long> selectedItems) throws SokokanriException {
        treeDAO.deleteSelected(selectedItems);
    }

    public Tree getById(Long userCd) throws SokokanriException {
        return treeDAO.getById(userCd);
    }


    @Transactional
    public Tree saveInformation(Tree providerToUpdate) throws SokokanriException {

        Long userId = providerToUpdate.getId();
        Tree tree = this.treeDAO.getById(userId);
        if (tree == null) {
            throw new SokokanriException(SokokanriMessage.getMessageErrorUserNotExists(LocaleContextHolder.getLocale()));
        }
        tree.setName(providerToUpdate.getName());
        tree.setDescription(providerToUpdate.getDescription());
        this.treeDAO.saveOrUpdate(tree);
        return tree;
    }

    @Transactional
    public Long add(Tree tree) throws SokokanriException {
        return this.treeDAO.add(tree);
    }

}
