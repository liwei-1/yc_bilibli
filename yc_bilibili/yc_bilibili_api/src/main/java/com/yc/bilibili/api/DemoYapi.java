package com.yc.bilibili.api;


import com.yc.bilibili.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api")
public class DemoYapi {

    @Autowired
    private DemoService demoService;
    @GetMapping("/getid")
    public Map<String, Object> getId(){
        return demoService.getId();
    }
}
