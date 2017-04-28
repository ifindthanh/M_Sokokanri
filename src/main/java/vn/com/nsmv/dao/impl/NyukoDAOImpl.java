package vn.com.nsmv.dao.impl;

import java.text.*;
import java.util.*;

import org.apache.log4j.*;
import org.hibernate.*;
import org.springframework.transaction.annotation.*;

import vn.com.nsmv.common.*;
import vn.com.nsmv.dao.*;
import vn.com.nsmv.entity.*;
import vn.com.nsmv.javabean.*;

/**
 */
@Transactional
public class NyukoDAOImpl implements NyukoDAO
{
	private static final Logger logger = Logger.getLogger(NyukoDAOImpl.class);
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory)
	{
		this.sessionFactory = sessionFactory;
	}
	public List<Nyuko> getAllImportingItems(long lastRetrieveTime, String sokoCd)
	{
		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Timestamp lastRetieve = new java.sql.Timestamp(lastRetrieveTime);
		String sql = "from Nyuko where soko_cd is not null and soko_cd = '" + sokoCd
			+ "' and delete_flg = 0 and status = 0 and (create_date > '" + lastRetieve
			+ "' or update_date > '" + lastRetieve + "')";
		@SuppressWarnings("unchecked")
		List<Nyuko> list = session.createQuery(sql).list();
		if (list != null)
		{
			return list;
		}
		return new ArrayList<Nyuko>();
	}
	public List<Nyuko> getAllImportedItems(long fromDate, long toDate, String sokoCd)
	{
		Session session = this.sessionFactory.getCurrentSession();

		String sql = "from Nyuko where soko_cd is not null and soko_cd = '" + sokoCd
			+ "' and status = 1 and delete_flg = 0 and nyko_date >= '"
			+ new java.sql.Timestamp(fromDate) + "' and nyko_date <= '"
			+ new java.sql.Timestamp(toDate) + "'";
		@SuppressWarnings("unchecked")
		List<Nyuko> list = session.createQuery(sql).list();
		if (list != null)
		{
			return list;
		}
		return new ArrayList<Nyuko>();
	}

	public boolean add(Nyuko nyuko)
	{
		try
		{
			Session session = this.sessionFactory.getCurrentSession();
			session.persist(nyuko);
		}
		catch (Exception e)
		{
			logger.debug(e);
			return false;
		}
		return true;
	}
	@SuppressWarnings("unchecked")
	public List<Nyuko> getImportingItems(
		SearchCondition searchCondition,
		SortCondition sortCondition,
		Integer offset,
		Integer maxResult,
		Integer status,
		String sokoCd)
	{
		Session session = this.sessionFactory.getCurrentSession();
		String strSql = "";
		StringBuilder searching = this.getSearching(searchCondition, status == 0);
		if ((searching != null) && (searching.length() != 0))
		{
			strSql += searching.toString();
		}

		strSql += Utils.getOrdering(sortCondition).toString();
		List<Nyuko> list;
		try
		{
			Query query = session.createQuery(
				"from Nyuko where soko_cd is not null and soko_cd = '" + sokoCd
					+ "' and delete_flg = 0 and status = " + status.intValue() + strSql);
			if (offset != null)
			{
				query = query.setFirstResult(offset);
			}
			if (maxResult != null)
			{
				query = query.setMaxResults(maxResult);
			}
			list = query.list();
		}
		catch (Exception exception)
		{
			return new ArrayList<Nyuko>();
		}
		if (list == null)
		{
			return new ArrayList<Nyuko>();
		}
		return list;
	}

	public int getCountImportingItems(
		SearchCondition searchCondition,
		SortCondition sortCondition,
		Integer status,
		String sokoCd)
	{
		Session session = this.sessionFactory.getCurrentSession();
		String strSql = "";
		StringBuilder searching = this.getSearching(searchCondition, status == 0);
		if (searching != null)
		{
			strSql += searching.toString();
		}

		strSql += Utils.getOrdering(sortCondition).toString();

		Long count = (Long) session.createQuery(
			"select count(*) from Nyuko where soko_cd is not null and soko_cd = '" + sokoCd
				+ "' and status = " + status.intValue() + strSql)
			.uniqueResult();

		return count.intValue();
	}

	public void update(Nyuko nyuko) throws SokokanriException
	{
		try
		{
			nyuko.setUpdateDate(new Date());
			Session session = this.sessionFactory.getCurrentSession();
			session.saveOrUpdate(nyuko);
		}
		catch (Exception ex)
		{
			logger.debug(ex);
			throw new SokokanriException(ex);
		}
	}

	public Nyuko getItemById(String id, String sokoCd) throws SokokanriException
	{
		try
		{
			Session session = this.sessionFactory.getCurrentSession();
			return (Nyuko) session.createQuery(
				"from Nyuko where soko_cd is not null and soko_cd  = '" + sokoCd + "' and sz_no = '"
					+ id + "'")
				.uniqueResult();
		}
		catch (Exception ex)
		{
			logger.debug(ex);
			throw new SokokanriException(ex);
		}
	}
	public Long countImportedItems(String sokoCd) throws SokokanriException
	{
		try
		{
			return ((Number) sessionFactory.getCurrentSession()
				.createQuery(
					"select COUNT(*) from Nyuko where soko_cd is not null and soko_cd = '" + sokoCd
						+ "' and status = 1 and delete_flg = 0")
				.uniqueResult()).longValue();
		}
		catch (Exception ex)
		{
			logger.debug(ex);
			throw new SokokanriException(ex);
		}
	}

	public Long countImportingItems(String sokoCd) throws SokokanriException
	{
		try
		{
			return ((Number) sessionFactory.getCurrentSession()
				.createQuery(
					"select COUNT(*) from Nyuko where soko_cd is not null and soko_cd = '" + sokoCd
						+ "' and status = 0 and delete_flg = 0")
				.uniqueResult()).longValue();
		}
		catch (Exception ex)
		{
			logger.debug(ex);
			throw new SokokanriException(ex);
		}
	}

	public Long countImportedToday(String sokoCd) throws SokokanriException
	{
		try
		{
			return ((Number) sessionFactory.getCurrentSession()
				.createQuery(
					"select COUNT(*) from Nyuko where soko_cd is not null and soko_cd = '" + sokoCd
						+ "' and delete_flg = 0 and DATE(nyko_date) = CURDATE()")
				.uniqueResult()).longValue();
		}
		catch (Exception ex)
		{
			throw new SokokanriException(ex);
		}
	}
	public List<VBuzaiKigo> getAllBuzaiKigo(String sokoCd)
	{
		Session session = this.sessionFactory.getCurrentSession();
		try
		{
			String sql = "from VBuzaiKigo where soko_cd = '" + sokoCd + "'";
			@SuppressWarnings("unchecked")
			List<VBuzaiKigo> list = session.createQuery(sql).list();
			if (list != null)
			{
				return list;
			}
		}
		catch (Exception ex)
		{
			return new ArrayList<VBuzaiKigo>();
		}
		return new ArrayList<VBuzaiKigo>();
	}
	public List<VKakoMongon> getAllKakoMongon(String sokoCd)
	{
		Session session = this.sessionFactory.getCurrentSession();
		try
		{
			String sql = "from VKakoMongon where soko_cd = '" + sokoCd + "'";
			@SuppressWarnings("unchecked")
			List<VKakoMongon> list = session.createQuery(sql).list();
			if (list != null)
			{
				return list;
			}
		}
		catch (Exception ex)
		{
			logger.debug(ex);
		}
		return new ArrayList<VKakoMongon>();
	}
	public List<VZaisitu> getAllZaisitu(String sokoCd)
	{
		Session session = this.sessionFactory.getCurrentSession();
		try
		{
			String sql = "from VZaisitu where soko_cd = '" + sokoCd + "'";
			@SuppressWarnings("unchecked")
			List<VZaisitu> list = session.createQuery(sql).list();
			if (list != null)
			{
				return list;
			}
		}
		catch (Exception ex)
		{
			logger.debug(ex);
		}
		return new ArrayList<VZaisitu>();
	}

	private StringBuilder getSearching(SearchCondition searchCondition, boolean importing)
	{
		StringBuilder searching = new StringBuilder();
		if (searchCondition == null)
		{
			return null;
		}
		if (!Utils.isEmpty(searchCondition.getSojoNo()))
		{
			searching.append(" and SOJO_NO like '%" + searchCondition.getSojoNo() + "%'");
		}
		if (!Utils.isEmpty(searchCondition.getSzNo()))
		{
			searching.append(" and SZ_NO like '%" + searchCondition.getSzNo() + "%'");
		}
		if (!Utils.isEmpty(searchCondition.getBuzaiKigo()))
		{
			searching.append(" and BUZAI_KIGO = '" + searchCondition.getBuzaiKigo() + "'");
		}
		if (!Utils.isEmpty(searchCondition.getZaisitu()))
		{
			searching.append(" and ZAISITU = '" + searchCondition.getZaisitu() + "'");
		}
		if (!Utils.isEmpty(searchCondition.getKakoMongon()))
		{
			searching.append(" and KAKO_MONGON = '" + searchCondition.getKakoMongon() + "'");
		}
		if (!Utils.isEmpty(searchCondition.getDansunMongon()))
		{
			searching
				.append(" and DANSUN_MONGON like '%" + searchCondition.getDansunMongon() + "%'");
		}
		if (!Utils.isEmpty(searchCondition.getNagasa()))
		{
			searching.append(" and NAGASA = '" + searchCondition.getNagasa() + "'");
		}

		String dateField = "SAGYO_DATE"; //search by sagyoDate
		if (!importing)
		{
			dateField = "NYKO_DATE"; //search by nykoDate
		}

		if (!Utils.isEmpty(searchCondition.getDateStart()))
		{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			searching.append(
				" and " + dateField + " >= '" + format.format(searchCondition.getDateStartDate())
					+ "'");
		}
		if (!Utils.isEmpty(searchCondition.getDateEnd()))
		{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			c.setTime(searchCondition.getDateEndDate());
			c.add(Calendar.DATE, 1); // number of days to add
			String dtEnd = format.format(c.getTime()); // dt is now the new date
			searching.append(" and " + dateField + " < '" + dtEnd + "'");
		}
		if (!searchCondition.isIncludeExportedItems())
		{
			searching.append(" and (EXPORTED_FLG is null or EXPORTED_FLG <> 1 )");
		}

		return searching;
	}
	public void deleteAll()
	{
		try
		{
			String hql = "delete from Trans where sz_no is not null";
			Session currentSession = sessionFactory.getCurrentSession();
			currentSession.createQuery(hql).executeUpdate();
			hql = "delete from Nyuko where sz_no is not null";
			currentSession.createQuery(hql).executeUpdate();
			hql = "delete from ShukkaHead where sagyo_date is not null or sojo_no is not null";
			currentSession.createQuery(hql).executeUpdate();
			hql = "delete from ShukkaBody where id > 0";
			currentSession.createQuery(hql).executeUpdate();
		}
		catch (Exception ex)
		{
			logger.debug(ex);
		}

	}
	public void delete(Nyuko item)
	{
		Session session = this.sessionFactory.getCurrentSession();
		try
		{
			session.delete(item);
		}
		catch (Exception ex)
		{
			logger.debug(ex);
		}
	}
	@SuppressWarnings("unchecked")
	public List<Nyuko> getItemsToExport(SearchCondition searchCondition, String sokoCd)
	{
		Session session = this.sessionFactory.getCurrentSession();
		String strSql = "";
		StringBuilder searching = this.getSearching(searchCondition, false);
		if ((searching != null) && (searching.length() != 0))
		{
			strSql += searching.toString();
		}
		strSql += " or ((exported_flg is null or exported_flg = 0) and status = 2 and soko_cd is not null and soko_cd = '"
			+ sokoCd + "' ) ";

		List<Nyuko> list;
		try
		{
			Query query = session.createQuery(
				"from Nyuko where soko_cd is not null and soko_cd = '" + sokoCd
					+ "' and  delete_flg = 0 and status = 1" + strSql + "order by sojo_no, seq");
			list = query.list();
		}
		catch (Exception exception)
		{
			return new ArrayList<Nyuko>();
		}
		if (list == null)
		{
			return new ArrayList<Nyuko>();
		}
		return list;
	}
}
