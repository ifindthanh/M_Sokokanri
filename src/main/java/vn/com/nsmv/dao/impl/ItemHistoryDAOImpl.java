package vn.com.nsmv.dao.impl;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import vn.com.nsmv.common.SokokanriException;
import vn.com.nsmv.dao.ItemHistoryDAO;
import vn.com.nsmv.entity.ItemHistory;

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

}
