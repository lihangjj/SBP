package qlm.cm.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import qlm.cm.vo.Client;

@Mapper
public interface IClientDAO extends IDAO<Integer, Client> {

}
