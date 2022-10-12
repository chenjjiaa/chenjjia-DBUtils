package com.chenjjiaa.utils;

import java.sql.*;

/**
 * This utils for MySQL
 *
 * Author：  chenjunjia
 * Date：    2022/10/12 19:04
 * WeChat：  China_JoJo_
 * Blog：    https://juejin.cn/user/1856417285289304/posts
 * Github：  https://github.com/chenjjiaa
 */
public class DBUtils {
    private String URL;
    private String USERNAME;
    private String PASSWORD;
    private static ThreadLocal threadLocal = new ThreadLocal();
    private static Connection conn = null;
    private static PreparedStatement ps = null;
    private static ResultSet rs = null;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public DBUtils(String url, String username, String password) {
        this.URL = url;
        this.USERNAME = username;
        this.PASSWORD = password;
    }

    /**
     * 获取连接
     */
    public Connection getConnection() {
        conn = (Connection) threadLocal.get();
        if (conn == null){
            try {
                conn = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            threadLocal.set(conn);
        }
        return conn;
    }

    /**
     *获取预编译DB操作对象
     */
    public PreparedStatement createPrepareStatement(String sql) throws SQLException {
        conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        return ps;
    }


    /**
     * 释放资源关闭连接
     */
    public static void close(){
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                /**
                 * 当前线程一定要和连接对象解除绑定关系。
                 */
                threadLocal.remove();
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public static void close(ResultSet rs){
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        close();
    }

    /**
     * 开启事务
     * @throws SQLException
     */
    public static void beginTransaction(Connection conn) throws SQLException {
        if (conn != null){
            conn.setAutoCommit(false);
        }
    }

    /**
     * 结束事务
     * @throws SQLException
     */
    public static void endTransaction(Connection conn) throws SQLException {
        if (conn != null){
            conn.setAutoCommit(true);
        }
    }

    /**
     * 提交事务
     * @throws SQLException
     */
    public static void commitTransaction(Connection conn) throws SQLException {
        if (conn != null){
            conn.commit();
        }
    }

    /**
     * 回滚事务：在异常触发时回滚
     * @throws SQLException
     */
    public static void rollbackTransaction(Connection conn) throws SQLException {
        if (conn != null){
            conn.rollback();
        }
    }
}
