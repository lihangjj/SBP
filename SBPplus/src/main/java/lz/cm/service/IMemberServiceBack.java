package lz.cm.service;


import lz.cm.vo.Member;
import lz.cm.vo.Role;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

public interface IMemberServiceBack extends IService<String, Member> {

    Member getMemberById(String mid);

    List<Role> getRolesAndActions(String mid)throws Exception;//得到全部角色，角色包含权限

    Member login(Member member) throws Exception;

    boolean editEflag(Member member) throws Exception;

    Set<String> getAllMemberRolesFlag(String mid) throws Exception;

    Set<String> getAllMemberActionsFlag(String mid) throws Exception;

    boolean editMember(Member member) throws Exception;
    @Transactional(propagation = Propagation.REQUIRED)
    boolean addMember(Member member) throws Exception;

    boolean editSflag(String memberid, int sflag) throws Exception;

    boolean plSuoOrJie(int sflag, String[] ids) throws Exception;

    boolean editStyle(Member member) throws Exception;
    boolean editFontStyle(Member member) throws Exception;

}
