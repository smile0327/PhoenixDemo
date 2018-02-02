package com.kevin.phoenix.main;

import com.kevin.phoenix.util.JDBCUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p></p>
 *
 * @Author: kevin
 * @Description:
 * @Company:
 * @Version: 1.0.0
 * @Date: Created in 15:06 2017/9/22.
 * @ProjectName: PhoenixDemo
 */
public class Main {

    public static void main(String[] args) {
        int pageSize = 5;
        int page = 2;
        String tableName = "condition_data_wh";
        String vin = "LJNDFV1T0GN100063";
        List<Map<String, String>> result = JDBCUtil.canDataQuery(pageSize, page, tableName, vin, getColumns());
        for (Map<String,String> map : result){
            System.out.println(map.toString());
        }
    }

    private static List<String> getColumns(){
        List<String> columns = new ArrayList<>();
        columns.add("vin");
        columns.add("sendTime");
        columns.add("V6");
        columns.add("V9");
        columns.add("V3");
        columns.add("B5");
        columns.add("G1");
        columns.add("G2");
        columns.add("G7");
        return columns;
    }

}
