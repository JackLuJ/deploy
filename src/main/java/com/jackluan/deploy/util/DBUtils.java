package com.jackluan.deploy.util;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface DBUtils {

    List<Map<String, Object>> queryList(String sql) throws SQLException;

    List<Map<String, Object>> queryListPrepare(String sql, Object... values) throws SQLException;

    Map<String, Object> queryOne(String sql) throws SQLException;

    Map<String, Object> queryOnePrepare(String sql, Object... values) throws SQLException;

    <T> List<T> queryObjList(String sql, Class<T> clazz) throws SQLException;

    <T> List<T> queryObjListPrepare(String sql, Class<T> clazz, Object... values) throws SQLException;

    <T> T queryObject(String sql, Class<T> clazz) throws SQLException;

    <T> T queryObjectPrepare(String sql, Class<T> clazz, Object... values) throws SQLException;

    int queryCount(String sql) throws SQLException;

    int quertCountPrepare(String sql, Object... values) throws SQLException;

    int update(String sql) throws SQLException;

    int updatePrepare(String sql, Object... values) throws SQLException;

    int insert(String sql) throws SQLException;

    int insertPrepare(String sql, Object... values) throws SQLException;

    int delete(String sql) throws SQLException;

    int deletePrepare(String sql, Object... values) throws SQLException;
}
