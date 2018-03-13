package com.tx.txspringboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

/**
 * @author Eric Cui
 * <p>Created by Intellij IDEA.
 * Date : 2018/3/2 23:57
 * Desc : 描述信息
 */
@Controller
@RequestMapping("/.well-known")
public class SSLController {

    @RequestMapping(value = "/acme-challenge/{key}", method = RequestMethod.GET)
    @ResponseBody
    public String acmeChallenge(@PathVariable("key") String key) {
        return keyPair.get(key);
    }

    private static HashMap<String, String> keyPair =
            new HashMap<String, String>() {
                {
                    put(
                            "cFHwS3ZXtRtw4MWKffmNPZQOHhMh8WIkpQL7OCt0n5k",
                            "cFHwS3ZXtRtw4MWKffmNPZQOHhMh8WIkpQL7OCt0n5k.uhFNJyDh_hUXRUp5pHj8iIYXkEcMcpyMncTUguI35vA");
                    put(
                            "p_HAYOWD3AdjeVthZRGPExoHEXOwNldnhOan2Nwl-5g",
                            "p_HAYOWD3AdjeVthZRGPExoHEXOwNldnhOan2Nwl-5g.uhFNJyDh_hUXRUp5pHj8iIYXkEcMcpyMncTUguI35vA");
                }
            };
}
