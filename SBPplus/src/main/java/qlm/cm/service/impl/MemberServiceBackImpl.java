package qlm.cm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qlm.cm.dao.IMemberDAO;
import qlm.cm.service.IMemberServiceBack;
import qlm.cm.vo.Action;
import qlm.cm.vo.Member;
import qlm.cm.vo.Role;

import java.util.*;

@Service
public class MemberServiceBackImpl implements IMemberServiceBack {

    @Autowired
    private IMemberDAO memberDAO;

    @Override
    public Member getMemberById(String mid) {
        try {
            return memberDAO.findById(mid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Role> getRolesAndActions(String mid){
        List<Role> allRoles = memberDAO.getAllMemberRoles(mid);//实体类

        for (Role x : allRoles) {
            x.setActions(memberDAO.getAllActionsByRoleId(x.getRoleid()));
        }
        return allRoles;
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
    public Set<String> getAllMemberRolesFlag(String mid) throws Exception {
        List<Role> allRolesV = memberDAO.getAllMemberRoles(mid);//实体类
        Set<String> allRoles = new HashSet<>();
        for (Role x : allRolesV) {
            allRoles.add(x.getFlag());
        }
        return allRoles;
    }

    @Override
    public Set<String> getAllMemberActionsFlag(String mid) throws Exception {
        List<Action> allActionsV = memberDAO.getAllMemberActions(mid);
        Set<String> allActions = new HashSet<>();

        for (Action x : allActionsV) {
            allActions.add(x.getFlag());
        }
        return allActions;
    }


}
