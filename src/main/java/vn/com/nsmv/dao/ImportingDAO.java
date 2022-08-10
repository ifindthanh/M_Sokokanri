package vn.com.nsmv.dao;

import vn.com.nsmv.common.SokokanriException;
import vn.com.nsmv.entity.ImportingEntity;
import vn.com.nsmv.javabean.OrderSearchCondition;
import vn.com.nsmv.javabean.SortCondition;
import vn.com.nsmv.javabean.TreeSearchCondition;

import java.util.List;
import java.util.Set;

public interface ImportingDAO {
    public Long add(ImportingEntity item) throws SokokanriException;

    public void saveOrUpdate(ImportingEntity item) throws SokokanriException;

    public void deleteById(Long id) throws SokokanriException;

    public ImportingEntity findById(Long id) throws SokokanriException;

    public List<ImportingEntity> getAllItems(OrderSearchCondition searchCondition, SortCondition sortCondition, Integer offset,
                                             Integer maxResults) throws SokokanriException;

    public int countAllItems(OrderSearchCondition searchCondition) throws SokokanriException;

    public boolean deleteSelected(Set<Long> ids);

    ImportingEntity getById(Long userCd);
}
