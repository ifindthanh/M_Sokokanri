package vn.com.nsmv.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import vn.com.nsmv.common.SokokanriException;
import vn.com.nsmv.dao.BillDAO;
import vn.com.nsmv.entity.Bill;
import vn.com.nsmv.entity.Category;
import vn.com.nsmv.javabean.SearchCondition;

public class BillDAOImpl implements BillDAO{

	private static final Logger logger = Logger.getLogger(BillDAOImpl.class);
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory)
	{
		this.sessionFactory = sessionFactory;
	}
	
	public Long add(Bill bill) throws SokokanriException {
		try
		{
			Session session = this.sessionFactory.getCurrentSession();
			session.persist(bill);
			return bill.getId();
		}
		catch (Exception ex)
		{
			logger.debug(ex);
			throw new SokokanriException(ex);
		}
	}

	public List<Bill> getAllBills(SearchCondition searchCondition, Integer offset, Integer maxResults)
			throws SokokanriException {
		try
		{
			Session session = this.sessionFactory.getCurrentSession();
			StringBuilder sql = new StringBuilder();
			sql.append("from Category c JOIN c.bill b where b.id > 0");
			Map<String, Object> params = new HashMap<String, Object>();
			if (searchCondition.getUserId() != null) {
				sql.append(" and c.user.id = :userId");
				params.put("userId", searchCondition.getUserId());
			}
			
			if (searchCondition.getStatus() != null) {
				sql.append(" and c.status = :status ");
				params.put("status", searchCondition.getStatus());
			}
			if (searchCondition.getBillId() != null) {
				sql.append(" and b.id = :billId ");
				params.put("billId", searchCondition.getBillId());
			}
			sql.append("group by b.id");
			Query query = session.createQuery(sql.toString());
			Iterator<Entry<String, Object>> iterator = params.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, Object> element = iterator.next();
				query.setParameter(element.getKey(), element.getValue());
			}
			if (offset != null)
			{
				query = query.setFirstResult(offset);
			}
			
			if (maxResults != null)
			{
				query = query.setMaxResults(maxResults);
			}
			List<Object[]> resultSet = query.list();
			List<Bill> result = new ArrayList<Bill>();
			if (resultSet != null) {
				for (Object[] item : resultSet) {
					if (Bill.class.isInstance(item[0])) {
						result.add((Bill) item[0]);
						continue;
					} 
					result.add((Bill) item[1]);
				}
			}
			return result;
		}
		catch (Exception ex)
		{
			logger.debug(ex);
			throw new SokokanriException(ex);
		}
	}

	public int countAllBills(SearchCondition searchCondition) throws SokokanriException {
		try
		{
			Session session = this.sessionFactory.getCurrentSession();
			StringBuilder sql = new StringBuilder();
			sql.append("select count (distinct b.id) from Category c JOIN c.bill b where b.id > 0");
			Map<String, Object> params = new HashMap<String, Object>();
			if (searchCondition.getStatus() != null) {
				sql.append(" and c.status = :status ");
				params.put("status", searchCondition.getStatus());
			}
			if (searchCondition.getBillId() != null) {
				sql.append(" and b.id = :billId ");
				params.put("billId", searchCondition.getBillId());
			}
			Query query = session.createQuery(sql.toString());
			Iterator<Entry<String, Object>> iterator = params.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, Object> element = iterator.next();
				query.setParameter(element.getKey(), element.getValue());
			}
			return ((Number) query.uniqueResult()).intValue();
		}
		catch (Exception ex)
		{
			logger.debug(ex);
			throw new SokokanriException(ex);
		}
	}

	public Bill getById(Long id) throws SokokanriException {
		try
		{
			Session session = this.sessionFactory.getCurrentSession();
			Query query = session.createQuery("from Bill where id = :bill_id");
			List list = query.setParameter("bill_id", id).list();
			if (list == null || list.isEmpty()) {
				return null;
			}
			Bill bill = (Bill) list.get(0);
			return bill;
		}
		catch (Exception ex)
		{
			logger.debug(ex);
			throw new SokokanriException(ex);
		}
	}
	
}
