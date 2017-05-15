package vn.com.nsmv.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;

public class OrderStatusTaglib extends AbstractTaglib {
	private int status;
	
	@Override
	protected void printUI(JspWriter writer) throws IOException {
		switch (this.status) {
		case 0:
			writer.write("<p style=\"color: #293a56\">Chờ duyệt</p>");
			break;
		case 1:
			writer.write("<p style=\"color: #293a56\">Đã duyệt</p>");
			break;
		case 2:
			writer.write("<p style=\"color: #293a56\">Đã mua</p>");
			break;
		case 3:
			writer.write("<p style=\"color: #293a56\">Đã chuyển về tại nước ngoài</p>");
			break;
		case 4:
			writer.write("<p style=\"color: #293a56\">Đã chuyển về VN</p>");
			break;

		default:
			break;
		}
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
