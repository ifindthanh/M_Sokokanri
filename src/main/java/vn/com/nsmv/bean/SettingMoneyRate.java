package vn.com.nsmv.bean;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import vn.com.nsmv.common.SokokanriException;
import vn.com.nsmv.dao.MoneyExchangeDAO;
import vn.com.nsmv.entity.MoneyExchange;

@Component
public class SettingMoneyRate {
    public static long VALUE = 0;
    private static final String ACCESS_KEY = "fdbcb9042d8aeabb2e87a18d6df68517";
    private static final String BASE_URL = "http://apilayer.net/api/";
    private static final String ENDPOINT = "live";
    private static final Logger logger = Logger.getLogger(SettingMoneyRate.class);
    private static CloseableHttpClient httpClient = HttpClients.createDefault();
    
    @Autowired
    MoneyExchangeDAO moneyExchangeDAO;
    @PostConstruct
    public void setting(){
        MoneyExchange moneyExchange = null;
        double currentRate = this.getCurrentRate();
        try {
            moneyExchange = this.moneyExchangeDAO.getMoneyExchange();
        } catch (SokokanriException ex) {
            logger.debug("Cannot get exchange money " + ex.getMessage());
            SettingMoneyRate.VALUE = Math.round(currentRate);
            return;
        }

        if (moneyExchange == null || moneyExchange.getValue() == null) {
            moneyExchange = new MoneyExchange();
            moneyExchange.setValue(currentRate);
            try {
                this.moneyExchangeDAO.add(moneyExchange);
            } catch (SokokanriException e) {
            }
            SettingMoneyRate.VALUE = Math.round(currentRate);
            return;
        }
        SettingMoneyRate.VALUE = Math.round(moneyExchange.getValue());
    }
    
    private double getCurrentRate(){
        HttpGet get = new HttpGet(BASE_URL + ENDPOINT + "?access_key=" + ACCESS_KEY + "&currencies=VND,GBP");
        
        try {
            CloseableHttpResponse response = httpClient.execute(get);
            HttpEntity entity = response.getEntity();

            // the following line converts the JSON Response to an equivalent
            // Java Object
            JSONObject exchangeRates = new JSONObject(EntityUtils.toString(entity));
            // Parsed JSON Objects are accessed according to the JSON resonse's
            // hierarchy, output strings are built
            double usdToGBP = exchangeRates.getJSONObject("quotes").getDouble("USDGBP");
            double usdToVND = exchangeRates.getJSONObject("quotes").getDouble("USDVND");
            response.close();
            return usdToVND / usdToGBP;
        } catch (Exception e) {
            logger.debug("Cannot update exchange money " + e.getMessage());
            return 0;
        }
    }
}
