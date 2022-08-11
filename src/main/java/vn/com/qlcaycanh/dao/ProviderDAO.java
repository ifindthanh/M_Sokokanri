package vn.com.qlcaycanh.dao;

import vn.com.qlcaycanh.common.MyException;
import vn.com.qlcaycanh.entity.Provider;
import vn.com.qlcaycanh.javabean.ProviderSearchCondition;
import vn.com.qlcaycanh.javabean.SortCondition;

import java.util.List;
import java.util.Set;

public interface ProviderDAO {
    public Long add(Provider item) throws MyException;

    public void saveOrUpdate(Provider item) throws MyException;

    public void deleteById(Long id) throws MyException;

    public Provider findById(Long id) throws MyException;

    public List<Provider> getAllItems(ProviderSearchCondition searchCondition, SortCondition sortCondition, Integer offset,
                                      Integer maxResults) throws MyException;

    public int countAllItems(ProviderSearchCondition searchCondition) throws MyException;

    public Provider getByEmail(String userCd);

    public boolean deleteSelected(Set<Long> ids);

    Provider getById(Long userCd);
}
