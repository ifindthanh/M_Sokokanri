package vn.com.qlcaycanh.taglib;

import java.io.*;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

public abstract class AbstractTaglib extends SimpleTagSupport
{
	@Override
	public final void doTag() throws JspException
	{
		JspWriter writer = getJspContext().getOut();
		try
		{
			this.printUI(writer);
		}
		catch (IOException ex)
		{
			throw new JspException("Error in Paginator tag", ex);
		}
	}

	protected abstract void printUI(JspWriter writer) throws IOException;
}
