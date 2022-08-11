package vn.com.qlcaycanh.service;

import org.springframework.transaction.annotation.Transactional;
import vn.com.qlcaycanh.common.MyException;
import vn.com.qlcaycanh.entity.Provider;
import vn.com.qlcaycanh.javabean.ProviderSearchCondition;
import vn.com.qlcaycanh.javabean.SortCondition;

import java.util.List;
import java.util.Set;

public interface ProviderService {
    public void updateProvider(Provider provider);

    public List<Provider> getAll(
            ProviderSearchCondition searchCondition, SortCondition sortCondition, Integer offset, Integer maxResults)
            throws MyException;

    @Transactional
    public int countAll(ProviderSearchCondition searchCondition) throws MyException;

    @Transactional
    public void delete(Set<Long> selectedItems) throws MyException;

    public Provider getById(Long userCd) throws MyException;


    @Transactional
    public Provider saveInformation(Provider providerToUpdate) throws MyException;

    @Transactional
    public Long add(Provider provider) throws MyException;

}
