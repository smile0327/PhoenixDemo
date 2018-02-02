package com.kevin.phoenix.entity;

/**
 * <p></p>
 *
 * @Author: kevin
 * @Description:
 * @Company:
 * @Version: 1.0.0
 * @Date: Created in 14:22 2017/9/22.
 * @ProjectName: PhoenixDemo
 */
public class RawData {

    private String rowKey;
    private String data;

    public RawData() {
    }

    public String getRowKey() {
        return rowKey;
    }

    public void setRowKey(String rowKey) {
        this.rowKey = rowKey;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
