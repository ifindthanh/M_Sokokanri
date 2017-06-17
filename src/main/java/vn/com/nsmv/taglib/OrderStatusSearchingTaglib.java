package vn.com.nsmv.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;

import vn.com.nsmv.common.Constants;
import vn.com.nsmv.common.Utils;

public class OrderStatusSearchingTaglib extends AbstractTaglib {
	private int status;

	@Override
	protected void printUI(JspWriter writer) throws IOException {
		writer.write("<select class=\"selectpicker form-control inputstl\" style=\"width:auto;\" name=\"status\">");
		writer.write("<option value= \"999\">Tất cả</option>");
		
		writer.write("<option value = \"0\"");
		if (this.status == 0) {
			writer.write("selected");	
		}
		writer.write(" >Chờ duyệt</option>");
		
		writer.write("<option value = \"1\"");
		if (this.status == 1) {
			writer.write("selected");	
		}
		writer.write(" >Đã duyệt</option>");
		
		writer.write("<option value = \"2\"");
		if (this.status == 2) {
			writer.write("selected");	
		}
		writer.write(" >Đã mua</option>");
		
		writer.write("<option value = \"3\"");
		if (this.status == 3) {
			writer.write("selected");	
		}
		writer.write(" >Đã chuyển hàng tại nước ngoài</option>");
		
		writer.write("<option value = \"4\"");
		if (this.status == 4) {
			writer.write("selected");	
		}
		writer.write(" >Đã chuyển về Việt Nam</option>");
		
		writer.write("<option value = \"5\"");
		if (this.status == 5) {
			writer.write("selected");	
		}
		writer.write(" >Đã nhập kho</option>");

		writer.write("<option value = \"6\"");
		if (this.status == 6) {
			writer.write("selected");	
		}
		writer.write(" >Đã xuất hóa đơn</option>");

		writer.write("<option value = \"7\"");
		if (this.status == 7) {
			writer.write("selected");	
		}
		writer.write(" >Giao hàng</option>");
		
		writer.write("</select>");
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
