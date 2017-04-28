package vn.com.nsmv.service.impl;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import vn.com.nsmv.dao.*;
import vn.com.nsmv.entity.*;
import vn.com.nsmv.service.*;

/**
 */
@Service("sokoService")
public class SokoServiceImpl implements SokoService
{
	@Autowired
	private SokoDAO sokoDAO;

	public List<Soko> getAllSokoes()
	{
		return this.sokoDAO.getAllSokoes();
	}

}
