package com.archelo.coupons.volley;

import android.util.Log;

import com.android.volley.Header;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.archelo.coupons.db.data.LoginStatus;
import com.archelo.coupons.db.data.MWG_GSA_S;
import com.archelo.coupons.urls.ShopriteURLS;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class Authenticate3601Request extends StringRequest {
    public final String TAG = "Authenticate3601Request";
    private MWG_GSA_S mwg_gsa_s;
    private String request;

    public Authenticate3601Request(String samlRequest,MWG_GSA_S mwg_gsa_s, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Request.Method.POST, ShopriteURLS.AUTHENTICATE3601, listener, errorListener);
        //this.cookieStore = cookieStore;
        this.mwg_gsa_s = mwg_gsa_s;
        this.request = samlRequest;
    }

    /*
     * This method return the entire un-parsed network response. The only purpose of this call is to
     * get cookies assigned as my random session.
     * */
    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        List<Header> headers = response.allHeaders;
//        Log.d(TAG, "Response code " + response.statusCode +" Headers: \n" + headers.toString().replaceAll("],", ",\r\n"));
        return super.parseNetworkResponse(response);
    }

   // https://secure.shoprite.com/User/Authenticate/3601?
    // binding=urn:oasis:names:tc:SAML:2.0:bindings:HTTP-POST&&forceChallenge=1
    // cancelUri=https://scheaders.shoprite.com/store/ShopRite/User/ReturnFromSignIn?success=False&store=ShopRite&addressId=0

    //https://secure.shoprite.com/User/Authenticate/3601?
    // binding=urn%3aoasis%3anames%3atc%3aSAML%3a2.0%3abindings%3aHTTP-POST&&forceChallenge=1
    // cancelUri=https%3a%2f%2fscheaders.shoprite.com%2fstore%2fShopRite%2fUser%2fReturnFromSignIn%3fsuccess%3dFalse%26store%3dShopRite%26addressId%3d0
    @Override
    public String getUrl() {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("binding", "urn%3aoasis%3anames%3atc%3aSAML%3a2.0%3abindings%3aHTTP-POST&");
        params.put("forceChallenge","1");
        params.put("cancelUri", "https%3a%2f%2fscheaders.shoprite.com%2fstore%2fShopRite%2fUser%2fReturnFromSignIn%3fsuccess%3dFalse%26store%3dShopRite%26addressId%3d0");
        String url = super.getUrl() + VolleyUtils.toURLEncodedString(params, false);
//        Log.d(TAG, "GETURL: " + url);
        return url;
    }

    @Override
    public Map<String, String> getHeaders() {
        Map<String, String> headers = new LinkedHashMap<>();
        headers.put("Host", "secure.shoprite.com");
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:60.0) Gecko/20100101 Firefox/60.0");
        headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,application/json, text/javascript,*/*;q=0.8");
        headers.put("Accept-Language", "en-US,en;q=0.9");
        //headers.put("Accept-Encoding", "gzip, deflate, br"); //death
        headers.put("Connection", "keep-alive");
        headers.put("Upgrade-Insecure-Requests", "1");
        headers.put("Origin","https://scheaders.shoprite.com");
        headers.put("Referer", "https://scheaders.shoprite.com/store/"+VolleyUtils.toEncodedString(mwg_gsa_s.getPseudoStoreId())+"/User/SignIn");
//        Log.d(TAG, "Request Headers: \n" + VolleyUtils.formatHeaders(headers));
        return headers;
    }


    @Override
    protected Map<String, String> getParams() {
        Map<String, String> params = new LinkedHashMap<>();
        String samlForm = VolleyUtils.getSamlRequestForm(request);
        String relayState = VolleyUtils.getRelayState(request);

        params.put("SAMLRequest", samlForm);
        params.put("RelayState", relayState);

        Log.d(TAG, "Params: \n" + VolleyUtils.formatParameters(params));
        return params;
    }

    @Override
    public String getBodyContentType() {
        return "application/x-www-form-urlencoded";
    }


    @Override
    public Priority getPriority() {
        return Priority.IMMEDIATE;
    }
}
