package vn.com.nsmv.taglib;

import java.io.*;

import javax.servlet.jsp.*;

import vn.com.nsmv.common.*;

public final class PaginationTaglib extends AbstractTaglib
{
	private String uri;
	private int offset;
	private int count;
	private int steps;
	private String previous = "&lsaquo;";
	private String next = "&rsaquo;";

	@Override
	protected void printUI(JspWriter out) throws IOException
	{
		// Calculator item in Select box
		int nbOfPages = 0;
		double k = (double) count / (double) steps;
		if (k > (int) k)
		{
			nbOfPages = (int) k + 1;
		}
		else
		{
			nbOfPages = (int) k;
		}

		// Calculator item info
		out.write("<div class=\"pull-left\" style=\"padding-top: 10px;\">");
		out.write("<nav>");
		out.write("<ul class=\"pagination\">");
		out.write(
			"<li style=\"margin-top: 8px;margin-right: 10px;\">&#x5404;&#x30DA;&#x30FC;&#x30B8;</li>");

		// write max results picker
		out.write("<li>");
		out.write(
			"<select name = \"maxResults\" class=\"selectpicker form-control inputstl\" style=\"width:auto;height: 35px;\""
				+ " id= \"maxResults\" style=\"width:180px\" onchange=\"" + "location.href='" + uri
				+ "?offset=0&maxResults='" + "+this.options[this.selectedIndex].value\">");
		this.writeOption(out, 5, this.steps == 5);
		this.writeOption(out, 10, this.steps == 10);
		this.writeOption(out, 20, this.steps == 20);
		this.writeOption(out, 50, this.steps == 50);

		out.write("</select>");
		out.write("</li>");
		out.write(
			"<li style=\"margin-top: 8px; margin-left: 10px;\">&#x4EF6;&#x3092;&#x8868;&#x793A;</li>"); // ä»¶ã‚’è¡¨ç¤º
		out.write("</ul>");
		out.write("</nav>");
		out.write("</div>");

		out.write("<div class=\"pull-right\" style=\"padding-top: 10px;\">");
		out.write("<nav>");
		out.write("<ul class=\"pagination\">");
		out.write("<li style=\"margin-top: 8px; margin-right: 20px;\">");
		out.write("&#x5168;" + count + "&#x4EF6;  "); //out.write("å…¨" + count + "ä»¶  ");

		String toAppendText = "&#x4EF6;&#x76EE;&#x3092;&#x8868;&#x793A;"; // ä»¶ç›®ã‚’è¡¨ç¤º
		if (this.count > this.steps)
		{
			if (this.offset == 0)
			{
				out.write(
					"&#65288;" + 1 + "~" + (this.offset + this.steps) + toAppendText + "&#65289;");
			}
			else if (this.offset + this.steps >= this.count)
			{
				out.write(
					"&#65288;" + (this.offset + 1) + "~" + this.count + toAppendText + "&#65289;");
			}
			else
			{
				out.write(
					"&#65288;" + (this.offset + 1) + "~" + (this.offset + this.steps) + toAppendText
						+ "&#65289;");
			}
		}
		else if (count == 0)
		{
			out.write("&#65288;0" + toAppendText + "&#65289;");
		}
		else
		{
			out.write("&#65288;" + (this.offset + 1) + "~" + count + toAppendText + "&#65289;");
		}
		out.write("</li>");

		// first button
		if (this.offset < this.steps)
			out.write(this.constructLink(0, "&laquo;", "disabled", true));
		else
			out.write(this.constructLink(0, "&laquo;", null, false));

		// back button
		if (this.offset < this.steps && this.offset == 0)
			out.write(this.constructLink(0, "&lsaquo;", "disabled", true));
		else
			out.write(this.constructLink(this.offset - this.steps, "&lsaquo;", null, false));

		// List page
		if (this.count > 0)
		{
			out.write("<li>");
			out.write(
				"<select name = \"offset\" id=\"pageNumber\" class=\"selectpicker form-control inputstl\" style=\"width:auto;height: 35px;\" onchange=\""
					+ "location.href='" + uri + "?offset='"
					+ "+this.options[this.selectedIndex].value "
					+ "+'&maxResults='+ document.getElementById('maxResults').options[document.getElementById('maxResults').selectedIndex].value\">");
			for (int j = 1; j <= nbOfPages; j++)
			{
				if (this.offset == (j - 1) * this.steps)
				{
					out.write(
						"<option value=\"" + (j - 1) * this.steps + "\"selected=\"selected\">" + j
							+ "</option>");
				}
				else
				{
					out.write("<option value=\"" + (j - 1) * this.steps + "\">" + j + "</option>");
				}
			}
			out.write("</select>");
			out.write("</li>");
		}

		// next button
		if (this.offset + this.steps >= this.count)
			out.write(this.constructLink(this.offset + this.steps, "&rsaquo;", "disabled", true));
		else
			out.write(this.constructLink(this.offset + this.steps, "&rsaquo;", null, false));

		// last button
		if (this.offset + this.steps >= count)
			out.write(
				this.constructLink((nbOfPages - 1) * this.steps, "&raquo;", "disabled", true));
		else
			out.write(this.constructLink((nbOfPages - 1) * this.steps, "&raquo;", null, false));
	}

	private void writeOption(Writer out, int number, boolean selected) throws IOException
	{
		out.write("<option value=\"");
		out.write(String.valueOf(number));
		out.write("\"");
		if (selected)
		{
			out.write(" selected=\"selected\" ");
		}
		out.write(">");
		out.write(String.valueOf(number));
		out.write("</option>");
	}

	private String constructLink(int page, String text, String className, boolean disabled)
	{
		StringBuilder link = new StringBuilder("<li");
		if (className != null)
		{
			link.append(" class=\"");
			link.append(className);
			link.append("\"");
		}
		if (disabled)
			link.append(">").append("<a href=\"#\">" + text + "</a></li>");
		else
			link.append(">").append(
				"<a href=\"" + uri + "?offset=" + (page < 0 ? 0 : page) + "&maxResults="
					+ this.steps + "\">" + text + "</a></li>");
		return link.toString();
	}

	public String getUri()
	{
		return uri;
	}

	public void setUri(String uri)
	{
		this.uri = uri;
	}

	public int getOffset()
	{
		return offset;
	}

	public void setOffset(int offset)
	{
		this.offset = offset;
	}

	public int getCount()
	{
		return count;
	}

	public void setCount(int count)
	{
		this.count = count;
	}

	public String getPrevious()
	{
		return previous;
	}

	public void setPrevious(String previous)
	{
		this.previous = previous;
	}

	public String getNext()
	{
		return next;
	}

	public void setNext(String next)
	{
		this.next = next;
	}

	public int getSteps()
	{
		return steps;
	}

	public void setSteps(int steps)
	{
		if (steps == 0)
		{
			this.steps = Constants.MAX_IMAGE_PER_PAGE;
			return;
		}
		this.steps = steps;
	}

}
