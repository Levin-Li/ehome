//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.eques.doorbell.a9048a3c38de2d7a.tools.http;

import com.burgstaller.okhttp.digest.Credentials;
import com.burgstaller.okhttp.digest.DigestAuthenticator;
import com.eques.doorbell.a9048a3c38de2d7a.util.StringUtil;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

public class MyDigestAuthenticator extends DigestAuthenticator {
    private static final Pattern QOP_REGEX = Pattern.compile("(qop|algorithm)=(\\w+)");

    public MyDigestAuthenticator(Credentials credentials) {
        super(credentials);
    }

    public synchronized Request authenticate(Route route, Response response) throws IOException {
        Request req = super.authenticate(route, response);
        String authenStr = req.header("Authorization");
        if(StringUtil.isEmpty(authenStr)) {
            throw new IOException("Parent DigestAuthenticator did not generator WWW_AUTH_RESP header");
        } else {
            Matcher m = QOP_REGEX.matcher(authenStr);

            for(int start = 0; m.find(start); m = QOP_REGEX.matcher(authenStr)) {
                authenStr = m.replaceFirst(m.group(1) + "=\"" + m.group(2) + "\"");
                start = m.end();
            }

            return req.newBuilder().header("Authorization", authenStr).build();
        }
    }
}
