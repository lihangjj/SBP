package lz.cm.dao;

import org.apache.ibatis.annotations.Mapper;
import lz.cm.vo.Client;

import java.util.Map;

@Mapper
public interface IClientDAO extends IDAO<Integer, Client> {
    boolean plUpdateDealFlag(Map<String, Object> map);

}
