package vn.com.qlcaycanh.common;

import java.util.*;

public final class MyException extends Exception
{

	private static final long serialVersionUID = 8160338232636520809L;
	private final String errorMessage;
	private final List<String> errorMessages = new ArrayList<String>();

	public MyException()
	{
		this.errorMessage = Constants.BLANK;
	}

	public MyException(String errorMessage)
	{
		this.errorMessage = errorMessage;
		this.errorMessages.add(errorMessage);
	}

	public MyException(Throwable ex)
	{
		if (MyException.class.isInstance(ex))
		{
			this.errorMessage = ((MyException) ex).getErrorMessage();
			this.errorMessages.add(((MyException) ex).getErrorMessage());
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
