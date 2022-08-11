package vn.com.qlcaycanh.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import vn.com.qlcaycanh.common.MyException;
import vn.com.qlcaycanh.common.Utils;
import vn.com.qlcaycanh.dao.ProviderDAO;
import vn.com.qlcaycanh.entity.Provider;
import vn.com.qlcaycanh.i18n.IMessage;
import vn.com.qlcaycanh.javabean.ProviderSearchCondition;
import vn.com.qlcaycanh.javabean.SortCondition;
import vn.com.qlcaycanh.service.ProviderService;

import java.util.List;
import java.util.Set;

@Service("providerService")
@EnableTransactionManagement
public class ProviderServiceImpl implements ProviderService {

    @Autowired
    private ProviderDAO providerDAO;

    public void updateProvider(Provider provider) {
        try {
            providerDAO.saveOrUpdate(provider);
        } catch (Exception e) {
            return;
        }
    }

    @Transactional
    public List<Provider> getAll(
            ProviderSearchCondition searchCondition, SortCondition sortCondition, Integer offset, Integer maxResults)
            throws MyException {
        return this.providerDAO.getAllItems(searchCondition, sortCondition, offset, maxResults);
    }

    @Transactional
    public int countAll(ProviderSearchCondition searchCondition) throws MyException {
        return this.providerDAO.countAllItems(searchCondition);
    }

    @Transactional
    public void delete(Set<Long> selectedItems) throws MyException {
        providerDAO.deleteSelected(selectedItems);
    }

    public Provider getById(Long userCd) throws MyException {
        return providerDAO.getById(userCd);
    }


    @Transactional
    public Provider saveInformation(Provider providerToUpdate) throws MyException {

        Long userId = providerToUpdate.getId();
        Provider provider = this.providerDAO.getById(userId);
        if (provider == null) {
            throw new MyException(IMessage.getMessageErrorUserNotExists(LocaleContextHolder.getLocale()));
        }
        provider.setName(providerToUpdate.getName());
        provider.setAddress(providerToUpdate.getAddress());
        provider.setDescription(providerToUpdate.getDescription());
        provider.setPhoneNumber(providerToUpdate.getPhoneNumber());
        provider.setRepresentor(providerToUpdate.getRepresentor());
        provider.setEmail(providerToUpdate.getEmail());
        this.providerDAO.saveOrUpdate(provider);
        return provider;
    }

    @Transactional
    public Long add(Provider provider) throws MyException {
        Utils.checkEmail(provider.getEmail());
        return this.providerDAO.add(provider);
    }

}
