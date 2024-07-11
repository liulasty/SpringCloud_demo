package com.lz.result;


import lombok.Data;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 封装分页查询结果
 * @author lz
 */

public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 总记录数
     */
    private long total;

    /**
     * 当前页数据集合
     */
    private List<T> records;

    /**
     * 构造方法，带非空检查
     * 
     * @param total 总记录数，必须大于等于0
     * @param records 记录列表，可以为空，为空时内部列表也将为空
     * @throws IllegalArgumentException 如果 total 小于0
     */
    public PageResult(long total, List<T> records) {
        if (total < 0) {
            throw new IllegalArgumentException("Total must be greater than or equal to 0");
        }

        this.total = total;
        if (records != null) {
            // 为了保持可变性，这里直接引用传入的列表
            // 如果需要保证不可变性，可以使用 Collections.unmodifiableList(new ArrayList<>(records))
            this.records = new ArrayList<>(records);
        } else {
            this.records = new ArrayList<>();
        }
    }

    /**
     * 提供一个方法来获取总记录数
     * 
     * @return 总记录数
     */
    public long getTotal() {
        return total;
    }

    /**
     * 提供一个方法来设置总记录数
     * 
     * @param total 新的总记录数
     * @throws IllegalArgumentException 如果 total 小于0
     */
    public void setTotal(long total) {
        if (total < 0) {
            throw new IllegalArgumentException("Total must be greater than or equal to 0");
        }
        this.total = total;
    }

    /**
     * 提供一个方法来获取记录列表
     * 
     * @return 记录列表
     */
    public List<T> getRecords() {
        return records;
    }

    /**
     * 提供一个方法来设置记录列表，注意这会改变内部列表的引用
     * 
     * @param records 新的记录列表
     */
    public void setRecords(List<T> records) {
        if (records != null) {
            this.records = new ArrayList<>(records);
        } else {
            this.records = new ArrayList<>();
        }
    }
}