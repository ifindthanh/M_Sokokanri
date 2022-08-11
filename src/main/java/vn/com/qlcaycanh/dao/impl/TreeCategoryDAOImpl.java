package vn.com.qlcaycanh.dao.impl;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import vn.com.qlcaycanh.common.MyException;
import vn.com.qlcaycanh.dao.TreeCategoryDAO;
import vn.com.qlcaycanh.entity.TreeCategory;
import vn.com.qlcaycanh.javabean.SortCondition;
import vn.com.qlcaycanh.javabean.TreeCategorySearchCondition;

import java.util.*;

public class TreeCategoryDAOImpl implements TreeCategoryDAO {

    private static final Logger logger = Logger.getLogger(TreeCategoryDAOImpl.class);
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    public Long add(TreeCategory item) throws MyException {
        try {
            Session session = this.sessionFactory.getCurrentSession();
            session.persist(item);
            return item.getId();
        } catch (Exception ex) {
            logger.debug(ex);
            throw new MyException(ex);
        }
    }


    public void saveOrUpdate(TreeCategory item) throws MyException {
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
            String hql = "delete TreeCategory where id = :id";
            Query query = session.createQuery(hql).setParameter("id", id);
            query.executeUpdate();
        } catch (Exception ex) {
            logger.debug(ex);
            throw new MyException(ex);
        }
    }

    public TreeCategory findById(Long id) throws MyException {
        try {
            Session session = this.sessionFactory.getCurrentSession();
            Query query = session.createQuery("from TreeCategory where id = :id");
            List list = query.setParameter("id", id).list();
            if (list == null || list.isEmpty()) {
                return null;
            }
            TreeCategory item = (TreeCategory) list.get(0);
            return item;
        } catch (Exception ex) {
            logger.debug(ex);
            throw new MyException(ex);
        }
    }

    public List<TreeCategory> getAllItems(TreeCategorySearchCondition searchCondition, SortCondition sortCondition, Integer offset, Integer maxResults) throws MyException {
        try {
            Session session = this.sessionFactory.getCurrentSession();
            StringBuilder sql = new StringBuilder();
            sql.append("from TreeCategory c where id <> 0");
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
            List<TreeCategory> result = query.list();
            if (result == null) {
                result = new ArrayList<TreeCategory>();
            }
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.debug(ex);
            throw new MyException(ex);
        }
    }

    public int countAllItems(TreeCategorySearchCondition searchCondition) throws MyException {
        try {
            Session session = this.sessionFactory.getCurrentSession();
            StringBuilder sql = new StringBuilder("select COUNT(*) from TreeCategory where id <> 0 ");
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

    public TreeCategory getByEmail(String email) {
        Session session = this.sessionFactory.openSession();
        Query query = session
                .createQuery(
                        "from TreeCategory where email = :email");
        query.setString("email", email);
        @SuppressWarnings("unchecked")
        List<TreeCategory> list = query
                .list();
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    public boolean deleteSelected(Set<Long> ids) {
        Session session = this.sessionFactory.getCurrentSession();
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE TreeCategory where id in :ids");
        Query query = session.createQuery(sql.toString());
        query.setParameterList("ids", ids);
        return query.executeUpdate() > 0;
    }

    public TreeCategory getById(Long id) {
        Session session = this.sessionFactory.openSession();
        Query query = session
                .createQuery(
                        "from TreeCategory where id = :id");
        query.setLong("id", id);
        @SuppressWarnings("unchecked")
        List<TreeCategory> list = query
                .list();
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

}
