package vn.com.nsmv.common;

import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;

import org.apache.log4j.*;

public class FileUtils
{
	private static final Logger logger = Logger.getLogger(Files.class);
	public static String writeTextFile(String folder, String fileName, String content)
	{
		FileOutputStream outStream = null;
		BufferedWriter bufferedWriter = null;
		try
		{
			Path myTempDir = Files.createTempDirectory(folder);
			Path outputFile = myTempDir.resolve(fileName);
			outStream = new FileOutputStream(outputFile.toFile());
			bufferedWriter = new BufferedWriter(
				new OutputStreamWriter(outStream, StandardCharsets.UTF_8));
			bufferedWriter.write(content);
			bufferedWriter.flush();
			return outputFile.toString();
		}
		catch (IOException e)
		{
			logger.debug(e);
			return null;
		}
		finally
		{
			try
			{
				if (bufferedWriter != null)
				{
					bufferedWriter.close();
				}
				if (outStream != null)
				{
					outStream.close();
				}
			}
			catch (IOException e)
			{
				logger.debug(e);
			}
		}
	}
}
