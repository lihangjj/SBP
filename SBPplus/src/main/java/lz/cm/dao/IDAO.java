package lz.cm.dao;

import lz.util.SpringUtil;
import lz.util.str.StrUtil;

import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IDAO<K, V> {
    boolean doCreate(V vo) throws Exception;

    boolean doUpdate(V vo) throws Exception;


    V findById(K id);

    default V findById(K id, Class cls) {
        V vo;
        String tbName = cls.getSimpleName().toLowerCase();
        StringBuffer buffer = new StringBuffer("SELECT*FROM " + tbName + " WHERE " + tbName + "id=" + "'" + id + "'");
        Connection connection = SpringUtil.getConnection();

        try {
            PreparedStatement pre = connection.prepareStatement(buffer.toString());
            ResultSet res = pre.executeQuery();
            ResultSetMetaData rsmd = res.getMetaData();
            if (res.next()) {
                vo = (V) cls.getConstructor().newInstance();
                for (int x = 1; x <= rsmd.getColumnCount(); x++) {
                    String columnName = rsmd.getColumnName(x);
                    Method setM = cls.getMethod("set" + StrUtil.initCap(columnName), cls.getDeclaredField(columnName).getType());
                    setM.invoke(vo, res.getObject(x));
                }
                return vo;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                //链接关闭
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    /**
     * 根据某个标记分页
     *
     * @param T
     * @param column
     * @param keyWord
     * @param currentPage
     * @param lineSize
     * @param condition
     * @return
     * @throws Exception
     */
    default Map<String, Object> splitVoByFlag(Class T, String column, String keyWord, Integer currentPage, Integer lineSize, String condition) {
        //1.用来保存查询结果的map,包含所有实体类和allRecorders

        //表名,需要将首字母小写，不然linux不认识会报错
        String tbName = T.getSimpleName().toLowerCase();
        //拼写sql
        StringBuffer buffer = new StringBuffer("SELECT*FROM " + tbName);
        //没有关键字
        boolean noK = keyWord == null || "".equals(keyWord);
        //没有参数值
        boolean noCondition = condition == null || "".equals(condition);
        if (!noK) {//此时有keyWord
            buffer.append(" WHERE ").append(column).append(" like ").append("'%" + keyWord + "%'");//这里需要加"'"
        }

        if (!noCondition) {//此时有额外条件字符串，在服务层拼接好！
            if (noK) {//此时没有keyWord
                buffer.append(" WHERE ");
            } else {
                buffer.append(" AND ");
            }
            buffer.append(condition);
        }
        if (!(currentPage==null||"".equals(currentPage)||lineSize==null||"".equals(lineSize))){//表示有分页限制，不然就全部查询
            buffer.append(" LIMIT " + (currentPage - 1) * lineSize + "," + lineSize);
        }
        String sql = buffer.toString();

//        Map<String, Object> cacheMap = (Map<String, Object>) CacheUtil.getCache(sql);
//        if (cacheMap != null) {//先看是否有缓存，有就直接返回该集合，暂时保存15秒，不开启缓存
//            return cacheMap;
//        }
        //开始数据库操作，最后必须关闭connection
        Connection connection = SpringUtil.getConnection();
        try {
            //这里的connection不能多实例，会卡死
            //先查询所有vo
            PreparedStatement pre = connection.prepareStatement(sql);
            ResultSet resultSet = pre.executeQuery();
            //获得表的结构，得到表字段的java类型，表字段的值
            ResultSetMetaData rsmd = resultSet.getMetaData();
            //得到一个保存VO的list
            List<V> allVo = new ArrayList<>();
            while (resultSet.next()) {
                //实例化vo
                V vo = (V) T.getConstructor().newInstance();
                for (int x = 1; x <= rsmd.getColumnCount(); x++) {
                    //反射获得成员类型
                    Class<?> type = T.getDeclaredField(rsmd.getColumnName(x)).getType();
                    //获得设置vo成员的方法，set+表字段开头大写，参数为Class.forName(type)
                    Method setM = T.getMethod("set" + StrUtil.initCap(rsmd.getColumnName(x)), type);
                    //获得表字段对应的值
                    //反射调用方法给vo成员赋值
                    setM.invoke(vo, resultSet.getObject(x));
                }
                //将一个赋值完毕后的vo添加到list集合
                allVo.add(vo);
            }
            Map<String, Object> map = new HashMap<>();
            //所有vo赋值完毕，添加到map集合
            map.put("allVo", allVo);
            //开始查询分页数量，改变sql语句，减掉limit,把*换成count(*)就行
            if (!(currentPage==null||"".equals(currentPage)||lineSize==null||"".equals(lineSize))){//表示有分页限制，不然就全部查询
                buffer.delete(buffer.lastIndexOf("LIMIT"), buffer.length());
            }
            buffer.replace(buffer.indexOf("*"), buffer.indexOf("*") + 1, " COUNT(*) ");
            pre = connection.prepareStatement(buffer.toString());
            resultSet = pre.executeQuery();
            while (resultSet.next()) {
                map.put("allRecorders", resultSet.getInt(1));
            }
            //        CacheUtil.setCache(sql,map);需要，可以缓存
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;

    }


}
