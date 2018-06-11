package com.archelo.coupons.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.LoaderManager.LoaderCallbacks;
import android.arch.lifecycle.ViewModelProviders;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.archelo.coupons.db.data.AzureToken;
import com.archelo.coupons.db.data.AzureUserInfo;
import com.archelo.coupons.db.data.Cookie;
import com.archelo.coupons.db.data.Coupon;
import com.archelo.coupons.db.data.LoginStatus;
import com.archelo.coupons.db.data.UserCoupons;
import com.archelo.coupons.db.model.AzureTokenViewModel;
import com.archelo.coupons.db.model.AzureUserInfoViewModel;
import com.archelo.coupons.db.model.CookieViewModel;
import com.archelo.coupons.db.model.CouponViewModel;
import com.archelo.coupons.urls.AzureUrls;
import com.archelo.coupons.urls.ShopriteURLS;
import com.archelo.coupons.volley.Authenticate3601Request;
import com.archelo.coupons.volley.AvailableCouponsRequest;
import com.archelo.coupons.volley.AzureServicesJSRequest;
import com.archelo.coupons.volley.AzureSessionRequest;
import com.archelo.coupons.volley.SamlRequest;
import com.archelo.coupons.volley.SamlResponse;
import com.archelo.coupons.volley.StatusRequest;
import com.archelo.coupons.volley.VerifySignInRedirectRequest;
import com.archelo.coupons.volley.VerifySignInRequest;
import com.archelo.coupons.volley.VolleyUtils;
import com.example.rtl1e.shopritecoupons.R;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    private static final String TAG = "LoginActivity";

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    private RequestQueue queue;
    private CookieManager cookieManager = new CookieManager();

    private Toast lastToast;
    private Response.ErrorListener volleyErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d(TAG, "Error occured " + error);
            showProgress(false);
        }
    };

    private CouponViewModel mCouponViewModel;
    private CookieViewModel mCookieViewModel;
    private AzureTokenViewModel mAzureTokenViewModel;
    private AzureUserInfoViewModel mAzureUserInfoModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = findViewById(R.id.email);
        populateAutoComplete();

        mPasswordView = findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);


        //Httpurlconnection queries cookiemanager for cookies
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        CookieHandler.setDefault(cookieManager);

        queue = Volley.newRequestQueue(this);
//        queue = Volley.newRequestQueue(this, new ProxiedHurlStack());

        mCouponViewModel = ViewModelProviders.of(this).get(CouponViewModel.class);
        mCookieViewModel = ViewModelProviders.of(this).get(CookieViewModel.class);
        mAzureTokenViewModel = ViewModelProviders.of(this).get(AzureTokenViewModel.class);
        mAzureUserInfoModel = ViewModelProviders.of(this).get(AzureUserInfoViewModel.class);
        mAzureTokenViewModel.deleteAll();
        mAzureUserInfoModel.deleteAll();
        mCookieViewModel.deleteAll();
    }


    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }

    private Toast showToast(CharSequence text) {
        if (lastToast != null) {
            lastToast.cancel();
        }
        lastToast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG);
        lastToast.show();
        return lastToast;
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            cookieManager.getCookieStore().removeAll();
            showProgress(true);
            performLogin(email, password);
//            mAuthTask = new UserLoginTask(email, password);
//            mAuthTask.execute((Void) null);
        }
    }

    private void performLogin(final String email, final String password) {
        showToast("Performing StatusRequest");
        StatusRequest statusRequest = new StatusRequest(Request.Method.GET, ShopriteURLS.STATUS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "StatusRequest response " + response);
                Log.d(TAG, "CookieManagerCookies: " + VolleyUtils.logCookies(cookieManager));
                LoginStatus loginStatus = new Gson().fromJson(response, LoginStatus.class);
                performSamlRequest(loginStatus, email, password);
            }
        }, volleyErrorListener);
        statusRequest.setShouldCache(false);
        queue.add(statusRequest);
    }

    private void performSamlRequest(final LoginStatus loginStatus, final String email, final String password) {
        showToast("Performing SamlRequest");
        SamlRequest samlRequest = new SamlRequest(loginStatus, Request.Method.GET, AzureUrls.SIGN_IN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "SamlRequest response " + response);
                Log.d(TAG, "CookieManagerCookies: " + VolleyUtils.logCookies(cookieManager));
                performAuthenticate3601Request(loginStatus, response, email, password);
            }
        }, volleyErrorListener);

        samlRequest.setShouldCache(false);
        queue.add(samlRequest);
    }

    private void performAuthenticate3601Request(final LoginStatus loginStatus, final String samlRequest, final String email, final String password) {
        showToast("Performing Authenticate3601Request");
        Authenticate3601Request authenticate3601Request = new Authenticate3601Request(loginStatus, samlRequest, Request.Method.POST, ShopriteURLS.AUTHENTICATE3601, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Authenticate3601Request response " + response);
                Log.d(TAG, "CookieManagerCookies: " + VolleyUtils.logCookies(cookieManager));
                performSamlResponseRequest(loginStatus, response, email, password);
            }
        }, volleyErrorListener);
        queue.add(authenticate3601Request);
    }

    private void performSamlResponseRequest(final LoginStatus loginStatus, final String response, final String email, final String password) {
        showToast("Performing SamlResponse");
        SamlResponse samlResponse = new SamlResponse(loginStatus, response, email, password, Request.Method.POST, ShopriteURLS.AUTHENTICATE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "SamlResponse response " + response);
                Log.d(TAG, "CookieManagerCookies: " + VolleyUtils.logCookies(cookieManager));
                performSignInVerifyRequest(loginStatus, response);
            }
        }, volleyErrorListener);

        queue.add(samlResponse);
    }

    private void performSignInVerifyRequest(final LoginStatus loginStatus, final String response) {
        showToast("Verifying sign in");
        VerifySignInRequest verifySignInRequest = new VerifySignInRequest(loginStatus, response, Request.Method.POST, AzureUrls.RETURN_FROM_SIGN_IN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "VerifySignInRequest response " + response);
                Log.d(TAG, "CookieManagerCookies: " + VolleyUtils.logCookies(cookieManager));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                final int status = error.networkResponse.statusCode;
                Log.d(TAG, "deliverError is expected for https to http redirects. error: " + status);

                /*
                 * The following redirect may be pointless
                 * */
                // Handle 30x
                if (HttpURLConnection.HTTP_MOVED_PERM == status || status == HttpURLConnection.HTTP_MOVED_TEMP || status == HttpURLConnection.HTTP_SEE_OTHER) {
                    final String location = error.networkResponse.headers.get("Location");
                    Log.d(TAG, "Location: " + location);
                    performSignInVerifyRedirectRequest(location, loginStatus);

                }
            }
        });

        queue.add(verifySignInRequest);
    }

    private void performSignInVerifyRedirectRequest(String location, final LoginStatus loginStatus) {
        showToast("Verifying redirect request");
        VerifySignInRedirectRequest request = new VerifySignInRedirectRequest(location, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "VerifySignInRedirectRequest response " + response);
                Log.d(TAG, "CookieManagerCookies: " + VolleyUtils.logCookies(cookieManager));
                performAzureJSRequest(loginStatus);
            }
        }, volleyErrorListener);
        queue.add(request);

    }

    private void performAzureJSRequest(final LoginStatus loginStatus) {
        showToast("Fetching azure token");
        AzureServicesJSRequest request = new AzureServicesJSRequest(ShopriteURLS.WEB_JS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "AzureServicesJSRequest response " + response);
                showToast("Parsing azure token");
                int index = response.indexOf(AzureToken.AUTHORIZATION);
                int endIndex = response.indexOf("&", index);
                String authorization = response.substring(index + 14, endIndex);

                index = response.indexOf(AzureToken.ZUMO_APPLICATION_TOKEN);
                endIndex = response.indexOf("\"", index + AzureToken.ZUMO_APPLICATION_TOKEN.length());
                String zumoApplicationToken = response.substring(index + AzureToken.ZUMO_APPLICATION_TOKEN.length(), endIndex);
                AzureToken azureToken = new AzureToken.AzureTokenBuilder()
                        .authorization(authorization)
                        .userID(loginStatus.getUserId())
                        .zumoApplicationToken(zumoApplicationToken)
                        .signInStatus(loginStatus.isSignedIn())
                        .build();

                Log.d(TAG, azureToken.toString());
                performAzureSessionRequest(azureToken);
            }
        }, volleyErrorListener);
        queue.add(request);
    }

    private void performAzureSessionRequest(final AzureToken azureToken) {
        showToast("Performing Azure Session Request");
        AzureSessionRequest request = new AzureSessionRequest(azureToken, AzureUrls.SESSION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "performAzureSessionRequest response " + response);
                Log.d(TAG, "CookieManagerCookies: " + VolleyUtils.logCookies(cookieManager));
                AzureUserInfo userInfo = new Gson().fromJson(response, AzureUserInfo.class);
                Log.d(TAG, userInfo.toString());
                performAvailableCouponsRequest(azureToken, userInfo);
            }
        }, volleyErrorListener);
        queue.add(request);
    }

    private void performAvailableCouponsRequest(final AzureToken azureToken, final AzureUserInfo azureUserInfo) {
        showToast("Performing Available coupons Request");
        AvailableCouponsRequest request = new AvailableCouponsRequest(azureToken, azureUserInfo, Request.Method.POST, AzureUrls.AVAILABLE_COUPONS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "performAvailableCouponsRequest response " + response);
                Log.d(TAG, "CookieManagerCookies: " + VolleyUtils.logCookies(cookieManager));
                UserCoupons userCoupons = new Gson().fromJson(response, UserCoupons.class);
                userCoupons.indexCoupons();
                Log.d(TAG, userCoupons.toString());
                performAllCouponsRequest(azureToken, azureUserInfo, userCoupons);
            }
        }, volleyErrorListener);
        queue.add(request);
    }

    private void performAllCouponsRequest(final AzureToken azureToken, final AzureUserInfo azureUserInfo, final UserCoupons userCoupons) {
        showToast("Performing remote db fetch");
        AvailableCouponsRequest request = new AvailableCouponsRequest(azureToken, azureUserInfo, Request.Method.POST, AzureUrls.COUPONS_METADATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "request response " + response);
                Log.d(TAG, "CookieManagerCookies: " + VolleyUtils.logCookies(cookieManager));

                storeResponseIntoDb(response, userCoupons);
                storeCookiesIntoDb();
                mAzureTokenViewModel.insert(azureToken);
                mAzureUserInfoModel.insert(azureUserInfo);

                Intent intent = new Intent(LoginActivity.this, CouponActivity.class);

                Toast toast = showToast("Done!");
                Log.d(TAG, "Starting activity");
                showProgress(false);
                startActivity(intent);
                toast.cancel();
            }
        }, volleyErrorListener);
        queue.add(request);
    }

    private void storeCookiesIntoDb() {
        List<HttpCookie> cookies = cookieManager.getCookieStore().getCookies();
        Cookie[] cookie = new Cookie[cookies.size()];
        for(int i = 0 ; i < cookies.size() ; i ++){
            cookie[i] = new Cookie(cookies.get(i));
        }
        showToast("Saving " +cookie.length+" httpCookies ");
        Log.d(TAG,"Saving " +cookie.length+" httpCookies ");
        mCookieViewModel.insert(cookie);
    }

    private void storeResponseIntoDb(String response, UserCoupons userCoupons) {
        int count = 0;
        Coupon[] couponCache = new Coupon[50];
        Gson gson = new Gson();
        InputStream inputStream = new ByteArrayInputStream(response.getBytes(StandardCharsets.UTF_8));
        JsonReader reader = null;
        try {
            reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
            reader.beginArray();
            while (reader.hasNext()) {
                Coupon coupon = gson.fromJson(reader, Coupon.class);
                couponCache[count++] = coupon;

                if(count == 50){
                    updateCouponContents(couponCache,userCoupons);
                    count = 0;
                }

            }
            if(count > 0){
                updateCouponContents(couponCache,userCoupons);
            }

            reader.endArray();
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

//        Coupon[] couponsArray = new Gson().fromJson(response, Coupon[].class);
//        Log.d(TAG, Arrays.toString(couponsArray));



    }

    private void updateCouponContents(Coupon[] couponsArray, UserCoupons userCoupons){
        for(Coupon coupon : couponsArray){
            Boolean avail = userCoupons.isClipped(coupon.getCoupon_id());
            if(avail == null){
                coupon.setClipped(false);
                coupon.setAvailable(false);
            }
            else{
                coupon.setClipped(avail);
                coupon.setAvailable(true);
            }
        }

        showToast("Saving " +couponsArray.length+" coupons");
        Log.d(TAG,"Saving " +couponsArray.length+" coupons");
        mCouponViewModel.insert(couponsArray);
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

}

