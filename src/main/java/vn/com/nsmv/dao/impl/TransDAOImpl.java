package vn.com.nsmv.dao.impl;

import java.util.*;

import javax.transaction.*;

import org.hibernate.*;

import vn.com.nsmv.common.*;
import vn.com.nsmv.dao.*;
import vn.com.nsmv.entity.*;

/**
 */
@Transactional
public class TransDAOImpl implements TransDAO
{
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory)
	{
		this.sessionFactory = sessionFactory;
	}

	public boolean add(Trans trans)
	{
		try
		{
			Session session = this.sessionFactory.getCurrentSession();
			session.persist(trans);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public Trans existInTempTable(String id, String sokoCd)
	{

		@SuppressWarnings("unchecked")
		List<Trans> list = sessionFactory.getCurrentSession()
			.createQuery(
				"from Trans where soko_cd is not null and soko_cd = '" + sokoCd + "' and sz_no = '"
					+ id + "' and (delete_flg is null or delete_flg = 0)")
			.list();
		if (list == null || list.isEmpty())
		{
			return null;
		}
		return list.get(0);
	}

	public void deleteInTempTable(String id, String sokoCd) throws SokokanriException
	{
		try
		{
			String hql = "update Trans set delete_flg = 1 where soko_cd is not null and soko_cd = '"
				+ sokoCd + "' and sz_no = '" + id + "' ";
			sessionFactory.getCurrentSession().createQuery(hql).executeUpdate();
		}
		catch (Exception ex)
		{
			throw new SokokanriException(ex);
		}
	}

}
