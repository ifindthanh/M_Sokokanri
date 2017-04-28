package vn.com.nsmv.entity;

import javax.persistence.*;

/**
 */

@Entity
@Table(name = "v_zaisitu")
public class VZaisitu implements java.io.Serializable
{

	private String zaisitu;

	public VZaisitu()
	{
	}

	public VZaisitu(String zaisitu)
	{
		this.zaisitu = zaisitu;
	}

	@Id

	@Column(name = "zaisitu", length = 12)
	public String getZaisitu()
	{
		return this.zaisitu;
	}

	public void setZaisitu(String zaisitu)
	{
		this.zaisitu = zaisitu;
	}

}
