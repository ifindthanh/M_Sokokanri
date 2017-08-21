package vn.com.nsmv.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import vn.com.nsmv.common.SokokanriException;
import vn.com.nsmv.dao.ItemHistoryDAO;
import vn.com.nsmv.entity.ItemHistory;
import vn.com.nsmv.javabean.SearchCondition;

public class ItemHistoryDAOImpl implements ItemHistoryDAO {

    private static final Logger logger = Logger.getLogger(ItemDAOImpl.class);
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void add(ItemHistory itemHistory) throws SokokanriException {
        try {
            Session session = this.sessionFactory.getCurrentSession();
            session.persist(itemHistory);
        } catch (Exception ex) {
            logger.debug(ex);
            throw new SokokanriException(ex);
        }
    }

    public List<ItemHistory> getAllHistory(SearchCondition searchCondition , Integer maxResults, Integer offset) throws SokokanriException {
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("from ItemHistory where id = :id order by updateDate desc");
        query.setParameter("id", searchCondition.getOrderId());
        return query.list();
    }

}
