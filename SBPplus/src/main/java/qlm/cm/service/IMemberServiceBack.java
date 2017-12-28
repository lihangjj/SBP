package qlm.cm.service;


import qlm.cm.vo.Member;
import qlm.cm.vo.Role;

import java.util.List;
import java.util.Set;

public interface IMemberServiceBack {

    Member getMemberById(String mid);

    List<Role> getRolesAndActions(String mid);//得到全部角色，角色包含权限

    Member login(Member member) throws Exception;

    boolean editEflag(Member member) throws Exception;

    Set<String> getAllMemberRolesFlag(String mid) throws Exception;

    Set<String> getAllMemberActionsFlag(String mid) throws Exception;


}
