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
import vn.com.nsmv.dao.CategoryDAO;
import vn.com.nsmv.entity.Category;
import vn.com.nsmv.javabean.SearchCondition;
import vn.com.nsmv.javabean.SortCondition;

public class CategoryDAOImpl implements CategoryDAO {
	private static final Logger logger = Logger.getLogger(CategoryDAOImpl.class);
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory)
	{
		this.sessionFactory = sessionFactory;
	}
	
	public Long add(Category category) throws SokokanriException {
		try
		{
			category.setCreatedDate(new Date());
			Session session = this.sessionFactory.getCurrentSession();
			session.persist(category);
			return category.getId();
		}
		catch (Exception ex)
		{
			logger.debug(ex);
			throw new SokokanriException(ex);
		}
		
	}

	public Category getById(Long categoryId) throws SokokanriException {
		try
		{
			Session session = this.sessionFactory.getCurrentSession();
			Query query = session.createQuery("from Category where id = :category_id");
			List list = query.setParameter("category_id", categoryId).list();
			if (list == null || list.isEmpty()) {
				return null;
			}
			Category category = (Category) list.get(0);
			return category;
		}
		catch (Exception ex)
		{
			logger.debug(ex);
			throw new SokokanriException(ex);
		}
	}

	public void saveCategory(Category category) throws SokokanriException {
		try
		{
			category.setUpdateDate(new Date());
			Session session = this.sessionFactory.getCurrentSession();
			session.saveOrUpdate(category);
		}
		catch (Exception ex)
		{
			logger.debug(ex);
			throw new SokokanriException(ex);
		}
	}

	
	public List<Category> getAllOrders(SearchCondition searchCondition, SortCondition sortCondition, Integer offset,
			Integer maxResults) throws SokokanriException {
		try
		{
			Session session = this.sessionFactory.getCurrentSession();
			StringBuilder sql = new StringBuilder();
			sql.append("from Category c where id <> 0");
			Map<String, Object> params = new HashMap<String, Object>();
			if (searchCondition != null) {
				sql.append(searchCondition.getSearching(searchCondition, params));
			}
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
			List<Category> result = query.list();
			if (result == null) {
				result = new ArrayList<Category>();
			}
			return result;
		}
		catch (Exception ex)
		{
			logger.debug(ex);
			throw new SokokanriException(ex);
		}
	}

	public int countAllOrders(SearchCondition searchCondition) throws SokokanriException {
		try
		{
			Session session = this.sessionFactory.getCurrentSession();
			StringBuilder sql = new StringBuilder("select COUNT(*) from Category where id <> 0 ");
			Map<String, Object> params = new HashMap<String, Object>();
			if (searchCondition != null) {
				sql.append(searchCondition.getSearching(searchCondition, params));
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
}
