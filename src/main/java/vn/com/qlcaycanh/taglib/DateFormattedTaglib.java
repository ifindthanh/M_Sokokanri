package vn.com.qlcaycanh.taglib;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.jsp.JspWriter;

public class DateFormattedTaglib extends AbstractTaglib{
    private Date date;
    @Override
    protected void printUI(JspWriter writer) throws IOException {
        if (this.date == null) {
            return;
        }
        SimpleDateFormat sdf = new SimpleDateFormat();
        writer.append(sdf.format(this.date));
    }
    
    public Date getDate() {
        return date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }
    
}
