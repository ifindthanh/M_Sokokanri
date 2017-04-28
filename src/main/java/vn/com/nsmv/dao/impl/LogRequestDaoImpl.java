package vn.com.nsmv.dao.impl;

import org.apache.log4j.*;
import org.hibernate.*;
import org.springframework.transaction.annotation.*;

import vn.com.nsmv.common.*;
import vn.com.nsmv.dao.*;
import vn.com.nsmv.entity.*;

@Transactional
public class LogRequestDaoImpl implements LogRequestDao
{
	private static final Logger logger = Logger.getLogger(LogRequestDaoImpl.class);
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory)
	{
		this.sessionFactory = sessionFactory;
	}

	public void addLog(LogRequest logRequest) throws SokokanriException
	{
		try
		{
			Session session = this.sessionFactory.getCurrentSession();
			session.persist(logRequest);
		}
		catch (Exception ex)
		{
			logger.debug(ex);
			throw new SokokanriException(ex);
		}
	}

}
