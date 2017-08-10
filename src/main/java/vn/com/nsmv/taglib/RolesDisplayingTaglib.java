package vn.com.nsmv.taglib;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspWriter;

import vn.com.nsmv.bean.RoleEnum;
import vn.com.nsmv.entity.Role;

public class RolesDisplayingTaglib extends AbstractTaglib {

    private List<Role> allRoles;
    private boolean disable;

    @Override
    protected void printUI(JspWriter writer) throws IOException {
        if (allRoles == null) {
            allRoles = new ArrayList<Role>();
        }
        boolean isUser = this.allRoles.contains(new Role("U"));
        writer.append("<div style=\"text-align: left\">");
        if (isUser) {
            writer.append("<div><input type=\"checkbox\" ");
            writer.append(" disabled ");
            writer.append(" checked ");
            writer.append(" onchange=\"selectRole(this, 'U')\" ");
            writer.append(" /> ");
            writer.append("Khách hàng");
            writer.append(" </div> ");
            writer.append("</div>");
            writer.append("<select name=\"roles\" multiple style=\"display:none\" id=\"allRoles\">");
            writer.append("<option value=\"U\" selected></option>");
            writer.append("</option>");
            writer.append("<select>");
            return;
        }
        for (Role item : RoleEnum.ALL_ROLES) {
            writer.append("<div><input type=\"checkbox\" ");
            if (this.disable) {
                writer.append(" disabled ");
            }
            if (this.allRoles.contains(item)) {
                writer.append(" checked ");
            }
            writer.append(" onchange=\"selectRole(this, '" + item.getRoleId() + "')\" ");
            writer.append(" /> ");
            writer.append(item.getRoleName());
            writer.append(" </div> ");
        }
        writer.append("</div>");

        if (this.disable) {
            return;
        }
        writer.append("<select name=\"roles\" multiple style=\"display:none\" id=\"allRoles\">");
        for (Role item : RoleEnum.ALL_ROLES) {
            writer.append("<option value=\"" + item.getRoleId() + "\"");
            writer.append(" id=\"" + item.getRoleId() + "\" ");
            if (this.allRoles.contains(item)) {
                writer.append(" selected ");
            }
            writer.append(">" + item.getRoleName() + "</option>");

        }
        writer.append("<select>");
    }

    public List<Role> getAllRoles() {
        return allRoles;
    }

    public void setAllRoles(List<Role> allRoles) {
        this.allRoles = allRoles;
    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

}
