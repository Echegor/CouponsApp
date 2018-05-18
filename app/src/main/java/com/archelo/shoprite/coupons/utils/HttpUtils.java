package com.archelo.shoprite.coupons.utils;

import android.util.Log;

import com.archelo.shoprite.coupons.http.Get;
import com.archelo.shoprite.coupons.http.HttpClientContext;
import com.archelo.shoprite.coupons.http.Post;
import com.archelo.shoprite.coupons.http.StateSaver;
import com.archelo.shoprite.coupons.json.AzureToken;
import com.archelo.shoprite.coupons.json.AzureUserInfo;
import com.archelo.shoprite.coupons.json.Coupon;
import com.archelo.shoprite.coupons.json.UserCoupons;
import com.archelo.shoprite.coupons.urls.AzureUrls;
import com.archelo.shoprite.coupons.urls.ShopriteURLS;
import com.google.gson.Gson;

import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.protocol.BasicHttpContextHC4;

import java.util.List;

public class HttpUtils {

    private static final String TAG = "HttpUtils";

    public static StateSaver performAzureSignIn(String email, String password) {

        try {
            return performAzureSignInImplementation(email,password);
        } catch (Exception e) {
            e.printStackTrace();
            return new StateSaver.StateSaverBuilder().error(true).errorString(e.toString()).build();
        }



//        List<Header> couponHeader = HeaderUtils.getAvailableCoupons(azureTokens);
//        for (String coupon : couponsArray.getAvailable_ids_array()) {
//            String response = new Post.PostBuilder()
//                    .url(AzureUrls.COUPONS_ADD)
//                    .headers(couponHeader)
//                    .jsonObject(ParamUtils.buildCouponJSON(coupon, userInfo))
//                    .contentType(ContentType.APPLICATION_JSON)
//                    .httpClientContext(context)
//                    .build().doPost();
//            System.out.println("Response: " + response);
//        }

    }

    private static StateSaver performAzureSignInImplementation(String email, String password) throws Exception {
        HttpHost proxy = new HttpHost("10.120.30.19", 8080, "http");
        RequestConfig globalConfig = RequestConfig
                .custom()
                .setProxy(proxy)
                .setConnectTimeout(5000)
                .setCircularRedirectsAllowed(false)
                .build();
        HttpClientContext context = new HttpClientContext(new BasicHttpContextHC4());
        context.setCookieStore(new BasicCookieStore());
        context.setRequestConfig(globalConfig);
        context.setAuthCache(new BasicAuthCache());

        String status = new Get.GetBuilder()
                .url(ShopriteURLS.STATUS)
                .headers(HeaderUtils.getStatus())
                .httpClientContext(context)
                .build().doGet();

        Log.d(TAG,"status " + status);

        String samlRequest = new Get.GetBuilder()
                .url(AzureUrls.SIGN_IN)
                .headers(HeaderUtils.getAzureSignInStatus())
                .encodeQueryParams(false)
                .queryParams(ParamUtils.getAzureSignInStatus(status))
                .httpClientContext(context)
                .build().doGet();

        Log.d(TAG,"samlRequest " + samlRequest);


        String authenticate3601 = new Post.PostBuilder()
                .url(ShopriteURLS.AUTHENTICATE3601)
                .headers(HeaderUtils.getAzureAuthenticationHeaders3601(status))
                .queryParams(ParamUtils.buildAzureSamlRequestQueryParameters())
                .dataParams(ParamUtils.buildSamlRequestDataParameters(samlRequest))
                .contentType(ContentType.APPLICATION_FORM_URLENCODED)
                .httpClientContext(context)
                .encodeQueryParams(true)
                .build().doPost();
        Log.d(TAG,"authenticate3601 " + authenticate3601);


        String postToken = ParamUtils.getRequestVerificationToken(authenticate3601);
        Log.d(TAG,"postToken " + postToken);

        String samlResponse = new Post.PostBuilder()
                .url(ShopriteURLS.AUTHENTICATE)
                .headers(HeaderUtils.getAuthenticationHeaders())
                .dataParams(ParamUtils.getAuthenticationInfo(postToken, email, password))
                .contentType(ContentType.APPLICATION_FORM_URLENCODED)
                .httpClientContext(context)
                .build().doPost();

        Log.d(TAG,"samlResponse " + samlResponse);

        List<NameValuePair> samlResponseData = ParamUtils.buildSamlResponseDataParameter(samlResponse);
        if(samlResponseData == null){
            return new StateSaver.StateSaverBuilder().error(true).errorString("Invalid username or password.").build();
        }
        String returnFromSignIn = new Post.PostBuilder()
                .url(AzureUrls.RETURN_FROM_SIGN_IN)
                .headers(HeaderUtils.getReturnFromSignInHeaders())
                .dataParams(samlResponseData)
                .contentType(ContentType.APPLICATION_FORM_URLENCODED)
                .httpClientContext(context)
                .build().doPost();


        Log.d(TAG,"returnFromSignIn " + returnFromSignIn);

        String webJS = new Get.GetBuilder()
                .url(ShopriteURLS.WEB_JS)
                .headers(HeaderUtils.getWebJsHeaders())
                .httpClientContext(context)
                .build().doGet();


        AzureToken azureTokens = ParamUtils.buildAzureToken(webJS, status);

        Log.d(TAG,"azureTokens " + azureTokens);
        String azureSession = new Get.GetBuilder()
                .url(AzureUrls.SESSION + azureTokens.getUserID())
                .headers(HeaderUtils.getAzureStatus())
                .queryParams(ParamUtils.buildAzureStatusQueryParam(azureTokens))
                .encodeQueryParams(false)
                .httpClientContext(context)
                .build().doGet();



        AzureUserInfo userInfo = new Gson().fromJson(azureSession, AzureUserInfo.class);

        Log.d(TAG,"userInfo " + userInfo);
        String availableCoupons = new Post.PostBuilder()
                .url(AzureUrls.AVAILABLE_COUPONS)
                .headers(HeaderUtils.getAvailableCoupons(azureTokens))
                .jsonObject(ParamUtils.buildPPCJSON(userInfo))
                .contentType(ContentType.APPLICATION_JSON)
                .httpClientContext(context)
                .build().doPost();

        Log.d(TAG,"userInfo " + userInfo);
        String couponData = new Post.PostBuilder()
                .url(AzureUrls.COUPONS_METADATA)
                .headers(HeaderUtils.getAvailableCoupons(azureTokens))
                .jsonObject(ParamUtils.buildPPCJSON(userInfo))
                .contentType(ContentType.APPLICATION_JSON)
                .httpClientContext(context)
                .build().doPost();


        Log.d(TAG,"Coupons Array " + availableCoupons);
        Log.d(TAG,"Cookies " + context.getCookieStore().getCookies().toString());
        UserCoupons userCoupons = new Gson().fromJson(availableCoupons, UserCoupons.class);
        Coupon[] coupons = new Gson().fromJson(couponData, Coupon[].class);
        return new StateSaver.StateSaverBuilder()
                .azureToken(azureTokens)
                .azureUserInfo(userInfo)
                .couponsArray(userCoupons)
                .error(false)
                .httpClientContext(context)
                .coupons(coupons)
                .build();
    }

}
