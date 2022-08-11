package vn.com.qlcaycanh.dao;

import vn.com.qlcaycanh.common.MyException;
import vn.com.qlcaycanh.entity.ImportingEntity;
import vn.com.qlcaycanh.javabean.OrderSearchCondition;
import vn.com.qlcaycanh.javabean.SortCondition;

import java.util.List;
import java.util.Set;

public interface ImportingDAO {
    public Long add(ImportingEntity item) throws MyException;

    public void saveOrUpdate(ImportingEntity item) throws MyException;

    public void deleteById(Long id) throws MyException;

    public ImportingEntity findById(Long id) throws MyException;

    public List<ImportingEntity> getAllItems(OrderSearchCondition searchCondition, SortCondition sortCondition, Integer offset,
                                             Integer maxResults) throws MyException;

    public int countAllItems(OrderSearchCondition searchCondition) throws MyException;

    public boolean deleteSelected(Set<Long> ids);

    ImportingEntity getById(Long userCd);
}
