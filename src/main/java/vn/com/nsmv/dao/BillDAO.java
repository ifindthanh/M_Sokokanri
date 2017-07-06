package vn.com.nsmv.dao;

import java.util.List;

import vn.com.nsmv.common.SokokanriException;
import vn.com.nsmv.entity.Bill;
import vn.com.nsmv.javabean.SearchCondition;

public interface BillDAO {
	public Long add(Bill bill) throws SokokanriException;
	public List<Bill> getAllBills(SearchCondition searchCondition, Integer offset,
			Integer maxResults) throws SokokanriException;
	public int countAllBills(SearchCondition searchCondition) throws SokokanriException;
	public Bill getById (Long id) throws SokokanriException;
	public void deleteBill (Long id) throws SokokanriException;
}
