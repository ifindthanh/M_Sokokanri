package vn.com.nsmv.common;

import java.util.*;

public final class SokokanriException extends Exception
{

	private static final long serialVersionUID = 8160338232636520809L;
	private final String errorMessage;
	private final List<String> errorMessages = new ArrayList<String>();

	public SokokanriException()
	{
		this.errorMessage = Constants.BLANK;
	}

	public SokokanriException(String errorMessage)
	{
		this.errorMessage = errorMessage;
		this.errorMessages.add(errorMessage);
	}

	public SokokanriException(Throwable ex)
	{
		if (SokokanriException.class.isInstance(ex))
		{
			this.errorMessage = ((SokokanriException) ex).getErrorMessage();
			this.errorMessages.add(((SokokanriException) ex).getErrorMessage());
		}
		else
		{
			this.errorMessage = ex.getLocalizedMessage();
			this.errorMessages.add(ex.getLocalizedMessage());
		}
	}

	public String getErrorMessage()
	{
		return this.errorMessage;
	}

	public List<String> getErrorMessages()
	{
		return this.errorMessages;
	}

	public void addErrorMessage(String message)
	{
		this.errorMessages.add(message);
	}
}
