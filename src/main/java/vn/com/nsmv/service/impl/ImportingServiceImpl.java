package vn.com.nsmv.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.nsmv.common.SokokanriException;
import vn.com.nsmv.dao.CategoryDAO;
import vn.com.nsmv.dao.ImportingDAO;
import vn.com.nsmv.entity.Category;
import vn.com.nsmv.entity.ImportingEntity;
import vn.com.nsmv.entity.User;
import vn.com.nsmv.i18n.SokokanriMessage;
import vn.com.nsmv.javabean.OrderSearchCondition;
import vn.com.nsmv.javabean.SortCondition;
import vn.com.nsmv.service.ImportingService;

import java.util.*;

@Service
public class ImportingServiceImpl implements ImportingService {
    @Autowired
    private ImportingDAO importingDAO;

    @Autowired
    private CategoryDAO categoryDAO;


    @Transactional
    public void updateTree(ImportingEntity tree) throws SokokanriException {
        try {
            tree.setTotal(tree.getQuantity() * tree.getPrice());
            importingDAO.saveOrUpdate(tree);
        } catch (Exception e) {
            throw new SokokanriException(e);
        }
    }

    @Transactional
    public List<ImportingEntity> getAll(
            OrderSearchCondition searchCondition, SortCondition sortCondition, Integer offset, Integer maxResults)
            throws SokokanriException {
        return this.importingDAO.getAllItems(searchCondition, sortCondition, offset, maxResults);
    }

    @Transactional
    public int countAll(OrderSearchCondition searchCondition) throws SokokanriException {
        return this.importingDAO.countAllItems(searchCondition);
    }

    @Transactional
    public void delete(Set<Long> selectedItems) throws SokokanriException {
        importingDAO.deleteSelected(selectedItems);
    }

    @Transactional
    public void delete(Long selectedItem) throws SokokanriException {
        Set<Long> items = new HashSet<Long>();
        items.add(selectedItem);
        this.delete(items);
    }

    public ImportingEntity getById(Long userCd) throws SokokanriException {
        return importingDAO.getById(userCd);
    }


    @Transactional
    public ImportingEntity saveInformation(ImportingEntity providerToUpdate) throws SokokanriException {

        Long userId = providerToUpdate.getId();
        ImportingEntity tree = this.importingDAO.getById(userId);
        if (tree == null) {
            throw new SokokanriException(SokokanriMessage.getMessageErrorUserNotExists(LocaleContextHolder.getLocale()));
        }
        tree.setTree(providerToUpdate.getTree());
        tree.setImportDate(new Date());
        tree.setQuantity(providerToUpdate.getQuantity());
        tree.setPrice(providerToUpdate.getPrice());
        tree.setTotal(providerToUpdate.getPrice() * providerToUpdate.getQuantity());
        tree.setProvider(providerToUpdate.getProvider());
        tree.setDescription(providerToUpdate.getDescription());
        this.importingDAO.saveOrUpdate(tree);
        return tree;
    }

    @Transactional
    public Long add(ImportingEntity tree) throws SokokanriException {
        return this.importingDAO.add(tree);
    }

    public Category getCategoryById(Long categoryId) {
        try {
            return categoryDAO.getById(categoryId);
        } catch (SokokanriException e) {
            return null;
        }
    }

    @Transactional(rollbackFor = {SokokanriException.class})
    public Long createOrder(Category category) throws SokokanriException {
        User user = new User();
        user.setId(category.getUserId());
        category.setUser(user);
        category.setStatus(0);
        Long categoryId = categoryDAO.add(category);

        Iterator<ImportingEntity> iterator = category.getItems().iterator();
        boolean hasRecord = false;
        while (iterator.hasNext()) {
            ImportingEntity item = iterator.next();

            if (item == null) {
                continue;
            }

            hasRecord = true;
            item.setCategory(category);
            item.setImportDate(new Date());
            this.importingDAO.add(item);
        }
        if (hasRecord) {
            return categoryId;
        }
        throw new SokokanriException("Cannot create order");
    }

    @Transactional
    public void saveOrder(Category category) throws SokokanriException {
        User user = new User();
        user.setId(category.getUserId());
        category.setUser(user);
        this.categoryDAO.saveCategory(category);
        Iterator<ImportingEntity> iterator = category.getItems().iterator();
        while (iterator.hasNext()) {
            ImportingEntity item = iterator.next();
            if (item == null) {
                continue;
            }

            item.setCategory(category);
            if (item.getId() == null) {
                this.importingDAO.add(item);
            } else {
                this.updateTree(item);
            }
        }
    }

}
