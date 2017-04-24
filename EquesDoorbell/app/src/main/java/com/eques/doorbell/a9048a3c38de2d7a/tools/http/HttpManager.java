//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.eques.doorbell.a9048a3c38de2d7a.tools.http;


public class HttpManager {
    public static String DIGEST_NAME = "xqew7ddy2zis";
    public static String DIGEST_PASS = "735aae4a75cd97dcdcba0c962e599141";
    private static HttpProvider defaultPovider;
    private static HttpProvider wulianCloudPovider;
    private static HttpProvider httpsPovider;
    private static HttpProviderFactory httpProviderFactory = new OkHttpProviderFactory();

    public HttpManager() {
    }

    public static void setHttpProviderFactory(HttpProviderFactory factory) {
        httpProviderFactory = factory;
    }

    public static HttpProvider getDefaultProvider() {
        if(defaultPovider == null) {
            defaultPovider = httpProviderFactory.createDefaultHttpProvider();
        }

        return defaultPovider;
    }

    public static HttpProvider getWulianCloudProvider() {
        if(wulianCloudPovider == null) {
            wulianCloudPovider = httpProviderFactory.createWulianCloudHttpProvider();
        }

        return wulianCloudPovider;
    }
    public static HttpProvider getHttpsProvider() {
        if(httpsPovider == null) {
            httpsPovider = httpProviderFactory.createHttpsProvider();
        }
        return httpsPovider;
    }
}
