package vn.com.nsmv.dao;

import vn.com.nsmv.common.SokokanriException;
import vn.com.nsmv.entity.Category;

public interface CategoryDAO {
	public Long add(Category category) throws SokokanriException;
	public Category getById(Long id) throws SokokanriException;
	public void saveCategory(Category category) throws SokokanriException;
}
