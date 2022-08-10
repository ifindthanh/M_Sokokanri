package vn.com.nsmv.dao;

import vn.com.nsmv.common.SokokanriException;
import vn.com.nsmv.entity.Provider;
import vn.com.nsmv.entity.User;
import vn.com.nsmv.javabean.ProviderSearchCondition;
import vn.com.nsmv.javabean.SearchCondition;
import vn.com.nsmv.javabean.SortCondition;

import java.util.List;
import java.util.Set;

public interface ProviderDAO {
    public Long add(Provider item) throws SokokanriException;

    public void saveOrUpdate(Provider item) throws SokokanriException;

    public void deleteById(Long id) throws SokokanriException;

    public Provider findById(Long id) throws SokokanriException;

    public List<Provider> getAllItems(ProviderSearchCondition searchCondition, SortCondition sortCondition, Integer offset,
                                      Integer maxResults) throws SokokanriException;

    public int countAllItems(ProviderSearchCondition searchCondition) throws SokokanriException;

    public Provider getByEmail(String userCd);

    public boolean deleteSelected(Set<Long> ids);

    Provider getById(Long userCd);
}
