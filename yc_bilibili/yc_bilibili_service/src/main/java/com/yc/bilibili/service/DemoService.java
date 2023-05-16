package com.yc.bilibili.service;


import com.yc.bilibili.dao.DemoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
public class DemoService {

    @Autowired
    private DemoDao demoDao;
    public Map<String, Object> getId(){
        String id = "1";
        return demoDao.getId(id);
    }

}
