package com.stylight.prettyurl.repository;

import com.stylight.prettyurl.exception.InvalidUrlException;
import com.stylight.prettyurl.util.DbUtils;
import com.stylight.prettyurl.util.URIUtils;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

@Repository
public class CachePrettyUrlMap {
    /**
     * dictory to store pretty url
     */
    private static final BidirectionalMap<String, String> book = new BidirectionalMap<>();
    /**
     * The basePath for all relative urls
     */
    @Value("${app.base.domain}")
    @Getter
    private String basePath;

    @Autowired
    private DbUtils dbUtils;

    @PostConstruct
    private void loadData() throws IOException {
        dbUtils.loadDataFromClassPath();
    }

    /**
     * @param key
     * @return the value of the matching if exists, otherwise go for best match
     */
    public String getByKey(String key) {
        return book.getOrDefault(key, getBestMatch(key, true));
    }

    public String getByValue(String val) {
        return book.getKeyOrDefault(val, getBestMatch(val, false));
    }

    /**
     * @param key
     * @return best matched value
     */
    private String getBestMatch(String key, boolean isKey) {
        try {
            URL url = new URL(key);
            List<String> queryParamMap = URIUtils.splitQuery(url);
            if (CollectionUtils.isEmpty(queryParamMap)) {
                return key;
            }
            String matchedKey = queryParamMap.stream()
                    .map(q -> URIUtils.removeQueryParam(key, q))
                    .filter(q -> {
                        String val = getFromDict(q, key, isKey);
                        return (val != key);
                    }).findFirst().orElse(key);
            return getFromDict(matchedKey, key, isKey);

        } catch (MalformedURLException | UnsupportedEncodingException e) {
            throw new InvalidUrlException();
        }

    }


    private String getFromDict(String q, String defaultVal, boolean isKey) {
        if (isKey) {
            return book.getOrDefault(q, defaultVal);
        } else {
            return book.getKeyOrDefault(q, defaultVal);
        }
    }

    /**
     * @param key   key of the dictionary
     * @param value value of the dictionary
     */
    public void loadLookup(String key, String value) {
        book.put(basePath + key, basePath + value);
    }

    /**
     * @param preLoadedData to load some values
     */
    public void loadList(Map<String, String> preLoadedData) {
        preLoadedData.entrySet().stream()
                .forEach(entry -> loadLookup(entry.getKey(), entry.getValue()));
    }

    public String appendDomain(String uri) {
        return basePath + uri;
    }

}
