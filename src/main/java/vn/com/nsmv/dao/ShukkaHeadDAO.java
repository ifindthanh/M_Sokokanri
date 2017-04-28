package vn.com.nsmv.dao;

import vn.com.nsmv.entity.*;

/**
 */
public interface ShukkaHeadDAO
{
	public ShukkaHead getShukkaHeadById(ShukkaHeadId headId);
	public void add(ShukkaHead shukkaHead);
	public void update(ShukkaHead shukkaHead);
	public void delete(ShukkaHead head);
}
