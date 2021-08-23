package com.stylight.prettyurl.util;

import lombok.experimental.UtilityClass;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

@UtilityClass
public class URIUtils {

    public static List<String> splitQuery(URL url) throws UnsupportedEncodingException {
        List<String> queryPairs = new LinkedList<>();
        String query = url.getQuery();
        if (StringUtils.hasText(query)) {
            String[] pairs = query.split("&");
            int len = pairs.length;
            int lastIndex = len - 1;

            /* Reading from right to left */
            for (int i = lastIndex; i >= 0; i--) {
                queryPairs.add(URLDecoder.decode(pairs[i], StandardCharsets.UTF_8.name()));
            }
        }
        return queryPairs;
    }

    public static String removeQueryParam(String url, String param) {
        int lastIndex = url.lastIndexOf(param);
        return url.substring(0, lastIndex - 1);
    }
}
