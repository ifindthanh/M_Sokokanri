package vn.com.nsmv.dao.impl;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import vn.com.nsmv.common.SokokanriException;
import vn.com.nsmv.dao.TreeDAO;
import vn.com.nsmv.entity.Tree;
import vn.com.nsmv.javabean.TreeSearchCondition;
import vn.com.nsmv.javabean.SortCondition;

import java.util.*;

public class TreeDAOImpl implements TreeDAO {

    private static final Logger logger = Logger.getLogger(TreeDAOImpl.class);
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    public Long add(Tree item) throws SokokanriException {
        try {
            Session session = this.sessionFactory.getCurrentSession();
            session.persist(item);
            return item.getId();
        } catch (Exception ex) {
            logger.debug(ex);
            throw new SokokanriException(ex);
        }
    }


    public void saveOrUpdate(Tree item) throws SokokanriException {
        try {
            Session session = this.sessionFactory.getCurrentSession();
            session.saveOrUpdate(item);
        } catch (Exception ex) {
            logger.debug(ex);
            throw new SokokanriException(ex);
        }
    }

    public void deleteById(Long id) throws SokokanriException {
        try {
            Session session = this.sessionFactory.getCurrentSession();
            String hql = "delete Tree where id = :id";
            Query query = session.createQuery(hql).setParameter("id", id);
            query.executeUpdate();
        } catch (Exception ex) {
            logger.debug(ex);
            throw new SokokanriException(ex);
        }
    }

    public Tree findById(Long id) throws SokokanriException {
        try {
            Session session = this.sessionFactory.getCurrentSession();
            Query query = session.createQuery("from Tree where id = :id");
            List list = query.setParameter("id", id).list();
            if (list == null || list.isEmpty()) {
                return null;
            }
            Tree item = (Tree) list.get(0);
            return item;
        } catch (Exception ex) {
            logger.debug(ex);
            throw new SokokanriException(ex);
        }
    }

    public List<Tree> getAllItems(TreeSearchCondition searchCondition, SortCondition sortCondition, Integer offset, Integer maxResults) throws SokokanriException {
        try {
            Session session = this.sessionFactory.getCurrentSession();
            StringBuilder sql = new StringBuilder();
            sql.append("from Tree c where id <> 0");
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
            List<Tree> result = query.list();
            if (result == null) {
                result = new ArrayList<Tree>();
            }
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.debug(ex);
            throw new SokokanriException(ex);
        }
    }

    public int countAllItems(TreeSearchCondition searchCondition) throws SokokanriException {
        try {
            Session session = this.sessionFactory.getCurrentSession();
            StringBuilder sql = new StringBuilder("select COUNT(*) from Tree where id <> 0 ");
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
            throw new SokokanriException(ex);
        }
    }

    public Tree getByEmail(String email) {
        Session session = this.sessionFactory.openSession();
        Query query = session
                .createQuery(
                        "from Tree where email = :email");
        query.setString("email", email);
        @SuppressWarnings("unchecked")
        List<Tree> list = query
                .list();
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    public boolean deleteSelected(Set<Long> ids) {
        Session session = this.sessionFactory.getCurrentSession();
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE Tree where id in :ids");
        Query query = session.createQuery(sql.toString());
        query.setParameterList("ids", ids);
        return query.executeUpdate() > 0;
    }

    public Tree getById(Long id) {
        Session session = this.sessionFactory.openSession();
        Query query = session
                .createQuery(
                        "from Tree where id = :id");
        query.setLong("id", id);
        @SuppressWarnings("unchecked")
        List<Tree> list = query
                .list();
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

}
