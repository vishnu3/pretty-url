package com.stylight.prettyurl.service.impl;

import com.stylight.prettyurl.repository.CachePrettyUrlMap;
import com.stylight.prettyurl.service.LookUpService;
import com.stylight.prettyurl.service.UrlType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/*
 * create Map of url
 * */

@Service
@AllArgsConstructor
public class LookUpServiceImpl implements LookUpService {

    private final CachePrettyUrlMap cachePrettyUrlMap;

    @Override
    public Map<String, String> createUrl(List<String> urls, UrlType urlType) {
        return urlType.generate(urls, cachePrettyUrlMap);
    }

}
