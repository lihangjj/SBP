package qlm.cm.dao;

import org.apache.ibatis.annotations.Mapper;
import qlm.cm.vo.Action;
import qlm.cm.vo.Member;
import qlm.cm.vo.Role;

import java.util.List;
import java.util.Map;

@Mapper
public interface IMemberDAO extends IDAO<String, Member> {
    boolean doCreate(Member vo) throws Exception;

    List<Member> findAllSplit(Map<String, Object> map);

    List<Role> getAllMemberRoles(String memberid);

    List<Action> getAllMemberActions(String memberid);

    Member login(Member member);

    boolean updateEflag(Member member);

    List<Action> getAllActionsByRoleId(Integer roleid);
}
