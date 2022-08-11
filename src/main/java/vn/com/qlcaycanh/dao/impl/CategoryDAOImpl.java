package vn.com.qlcaycanh.dao.impl;

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

import vn.com.qlcaycanh.common.MyException;
import vn.com.qlcaycanh.dao.CategoryDAO;
import vn.com.qlcaycanh.entity.Category;
import vn.com.qlcaycanh.javabean.SearchCondition;
import vn.com.qlcaycanh.javabean.SortCondition;

public class CategoryDAOImpl implements CategoryDAO {
	private static final Logger logger = Logger.getLogger(CategoryDAOImpl.class);
	private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
	
	public Long add(Category category) throws MyException {
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
			throw new MyException(ex);
		}
		
	}

	public Category getById(Long categoryId) throws MyException {
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
			throw new MyException(ex);
		}
	}

	public void saveCategory(Category category) throws MyException {
		try
		{
			category.setUpdateDate(new Date());
			Session session = this.sessionFactory.getCurrentSession();
			session.saveOrUpdate(category);
		}
		catch (Exception ex)
		{
			logger.debug(ex);
			throw new MyException(ex);
		}
	}

	
	public List<Category> getAllOrders(SearchCondition searchCondition, SortCondition sortCondition, Integer offset,
			Integer maxResults) throws MyException {
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
			throw new MyException(ex);
		}
	}


	public void deleteOrder(Long id) throws MyException {
		try
		{
	    	Session session = this.sessionFactory.getCurrentSession();
			String hql = "delete Category where id = :id";
		    Query query = session.createQuery(hql).setParameter("id", id);
		    query.executeUpdate();
		}
		catch (Exception ex)
		{
			logger.debug(ex);
			throw new MyException(ex);
		}		
	}
}
