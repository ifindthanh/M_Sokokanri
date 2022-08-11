package vn.com.qlcaycanh.dao;

import java.util.List;

import vn.com.qlcaycanh.common.MyException;
import vn.com.qlcaycanh.entity.Category;
import vn.com.qlcaycanh.javabean.SearchCondition;
import vn.com.qlcaycanh.javabean.SortCondition;

public interface CategoryDAO {
	public Long add(Category category) throws MyException;
	public Category getById(Long id) throws MyException;
	public void saveCategory(Category category) throws MyException;
	public List<Category> getAllOrders(SearchCondition searchCondition, SortCondition sortCondition, Integer offset,
			Integer maxResults) throws MyException;
	public void deleteOrder(Long id) throws MyException;
}
