package com.jackluan.deploy.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataBuildUtils {

    public static List<Map<String, Object>> data2MapList(ResultSet rs) throws SQLException {
        List<Map<String, Object>> list = new ArrayList<>();
        ResultSetMetaData rsdm = rs.getMetaData();
        int columnCount = rsdm.getColumnCount();
        while (rs.next()) {
            list.add(rs2Map(rs, rsdm, columnCount));
        }
        return list;
    }

    public static Map<String, Object> data2Map(ResultSet rs) throws SQLException {
        ResultSetMetaData rsdm = rs.getMetaData();
        int columnCount = rsdm.getColumnCount();
        while (rs.next()) {
            return rs2Map(rs, rsdm, columnCount);
        }
        return null;
    }

    public static <T> List<T> data2EntityList(ResultSet rs, Class<T> clazz) throws SQLException {
        List<T> list = null;
        Map<String, String> filedMethodMap = null;
        if (rs.next()) {
            Field[] fList = clazz.getDeclaredFields();
            filedMethodMap = field2UpperCase(fList);
            rs.previous();
        } else {
            return list;
        }
        while (rs.next()) {
            list.add(rs2Entity(clazz, rs, filedMethodMap));
        }
        return list;
    }

    public static <T> T data2Entity(ResultSet rs, Class<T> clazz) throws SQLException {
        if (rs.next()) {
            Field[] fList = clazz.getDeclaredFields();
            Map<String, String> filedMethodMap = field2UpperCase(fList);
            return rs2Entity(clazz, rs, filedMethodMap);
        } else {
            return null;
        }
    }

    private static Map<String, Object> rs2Map(ResultSet rs, ResultSetMetaData rsdm, int columnCount) throws SQLException {
        Map<String, Object> map = new HashMap<>();
        for (int i = 1; i <= columnCount; i++) {
            String key = rsdm.getColumnLabel(i);
            Object obj = rs.getObject(key);
            if (obj == null) continue;
            map.put(key, obj);
        }
        return map;
    }

    private static <T> T rs2Entity(Class<T> clazz, ResultSet rs, Map<String, String> filedMethodMap) throws SQLException {
        T obj = null;
        ResultSetMetaData rsmd = rs.getMetaData();
        try {
            obj = clazz.getDeclaredConstructor().newInstance();
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                String columnLabel = rsmd.getColumnLabel(i);
                String methodName = filedMethodMap.get(columnLabel.toUpperCase());
                Class dbTypeClass = Class.forName(rsmd.getColumnClassName(i));
                if (methodName == null)
                    throw new RuntimeException("columnLabel can not fetch Java bean className:" + clazz.getName() + "and columnLabel name:" + columnLabel);
                Method m = clazz.getDeclaredMethod(methodName, dbTypeClass);
                m.invoke(obj, getResult(rs, dbTypeClass, columnLabel));
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return obj;
    }

    private static Map<String, String> field2UpperCase(Field[] fList) {
        if (fList == null || fList.length == 0) return null;
        Map<String, String> map = new HashMap<>();
        for (Field f : fList) {
            String methodName = "set" + CommonUtils.upperCaseFirst(f.getName());
            map.put(f.getName().toUpperCase(), methodName);
        }
        return map;
    }

    private static <T> T getResult(ResultSet rs, Class<T> clazz, String columnLabel) throws SQLException {
        if (clazz.getName().equals("java.lang.String")) {
            System.out.println(rs.getString(columnLabel));
            return (T) rs.getString(columnLabel);
        } else if (clazz.getName().equals("java.lang.Integer")) {
            System.out.println(rs.getInt(columnLabel));
            return (T) Integer.valueOf(rs.getInt(columnLabel));
        } else {
            throw new RuntimeException("dbType to JavaType not fetch. type:" + clazz.getName());
        }
    }
}
