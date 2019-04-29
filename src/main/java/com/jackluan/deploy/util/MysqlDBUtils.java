package com.jackluan.deploy.util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

public class MysqlDBUtils implements DBUtils {
    @Override
    public List<Map<String, Object>> queryList(String sql) throws SQLException {
        Statement st = null;
        ResultSet rs = null;
        List<Map<String, Object>> result = null;
        try {
            st = getStatement();
            rs = st.executeQuery(sql);
            result = DataBuildUtils.data2MapList(rs);
        } finally {
            if (st != null) st.close();
            if (rs != null) rs.close();
        }
        return result;
    }


    @Override
    public List<Map<String, Object>> queryListPrepare(String sql, Object... values) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Map<String, Object>> result = null;
        try {
            ps = getPrepareStatement(sql);
            int sum = values != null ? values.length : 0;
            for (int i = 0; i < sum; i++) {
                ps.setObject(i + 1, values[i]);
            }
            rs = ps.executeQuery();
            result = DataBuildUtils.data2MapList(rs);
        } finally {
            if (ps != null) ps.close();
            if (rs != null) rs.close();
        }
        return result;
    }

    @Override
    public Map<String, Object> queryOne(String sql) throws SQLException {
        Statement st = null;
        ResultSet rs = null;
        Map<String, Object> result = null;
        try {
            st = getStatement();
            rs = st.executeQuery(sql);
            result = DataBuildUtils.data2Map(rs);
        } finally {
            if (st != null) st.close();
            if (rs != null) rs.close();
        }
        return result;
    }

    @Override
    public Map<String, Object> queryOnePrepare(String sql, Object... values) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Map<String, Object> result = null;
        try {
            ps = getPrepareStatement(sql);
            int sum = values != null ? values.length : 0;
            for (int i = 0; i < sum; i++) {
                ps.setObject(i + 1, values[i]);
            }
            rs = ps.executeQuery();
            result = DataBuildUtils.data2Map(rs);
        } finally {
            if (ps != null) ps.close();
            if (rs != null) rs.close();
        }
        return result;
    }

    @Override
    public <T> List<T> queryObjList(String sql, Class<T> clazz) throws SQLException {
        Statement st = null;
        ResultSet rs = null;
        List<T> result = null;
        try {
            st = getStatement();
            rs = st.executeQuery(sql);
            result = DataBuildUtils.data2EntityList(rs, clazz);
        } finally {
            if (st != null) st.close();
            if (rs != null) rs.close();
        }
        return result;
    }

    @Override
    public <T> List<T> queryObjListPrepare(String sql, Class<T> clazz, Object... values) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<T> result = null;
        try {
            ps = getPrepareStatement(sql);
            int sum = values != null ? values.length : 0;
            for (int i = 0; i < sum; i++) {
                ps.setObject(i + 1, values[i]);
            }
            rs = ps.executeQuery();
            result = DataBuildUtils.data2EntityList(rs, clazz);
        } finally {
            if (ps != null) ps.close();
            if (rs != null) rs.close();
        }
        return result;
    }

    @Override
    public <T> T queryObject(String sql, Class<T> clazz) throws SQLException {
        Statement st = null;
        ResultSet rs = null;
        T result = null;
        try {
            st = getStatement();
            rs = st.executeQuery(sql);
            result = DataBuildUtils.data2Entity(rs, clazz);
        } finally {
            if (st != null) st.close();
            if (rs != null) rs.close();
        }
        return result;
    }

    @Override
    public <T> T queryObjectPrepare(String sql, Class<T> clazz, Object... values) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        T result = null;
        try {
            ps = getPrepareStatement(sql);
            int sum = values != null ? values.length : 0;
            for (int i = 0; i < sum; i++) {
                ps.setObject(i + 1, values[i]);
            }
            rs = ps.executeQuery();
            result = DataBuildUtils.data2Entity(rs, clazz);
        } finally {
            if (ps != null) ps.close();
            if (rs != null) rs.close();
        }
        return result;
    }

    @Override
    public int queryCount(String sql) throws SQLException {
        return 0;
    }

    @Override
    public int quertCountPrepare(String sql, Object... values) throws SQLException {
        return 0;
    }

    @Override
    public int update(String sql) throws SQLException {
        return 0;
    }

    @Override
    public int updatePrepare(String sql, Object... values) throws SQLException {
        return 0;
    }

    @Override
    public int insert(String sql) throws SQLException {
        return 0;
    }

    @Override
    public int insertPrepare(String sql, Object... values) throws SQLException {
        return 0;
    }

    @Override
    public int delete(String sql) throws SQLException {
        return 0;
    }

    @Override
    public int deletePrepare(String sql, Object... values) throws SQLException {
        return 0;
    }


    private Statement getStatement() throws SQLException {
        return ConnectionFactory.getConnection().createStatement();
    }

    private PreparedStatement getPrepareStatement(String sql) throws SQLException {
        return ConnectionFactory.getConnection().prepareStatement(sql);
    }
}
