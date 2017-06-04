package vn.com.nsmv.dao;

import vn.com.nsmv.common.SokokanriException;
import vn.com.nsmv.entity.MoneyExchange;

public interface MoneyExchangeDAO {
	public void updateMoneyExchange(MoneyExchange moneyExchange) throws SokokanriException;

	public MoneyExchange getMoneyExchange() throws SokokanriException;

	public void add(MoneyExchange moneyExchange) throws SokokanriException;
}
