package com.stylight.prettyurl.controller;

import com.stylight.prettyurl.service.LookUpService;
import com.stylight.prettyurl.service.UrlType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class UrlLookUpController {

    @Autowired
    private LookUpService lookUpService;

    @PostMapping("/pretty")
    public ResponseEntity<Map<String, String>> getPrettyUrls(@RequestBody List<String> canonicalUrls) {
        return ResponseEntity.ok(lookUpService.createUrl(canonicalUrls, UrlType.PRETTY));
    }

    @PostMapping("/canonical")
    public ResponseEntity<Map<String, String>> getCanonicalUrls(@RequestBody List<String> prettyUrls) {
        return ResponseEntity.ok(lookUpService.createUrl(prettyUrls, UrlType.CANONICAL));
    }


}
