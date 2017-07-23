package vn.com.nsmv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import vn.com.nsmv.bean.ResponseResult;
import vn.com.nsmv.bean.SettingMoneyRate;
import vn.com.nsmv.common.SokokanriException;
import vn.com.nsmv.service.MoneyRateService;

@Controller
public class MoneyExchangeController {
    @Autowired
    private MoneyRateService moneyRateService;
    
    @RequestMapping(value = "/ti-gia/luu-ti-gia", method=RequestMethod.GET)
    public @ResponseBody ResponseEntity<ResponseResult<String>> updateRate(@RequestParam Double value)
    {
        try {
            this.moneyRateService.saveMoneyRate(value);
            SettingMoneyRate.VALUE = Math.round(value);
            
            return new ResponseEntity<ResponseResult<String>>(new ResponseResult<String>(1, "Success", null), HttpStatus.OK);
        } catch (SokokanriException e) {
            return new ResponseEntity<ResponseResult<String>>(new ResponseResult<String>(0, e.getErrorMessage(), null), HttpStatus.OK);
        }
    }
}
