package qlm.cm.service.impl;

import qlm.cm.dao.IMemberDAO;
import qlm.cm.service.IMemberServiceBack;
import qlm.cm.vo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class MemberServiceBackImpl implements IMemberServiceBack {

    @Autowired
    private IMemberDAO memberDAO;

    @Override
    public Member getMemberById(String mid) throws Exception {
        return memberDAO.findById(mid);
    }

    @Override
    public Map<String, Set<String>> getRolesAndActions(String mid) throws Exception {
        Map<String, Set<String>> map = new HashMap<>();
        Set<String> allRoles = memberDAO.getAllMemberRoles(mid);
        Set<String> allActions = memberDAO.getAllMemberActions(mid);
        map.put("allRoles", allRoles);
        map.put("allActions", allActions);
        return map;
    }

    @Override
    public Member login(Member member) throws Exception {

        return memberDAO.login(member);
    }

    @Override
    public boolean editEflag(Member member) throws Exception {
        return memberDAO.updateEflag(member);
    }

    @Override
    public Set<String> getAllMemberRoles(String mid) throws Exception {
        return memberDAO.getAllMemberRoles(mid);
    }

    @Override
    public Set<String> getAllMemberActions(String mid) throws Exception {
        return memberDAO.getAllMemberActions(mid);
    }


}
