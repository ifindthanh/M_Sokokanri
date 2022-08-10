package vn.com.nsmv.service;

import org.springframework.transaction.annotation.Transactional;
import vn.com.nsmv.common.SokokanriException;
import vn.com.nsmv.entity.Category;
import vn.com.nsmv.entity.ImportingEntity;
import vn.com.nsmv.javabean.OrderSearchCondition;
import vn.com.nsmv.javabean.SortCondition;
import vn.com.nsmv.javabean.TreeSearchCondition;

import java.util.List;
import java.util.Set;

public interface ImportingService {
    public void updateTree(ImportingEntity ImportingEntity) throws SokokanriException;

    public List<ImportingEntity> getAll(
            OrderSearchCondition searchCondition, SortCondition sortCondition, Integer offset, Integer maxResults)
            throws SokokanriException;

    @Transactional
    public int countAll(OrderSearchCondition searchCondition) throws SokokanriException;

    @Transactional
    public void delete(Set<Long> selectedItems) throws SokokanriException;

    @Transactional
    public void delete(Long selectedItem) throws SokokanriException;

    public ImportingEntity getById(Long userCd) throws SokokanriException;


    @Transactional
    public ImportingEntity saveInformation(ImportingEntity TreeToUpdate) throws SokokanriException;

    @Transactional
    public Long add(ImportingEntity ImportingEntity) throws SokokanriException;

    @Transactional
    public Category getCategoryById(Long categoryId);

    Long createOrder(Category category) throws SokokanriException;

    void saveOrder(Category category) throws SokokanriException;
}
