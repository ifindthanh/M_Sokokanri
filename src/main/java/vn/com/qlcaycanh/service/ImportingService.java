package vn.com.qlcaycanh.service;

import org.springframework.transaction.annotation.Transactional;
import vn.com.qlcaycanh.common.MyException;
import vn.com.qlcaycanh.entity.Category;
import vn.com.qlcaycanh.entity.ImportingEntity;
import vn.com.qlcaycanh.javabean.OrderSearchCondition;
import vn.com.qlcaycanh.javabean.SortCondition;

import java.util.List;
import java.util.Set;

public interface ImportingService {
    public void updateTree(ImportingEntity ImportingEntity) throws MyException;

    public List<ImportingEntity> getAll(
            OrderSearchCondition searchCondition, SortCondition sortCondition, Integer offset, Integer maxResults)
            throws MyException;

    @Transactional
    public int countAll(OrderSearchCondition searchCondition) throws MyException;

    @Transactional
    public void delete(Set<Long> selectedItems) throws MyException;

    @Transactional
    public void delete(Long selectedItem) throws MyException;

    public ImportingEntity getById(Long userCd) throws MyException;


    @Transactional
    public ImportingEntity saveInformation(ImportingEntity TreeToUpdate) throws MyException;

    @Transactional
    public Long add(ImportingEntity ImportingEntity) throws MyException;

    @Transactional
    public Category getCategoryById(Long categoryId);

    Long createOrder(Category category) throws MyException;

    void saveOrder(Category category) throws MyException;
}
