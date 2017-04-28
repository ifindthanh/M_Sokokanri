package vn.com.nsmv.dao.impl;

import java.math.*;
import java.text.*;
import java.util.*;

import javax.transaction.*;

import org.apache.log4j.*;
import org.hibernate.*;

import vn.com.nsmv.common.*;
import vn.com.nsmv.dao.*;
import vn.com.nsmv.entity.*;
import vn.com.nsmv.javabean.*;

/**
 */
@Transactional
public class ShukkaBodyDAOImpl implements ShukkaBodyDAO
{
	private static final SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
	private static final SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd");
	private static final SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
	private static final Logger logger = Logger.getLogger(ShukkaBodyDAOImpl.class);
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory)
	{
		this.sessionFactory = sessionFactory;
	}

	public List<ShukkaBody> getAllExportingItems(long lastRetrieveTime, String sokoCd)
	{
		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Timestamp lastRetieve = new java.sql.Timestamp(lastRetrieveTime);
		String sql = "from ShukkaBody where soko_cd is not null and soko_cd = '" + sokoCd
			+ "' and status = 0  and delete_flg = 0 and (create_date > '" + lastRetieve
			+ "' or update_date > '" + lastRetieve + "')";
		@SuppressWarnings("unchecked")
		List<ShukkaBody> listShukkaBody = session.createQuery(sql).list();
		if (listShukkaBody == null)
		{
			return new ArrayList<ShukkaBody>();
		}
		return listShukkaBody;
	}

	public List<ShukkaBody> getAllExportedItems(long fromDate, long toDate, String sokoCd)
	{
		Session session = this.sessionFactory.getCurrentSession();

		String sql = "from ShukkaBody where soko_cd is not null and soko_cd = '" + sokoCd
			+ "' and status = 1 and delete_flg = 0 and shukka_date >= '"
			+ new java.sql.Timestamp(fromDate) + "' and shukka_date <= '"
			+ new java.sql.Timestamp(toDate) + "'";
		@SuppressWarnings("unchecked")
		List<ShukkaBody> listShukkaBody = session.createQuery(sql).list();
		if (listShukkaBody == null)
		{
			return new ArrayList<ShukkaBody>();
		}
		return listShukkaBody;
	}

	public void update(ShukkaBody shukkaBody) throws SokokanriException
	{
		try
		{
			Session session = this.sessionFactory.getCurrentSession();
			session.saveOrUpdate(shukkaBody);
		}
		catch (Exception ex)
		{
			throw new SokokanriException(ex);
		}
	}

	public int countExportingItems(
		ExportingSearchCondition searchCondition,
		SortCondition sortCondition,
		int status,
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

		SQLQuery query = session.createSQLQuery(
			"SELECT count(distinct body.sagyo_date, body.SOJO_NO) FROM shukka_body body join shukka_head head on head.sagyo_date = body.sagyo_date and head.sojo_no = body.sojo_no and body.soko_cd = head.soko_cd WHERE "
				+ "body.soko_cd is not null and body.soko_cd = '" + sokoCd + "' and body.status = '"
				+ status + "'" + strSql);

		BigInteger count = (BigInteger) query.uniqueResult();

		return count.intValue();
	}

	public void add(ShukkaBody shukkaBody)
	{
		try
		{
			Session session = this.sessionFactory.getCurrentSession();
			session.persist(shukkaBody);
		}
		catch (Exception e)
		{
			logger.debug(e);
		}
	}

	public ShukkaBody getById(String bodyId)
	{
		Session session = sessionFactory.getCurrentSession();
		return (ShukkaBody) session
			.createQuery("from ShukkaBody where id = '" + bodyId + "' and delete_flg = 0")
			.uniqueResult();
	}

	public Long countItemByHeader(String sagyoDate, String sojoNo, String sokoCd)
	{
		Session session = sessionFactory.getCurrentSession();
		return (Long) session.createQuery(
			"select count(*) from ShukkaBody where soko_cd is not null and soko_cd = '" + sokoCd
				+ "' and sagyo_date = '" + sagyoDate + "' and sojo_no = '" + sojoNo
				+ "' and delete_flg = 0")
			.uniqueResult();
	}

	public List<ShukkaBody> getBodyByHeader(String sagyoDate, String sojoNo, String sokoCd)
	{
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<ShukkaBody> listShukkaBody = session.createQuery(
			"from ShukkaBody where soko_cd is not null and soko_cd = '" + sokoCd
				+ "' and sagyo_date = '" + sagyoDate + "' and sojo_no = '" + sojoNo
				+ "' and delete_flg = 0 order by data_kbn, renban")
			.list();
		if (listShukkaBody == null)
		{
			return new ArrayList<ShukkaBody>();
		}
		return listShukkaBody;
	}

	public Long countExportingItems(String sokoCd) throws SokokanriException
	{
		try
		{
			return ((Number) sessionFactory.getCurrentSession()
				.createQuery(
					"select count(*) from ShukkaBody where soko_cd is not null and soko_cd = '"
						+ sokoCd + "' and status = 0 and delete_flg = 0")
				.uniqueResult()).longValue();
		}
		catch (Exception ex)
		{
			throw new SokokanriException(ex);
		}
	}

	public Long countExportedToday(String sokoCd) throws SokokanriException
	{
		try
		{
			return ((Number) sessionFactory.getCurrentSession()
				.createQuery(
					"select count(*) from ShukkaBody where soko_cd is not null and soko_cd = '"
						+ sokoCd
						+ "' and  status = 1 and delete_flg = 0 and DATE(shukka_date) = CURDATE()")
				.uniqueResult()).longValue();
		}
		catch (Exception ex)
		{
			throw new SokokanriException(ex);
		}
	}

	private StringBuilder getSearching(ExportingSearchCondition searchCondition, boolean exporting)
	{
		StringBuilder searching = new StringBuilder();
		if (searchCondition == null)
		{
			return null;
		}
		if (!Utils.isEmpty(searchCondition.getSagyoDate()))
		{

			Date date = null;
			try
			{
				date = format.parse(searchCondition.getSagyoDate().trim());
				searching.append(" and body.SAGYO_DATE = '" + format1.format(date) + "'");
			}
			catch (ParseException ex)
			{
				logger.debug(ex);
			}
		}
		if (!Utils.isEmpty(searchCondition.getSukkaYoteibi()))
		{
			Date date = null;
			try
			{
				date = format.parse(searchCondition.getSukkaYoteibi().trim());
				searching.append(" and head.SUKKA_YOTEIBI = '" + format1.format(date) + "'");
			}
			catch (ParseException ex)
			{
				logger.debug(ex);
			}
		}
		if (!Utils.isEmpty(searchCondition.getJyuoka()))
		{
			searching
				.append(" and head.JYUOKA like '%" + searchCondition.getJyuoka().trim() + "%'");
		}
		if (!Utils.isEmpty(searchCondition.getSojoNo()))
		{
			searching
				.append(" and head.SOJO_NO like '%" + searchCondition.getSojoNo().trim() + "%'");
		}

		if (!exporting)
		{
			// search by shukka date
			if (!Utils.isEmpty(searchCondition.getShukkaDate()))
			{
				Date shukkaDate = null;
				try
				{
					shukkaDate = format.parse(searchCondition.getShukkaDate().trim());
					searching.append(
						" and DATE(body.SHUKKA_DATE)  ='" + format2.format(shukkaDate) + "'");
				}
				catch (ParseException ex)
				{
					logger.debug(ex);
				}
			}
		}
		return searching;
	}

	public Set<ShukkaHeadId> getShukkaHeadIds(
		ExportingSearchCondition searchCondition,
		SortCondition sortCondition,
		Integer offset,
		Integer maxResults,
		int status,
		String sokoCd)
	{
		Session session = sessionFactory.getCurrentSession();
		String limit;
		if (offset == null || maxResults == null)
		{
			limit = Constants.BLANK;
		}
		else
		{
			limit = " LIMIT " + offset + ", " + maxResults;
		}
		String strSql = "";
		StringBuilder searching = this.getSearching(searchCondition, status == 0);
		if ((searching != null) && (searching.length() != 0))
		{
			strSql += searching.toString();
		}

		strSql += Utils.getOrdering(sortCondition).toString();
		SQLQuery query = session.createSQLQuery(
			"SELECT distinct body.sagyo_date, body.SOJO_NO FROM shukka_body body join shukka_head head on head.sagyo_date = body.sagyo_date and head.sojo_no = body.sojo_no  and head.soko_cd = body.soko_cd WHERE head.soko_cd is not null and head.soko_cd = '"
				+ sokoCd + "' and  body.delete_flg = 0 and head.delete_flg = 0 and  body.status = '"
				+ status + "'" + strSql + " order by body.sojo_no " + limit);
		@SuppressWarnings("unchecked")
		List<Object[]> rows = query.list();
		Set<ShukkaHeadId> result = new LinkedHashSet<ShukkaHeadId>();
		if (rows.size() > 0)
		{
			for (Object[] row : rows)
			{
				ShukkaHeadId headId = new ShukkaHeadId();
				headId.setSagyoDate(row[0].toString());
				headId.setSojoNo(row[1].toString());
				headId.setSokoCd(sokoCd);
				result.add(headId);
			}
		}
		return result;
	}

	public ShukkaBody getShukkaBody(
		String sagyoDate,
		String sojoNo,
		String dataKbn,
		Integer renban,
		String sokoCd)
	{
		return (ShukkaBody) sessionFactory.getCurrentSession()
			.createQuery(
				"from ShukkaBody where soko_cd is not null and soko_cd = '" + sokoCd
					+ "' and sagyo_date = '" + sagyoDate + "' and sojo_no = '" + sojoNo
					+ "' and data_kbn = '" + dataKbn + "' and renban = " + renban)
			.setMaxResults(1)
			.uniqueResult();
	}

	public void delete(ShukkaBody body)
	{
		Session session = this.sessionFactory.getCurrentSession();
		try
		{
			session.delete(body);
		}
		catch (Exception ex)
		{
			logger.debug(ex);
		}

	}

}
