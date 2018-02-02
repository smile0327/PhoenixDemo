package com.kevin.phoenix.util;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p></p>
 *
 * @Author: kevin
 * @Description:
 * @Company:
 * @Version: 1.0.0
 * @Date: Created in 11:59 2017/9/22.
 * @ProjectName: PhoenixDemo
 */
public class JDBCUtil {

    /**
     * 获取jdbc连接
     *
     * @return
     */
    public static Connection getConnection() {
        try {
            // load driverPhoenixDriver
            Class.forName("org.apache.phoenix.jdbc.PhoenixDriver");

            // get connection
            // jdbc 的 url 类似为 jdbc:phoenix [ :<zookeeper quorum> [ :<port number> ] [ :<root node> ] ]，
            // 需要引用三个参数：hbase.zookeeper.quorum、hbase.zookeeper.property.clientPort、and zookeeper.znode.parent，
            // 这些参数可以缺省不填而在 hbase-site.xml 中定义。
            return DriverManager.getConnection("jdbc:phoenix:192.168.1.105,192.168.1.111,192.168.1.117:2181");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 原始数据查询
     */
    public static void rawDataQuery() {
        Connection conn = null;
        try {
            conn = getConnection();
            if (conn == null) {
                System.out.println("conn is null...");
                return;
            }
            String sql = "select * from \"jn_raw_data\" limit 1";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    System.out.print(rs.getString("rk") + "\t");
                    System.out.print(rs.getString("data"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 分页查询can数据
     * @param pageSize
     * @param page
     * @param tableName
     * @param vin
     * @param columns
     */
    public static List<Map<String, String>> canDataQuery(int pageSize, int page, String tableName, String vin, List<String> columns) {
        List<Map<String,String>> result = new ArrayList<>();
        if (page < 1){
            page = 1;
        }
        int offset = (page - 1) * pageSize;
        Connection conn = null;
        try {
            conn = getConnection();
            String sql = createSql(tableName, columns);
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,vin);
            ps.setInt(2,pageSize);
            ps.setInt(3,offset);
            ResultSet rs = ps.executeQuery();
            if (rs != null){
                while (rs.next()){
                    Map<String,String> dataMap = new HashMap<>();
                    for (String column : columns){
                        dataMap.put(column,rs.getString(column));
                    }
                    result.add(dataMap);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null)
            {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * 创建sql语句
     * @param tableName
     * @param columns  @return
     */
    private static String createSql(String tableName, List<String> columns){
        StringBuffer sql = new StringBuffer();
        sql.append("select ");
        for (int i = 0 ; i < columns.size() ; i++){
            sql.append("\"").append(columns.get(i)).append("\"");
            if (i != columns.size()-1){
                sql.append(",");
            }
        }
        sql.append(" from \"").append(tableName).append("\"").append("where \"vin\" = ? limit ? offset ? ");
        System.out.println("SQL : " + sql.toString());
        return sql.toString();
    }


}
