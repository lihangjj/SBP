package lz.cm.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public abstract class AbstractDAO implements IMemberDAO{
    protected  PreparedStatement pre;
    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    public SqlSessionFactory getSqlSessionFactory() {
        return this.sqlSessionFactory;
    }

    public SqlSession getSession() {
        return this.sqlSessionFactory.openSession();
    }

    public Connection getConnection() {

        return getSession().getConnection();
    }

    public  void testJdbc() throws Exception {
        pre = getConnection().prepareStatement("SELECT *FROM member");

        ResultSet resultSet = pre.executeQuery();
        while (resultSet.next()) {
            String s = resultSet.getString(1);
            System.err.println(s);
        }
    }

}
