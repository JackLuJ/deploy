package com.jackluan.deploy.util;

import java.sql.Connection;

public class ConnectionFactory {

    private static ThreadLocal<Connection> localConnection = new ThreadLocal<>();

    public static void setConnection(Connection con){
        localConnection.set(con);
    }

    public static Connection getConnection(){
        return localConnection.get();
    }

    public static void clean(){
        localConnection.remove();
    }
}
