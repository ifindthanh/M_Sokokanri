package vn.com.nsmv.dao.impl;

import java.util.*;

import org.hibernate.*;
import org.springframework.transaction.annotation.*;

import vn.com.nsmv.dao.*;
import vn.com.nsmv.entity.*;

@Transactional
public class PriShainBasDAOImpl implements PriShainBasDAO
{

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory)
	{
		this.sessionFactory = sessionFactory;
	}

	public PriShainBas getPriShainBas(String stf_no)
	{
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<PriShainBas> list = session
			.createQuery("from PriShainBas where STF_NO = '" + stf_no + "' ").list();
		if (list != null && !list.isEmpty())
		{
			return list.get(0);
		}
		return null;
	}
}