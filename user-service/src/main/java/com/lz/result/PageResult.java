package com.lz.result;


import lombok.Data;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 封装分页查询结果
 * @author lz
 */
@Data
public class PageResult<T> implements Serializable {

    /**
     * 总记录数
     */
    private long total; 

    /**
    当前页数据集合
     */
    private  List<T> records;

    public PageResult(long total, List<T> records) {
        this.total = total;
        this.records = new ArrayList<>(records); // 或者使用不可变列表
    }







}