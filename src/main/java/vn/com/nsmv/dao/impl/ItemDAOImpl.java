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
                if (List.class.isInstance(element.getValue())) {
                    query.setParameterList(element.getKey(), (List) element.getValue());
                } else {
                    query.setParameter(element.getKey(), element.getValue());
                }
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
                if (List.class.isInstance(element.getValue())) {
                    query.setParameterList(element.getKey(), (List) element.getValue());
                } else {
                    query.setParameter(element.getKey(), element.getValue());
                }
            }
            return ((Number) query.uniqueResult()).intValue();
        }
        catch (Exception ex)
        {
            logger.debug(ex);
            throw new SokokanriException(ex);
        }
    }
    
    public List<String> getAllBrands(Long userId, Integer status)
    {
        Session session = this.sessionFactory.getCurrentSession();
        try
        {
            StringBuilder sql = new StringBuilder("select distinct TRIM(brand) from VBrand where brand is not null");
            @SuppressWarnings("unchecked")
            List<String> list = getList(userId, status, session, sql);
            if (list != null)
            {
                return list;
            }
        }
        catch (Exception ex)
        {
            return new ArrayList<String>();
        }
        return new ArrayList<String>();
    }

    public List<String> getAllBuyingCodes(Long userId, Integer status) throws SokokanriException {
        Session session = this.sessionFactory.getCurrentSession();
        try
        {
            StringBuilder sql = new StringBuilder("select distinct TRIM(buyingCode) from VBuyingCode where buyingCode is not null");
            @SuppressWarnings("unchecked")
            List<String> list = getList(userId, status, session, sql);
            if (list != null)
            {
                return list;
            }
        }
        catch (Exception ex)
        {
            return new ArrayList<String>();
        }
        return new ArrayList<String>();
    }

    private List<String> getList(Long userId, Integer status, Session session, StringBuilder sql) {
        Map<String, Object> params = new HashMap<String, Object>();
        if (userId != null) {
            sql.append(" AND userId = :userId ");
            params.put("userId", userId);
        }
        
        if (status != null && status != 999) {
            
            if (status == 0) {
                sql.append(" AND (status = :status or status = -1)");
            } else if (status == 1) {
                sql.append(" AND (status = :status or status = -2)");
            } else {
                sql.append(" AND status = :status");
            }
            params.put("status", status);
        }  else {
            sql.append(" and status <> 8 and status <> -5");
        }
        
        Query query = session.createQuery(sql.toString());
        Iterator<Entry<String, Object>> iterator = params.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<String, Object> element = iterator.next();
            query.setParameter(element.getKey(), element.getValue());
        }
        
        @SuppressWarnings("unchecked")
        List<String> list = query.list();
        return list;
    }

    public List<String> getAllTransferId(Long userId, Integer status) throws SokokanriException {
        Session session = this.sessionFactory.getCurrentSession();
        try
        {
            StringBuilder sql = new StringBuilder("select distinct TRIM(transferId) from VTransferId where transferId is not null");
            @SuppressWarnings("unchecked")
            List<String> list = getList(userId, status, session, sql);
            if (list != null)
            {
                return list;
            }
        }
        catch (Exception ex)
        {
            return new ArrayList<String>();
        }
        return new ArrayList<String>();
    }

    public Double getLoanMoney(Long id) throws SokokanriException {
        //get all loan money
        // this money is sum of total of the orders that has status from approved (1) to waiting to bill exported (6)
        Session session = this.sessionFactory.getCurrentSession();
        try
        {
            StringBuilder sql = new StringBuilder("select SUM(total) from Item where user.id = :userId and status > 0 and status < 7");
            Query query = session.createQuery(sql.toString());
            query.setLong("userId", id);
            return (Double) query.uniqueResult();
        }
        catch (Exception ex)
        {
            throw new SokokanriException(ex);
        }
    }

}
