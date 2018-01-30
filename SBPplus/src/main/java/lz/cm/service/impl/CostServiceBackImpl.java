package lz.cm.service.impl;

import lz.cm.dao.ICostDAO;
import lz.cm.dao.IMemberDAO;
import lz.cm.service.AbstractService;
import lz.cm.service.ICostServiceBack;
import lz.cm.vo.Cost;
import lz.cm.vo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CostServiceBackImpl extends AbstractService implements ICostServiceBack {
    @Autowired
    private ICostDAO costDAO;
    @Autowired
    private IMemberDAO memberDAO;

    @Override
    public boolean addCost(Cost cost) throws Exception {
        return costDAO.doCreate(cost);
    }

    @Override
    public boolean updateDflag(int dflag, String[] ids) throws Exception {
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("dflag", dflag);
        parameterMap.put("ids", ids);
        return costDAO.updateDflag(parameterMap);
    }

    @Override
    public boolean updateBxflag(int bxflag, String[] ids) throws Exception {
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("bxflag", bxflag);
        parameterMap.put("ids", ids);
        return costDAO.updateBxflag(parameterMap);
    }

    @Override
    public boolean edit(Cost cost) throws Exception {
        return costDAO.doUpdate(cost);
    }

    @Override
    public Map<String, Object> selectMyCost(String mid, String column, String keyWord, Integer currentPage, Integer lineSize, String parameterName, String parameterValue) throws Exception {
        String c1 = getCondition(parameterName, "=", parameterValue);
        String c2 = "".equals(c1) ? " memberid='" + mid + "'" : " AND" + " memberid='" + mid + "'";
        Map<String, Object> resMap = costDAO.splitVoByFlag(Cost.class, column, keyWord, currentPage, lineSize, c1 + c2);
        List<Cost> allCost = (List<Cost>) resMap.get("allVo");
        double allCostMoney=0;
        for (Cost c : allCost) {//需要给每个Cost设置对应的人员
            c.setMember(memberDAO.findById(c.getMemberid()));
            allCostMoney+=c.getCost();
        }
        resMap.put("allVo", allCost);
        resMap.put("allCostMoney", allCostMoney);
        return resMap;
    }

    @Override
    public double getAllCost() throws Exception {
        return costDAO.getAllCost();
    }

    @Override
    public double getAllCostByMember(String memberid) throws Exception {
        return costDAO.getAllCostByMember(memberid);
    }

    @Override
    public Cost getVoById(Integer integer) {
        try {
            return costDAO.findById(integer, Cost.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Map<String, Object> splitVoByFlag(String column, String keyWord, Integer currentPage, Integer lineSize, String parameterName, String parameterValue) throws Exception {
        Map<String, Object> resMap = costDAO.splitVoByFlag(Cost.class, column, keyWord, currentPage, lineSize, getCondition(parameterName, "=", parameterValue));
        double allCostMoney = 0;
        List<Cost> allCost = (List<Cost>) resMap.get("allVo");
        for (Cost c : allCost) {//需要给每个Cost设置对应的人员
            c.setMember(memberDAO.findById(c.getMemberid()));
            allCostMoney += c.getCost();
        }
        resMap.put("allVo", allCost);
        resMap.put("allCostMoney", allCostMoney);
        return resMap;
    }
}
