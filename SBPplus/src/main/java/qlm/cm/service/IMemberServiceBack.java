package qlm.cm.service;


import qlm.cm.vo.Member;

import java.util.Map;
import java.util.Set;

public interface IMemberServiceBack {

    Member getMemberById(String mid) throws Exception;

    Map<String, Set<String>> getRolesAndActions(String mid) throws Exception;

    Member login(Member member) throws Exception;

    boolean editEflag(Member member) throws Exception;

    Set<String> getAllMemberRoles(String mid) throws Exception;

    Set<String> getAllMemberActions(String mid) throws Exception;

}
