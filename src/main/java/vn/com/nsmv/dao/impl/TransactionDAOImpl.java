package vn.com.nsmv.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import vn.com.nsmv.common.SokokanriException;
import vn.com.nsmv.dao.TransactionDAO;
import vn.com.nsmv.entity.Transaction;


public class TransactionDAOImpl implements TransactionDAO {
    
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory)
    {
        this.sessionFactory = sessionFactory;
    }
    public Long addTransaction(Transaction transaction) throws SokokanriException {
        try
        {
            Session session = this.sessionFactory.getCurrentSession();
            session.persist(transaction);
        }
        catch (Exception ex)
        {
            throw new SokokanriException(ex);
        }
        return transaction.getId();
    }

}
