package com.company.util;

import javax.servlet.http.HttpServletRequest;

public class UrlUtil {
    private UrlUtil() {
    }

    public static String extractUrlPrefix(HttpServletRequest request) {
        String url = request.getRequestURL().toString();
        String uri = request.getRequestURI();
        return url.substring(0, url.length() - uri.length()) + request.getContextPath();
    }
}
