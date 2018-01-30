package lz.cm.service;

import java.util.HashMap;
import java.util.Map;

public interface IService<K, V> {


    V getVoById(K id);

    /**
     * 根据参数标记分页
     *
     * @param column
     * @param keyWord
     * @param currentPage
     * @param lineSize
     * @param parameterName
     * @param parameterValue
     * @return
     * @throws Exception
     */
    Map<String, Object> splitVoByFlag(String column, String keyWord, Integer currentPage, Integer lineSize, String parameterName, String parameterValue) throws Exception;


    /**
     * 得到分页参数的map
     *
     * @param column
     * @param keyWord
     * @param currentPage
     * @param lineSize
     * @param parameterName
     * @param parameterValue
     * @return
     */
    default Map<String, Object> getParameterMap(String column, String keyWord, Integer currentPage, Integer lineSize, String parameterName, String parameterValue) {

        Map<String, Object> parameterMap = new HashMap<>();
        if (keyWord == null || "".equals(keyWord)) {
            parameterMap.put("keyWord", null);
        } else {
            parameterMap.put("keyWord", "%" + keyWord + "%");//这里要加百分号
            parameterMap.put("column", column);
        }
        if (parameterValue == null || "".equals(parameterValue)) {
            parameterMap.put("parameterValue", null);
        } else {
            parameterMap.put("parameterName", parameterName);
            parameterMap.put("parameterValue", parameterValue);
        }
        parameterMap.put("start", (currentPage - 1) * lineSize);
        parameterMap.put("lineSize", lineSize);
        return parameterMap;
    }

    /**
     * 得到是否真的有额外sql条件
     *
     * @param parameterName
     * @param condition
     * @param parameterValue
     * @return
     */
    default String getCondition(String parameterName, String condition, String parameterValue) {
        if (parameterName == null || "".equals(parameterName) || parameterValue == null || "".equals(parameterValue)) {
            return "";
        } else {
            if (condition == null || "".equals(condition)) {
                condition = "=";
            }
            return parameterName + condition + parameterValue;
        }
    }
}
