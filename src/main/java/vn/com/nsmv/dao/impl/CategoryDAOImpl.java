package vn.com.nsmv.dao.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import vn.com.nsmv.common.SokokanriException;
import vn.com.nsmv.dao.CategoryDAO;
import vn.com.nsmv.entity.Category;

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
}
