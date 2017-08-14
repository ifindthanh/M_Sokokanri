package vn.com.nsmv.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
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
            transaction.setCreateDate(new Date());
            session.persist(transaction);
        }
        catch (Exception ex)
        {
            throw new SokokanriException(ex);
        }
        return transaction.getId();
    }
    
    public List<Transaction> listAllTransactions(Long userId) throws SokokanriException {
        try {
            Session session = this.sessionFactory.getCurrentSession();
            StringBuilder sql = new StringBuilder();
            sql.append("from Transaction c where id <> 0 and userId = :userId order by createDate desc");
            Query query = session.createQuery(sql.toString());
            query.setParameter("userId", userId);
            List<Transaction> result = query.list();
            if (result == null) {
                result = new ArrayList<Transaction>();
            }
            return result;
        } catch (Exception ex) {
            throw new SokokanriException(ex);
        }
    }

}
