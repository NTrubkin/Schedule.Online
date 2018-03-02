package com.company.service.auth.oauth2;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class OAuth2Authenticator {

    private static final String ENCODING = "UTF-8";

    private final String userAgent;
    private final String appId;
    private final String appSecret;
    private final String redirectUrl;
    private final String getTokenUrl;
    private final String readDataUrl;
    private final OAuth2DataRestorer restorer;

    // todo велосипед. изучить spring security oauth2
    public OAuth2Authenticator(String userAgent, String appId, String appSecret, String redirectUrl, String getTokenUrl, String readDataUrl, OAuth2DataRestorer restorer) {
        this.userAgent = userAgent;
        this.appId = appId;
        this.appSecret = appSecret;
        this.redirectUrl = redirectUrl;
        this.getTokenUrl = getTokenUrl;
        this.readDataUrl = readDataUrl;
        this.restorer = restorer;
    }

    public OAuth2Account readAccountData(String code, String urlPrefix) {
        try {
            //get token
            String accessTokenData = sendRequest(getTokenUrl, appId, appSecret, urlPrefix + redirectUrl, code);
            String accessToken = restorer.restoreAccessToken(accessTokenData);

            // read data, check verification
            String accountData = sendRequest(readDataUrl, accessToken);
            // todo check account verification
            return restorer.restoreAccountData(accountData);
        } catch (IOException e) {
            throw new OAuth2Exception("Read OAuth2 account data error", e);
        }

    }

    private String sendRequest(String urlPattern, String... args) {
        String url = String.format(urlPattern, (Object[]) args);

        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", userAgent);
            con.setRequestProperty("Accept-Charset", ENCODING);

            int responseCode = con.getResponseCode();

            if (responseCode == HttpStatus.OK.value()) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), ENCODING));
                String result = in.readLine();
                in.close();
                return result;
            }
            else {
                throw new OAuth2Exception("Access token requesting error. Auth2 server returns " + responseCode);
            }
        } catch (IOException e) {
            throw new OAuth2Exception("Request error by url " + url, e);
        }
    }

    public void authenticate(UserDetails userDetails) {
        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}
