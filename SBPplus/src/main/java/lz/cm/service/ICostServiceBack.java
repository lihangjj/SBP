package lz.cm.service;

import lz.cm.vo.Cost;

import java.util.Map;

public interface ICostServiceBack extends IService<Integer, Cost> {
    boolean addCost(Cost cost) throws Exception;

    boolean updateDflag(int dflag, String[] ids) throws Exception;

    boolean updateBxflag(int bxflag, String[] ids) throws Exception;

    boolean edit(Cost cost) throws Exception;

    Map<String, Object> selectMyCost(String mid, String column, String keyWord, Integer currentPage, Integer lineSize, String parameterName, String parameterValue) throws Exception;

    double getAllCost() throws Exception;
    //查询某个人的话费总额
    double getAllCostByMember(String memberid) throws Exception;
}
