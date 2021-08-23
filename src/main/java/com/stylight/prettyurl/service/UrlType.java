package com.stylight.prettyurl.service;

import com.stylight.prettyurl.repository.CachePrettyUrlMap;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum UrlType {
    PRETTY {
        @Override
        public Map<String, String> generate(List<String> urls, CachePrettyUrlMap urlDataMap) {
            return urls.stream()
                    .map(url -> urlDataMap.appendDomain(url))
                    .collect(Collectors.toMap(
                            Function.identity(),
                            key -> urlDataMap.getByKey(key))
                    );
        }
    },
    CANONICAL {
        @Override
        public Map<String, String> generate(List<String> urls, CachePrettyUrlMap urlDataMap) {
            return urls.stream()
                    .map(url -> urlDataMap.appendDomain(url))
                    .collect(Collectors.toMap(
                            Function.identity(),
                            val -> urlDataMap.getByValue(val))
                    );
        }
    };

    public abstract Map<String, String> generate(List<String> urls, CachePrettyUrlMap urlDataMap);

}