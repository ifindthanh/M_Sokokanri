package vn.com.nsmv.dao.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import vn.com.nsmv.common.SokokanriException;
import vn.com.nsmv.dao.ItemDAO;
import vn.com.nsmv.entity.Item;

public class ItemDAOImpl implements ItemDAO {

	private static final Logger logger = Logger.getLogger(ItemDAOImpl.class);
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory)
	{
		this.sessionFactory = sessionFactory;
	}
	
	public void add(Item item) throws SokokanriException {
		try
		{
			Session session = this.sessionFactory.getCurrentSession();
			item.setCreatedDate(new Date());
			session.persist(item);
		}
		catch (Exception ex)
		{
			logger.debug(ex);
			throw new SokokanriException(ex);
		}
		
	}

	public List<Item> getItemsInCategory(Long categoryId) throws SokokanriException {
		try
		{
			Session session = this.sessionFactory.getCurrentSession();
			Query query = session.createQuery("from Items where category_id = :category_id");
			query.setParameter("category_id", categoryId);
			return query.list();
		}
		catch (Exception ex)
		{
			logger.debug(ex);
			throw new SokokanriException(ex);
		}
	}

	public void saveOrUpdate(Item item) throws SokokanriException {
		try
		{
			Session session = this.sessionFactory.getCurrentSession();
			item.setUpdateDate(new Date());
			session.saveOrUpdate(item);
		}
		catch (Exception ex)
		{
			logger.debug(ex);
			throw new SokokanriException(ex);
		}
	}

	@Transactional
	public void deleteById(Long id) throws SokokanriException {
	    try
		{
	    	Session session = this.sessionFactory.getCurrentSession();
			String hql = "delete Item where id = :id";
		    Query query = session.createQuery(hql).setParameter("id", id);
		    query.executeUpdate();
		}
		catch (Exception ex)
		{
			logger.debug(ex);
			throw new SokokanriException(ex);
		}
	}

}
