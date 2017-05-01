package vn.com.nsmv.dao.impl;

import java.util.*;

import org.hibernate.*;
import org.springframework.transaction.annotation.*;

import vn.com.nsmv.dao.*;
import vn.com.nsmv.entity.*;

/**
 */
@Transactional
public class SokoDAOImpl implements SokoDAO
{
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory)
	{
		this.sessionFactory = sessionFactory;
	}

	public List<Soko> getAllSokoes()
	{
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Soko> list = session.createQuery("from Soko").list();
		if (list != null)
		{
			return list;
		}
		return new ArrayList<Soko>();
	}

}
