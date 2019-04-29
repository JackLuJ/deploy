package com.jackluan.deploy.util;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Aspect
@Component
public class TransactionAop {

    @Autowired
    DataSource dataSource;

    @Pointcut("@annotation(com.jackluan.deploy.util.DBTransaction)")
    public void dbTransaction() {
    }

    @Before(value = "dbTransaction()")
    public void before(JoinPoint joinPoint) {
        Connection con = null;
        Object obj = joinPoint.getThis();
        System.out.println(obj.getClass());
        synchronized (obj) {
            try {
                con = ConnectionFactory.getConnection();
                if (con == null) {
                    System.out.println("------- get Connection -------");
                    con = dataSource.getConnection();
                    con.setAutoCommit(false);
                    ConnectionFactory.setConnection(con);
                }
            } catch (SQLException e) {
                if (con != null) {
                    try {
                        con.close();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }finally {
                        ConnectionFactory.clean();
                    }
                }
            }
        }
    }

    @AfterThrowing(value = "dbTransaction()", throwing = "exception")
    public void error(JoinPoint joinPoint) {
        Connection con = ConnectionFactory.getConnection();
        if (con != null) {
            System.out.println("------- rollback -------");
            try {
                con.rollback();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                ConnectionFactory.clean();
            }
        }
    }

    @After(value = "dbTransaction()")
    public void after(JoinPoint joinPoint) {
        Connection con = ConnectionFactory.getConnection();
        if (con != null) {
            System.out.println("------- commit and close -------");
            try {
                con.commit();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                ConnectionFactory.clean();
            }

        }
    }
}
