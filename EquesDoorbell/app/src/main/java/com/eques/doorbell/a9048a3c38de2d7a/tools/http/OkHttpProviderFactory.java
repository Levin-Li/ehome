//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.eques.doorbell.a9048a3c38de2d7a.tools.http;


public class OkHttpProviderFactory implements HttpProviderFactory {
    public OkHttpProviderFactory() {
    }

    public HttpProvider createDefaultHttpProvider() {
        return new OkHttpProvider();
    }

    public HttpProvider createWulianCloudHttpProvider() {
        return new OkHttpProvider(HttpManager.DIGEST_NAME, HttpManager.DIGEST_PASS);
    }

    @Override
    public HttpProvider createHttpsProvider() {
        return new OkHttpProvider("");
    }
}
