package lz.cm.dao;

import lz.cm.vo.Action;
import lz.cm.vo.Member;
import lz.cm.vo.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface IMemberDAO extends IDAO<String, Member> {
    boolean doCreate(Member vo) throws Exception;


    List<Role> getAllMemberRoles(String memberid);

    List<Action> getAllMemberActions(String memberid);

    Member login(Member member);

    boolean updateEflag(Member member);

    List<Action> getAllActionsByRoleId(Integer roleid);

    boolean updateMember(Member member);

    boolean doUpdateSflag(Map<String, Object> map);

    boolean doUpdateSflagBatch(Map<String, Object> map);

    List<Member> splitVoByFlag(Map<String, Object> map);

    Integer splitVoByFlagCount(Map<String, Object> map);

    boolean updateStyle(Member member);

    boolean updateSysFont(Member member);

    List<Integer> getAllRoleIdByMemberId(String memberid);

    boolean addRoleToMember(Map<String, Object> map);

    boolean removeRoleFromMember(Map<String, Object> map);
}
