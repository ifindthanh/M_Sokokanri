package vn.com.nsmv.common;

import java.util.*;

public final class HTMLUtils
{

	private static final Map<Character, String> URL_ESCAPE_MAP = HTMLUtils.INIT_URL_ESCAPE_MAP();
	private static final Map<Character, String> HTML_ATTRIBUTE_ESCAPE_MAP = HTMLUtils
		.INIT_HTML_ATTRIBUTE_ESCAPE_MAP();
	private static final Map<Character, String> JAVASCRIPT_STRING_ESCAPE_MAP = HTMLUtils
		.INIT_JAVASCRIPT_STRING_ESCAPE_MAP();

	private static Map<Character, String> INIT_URL_ESCAPE_MAP()
	{
		Map<Character, String> escapeMap = new HashMap<Character, String>();
		escapeMap.put(Character.valueOf('~'), "%7E");
		escapeMap.put(Character.valueOf('`'), "%60");
		escapeMap.put(Character.valueOf('!'), "%21");
		escapeMap.put(Character.valueOf('%'), "%25");
		escapeMap.put(Character.valueOf('^'), "%5E");
		escapeMap.put(Character.valueOf('='), "%3D");
		escapeMap.put(Character.valueOf('{'), "%7B");
		escapeMap.put(Character.valueOf('}'), "%7D");
		escapeMap.put(Character.valueOf(';'), "%3B");
		escapeMap.put(Character.valueOf(':'), "%3A");
		escapeMap.put(Character.valueOf('"'), "%22");
		escapeMap.put(Character.valueOf('|'), "%7C");
		escapeMap.put(Character.valueOf('\\'), "%5C");
		escapeMap.put(Character.valueOf(','), "%2C");
		escapeMap.put(Character.valueOf('<'), "%3C");
		escapeMap.put(Character.valueOf('>'), "%3E");
		escapeMap.put(Character.valueOf('?'), "%3F");
		escapeMap.put(Character.valueOf('\r'), "%0D");
		escapeMap.put(Character.valueOf('\n'), "%0A");
		escapeMap.put(Character.valueOf(' '), "%20");
		escapeMap.put(Character.valueOf('!'), "%21");
		escapeMap.put(Character.valueOf('#'), "%23");
		escapeMap.put(Character.valueOf('$'), "%24");
		escapeMap.put(Character.valueOf('&'), "%26");
		escapeMap.put(Character.valueOf('\''), "%27");
		escapeMap.put(Character.valueOf('('), "%28");
		escapeMap.put(Character.valueOf(')'), "%29");
		escapeMap.put(Character.valueOf('*'), "%2A");
		escapeMap.put(Character.valueOf('+'), "%2B");
		escapeMap.put(Character.valueOf(','), "%2C");
		escapeMap.put(Character.valueOf('/'), "%2F");
		escapeMap.put(Character.valueOf(':'), "%3A");
		escapeMap.put(Character.valueOf(';'), "%3B");
		escapeMap.put(Character.valueOf('='), "%3D");
		escapeMap.put(Character.valueOf('?'), "%3F");
		escapeMap.put(Character.valueOf('@'), "%40");
		escapeMap.put(Character.valueOf('['), "%5B");
		escapeMap.put(Character.valueOf(']'), "%5D");
		return escapeMap;
	}

	private static Map<Character, String> INIT_HTML_ATTRIBUTE_ESCAPE_MAP()
	{
		Map<Character, String> escapeMap = new HashMap<Character, String>();
		escapeMap.put(Character.valueOf('"'), "&#34;");
		escapeMap.put(Character.valueOf('\''), "&#39;");
		escapeMap.put(Character.valueOf('&'), "&#38;");
		escapeMap.put(Character.valueOf('#'), "&#35;");
		escapeMap.put(Character.valueOf(';'), "&#59;");
		escapeMap.put(Character.valueOf('<'), "&#60;");
		escapeMap.put(Character.valueOf('>'), "&#62;");
		escapeMap.put(Character.valueOf('['), "&#91;");
		escapeMap.put(Character.valueOf('\\'), "&#92;");
		escapeMap.put(Character.valueOf(']'), "&#93;");
		escapeMap.put(Character.valueOf('{'), "&#123;");
		escapeMap.put(Character.valueOf('}'), "&#125;");
		//issue 269: ext:
		//escapeMap.put(Character.valueOf(':'), "&#58;");
		return escapeMap;
	}

	private static Map<Character, String> INIT_JAVASCRIPT_STRING_ESCAPE_MAP()
	{
		Map<Character, String> escapeMap = new HashMap<Character, String>();
		escapeMap.put(Character.valueOf('"'), "&#34;");
		escapeMap.put(Character.valueOf('\''), "&#39;");
		escapeMap.put(Character.valueOf('\\'), "&#92;");
		return escapeMap;
	}

	public static String escapeForHTMLAttributeValue(CharSequence str)
	{
		return HTMLUtils.escapeStringFor(str, HTMLUtils.HTML_ATTRIBUTE_ESCAPE_MAP);
	}

	// Only EmailHelper uses this now
	public static String escapeForURL(CharSequence str)
	{
		return HTMLUtils.escapeStringFor(str, HTMLUtils.URL_ESCAPE_MAP);
	}

	public static String escapeForJavascriptString(CharSequence str)
	{
		return HTMLUtils.escapeStringFor(str, HTMLUtils.JAVASCRIPT_STRING_ESCAPE_MAP);
	}

	private static String escapeStringFor(CharSequence str, Map<Character, String> replacementMap)
	{
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < str.length(); i++)
		{
			char c = str.charAt(i);
			String replacement = replacementMap.get(Character.valueOf(c));
			if (replacement == null)
			{
				sb.append(c);
			}
			else
			{
				sb.append(replacement);
			}
		}
		return sb.toString();
	}

	public static String getJSScriptIncludeForFile(String file)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("<script type='text/javascript' ");
		sb.append("src=");
		sb.append("'");
		sb.append(file);
		sb.append("'");
		sb.append('>');
		sb.append("</script>");
		return sb.toString();
	}

}
