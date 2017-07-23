package vn.com.nsmv.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.nsmv.common.SokokanriException;
import vn.com.nsmv.dao.MoneyExchangeDAO;
import vn.com.nsmv.entity.MoneyExchange;
import vn.com.nsmv.service.MoneyRateService;


@Service("moneyRateService")
public class MoneyRateImpl implements MoneyRateService {

    @Autowired
    private MoneyExchangeDAO moneyExchangeDAO;
    
    @Transactional
    public void saveMoneyRate(Double value) throws SokokanriException {
        MoneyExchange moneyExchange = this.moneyExchangeDAO.getMoneyExchange();
        if (moneyExchange == null) {
            moneyExchange = new MoneyExchange();
        }
        moneyExchange.setValue(value);
        this.moneyExchangeDAO.save(moneyExchange);
    }

}
