package com.rasapishe.customer.service;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.rasapishe.customer.RasaApplication;
import com.rasapishe.customer.service.dto.DepositListReq;
import com.rasapishe.customer.service.dto.DepositTransferReq;
import com.rasapishe.customer.service.dto.DepositTransferResp;
import com.rasapishe.customer.service.dto.LoginBoomRespDto;
import com.rasapishe.customer.service.dto.LoginReqDto;
import com.rasapishe.customer.service.dto.LoginRespDto;
import com.rasapishe.customer.service.dto.LoginsCallBack;
import com.rasapishe.customer.service.dto.ServiceCallBack;
import com.rasapishe.customer.service.dto.SessionData;
import com.rasapishe.customer.utils.ServiceUtil;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mohammadreza on 2/15/2017 AD.
 */

public class TosanServices {


	private void login(LoginReqDto reqDto, final LoginsCallBack loginsCallBack) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                RasaApplication.URL_TOSAN_LOGIN,
                ServiceUtil.getJsonObject(reqDto)
                , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                LoginRespDto loginRespDto = new
                        Gson().fromJson(((JSONObject) response).toString(), LoginRespDto.class);
                SessionData.loginRespDto = loginRespDto;
                loginsCallBack.onLoginSuccess();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                loginsCallBack.onLoginFailed();

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> mapHeaders = new HashMap<String, String>();
                mapHeaders.put("Device-Id", RasaApplication.DEVICE_ID);
                mapHeaders.put("App-Key", RasaApplication.APP_KEY);
                mapHeaders.put("Bank-Id", RasaApplication.BANK_ID.toString());
                mapHeaders.put("Trace-No", RasaApplication.TRACE_NO);
                mapHeaders.put("Trace-Date", RasaApplication.TRACE_DATE);
                mapHeaders.put("Token-Id", SessionData.loginBoomRespDto.getToken());
                mapHeaders.put("Accept-Language", RasaApplication.LANGUAGE);
                mapHeaders.put("Sandbox", RasaApplication.SANDBOX + "");
                return mapHeaders;

            }
        };

        NetworkServiceQueue.getInstance().getRequestQueue().add(jsonObjectRequest);

    }

	private void loginBoom(LoginReqDto reqDto, final LoginsCallBack loginsCallBack) {

		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
				Request.Method.POST,
				RasaApplication.URL_TOSAN_BOOM_LOGIN,
				ServiceUtil.getJsonObject(reqDto)
				, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				LoginBoomRespDto loginRespDto = new
						Gson().fromJson(((JSONObject) response).toString(), LoginBoomRespDto.class);
				SessionData.loginBoomRespDto = loginRespDto;
				loginsCallBack.onLoginBoomSuccess();

			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				loginsCallBack.onLoginBoomFailed();

			}
		}){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> mapHeaders = new HashMap<String, String>();
                mapHeaders.put("Device-Id", RasaApplication.DEVICE_ID);
                mapHeaders.put("App-Key", RasaApplication.APP_KEY);
                return mapHeaders;

            }
        };

        NetworkServiceQueue.getInstance().getRequestQueue().add(jsonObjectRequest);
	}

	public void depositeTransfer(final LoginReqDto loginReqDto,
								 final DepositTransferReq transferReqDto,
	                             final ServiceCallBack serviceCallBack) {
		LoginsCallBack loginsCallBack = new LoginsCallBack() {
			@Override
			public void onLoginFailed() {
				serviceCallBack.onLoginFailed();
			}

			@Override
			public void onLoginSuccess() {
				//depositList(serviceCallBack);
                transfer(transferReqDto, serviceCallBack);
			}

			@Override
			public void onLoginBoomFailed() {
				serviceCallBack.onLoginBoomFailed();
			}

			@Override
			public void onLoginBoomSuccess() {
				login(loginReqDto, this);
			}
		};

		LoginReqDto loginReq = new LoginReqDto();
		loginReq.setUsername(RasaApplication.TOSAN_USERNAME);
		loginReq.setPassword(RasaApplication.TOSAN_PASSORD);

		loginBoom(loginReq, loginsCallBack);
	}

	private void transfer(DepositTransferReq reqDto, final ServiceCallBack serviceCallBack) {
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
				Request.Method.POST,
				RasaApplication.URL_TOSAN_DEPOSITE_TRANSFER,
				ServiceUtil.getJsonObject(reqDto)
				, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				DepositTransferResp resp = new
						Gson().fromJson(((JSONObject) response).toString(), DepositTransferResp.class);

				serviceCallBack.onDepositTransferSuccess(resp);

			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				android.util.Log.e("Transfer Failed", new String(error.networkResponse.data));
				serviceCallBack.onDepositTransferFailed();

			}
		}) {
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				return makeGeneralHeaders();
			}

		};

		NetworkServiceQueue.getInstance().getRequestQueue().add(jsonObjectRequest);

	}

	private void depositList(final ServiceCallBack serviceCallBack) {
        DepositListReq reqDto = new DepositListReq();
        reqDto.setDeposit_status("OPEN");
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
				Request.Method.POST,
				RasaApplication.URL_TOSAN_DEPOSITE_LIST,
				ServiceUtil.getJsonObject(reqDto)
				, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				// DO some thing with response;
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
                String body = new String (error.networkResponse.data);
				serviceCallBack.onDepositTransferFailed();

			}
		}) {
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
                return makeGeneralHeaders();
			}
		};

		NetworkServiceQueue.getInstance().getRequestQueue().add(jsonObjectRequest);

	}


    private Map<String, String> makeGeneralHeaders(){
        Map<String, String> mapHeaders = new HashMap<String, String>();
        mapHeaders.put("Device-Id", RasaApplication.DEVICE_ID);
        mapHeaders.put("Session", SessionData.loginRespDto.getSession_id());
        mapHeaders.put("App-Key", RasaApplication.APP_KEY);
        mapHeaders.put("Bank-Id", RasaApplication.BANK_ID.toString());
        mapHeaders.put("Trace-No", RasaApplication.TRACE_NO);
        mapHeaders.put("Trace-Date", RasaApplication.TRACE_DATE);
        mapHeaders.put("Token-Id", SessionData.loginBoomRespDto.getToken());
        mapHeaders.put("Accept-Language", RasaApplication.LANGUAGE);
        mapHeaders.put("Sandbox", RasaApplication.SANDBOX + "");
        return  mapHeaders;
    }


}
