package vn.com.nsmv.common;

import java.beans.*;

public final class SokokanriStringEditor extends PropertyEditorSupport
{
	public void setAsText(String text)
	{
		String value = text.replaceAll("(^\\p{Z}+|\\p{Z}+$)", "");
		setValue(value);
	}
}