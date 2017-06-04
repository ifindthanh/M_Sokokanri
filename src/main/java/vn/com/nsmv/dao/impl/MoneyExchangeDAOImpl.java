package vn.com.nsmv.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import vn.com.nsmv.common.SokokanriException;
import vn.com.nsmv.dao.MoneyExchangeDAO;
import vn.com.nsmv.entity.MoneyExchange;

public class MoneyExchangeDAOImpl implements MoneyExchangeDAO {
	private SessionFactory sessionFactory;
	
	@Transactional
	public void updateMoneyExchange(MoneyExchange moneyExchange) throws SokokanriException {
		Session session = sessionFactory.getCurrentSession();
		try {
			moneyExchange.setUpdateDate(new Date());
			session.saveOrUpdate(moneyExchange);
		} catch (Exception ex) {
			throw new SokokanriException(ex);
		}
	}
	
	public void setSessionFactory(SessionFactory sessionFactory)
	{
		this.sessionFactory = sessionFactory;
	}

	@Transactional
	public MoneyExchange getMoneyExchange() throws SokokanriException {
		Session session = sessionFactory.getCurrentSession();
		try {
			List list = session.createQuery("from MoneyExchange where id = '1'").list();
			if (list == null || list.isEmpty()) {
				return null;
			}
			return (MoneyExchange) list.get(0);
		} catch (Exception ex) {
			throw new SokokanriException(ex);
		}
	}

	@Transactional
	public void add(MoneyExchange moneyExchange) throws SokokanriException {
		Session session = sessionFactory.getCurrentSession();
		try {
			moneyExchange.setCreateDate(new Date());
			session.persist(moneyExchange);
		} catch (Exception ex) {
			throw new SokokanriException(ex);
		}
		
	}

}
