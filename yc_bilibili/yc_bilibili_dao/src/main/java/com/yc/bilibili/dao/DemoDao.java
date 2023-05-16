package com.yc.bilibili.dao;


import org.apache.ibatis.annotations.Mapper;

import java.util.Map;
import java.util.Objects;

@Mapper
public interface DemoDao {


     Map<String, Object> getId(String id);
}
