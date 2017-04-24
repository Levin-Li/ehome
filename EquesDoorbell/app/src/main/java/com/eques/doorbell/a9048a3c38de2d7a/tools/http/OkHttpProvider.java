//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.eques.doorbell.a9048a3c38de2d7a.tools.http;

import android.os.Build;

import com.alibaba.fastjson.JSONObject;
import com.burgstaller.okhttp.AuthenticationCacheInterceptor;
import com.burgstaller.okhttp.CachingAuthenticatorDecorator;
import com.burgstaller.okhttp.digest.Credentials;
import com.eques.doorbell.a9048a3c38de2d7a.tools.logger.LoggerFactory;
import com.eques.doorbell.a9048a3c38de2d7a.util.StringUtil;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

class OkHttpProvider implements HttpProvider {
    private static com.eques.doorbell.a9048a3c38de2d7a.tools.logger.Logger Logger = LoggerFactory.getLogger(OkHttpProvider.class);
    private static final String[] HEADER_STATUS = new String[]{"status", "retCode"};
    private Builder builder;
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType TEXT = MediaType.parse("text/plain; charset=utf-8");

    OkHttpProvider(String diUsername, String diPassword) {
        Credentials credentials = new Credentials(diUsername, diPassword);
        MyDigestAuthenticator digestAuthenticator = new MyDigestAuthenticator(credentials);
        this.builder = new Builder();
        ConcurrentHashMap authCache = new ConcurrentHashMap();
        this.builder.authenticator(new CachingAuthenticatorDecorator(digestAuthenticator, authCache)).addInterceptor(new AuthenticationCacheInterceptor(authCache));
    }

    OkHttpProvider() {
        this.builder = new Builder();
    }
    OkHttpProvider(String https) {
        X509TrustManager xtm = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                X509Certificate[] x509Certificates = new X509Certificate[0];
                return x509Certificates;
            }
        };

        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("SSL");

            sslContext.init(null, new TrustManager[]{xtm}, new SecureRandom());

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

        HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
        this.builder = new Builder();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                /*Android 4.X 对TLS1.1、TLS1.2的支持*/
            this.builder.sslSocketFactory(new Tls12SocketFactory(sslContext.getSocketFactory()))
                    .hostnameVerifier(DO_NOT_VERIFY)
                    .build();
        }else {
            this.builder.sslSocketFactory(sslContext.getSocketFactory())
                    .hostnameVerifier(DO_NOT_VERIFY)
                    .build();
        }
    }
    private static class Tls12SocketFactory extends SSLSocketFactory {

        private static final String[] TLS_SUPPORT_VERSION = {"TLSv1.1", "TLSv1.2"};

        final SSLSocketFactory delegate;

        private Tls12SocketFactory(SSLSocketFactory base) {
            this.delegate = base;
        }

        @Override
        public String[] getDefaultCipherSuites() {
            return delegate.getDefaultCipherSuites();
        }

        @Override
        public String[] getSupportedCipherSuites() {
            return delegate.getSupportedCipherSuites();
        }

        @Override
        public Socket createSocket(Socket s, String host, int port, boolean autoClose) throws IOException {
            return patch(delegate.createSocket(s, host, port, autoClose));
        }

        @Override
        public Socket createSocket(String host, int port) throws IOException, UnknownHostException {
            return patch(delegate.createSocket(host, port));
        }

        @Override
        public Socket createSocket(String host, int port, InetAddress localHost, int localPort) throws IOException, UnknownHostException {
            return patch(delegate.createSocket(host, port, localHost, localPort));
        }

        @Override
        public Socket createSocket(InetAddress host, int port) throws IOException {
            return patch(delegate.createSocket(host, port));
        }

        @Override
        public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort) throws IOException {
            return patch(delegate.createSocket(address, port, localAddress, localPort));
        }

        private Socket patch(Socket s) {
            //代理SSLSocketFactory在创建一个Socket连接的时候，会设置Socket的可用的TLS版本。
            if (s instanceof SSLSocket) {
                ((SSLSocket) s).setEnabledProtocols(TLS_SUPPORT_VERSION);
            }
            return s;
        }
    }
    public JSONObject post(String url) {
        return this.post(url, (Map)null, (Map)(new HashMap()));
    }

    public JSONObject post(String url, Map<String, String> paramters) {
        return this.post(url, (Map)null, (Map)paramters);
    }

    public JSONObject post(String url, Map<String, String> headers, Map<String, String> paramters) {
        okhttp3.FormBody.Builder bodyBuilder = new okhttp3.FormBody.Builder();
        if(paramters != null) {
            Iterator okbody = paramters.entrySet().iterator();

            while(okbody.hasNext()) {
                Entry result = (Entry)okbody.next();
                bodyBuilder.add((String)result.getKey(), (String)result.getValue());
            }
        }

        FormBody okbody1 = bodyBuilder.build();
         Logger.debug("url:" + url + ";" + "parameter:" + okbody1.toString());

        JSONObject result1 = this.post(url, headers, (RequestBody)okbody1);
        this.logResult(result1);
        return result1;
    }

    public JSONObject post(String url, Map<String, String> headers, JSONObject jsonObject) {
        String body = "";
        if(jsonObject != null) {
            body = jsonObject.toJSONString();
        }

        Logger.debug("url:" + url + ";" + "parameter:" + body);

        RequestBody okbody = RequestBody.create(JSON, body);
        JSONObject result = this.post(url, headers, okbody);
        this.logResult(result);
        return result;
    }

    public JSONObject post(String url, Map<String, String> headers, String body) {
        Logger.debug("url:" + url + ";" + "parameter:" + body);

        RequestBody okbody = RequestBody.create(TEXT, body);
        JSONObject result = this.post(url, headers, okbody);
        this.logResult(result);
        return result;
    }

    public JSONObject post(String url, Map<String, String> headers, byte[] body) {
       Logger.debug("url:" + url + ";" + "parameter:");

        RequestBody okbody = RequestBody.create(TEXT, body);
        JSONObject result = this.post(url, headers, okbody);
        this.logResult(result);
        return result;
    }

    public byte[] getPicture(String url) {
        byte[] ret = null;

        try {
            OkHttpClient e = this.builder.build();
            Request request = (new okhttp3.Request.Builder()).url(HttpUrl.parse(url)).get().build();
            Response response = e.newCall(request).execute();
            if(response.isSuccessful()) {
                ret = response.body().bytes();
            }

            response.close();
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return ret;
    }

    public boolean verificationResponse(String url) {
        try {
            OkHttpClient e = this.builder.build();
            Request request = (new okhttp3.Request.Builder()).url(HttpUrl.parse(url)).get().build();
            Response response = e.newCall(request).execute();
            if(response.isSuccessful()) {
                return true;
            }
        } catch (IOException var5) {
            var5.printStackTrace();
        }

        return false;
    }

    private JSONObject post(String url, Map<String, String> headers, RequestBody okbody) {
        JSONObject result = new JSONObject();

        try {
            okhttp3.Request.Builder e = this.getRequestBuilder(headers);
            Request request = e.url(HttpUrl.parse(url)).post(okbody).build();
            OkHttpClient client = this.builder.build();
            Response response = client.newCall(request).execute();
            this.parseResponse(result, response);
        } catch (Exception var9) {
            var9.printStackTrace();
        }

        return result;
    }

    private void logResult(JSONObject result) {
            StringBuilder sb = new StringBuilder("result:");
            if(result != null) {
                sb.append(result.toJSONString());
            }
    }

    private okhttp3.Request.Builder getRequestBuilder(Map<String, String> headers) {
        okhttp3.Request.Builder requestBuilder = new okhttp3.Request.Builder();
        if(headers != null) {
            Iterator var3 = headers.entrySet().iterator();

            while(var3.hasNext()) {
                Entry pair = (Entry)var3.next();
                requestBuilder.addHeader((String)pair.getKey(), (String)pair.getValue());
            }
        }

        return requestBuilder;
    }

    private void parseResponse(JSONObject result, Response response) {
        if(response != null && response.isSuccessful()) {
            Headers responseHeaders = response.headers();
            JSONObject headerJson = new JSONObject();
            if(responseHeaders != null) {
                String[] bodyStr = HEADER_STATUS;
                int e = bodyStr.length;

                for(int var7 = 0; var7 < e; ++var7) {
                    String headerName = bodyStr[var7];
                    String status = responseHeaders.get(headerName);
                    if(!StringUtil.isEmpty(status)) {
                        headerJson.put(headerName, status);
                    }
                }
            }

            result.put("header", headerJson);
            String var15 = "";

            try {
                var15 = response.body().string();
            } catch (Exception var13) {
            } finally {
                response.close();
                result.put("body", var15);
            }

        }
    }
}
