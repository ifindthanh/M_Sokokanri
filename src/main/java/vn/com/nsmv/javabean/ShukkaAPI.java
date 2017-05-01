package vn.com.nsmv.javabean;

import java.util.*;

/**
 */
public class ShukkaAPI
{
	private final ShukkaHeadBean shukkaHeadBean;
	private List<ShukkaBodyBean> shukkaBodyBeans = new ArrayList<ShukkaBodyBean>();

	public ShukkaAPI(ShukkaHeadBean shukkaHeadBean)
	{
		this.shukkaHeadBean = shukkaHeadBean;
	}

	public ShukkaAPI(ShukkaHeadBean shukkaHeadBean, List<ShukkaBodyBean> shukkaBodyBeans)
	{
		this.shukkaHeadBean = shukkaHeadBean;
		this.shukkaBodyBeans = shukkaBodyBeans;
	}

	public ShukkaHeadBean getShukkaHeadBean()
	{
		return this.shukkaHeadBean;
	}

	public List<ShukkaBodyBean> getShukkaBodyBeans()
	{
		return this.shukkaBodyBeans;
	}

	public void addBody(ShukkaBodyBean body)
	{
		this.shukkaBodyBeans.add(body);
	}

}
