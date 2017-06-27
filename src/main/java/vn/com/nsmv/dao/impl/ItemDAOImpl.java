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
import org.springframework.transaction.annotation.Transactional;

import vn.com.nsmv.common.SokokanriException;
import vn.com.nsmv.dao.ItemDAO;
import vn.com.nsmv.entity.Item;
import vn.com.nsmv.javabean.SearchCondition;
import vn.com.nsmv.javabean.SortCondition;

public class ItemDAOImpl implements ItemDAO {

    private static final Logger logger = Logger.getLogger(ItemDAOImpl.class);
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void add(Item item) throws SokokanriException {
        try {
            Session session = this.sessionFactory.getCurrentSession();
            item.setCreatedDate(new Date());
            session.persist(item);
        } catch (Exception ex) {
            logger.debug(ex);
            throw new SokokanriException(ex);
        }

    }

    public List<Item> getItemsInCategory(Long categoryId) throws SokokanriException {
        try {
            Session session = this.sessionFactory.getCurrentSession();
            Query query = session.createQuery("from Items where category_id = :category_id");
            query.setParameter("category_id", categoryId);
            return query.list();
        } catch (Exception ex) {
            logger.debug(ex);
            throw new SokokanriException(ex);
        }
    }

    public void saveOrUpdate(Item item) throws SokokanriException {
        try {
            Session session = this.sessionFactory.getCurrentSession();
            item.setUpdateDate(new Date());
            session.saveOrUpdate(item);
        } catch (Exception ex) {
            logger.debug(ex);
            throw new SokokanriException(ex);
        }
    }

    @Transactional
    public void deleteById(Long id) throws SokokanriException {
        try {
            Session session = this.sessionFactory.getCurrentSession();
            String hql = "delete Item where id = :id";
            Query query = session.createQuery(hql).setParameter("id", id);
            query.executeUpdate();
        } catch (Exception ex) {
            logger.debug(ex);
            throw new SokokanriException(ex);
        }
    }

    public Item findById(Long id) throws SokokanriException {
        try {
            Session session = this.sessionFactory.getCurrentSession();
            Query query = session.createQuery("from Item where id = :id");
            List list = query.setParameter("id", id).list();
            if (list == null || list.isEmpty()) {
                return null;
            }
            Item item = (Item) list.get(0);
            return item;
        } catch (Exception ex) {
            logger.debug(ex);
            throw new SokokanriException(ex);
        }
    }

    public List<Item> getAllItems(
        SearchCondition searchCondition, SortCondition sortCondition, Integer offset, Integer maxResults)
        throws SokokanriException {
        try {
            Session session = this.sessionFactory.getCurrentSession();
            StringBuilder sql = new StringBuilder();
            sql.append("from Item c where id <> 0");
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
            if (offset != null) {
                query = query.setFirstResult(offset);
            }

            if (maxResults != null) {
                query = query.setMaxResults(maxResults);
            }
            List<Item> result = query.list();
            if (result == null) {
                result = new ArrayList<Item>();
            }
            return result;
        } catch (Exception ex) {
            logger.debug(ex);
            throw new SokokanriException(ex);
        }
    }
    
    public int countAllItems(SearchCondition searchCondition) throws SokokanriException {
        try
        {
            Session session = this.sessionFactory.getCurrentSession();
            StringBuilder sql = new StringBuilder("select COUNT(*) from Item where id <> 0 ");
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
