package com.rasapishe.customer;

import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;

import com.rasapishe.boom.customer.R;
import com.rasapishe.customer.enums.BankId;
import com.rasapishe.customer.objectmodel.BusinessContent;
import com.rasapishe.customer.objectmodel.FactorContent;

import java.io.IOException;
import java.io.InputStream;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Zahra Jamshidi
 * on 10/02/2017.
 */

public class RasaApplication extends Application {

	public static final String URL_TOSAN_SERVER = "https://app.tosanboom.com:4432/v1";
	public static final String URL_SERVER = URL_TOSAN_SERVER;
	public static final String URL_TOSAN_LOGIN = URL_SERVER + "/auth/login/";
	public static final String URL_TOSAN_BOOM_LOGIN = URL_SERVER + "/auth/market/login/";
	public static final String URL_TOSAN_DEPOSITE_TRANSFER = URL_SERVER + "/deposits/transfer/normal";
	public static final String URL_TOSAN_DEPOSITE_LIST = URL_SERVER + "/deposits";
	public static final String TOSAN_USERNAME = "samimi";
	public static final String TOSAN_PASSORD = "1807014000";
	public static final String APP_KEY = "12502";
	public static final boolean SANDBOX = false;
	public static final String DEVICE_ID = "34781237487384783274";
	public static final BankId BANK_ID= BankId.ANSBIR;
	public static final String TRACE_NO = "12456709";
	public static final String TRACE_DATE = "2017-02-16T23:15:05Z";
	public static final String LANGUAGE = "fa-IR";

	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(RasaApplication.class);
	public static Context applicationContext;

	@Override
	public void onCreate() {
		super.onCreate();

		logger.debug("Rasa Application is creating");
		setupFont();
		applicationContext = getApplicationContext();

		createDummyItems();
	}

	private void setupFont() {
		logger.debug("font setup");
		CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
				.setDefaultFontPath("font/iransans-light-4.ttf")
				.setFontAttrId(R.attr.fontPath)
				.build()
		);
	}

	private void createDummyItems() {
		logger.debug("creating dummy items");
		FactorContent.makeFactors();

		AssetManager mngr = getAssets();
		try {
			InputStream inputStream = mngr.open("input/Business.txt");
			BusinessContent.makeBusiness(inputStream);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
