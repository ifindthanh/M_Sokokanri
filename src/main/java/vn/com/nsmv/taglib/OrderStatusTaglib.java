package vn.com.nsmv.taglib;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.jsp.JspWriter;

import org.springframework.context.i18n.LocaleContextHolder;

import vn.com.nsmv.i18n.SokokanriMessage;

public class OrderStatusTaglib extends AbstractTaglib {
	private int status;
	
	@Override
	protected void printUI(JspWriter writer) throws IOException {
	    Locale locale = LocaleContextHolder.getLocale();
		switch (this.status) {
		case -2:
                writer.write("<p style=\"color: #f44283\">" + SokokanriMessage.getLabelNoted(locale) + "</p>");
			break;
		case -1:
			writer.write("<p style=\"color: #f44283\">" + SokokanriMessage.getLabelNoted(locale) + "</p>");
			break;
		case 0:
			writer.write("<p style=\"color: #293a56\">" + SokokanriMessage.getLabelWaitingToApproval(locale) + "</p>");
			break;
		case 1:
			writer.write("<p style=\"color: #293a56\">" + SokokanriMessage.getLabelApproved(locale) + "</p>");
			break;
		case 2:
			writer.write("<p style=\"color: #293a56\">" + SokokanriMessage.getLabelBought(locale) + "</p>");
			break;
		case 3:
			writer.write("<p style=\"color: #293a56\">" + SokokanriMessage.getLabelTransferred(locale) + "</p>");
			break;
		case 4:
			writer.write("<p style=\"color: #293a56\">" + SokokanriMessage.getLabelTransferredToVn(locale) + "</p>");
			break;
		case 5:
			writer.write("<p style=\"color: #293a56\">" + SokokanriMessage.getLabelStored(locale) + "</p>");
			break;
		case 6:
            writer.write("<p style=\"color: #293a56\">" + SokokanriMessage.getLabelBillExported(locale) + "</p>");
            break;
		case 7:
            writer.write("<p style=\"color: #293a56\">" + SokokanriMessage.getLabelIsShipping(locale) + "</p>");
            break;
		case 8:
            writer.write("<p style=\"color: green\">" + SokokanriMessage.getLabelFinished(locale) + "</p>");
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
