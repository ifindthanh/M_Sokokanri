package vn.com.nsmv.dao.impl;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import vn.com.nsmv.common.SokokanriException;
import vn.com.nsmv.dao.ItemDAO;
import vn.com.nsmv.entity.Item;

public class ItemDAOImpl implements ItemDAO {

	private static final Logger logger = Logger.getLogger(LogRequestDaoImpl.class);
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory)
	{
		this.sessionFactory = sessionFactory;
	}
	
	public void add(Item item) throws SokokanriException {
		try
		{
			Session session = this.sessionFactory.getCurrentSession();
			session.persist(item);
		}
		catch (Exception ex)
		{
			logger.debug(ex);
			throw new SokokanriException(ex);
		}
		
	}

}
