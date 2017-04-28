package vn.com.nsmv.service.impl;

import org.apache.log4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import vn.com.nsmv.dao.*;
import vn.com.nsmv.entity.*;
import vn.com.nsmv.service.*;

@Service("logRequestService")
public class LogRequestServiceImpl implements LogRequestService
{
	@Autowired
	private LogRequestDao logRequestDao;
	private static final Logger logger = Logger.getLogger(LogRequestServiceImpl.class);

	public void add(LogRequest logRequest)
	{
		try
		{
			logRequestDao.addLog(logRequest);
		}
		catch (Exception ex)
		{
			logger.debug(ex);
		}

	}

}
