package vn.com.nsmv.dao.impl;

import java.util.*;

import javax.transaction.*;

import org.hibernate.*;

import vn.com.nsmv.dao.*;
import vn.com.nsmv.entity.*;

/**
 */
@Transactional
public class ShukkaHeadDAOImpl implements ShukkaHeadDAO
{
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory)
	{
		this.sessionFactory = sessionFactory;
	}

	public ShukkaHead getShukkaHeadById(ShukkaHeadId headId)
	{
		Session session = sessionFactory.getCurrentSession();
		return (ShukkaHead) session.createQuery(
			"from ShukkaHead where soko_cd is not null and soko_cd = '" + headId.getSokoCd()
				+ "' and sagyo_date = '" + headId.getSagyoDate() + "' and sojo_no = '"
				+ headId.getSojoNo() + "'")
			.uniqueResult();
	}

	public void add(ShukkaHead shukkaHead)
	{
		Session session = null;
		try
		{
			session = this.sessionFactory.getCurrentSession();
			session.persist(shukkaHead);
		}
		catch (Exception e)
		{
			if (session != null)
			{
				session.flush();
			}
		}
	}

	public void update(ShukkaHead shukkaHead)
	{
		Session session = null;
		try
		{
			shukkaHead.setUpdateDate(new Date());
			session = this.sessionFactory.getCurrentSession();
			session.saveOrUpdate(shukkaHead);
		}
		catch (Exception e)
		{
			if (session != null)
			{
				session.flush();
			}
		}

	}

	public void delete(ShukkaHead head)
	{
		Session session = this.sessionFactory.getCurrentSession();
		try
		{
			session.delete(head);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
