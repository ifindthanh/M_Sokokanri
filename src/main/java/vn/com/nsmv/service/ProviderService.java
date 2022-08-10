package vn.com.nsmv.service;

import org.springframework.transaction.annotation.Transactional;
import vn.com.nsmv.common.SokokanriException;
import vn.com.nsmv.entity.Provider;
import vn.com.nsmv.javabean.ProviderSearchCondition;
import vn.com.nsmv.javabean.SortCondition;

import java.util.List;
import java.util.Set;

public interface ProviderService {
    public void updateProvider(Provider provider);

    public List<Provider> getAll(
            ProviderSearchCondition searchCondition, SortCondition sortCondition, Integer offset, Integer maxResults)
            throws SokokanriException;

    @Transactional
    public int countAll(ProviderSearchCondition searchCondition) throws SokokanriException;

    @Transactional
    public void delete(Set<Long> selectedItems) throws SokokanriException;

    public Provider getById(Long userCd) throws SokokanriException;


    @Transactional
    public Provider saveInformation(Provider providerToUpdate) throws SokokanriException;

    @Transactional
    public Long add(Provider provider) throws SokokanriException;

}
