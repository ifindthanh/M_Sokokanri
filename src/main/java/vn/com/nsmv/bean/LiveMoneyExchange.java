package vn.com.nsmv.bean;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import vn.com.nsmv.common.SokokanriException;
import vn.com.nsmv.dao.MoneyExchangeDAO;
import vn.com.nsmv.entity.MoneyExchange;

public class LiveMoneyExchange {
	@Autowired
	MoneyExchangeDAO moneyExchangeDAO;
	
	public static String VALUE = "";
	// essential URL structure is built using constants
	public static final String ACCESS_KEY = "fdbcb9042d8aeabb2e87a18d6df68517";
	public static final String BASE_URL = "http://apilayer.net/api/";
	public static final String ENDPOINT = "live";
	private static final Logger logger = Logger.getLogger(LiveMoneyExchange.class);
	
	// this object is used for executing requests to the (REST) API
	static CloseableHttpClient httpClient = HttpClients.createDefault();

	/**
	 * 
	 * Notes:
	 * 
	 * A JSON response of the form {"key":"value"} is considered a simple Java
	 * JSONObject. To get a simple value from the JSONObject, use: <JSONObject
	 * identifier>.get<Type>("key");
	 * 
	 * A JSON response of the form {"key":{"key":"value"}} is considered a
	 * complex Java JSONObject. To get a complex value like another JSONObject,
	 * use: <JSONObject identifier>.getJSONObject("key")
	 * 
	 * Values can also be JSONArray Objects. JSONArray objects are simple,
	 * consisting of multiple JSONObject Objects.
	 * 
	 * 
	 */

	@Scheduled(fixedDelay = 3600000)
	public void sendLiveRequest() {
		MoneyExchange moneyExchange;
		try {
			moneyExchange = this.moneyExchangeDAO.getMoneyExchange();
		} catch (SokokanriException ex) {
			throw new RuntimeException(ex);
		}
		
		// The following line initializes the HttpGet Object with the URL in
		// order to send a request
		/*HttpGet get = new HttpGet(BASE_URL + ENDPOINT + "?access_key=" + ACCESS_KEY + "&currencies=VND,GBP");
		
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
			LiveMoneyExchange.VALUE =  String.format("%.4f", usdToVND / usdToGBP);
			try {
				if (moneyExchange == null) {
					moneyExchange = new MoneyExchange();
					moneyExchange.setValue(usdToVND / usdToGBP);
					this.moneyExchangeDAO.add(moneyExchange);
					return;
				}
				moneyExchange.setValue(usdToVND / usdToGBP);
				this.moneyExchangeDAO.updateMoneyExchange(moneyExchange);
			} catch (SokokanriException e) {
				throw new RuntimeException(e);
			}
			response.close();
			logger.debug("==DEBUG== Update exchange money to " + LiveMoneyExchange.VALUE);
		} catch (Exception e) {
			logger.debug("==DEBUG== Cannot update exchange money " + e.getMessage());
			if (moneyExchange == null) {
				throw new RuntimeException("Cannot connect");
			}
			LiveMoneyExchange.VALUE = String.format("%.4f", moneyExchange.getValue());
		}*/
	}

}
