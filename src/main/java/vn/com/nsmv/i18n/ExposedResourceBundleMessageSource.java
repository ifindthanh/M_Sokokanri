package vn.com.nsmv.i18n;

import java.util.*;

import org.springframework.context.support.*;

public class ExposedResourceBundleMessageSource extends ResourceBundleMessageSource
{

	public ExposedResourceBundleMessageSource()
	{
		super();
		this.setBasename("messages");
		this.setDefaultEncoding("UTF-8");
	}

	public Set<String> getKeys(String basename, Locale locale)
	{
		ResourceBundle bundle = getResourceBundle(basename, locale);
		return bundle.keySet();
	}
}
