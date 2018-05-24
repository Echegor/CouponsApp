package com.archelo.volley;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

import com.android.volley.toolbox.HurlStack;

public class ProxiedHurlStack extends HurlStack {

    @Override
    protected HttpURLConnection createConnection(URL url) throws IOException {

        // Start the connection by specifying a proxy server
        Proxy proxy = new Proxy(Proxy.Type.HTTP,
                InetSocketAddress.createUnresolved("10.120.30.19", 8080));

        return (HttpURLConnection) url
                .openConnection(proxy);
    }
}
