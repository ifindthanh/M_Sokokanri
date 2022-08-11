package vn.com.qlcaycanh.i18n;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class GenerateMessage
{
	private static final String regex = "[_A-Za-z0-9]*";
	public static void main(String[] args)
	{
		ExposedResourceBundleMessageSource messageSource = new ExposedResourceBundleMessageSource();
		Set<String> keys = messageSource.getKeys("messages", Locale.US);
		PrintWriter outFile = null;
		try
		{
			String absolutePath = new File("").getAbsolutePath();
			absolutePath = absolutePath.concat(File.separator)
				.concat("src")
				.concat(File.separator)
				.concat("main")
				.concat(File.separator)
				.concat("java")
				.concat(File.separator)
				.concat("vn")
				.concat(File.separator)
				.concat("com")
				.concat(File.separator)
				.concat("qlcaycanh")
				.concat(File.separator)
				.concat("i18n")
				.concat(File.separator)
				.concat("IMessage.java");
			outFile = new PrintWriter(
				new OutputStreamWriter(new FileOutputStream(new File(absolutePath)), "UTF-8"));
			;
			outFile.write("package vn.com.nsmv.i18n;");
			outFile.println();
			outFile.println();
			outFile.write("import java.util.Locale;");
			outFile.println();
			outFile.write("public class IMessage {");
			outFile.println();
			outFile.write("	");
			outFile.write(
				"private static final ExposedResourceBundleMessageSource messageSource = new ExposedResourceBundleMessageSource();");
			outFile.println();
			outFile.println();

			for (String item : keys)
			{
				String message = messageSource.getMessage(item, null, Locale.US);
				outFile.write("	");
				outFile.write("//" + message);
				outFile.println();
				outFile.write("	");
				outFile.write(
					"public static String get" + getNameInMethod(item) + "(" + getParams(message)
						+ "){\n");
				outFile.println();
				outFile.write("		");
				outFile.write(createArgs(message));
				outFile.println();
				outFile.write("		");
				outFile.write(
					"return IMessage.messageSource.getMessage(\"" + item
						+ "\", args, locale);");
				outFile.println();
				outFile.write("}\n");
				outFile.println();
			}
			outFile.write("}");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (outFile != null)
			{
				outFile.close();
			}
		}
	}

	private static String getNameInMethod(String name)
	{

		Pattern pattern = Pattern.compile(GenerateMessage.regex);
		if (!pattern.matcher(name.replaceAll("\\.", "_")).find())
		{
			throw new RuntimeException("Item " + name + " is not a valid key.");
		}
		StringBuilder result = new StringBuilder();
		String[] items = name.split("\\.");
		for (int i = 0; i < items.length; i++)
		{
			String item = items[i];
			result.append(item.substring(0, 1).toUpperCase() + item.substring(1));
		}
		return result.toString();
	}

	private static String getParams(String name)
	{
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < name.split("\\{").length - 1; i++)
		{
			result.append("Object arg" + i);
			result.append(", ");
		}
		result.append(" Locale locale");
		return result.toString();
	}
	private static String createArgs(String name)
	{
		StringBuilder result = new StringBuilder("Object[] args = {");
		for (int i = 0; i < name.split("\\{").length - 1; i++)
		{
			if (i != 0)
			{
				result.append(", ");
			}
			result.append("arg" + i);
		}
		result.append("};");
		return result.toString();
	}
}
