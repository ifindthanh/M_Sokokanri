package vn.com.nsmv.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;

import vn.com.nsmv.common.Utils;


public class IDFormattedTaglib extends AbstractTaglib {
    
    private Long id;
    @Override
    protected void printUI(JspWriter writer) throws IOException {
        writer.print(Utils.getFormattedId(id, 7));
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    

}
