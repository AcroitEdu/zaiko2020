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

    public void setUserFactory(IUserFactory userFactory) {
        this.userFactory = userFactory;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public void setNumberColumn(String numberColumn) {
        this.numberColumn = numberColumn;
    }

    public void setIdColumn(String idColumn) {
        this.idColumn = idColumn;
    }

    public void setPasswordColumn(String passwordColumn) {
        this.passwordColumn = passwordColumn;
    }

    public void setDatasource(DataSource datasource) {
        this.datasource = datasource;
    }

    @Autowired
    IUserFactory userFactory;

    @Autowired
    String url;
    @Autowired
    String driver;
    @Autowired
    String username;
    @Autowired
    String password;

    @Autowired
    String query;
    @Autowired
    String numberColumn;
    @Autowired
    String idColumn;
    @Autowired
    String passwordColumn;
    DataSource datasource;
    public UserDataAccess() throws SQLException {
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
