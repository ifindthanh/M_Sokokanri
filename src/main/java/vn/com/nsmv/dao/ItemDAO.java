package vn.com.nsmv.dao;

import java.util.List;

import vn.com.nsmv.common.SokokanriException;
import vn.com.nsmv.entity.Item;

public interface ItemDAO {
	public void add(Item item) throws SokokanriException;
	public List<Item> getItemsInCategory(Long categoryId) throws SokokanriException;
	public void saveOrUpdate(Item item) throws SokokanriException;
	public void deleteById(Long id) throws SokokanriException;
	public Item findById(Long id) throws SokokanriException;
	
}
