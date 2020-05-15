package jp.co.acroit.zaiko2020.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.springframework.beans.factory.annotation.Autowired;

import jp.co.acroit.zaiko2020.user.IUser;
import jp.co.acroit.zaiko2020.user.IUserFactory;

public class UserDataAccess implements IUserDataAccess {


    /**
     * @param userFactory
     * @param url
     * @param driver
     * @param username
     * @param password
     * @param query
     * @param numberColumn
     * @param idColumn
     * @param passwordColumn
     * @param datasource
     */
    @Autowired
    public UserDataAccess(IUserFactory userFactory, String url, String driver, String username, String password,
            String query, String numberColumn, String idColumn, String passwordColumn) {
        this.userFactory = userFactory;
        this.url = url;
        this.driver = driver;
        this.username = username;
        this.password = password;
        this.query = query;
        this.numberColumn = numberColumn;
        this.idColumn = idColumn;
        this.passwordColumn = passwordColumn;

        PoolProperties p = new PoolProperties();

        // 接続情報の設定
        p.setUrl(url);
        p.setDriverClassName(driver);
        p.setUsername(username);
        p.setPassword(password);

        // その他オプション
        p.setJmxEnabled(true);
        p.setTestWhileIdle(false);
        p.setTestOnBorrow(true);
        p.setValidationQuery("SELECT 1");
        p.setTestOnReturn(false);
        p.setValidationInterval(30000);
        p.setTimeBetweenEvictionRunsMillis(30000);
        p.setMaxActive(100);
        p.setInitialSize(10);
        p.setMaxWait(10000);
        p.setRemoveAbandonedTimeout(60);
        p.setMinEvictableIdleTimeMillis(30000);
        p.setMinIdle(10);
        p.setLogAbandoned(true);
        p.setRemoveAbandoned(true);
        p.setJdbcInterceptors("org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;"
                + "org.apache.tomcat.jdbc.pool.interceptor.StatementCache");


        datasource = new DataSource();
        datasource.setPoolProperties(p);

    }


    IUserFactory userFactory;
    String url;
    String driver;
    String username;
    String password;
    String query;
    String numberColumn;
    String idColumn;
    String passwordColumn;
    DataSource datasource;


    @Override
    public IUser findById(String id){
        Connection con = null;
        try {
            con = datasource.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            long num;
            String digest;
            String idFound;
            while (rs.next()) {
                num = rs.getLong(numberColumn);
                idFound = rs.getString(idColumn);
                digest = rs.getString(passwordColumn);
                if(idFound.equals(id)) {
                    rs.close();
                    con.close();
                    con = null;
                    return userFactory.create(num, idFound, digest);
                }
            }
            rs.close();
            con.close();
            con = null;
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (Exception ignore) {
                }
            }
        }
    }

}
