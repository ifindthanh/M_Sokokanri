package vn.com.nsmv.dao.impl;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import vn.com.nsmv.common.SokokanriException;
import vn.com.nsmv.dao.ImportingDAO;
import vn.com.nsmv.entity.ImportingEntity;
import vn.com.nsmv.javabean.OrderSearchCondition;
import vn.com.nsmv.javabean.SortCondition;
import vn.com.nsmv.javabean.TreeSearchCondition;

import java.util.*;

public class ImportingDAOImpl implements ImportingDAO {
    private static final Logger logger = Logger.getLogger(ImportingDAOImpl.class);
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    public Long add(ImportingEntity item) throws SokokanriException {
        try {
            Session session = this.sessionFactory.getCurrentSession();
            session.persist(item);
            return item.getId();
        } catch (Exception ex) {
            logger.debug(ex);
            throw new SokokanriException(ex);
        }
    }


    public void saveOrUpdate(ImportingEntity item) throws SokokanriException {
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
            String hql = "delete ImportingEntity where id = :id";
            Query query = session.createQuery(hql).setParameter("id", id);
            query.executeUpdate();
        } catch (Exception ex) {
            logger.debug(ex);
            throw new SokokanriException(ex);
        }
    }

    public ImportingEntity findById(Long id) throws SokokanriException {
        try {
            Session session = this.sessionFactory.getCurrentSession();
            Query query = session.createQuery("from ImportingEntity where id = :id");
            List list = query.setParameter("id", id).list();
            if (list == null || list.isEmpty()) {
                return null;
            }
            ImportingEntity item = (ImportingEntity) list.get(0);
            return item;
        } catch (Exception ex) {
            logger.debug(ex);
            throw new SokokanriException(ex);
        }
    }

    public List<ImportingEntity> getAllItems(OrderSearchCondition searchCondition, SortCondition sortCondition, Integer offset, Integer maxResults) throws SokokanriException {
        try {
            Session session = this.sessionFactory.getCurrentSession();
            StringBuilder sql = new StringBuilder();
            sql.append("from ImportingEntity c where id <> 0");
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
            List<ImportingEntity> result = query.list();
            if (result == null) {
                result = new ArrayList<ImportingEntity>();
            }
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.debug(ex);
            throw new SokokanriException(ex);
        }
    }

    public int countAllItems(OrderSearchCondition searchCondition) throws SokokanriException {
        try {
            Session session = this.sessionFactory.getCurrentSession();
            StringBuilder sql = new StringBuilder("select COUNT(*) from ImportingEntity where id <> 0 ");
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

    public ImportingEntity getByEmail(String email) {
        Session session = this.sessionFactory.openSession();
        Query query = session
                .createQuery(
                        "from ImportingEntity where email = :email");
        query.setString("email", email);
        @SuppressWarnings("unchecked")
        List<ImportingEntity> list = query
                .list();
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    public boolean deleteSelected(Set<Long> ids) {
        Session session = this.sessionFactory.getCurrentSession();
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE ImportingEntity where id in :ids");
        Query query = session.createQuery(sql.toString());
        query.setParameterList("ids", ids);
        return query.executeUpdate() > 0;
    }

    public ImportingEntity getById(Long id) {
        Session session = this.sessionFactory.openSession();
        Query query = session
                .createQuery(
                        "from ImportingEntity where id = :id");
        query.setLong("id", id);
        @SuppressWarnings("unchecked")
        List<ImportingEntity> list = query
                .list();
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

}
