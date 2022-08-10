package vn.com.nsmv.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import vn.com.nsmv.common.SokokanriException;
import vn.com.nsmv.common.Utils;
import vn.com.nsmv.dao.ProviderDAO;
import vn.com.nsmv.entity.Provider;
import vn.com.nsmv.i18n.SokokanriMessage;
import vn.com.nsmv.javabean.ProviderSearchCondition;
import vn.com.nsmv.javabean.SortCondition;
import vn.com.nsmv.service.ProviderService;

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
            throws SokokanriException {
        return this.providerDAO.getAllItems(searchCondition, sortCondition, offset, maxResults);
    }

    @Transactional
    public int countAll(ProviderSearchCondition searchCondition) throws SokokanriException {
        return this.providerDAO.countAllItems(searchCondition);
    }

    @Transactional
    public void delete(Set<Long> selectedItems) throws SokokanriException {
        providerDAO.deleteSelected(selectedItems);
    }

    public Provider getById(Long userCd) throws SokokanriException {
        return providerDAO.getById(userCd);
    }


    @Transactional
    public Provider saveInformation(Provider providerToUpdate) throws SokokanriException {

        Long userId = providerToUpdate.getId();
        Provider provider = this.providerDAO.getById(userId);
        if (provider == null) {
            throw new SokokanriException(SokokanriMessage.getMessageErrorUserNotExists(LocaleContextHolder.getLocale()));
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
    public Long add(Provider provider) throws SokokanriException {
        Utils.checkEmail(provider.getEmail());
        return this.providerDAO.add(provider);
    }

}
