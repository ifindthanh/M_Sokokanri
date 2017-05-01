package vn.com.nsmv.dao.impl;

import java.util.*;

import org.hibernate.*;
import org.springframework.transaction.annotation.*;

import vn.com.nsmv.common.*;
import vn.com.nsmv.dao.*;
import vn.com.nsmv.entity.*;

@Transactional
public class UserDAOImpl implements UserDAO
{

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory)
	{
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	public List<User> listAll(Integer offset, Integer maxResults)
	{
		Session session = this.sessionFactory.getCurrentSession();

		List<User> list = session.createQuery("from User where delete_flg ='0'")
			.setFirstResult(offset != null ? offset : 0)
			.setMaxResults(maxResults != null ? maxResults : Constants.MAX_IMAGE_PER_PAGE)
			.list();

		//		if (list != null && list.size() > 0)
		//		{
		//			return list;
		//		}
		//		return null;
		if (list == null)
		{
			return new ArrayList<User>();
		}
		return list;
	}

	public Long countListUser()
	{
		return ((Number) sessionFactory.getCurrentSession()
			.createSQLQuery("select COUNT(*) from user where delete_flg ='0' ")
			.uniqueResult()).longValue();
	}

	public Long countUserByEmail(String email)
	{
		return ((Number) sessionFactory.getCurrentSession()
			.createSQLQuery(
				"select COUNT(*) from user where delete_flg ='0' and email='" + email + "' ")
			.uniqueResult()).longValue();
	}

	public boolean add(User user)
	{
		try
		{
			Session session = this.sessionFactory.getCurrentSession();
			session.persist(user);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
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

	public User getUserByCd(String email)
	{
		//lay thong tin user tu DB dua vao userCd
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
	public User getUser(String userCd, String password)
	{
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<User> list = session.createQuery(
			"from User where user_cd = '" + userCd + "'  and password = '" + Utils.encode(password)
				+ "' and delete_flg = '0'")
			.list();
		if (list != null && !list.isEmpty())
		{
			return list.get(0);
		}
		return null;
	}
	public boolean isExists(String userCd)
	{
		//check userCd co trong DB khong
		Session session = this.sessionFactory.getCurrentSession();
		boolean check = false;
		@SuppressWarnings("unchecked")
		List<User> list = session
			.createQuery("from User where user_cd = '" + userCd + "' and delete_flg = '0'").list();
		if (list != null && !list.isEmpty())
		{
			check = true;
		}
		return check;
	}
	/******************************************
	 * get list user by table id column
	 *
	 * @param String listIs
	 * @return List<User>
	 ******************************************/
	public List<User> getListUserById(String listId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<User> list = session.createQuery("from User where id in (" + listId + ")").list();
		//		if (list != null && list.size() > 0)
		//		{
		//			return list;
		//		}
		//		return null;
		if (list == null)
		{
			return new ArrayList<User>();
		}
		return list;
	}

	/******************************************
	 * Delete user
	 *
	 * @param String listId
	 * @return boolean
	 ******************************************/
	public boolean deleteUser(String listId)
	{
		boolean result = false;
		try
		{
			String hql = "update User set DELETE_FLG='1', UPDATE_DATE=now() where ID IN (" + listId
				+ ") ";
			Session session = this.sessionFactory.getCurrentSession();
			Query query = session.createQuery(hql);
			query.executeUpdate();
			result = true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return result;
		}
		return result;
	}

	/******************************************
	 * Get list user by search condition
	 *
	 * @param String
	 *            searchCondition, Integer offset, Integer maxResults
	 * @return List<BusinessCard>
	 ******************************************/
	public List<User> getListUserByCondition(
		String searchCondition,
		Integer offset,
		Integer maxResults)
	{
		Session session = this.sessionFactory.getCurrentSession();
		String strSql = "";
		if (!Utils.isEmpty(searchCondition))
		{
			String strName = "%";
			for (int i = 0; i < searchCondition.length(); i++)
			{
				if (Character.toString(searchCondition.charAt(i)).equals("　"))
				{
					continue;
				}
				if (!Utils.isEmpty(Character.toString(searchCondition.charAt(i))))
				{
					strName = strName + searchCondition.charAt(i) + "%";
				}
			}
			strSql = strSql + " CONCAT(last_name,first_name) like '" + strName + "' ";
		}
		@SuppressWarnings({ "unchecked" })
		List<User> list = session
			.createQuery(
				"from User " + " WHERE delete_flg ='0' and ( COMPANY_NAME like '%" + searchCondition
					+ "%' or DEPT like '%" + searchCondition + "%' or TITLE like '%"
					+ searchCondition + "%' or USER_CD like '%" + searchCondition
					+ "%' or EMAIL like '%" + searchCondition + "%' or PHONE like '%"
					+ searchCondition + "%' or " + strSql + ")")
			.setFirstResult(offset != null ? offset : 0)
			.setMaxResults(maxResults != null ? maxResults : Constants.MAX_IMAGE_PER_PAGE)
			.list();
		//		if (list != null && list.size() > 0)
		//		{
		//			return list;
		//		}
		//		return null;
		if (list == null)
		{
			return new ArrayList<User>();
		}
		return list;
	}

	/******************************************
	 * Get the number of user by search condition
	 *
	 * @param String
	 *            searchCondition
	 * @return List<BusinessCard>
	 ******************************************/
	public Long countUserBySearchCondition(String searchCondition)
	{
		try
		{
			String strSql = "";
			if (!Utils.isEmpty(searchCondition))
			{
				String strName = "%";
				for (int i = 0; i < searchCondition.length(); i++)
				{
					if (Character.toString(searchCondition.charAt(i)).equals("　"))
					{
						continue;
					}
					if (!Utils.isEmpty(Character.toString(searchCondition.charAt(i))))
					{
						strName = strName + searchCondition.charAt(i) + "%";
					}
				}
				strSql = strSql + " CONCAT(last_name,first_name) like '" + strName + "' ";
			}
			return ((Number) sessionFactory.getCurrentSession()
				.createSQLQuery(
					"select COUNT(*) from user  WHERE delete_flg ='0' and ( COMPANY_NAME like '%"
						+ searchCondition + "%' or DEPT like '%" + searchCondition
						+ "%' or TITLE like '%" + searchCondition + "%' or USER_CD like '%"
						+ searchCondition + "%' or EMAIL like '%" + searchCondition
						+ "%' or PHONE like '%" + searchCondition + "%' or " + strSql + ")")
				.uniqueResult()).longValue();
		}
		catch (Exception e)
		{
			return null;
		}
	}

	public Set<UserRole> getRoles(Long userID) {
		Session session = this.sessionFactory.openSession();
		Query query = session
			.createQuery(
				"from UserRole where user_id = :user_id");
		query.setLong("user_id", userID);
		return new HashSet(query.list());
	}

}