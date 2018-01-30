package lz.cm.dao;

import lz.cm.vo.Cost;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface ICostDAO extends IDAO<Integer, Cost> {
    boolean updateDflag(Map<String, Object> map);

    boolean updateBxflag(Map<String, Object> map);
    Double getAllCost();

    Double getAllCostByMember(String memberid);

}
