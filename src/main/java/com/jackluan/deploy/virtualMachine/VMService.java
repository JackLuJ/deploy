package com.jackluan.deploy.virtualMachine;

import com.jackluan.deploy.util.ConnectionFactory;
import com.jackluan.deploy.util.DBTransaction;
import com.jackluan.deploy.util.TransactionAop;
import com.jackluan.deploy.virtualMachine.entity.VMEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VMService {

    @Autowired
    VMMapper vmMapper;

    @Autowired
    DataSource dataSource;

    public void createVM(VMEntity vmEntity) {
        vmMapper.insert(vmEntity);
    }

    public VMEntity queryVM(String id) {
        return vmMapper.query(id).get(0);
    }

    @DBTransaction
    public List<VMEntity> queryVMByJdbc() {
        Connection con = null;
        ResultSet rs = null;
        List<VMEntity> list = new ArrayList<>();
        try {
            con = ConnectionFactory.getConnection();
            Statement statement = con.createStatement();
            String sql = "select id, ip, port, status from virtual_machine";
            rs = statement.executeQuery(sql);
            while (rs.next()) {
                ResultSetMetaData rsmd = rs.getMetaData();
                list.add(rs2Map(VMEntity.class, rs, rsmd));
            }
        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    private <T> T rs2Map(Class<T> clazz, ResultSet rs, ResultSetMetaData rsmd) {
        T obj = null;
        try {
            System.out.println("clazz:" + clazz.getName());
            Field[] fList = clazz.getDeclaredFields();
            System.out.println("fList:" + fList.length);
            Map<String, String> map = field2UpperCase(fList);
            System.out.println("map:" + map);
            obj = clazz.getDeclaredConstructor().newInstance();
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                String columnLabel = rsmd.getColumnLabel(i);
                String methodName = map.get(columnLabel.toUpperCase());
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
        } catch (SQLException e) {
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

    private Map<String, String> field2UpperCase(Field[] fList) {
        if (fList == null || fList.length == 0) return null;
        Map<String, String> map = new HashMap<>();
        for (Field f : fList) {
            String methodName = "set" + upperCaseFirst(f.getName());
            map.put(f.getName().toUpperCase(), methodName);
        }
        return map;
    }

    private String upperCaseFirst(String s) {
        char[] ch = s.toCharArray();
        if (ch[0] > 'a' && ch[0] < 'z') {
            ch[0] = (char) (ch[0] - 32);
            return new String(ch);
        }
        return s;
    }

    private <T> T getResult(ResultSet rs, Class<T> clazz, String columnLabel) throws SQLException{
        if (clazz.getName().equals("java.lang.String")){
            System.out.println(rs.getString(columnLabel));
            return (T) rs.getString(columnLabel);
        }else if (clazz.getName().equals("java.lang.Integer")){
            System.out.println(rs.getInt(columnLabel));
            return (T) Integer.valueOf(rs.getInt(columnLabel));
        }else{
            throw new RuntimeException("dbType to JavaType not fetch. type:"+clazz.getName());
        }
    }
}
