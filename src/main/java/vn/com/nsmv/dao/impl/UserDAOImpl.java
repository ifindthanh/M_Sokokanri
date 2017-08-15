package vn.com.nsmv.dao.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import vn.com.nsmv.common.SokokanriException;
import vn.com.nsmv.dao.UserDAO;
import vn.com.nsmv.entity.Role;
import vn.com.nsmv.entity.User;
import vn.com.nsmv.entity.UserRole;
import vn.com.nsmv.javabean.SortCondition;
import vn.com.nsmv.javabean.UserSearchCondition;

public class UserDAOImpl implements UserDAO
{

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory)
	{
		this.sessionFactory = sessionFactory;
	}


	public long add(User user) throws SokokanriException
	{
		try
		{
			Session session = this.sessionFactory.getCurrentSession();
			session.persist(user);
			return user.getId();
		}
		catch (Exception e)
		{
			throw new SokokanriException(e);
		}
	}

	public boolean update(User user)
	{
		try
		{
			Session session = this.sessionFactory.getCurrentSession();
			//session.persist(user);
			session.saveOrUpdate(user);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public User getUserByEmail(String email)
	{
		Session session = this.sessionFactory.openSession();
		Query query = session
			.createQuery(
				"from User where email = :email");
		query.setString("email", email);
		@SuppressWarnings("unchecked")
		List<User> list = query
			.list();
		if (list != null && !list.isEmpty())
		{
			return list.get(0);
		}
		return null;
	}

	public Set<UserRole> getRoles(Long userID) {
		Session session = this.sessionFactory.openSession();
		Query query = session
			.createQuery(
				"from UserRole where user_id = :user_id");
		query.setLong("user_id", userID);
		return new HashSet(query.list());
	}

    public void addUserRole(UserRole userRole) throws SokokanriException {
        try
        {
            Session session = this.sessionFactory.getCurrentSession();
            session.persist(userRole);
        }
        catch (Exception e)
        {
            throw new SokokanriException(e);
        }
        
    }
    
    public void saveUser(User user) throws SokokanriException {
        try
        {
            Session session = this.sessionFactory.getCurrentSession();
            session.saveOrUpdate(user);
        }
        catch (Exception ex)
        {
            throw new SokokanriException(ex);
        }
    }


    public boolean deleteUser(Set<Long> ids) {
        Session session = this.sessionFactory.getCurrentSession();
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE User where id in :ids");
        Query query = session.createQuery(sql.toString());
        query.setParameterList("ids", ids);
        return query.executeUpdate() > 0;
    }


    public List<User> getAllUsers(
        UserSearchCondition searchCondition, SortCondition sortCondition, Integer offset, Integer maxResults) {
        Session session = this.sessionFactory.getCurrentSession();
        StringBuilder sql = new StringBuilder();
        sql.append("from User where id <> 0");
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
        if (offset != null)
        {
            query = query.setFirstResult(offset);
        }
        
        if (maxResults != null)
        {
            query = query.setMaxResults(maxResults);
        }
        List<User> result = query.list();
       return result;
    }


    public User getUserByCode(Long userCd) throws SokokanriException {
        Session session = this.sessionFactory.openSession();
        Query query = session
            .createQuery(
                "from User where id = :id");
        query.setLong("id", userCd);
        @SuppressWarnings("unchecked")
        List<User> list = query
            .list();
        if (list != null && !list.isEmpty())
        {
            return list.get(0);
        }
        return null;
    }
    
    public Role getRoleById(String roleId) {
        Session session = this.sessionFactory.openSession();
        Query query = session
            .createQuery(
                "from Role where roleId = :roleId");
        query.setString("roleId", roleId);
        @SuppressWarnings("unchecked")
        List<Role> list = query
            .list();
        if (list != null && !list.isEmpty())
        {
            return list.get(0);
        }
        return null;
    }


    public List<Role> getAllRoles() {
        Session session = this.sessionFactory.openSession();
        Query query = session
            .createQuery(
                "from Role");
        return (List<Role>) query.list();
    }


    public void deleteAllOtherRoles(Long userId, List<String> roles) throws SokokanriException {
        try {
            Session session = this.sessionFactory.getCurrentSession();
            String hql = "delete UserRole where id.userID = :userID and id.roleID not in :roleID";
            Query query = session.createQuery(hql).setParameter("userID", userId).setParameterList("roleID", roles);
            query.executeUpdate();
        } catch (Exception ex) {
            throw new SokokanriException(ex);
        }
    }



    public UserRole getUserRole(Long userId, String roleId) throws SokokanriException {
        Session session = this.sessionFactory.openSession();
        Query query = session
            .createQuery(
                "from UserRole where id.userID = :userID and id.roleID = :roleID");
        query.setParameter("userID", userId);
        query.setParameter("roleID", roleId);
        @SuppressWarnings("unchecked")
        List<UserRole> list = query
            .list();
        if (list != null && !list.isEmpty())
        {
            return list.get(0);
        }
        return null;
    }


    public int countAllUsers(UserSearchCondition searchCondition) {
        Session session = this.sessionFactory.getCurrentSession();
        StringBuilder sql = new StringBuilder();
        sql.append("select COUNT(*) from User where id <> 0");
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

}