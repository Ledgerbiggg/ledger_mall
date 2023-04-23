package com.tulingxueyuan.mall.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @author ledger
 * @version 1.0
 **/
@RestController
public class TestController {
    @GetMapping("/getmsg")
    public HashMap<String,String> getMsg(){
        HashMap<String, String> map = new HashMap<>();
        map.put("ledger","123");
        map.put("ledger2","12112.23");
        map.put("ledger3","312");
        return map;
    }

}
