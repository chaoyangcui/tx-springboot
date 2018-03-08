package com.tx.txspringboot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Eric
 * @since 2018/3/8 17:14
 */
@RestController
@RequestMapping("/")
public class HomeController {

    @RequestMapping(value = {"", "index", "home"}, method = RequestMethod.GET)
    public String home() {
        return "Welcome ! Have fun !";
    }

}
