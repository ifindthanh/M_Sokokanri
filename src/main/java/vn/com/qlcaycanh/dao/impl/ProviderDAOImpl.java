package vn.com.qlcaycanh.dao.impl;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import vn.com.qlcaycanh.common.MyException;
import vn.com.qlcaycanh.dao.ProviderDAO;
import vn.com.qlcaycanh.entity.Provider;
import vn.com.qlcaycanh.javabean.ProviderSearchCondition;
import vn.com.qlcaycanh.javabean.SortCondition;

import java.util.*;

public class ProviderDAOImpl implements ProviderDAO {

    private static final Logger logger = Logger.getLogger(ProviderDAOImpl.class);
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    public Long add(Provider item) throws MyException {
        try {
            Session session = this.sessionFactory.getCurrentSession();
            session.persist(item);
            return item.getId();
        } catch (Exception ex) {
            logger.debug(ex);
            throw new MyException(ex);
        }
    }


    public void saveOrUpdate(Provider item) throws MyException {
        try {
            Session session = this.sessionFactory.getCurrentSession();
            session.saveOrUpdate(item);
        } catch (Exception ex) {
            logger.debug(ex);
            throw new MyException(ex);
        }
    }

    public void deleteById(Long id) throws MyException {
        try {
            Session session = this.sessionFactory.getCurrentSession();
            String hql = "delete Provider where id = :id";
            Query query = session.createQuery(hql).setParameter("id", id);
            query.executeUpdate();
        } catch (Exception ex) {
            logger.debug(ex);
            throw new MyException(ex);
        }
    }

    public Provider findById(Long id) throws MyException {
        try {
            Session session = this.sessionFactory.getCurrentSession();
            Query query = session.createQuery("from Provider where id = :id");
            List list = query.setParameter("id", id).list();
            if (list == null || list.isEmpty()) {
                return null;
            }
            Provider item = (Provider) list.get(0);
            return item;
        } catch (Exception ex) {
            logger.debug(ex);
            throw new MyException(ex);
        }
    }

    public List<Provider> getAllItems(ProviderSearchCondition searchCondition, SortCondition sortCondition, Integer offset, Integer maxResults) throws MyException {
        try {
            Session session = this.sessionFactory.getCurrentSession();
            StringBuilder sql = new StringBuilder();
            sql.append("from Provider c where id <> 0");
            Map<String, Object> params = new HashMap<String, Object>();
            if (searchCondition != null) {
                sql.append(searchCondition.getSearching(params));
            }
            Query query = session.createQuery(sql.toString());
            Iterator<Map.Entry<String, Object>> iterator = params.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> element = iterator.next();
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
            List<Provider> result = query.list();
            if (result == null) {
                result = new ArrayList<Provider>();
            }
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.debug(ex);
            throw new MyException(ex);
        }
    }

    public int countAllItems(ProviderSearchCondition searchCondition) throws MyException {
        try {
            Session session = this.sessionFactory.getCurrentSession();
            StringBuilder sql = new StringBuilder("select COUNT(*) from Provider where id <> 0 ");
            Map<String, Object> params = new HashMap<String, Object>();
            if (searchCondition != null) {
                sql.append(searchCondition.getSearching(params));
            }
            Query query = session.createQuery(sql.toString());
            Iterator<Map.Entry<String, Object>> iterator = params.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> element = iterator.next();
                if (List.class.isInstance(element.getValue())) {
                    query.setParameterList(element.getKey(), (List) element.getValue());
                } else {
                    query.setParameter(element.getKey(), element.getValue());
                }
            }
            return ((Number) query.uniqueResult()).intValue();
        } catch (Exception ex) {
            logger.debug(ex);
            throw new MyException(ex);
        }
    }

    public Provider getByEmail(String email) {
        Session session = this.sessionFactory.openSession();
        Query query = session
                .createQuery(
                        "from Provider where email = :email");
        query.setString("email", email);
        @SuppressWarnings("unchecked")
        List<Provider> list = query
                .list();
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    public boolean deleteSelected(Set<Long> ids) {
        Session session = this.sessionFactory.getCurrentSession();
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE Provider where id in :ids");
        Query query = session.createQuery(sql.toString());
        query.setParameterList("ids", ids);
        return query.executeUpdate() > 0;
    }

    public Provider getById(Long id) {
        Session session = this.sessionFactory.openSession();
        Query query = session
                .createQuery(
                        "from Provider where id = :id");
        query.setLong("id", id);
        @SuppressWarnings("unchecked")
        List<Provider> list = query
                .list();
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

}
