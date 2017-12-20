package qlm.cm.dao;

import org.apache.ibatis.annotations.Mapper;
import qlm.cm.vo.Member;

import java.util.List;
import java.util.Map;

@Mapper
public interface IMemberDAO extends IDAO<String, Member> {
    boolean doCreate(Member vo) throws Exception;

    List<Member> findAllSplit(Map<String, Object> map);
}