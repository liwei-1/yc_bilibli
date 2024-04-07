package com.yc.bilibili.daomin;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 分页用具类
 */
@Data
@AllArgsConstructor
public class PageResult<T> {
    /**
     * 总数
     */
    private Integer total;

    /**
     * 数据data
     */
    private List<T> list;

}
