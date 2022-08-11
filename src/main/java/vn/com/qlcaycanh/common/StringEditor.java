package vn.com.qlcaycanh.common;

import java.beans.*;

public final class StringEditor extends PropertyEditorSupport
{
	public void setAsText(String text)
	{
		String value = text.replaceAll("(^\\p{Z}+|\\p{Z}+$)", "");
		setValue(value);
	}
}