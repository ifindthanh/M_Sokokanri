package vn.com.qlcaycanh.taglib;

import java.io.IOException;
import java.util.Set;

import javax.servlet.jsp.JspWriter;

public class CheckboxStatusTaglib extends AbstractTaglib{
	
	private Set<Long> selectedItems;
	private String action;
	private Long item;
	@Override
	protected void printUI(JspWriter writer) throws IOException {
		writer.append("<input type=\"checkbox\" class=\"order_id\" order_id=\"" + this.item + "\" onchange=\"selectItem('" + this.item + "', this, '"+this.action+"')\"");
		if (this.selectedItems.contains(item)) {
			writer.append(" checked ");
		}
		writer.append("/>");
	}
	public Set<Long> getSelectedItems() {
		return selectedItems;
	}
	public void setSelectedItems(Set<Long> selectedItems) {
		this.selectedItems = selectedItems;
	}
	public Long getItem() {
		return item;
	}
	public void setItem(Long item) {
		this.item = item;
	}
    
    public String getAction() {
        return action;
    }
    
    public void setAction(String action) {
        this.action = action;
    }
	
}
