package vn.com.nsmv.dao;

import java.util.List;

import vn.com.nsmv.common.SokokanriException;
import vn.com.nsmv.entity.Category;
import vn.com.nsmv.javabean.SearchCondition;
import vn.com.nsmv.javabean.SortCondition;

public interface CategoryDAO {
	public Long add(Category category) throws SokokanriException;
	public Category getById(Long id) throws SokokanriException;
	public void saveCategory(Category category) throws SokokanriException;
	public List<Category> getAllOrders(SearchCondition searchCondition, SortCondition sortCondition, Integer offset,
			Integer maxResults) throws SokokanriException;
	public void deleteOrder(Long id) throws SokokanriException;
}
