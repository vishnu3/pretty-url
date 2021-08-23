package com.stylight.prettyurl.service;

import java.util.List;
import java.util.Map;

public interface LookUpService {

    Map<String, String> createUrl(List<String> urls, UrlType urlType);

}
