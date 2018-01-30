package lz.cm.service.impl;

import lz.cm.dao.IRoleDAO;
import lz.cm.service.AbstractService;
import lz.cm.service.IRoleService;
import lz.cm.service.IService;
import lz.util.str.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lz.cm.dao.IMemberDAO;
import lz.cm.service.IMemberServiceBack;
import lz.cm.vo.Action;
import lz.cm.vo.Member;
import lz.cm.vo.Role;

import java.util.*;

@Service
public class MemberServiceBackImpl extends AbstractService implements IMemberServiceBack {

    @Autowired
    private IMemberDAO memberDAO;
    @Autowired
    private IRoleService roleService;

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
    public List<Role> getRolesAndActions(String mid) {
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

    @Override
    public boolean editMember(Member member) throws Exception {
        return memberDAO.updateMember(member);
    }

    @Override
    public boolean addMember(Member member) throws Exception {
        String[] roleids= StrUtil.BASIC_ROLE.split("-");
        roleService.plAddRoleToMembers(roleids,new String[]{member.getMemberid()});
        return memberDAO.doCreate(member);
    }

    @Override
    public Member getVoById(String s) {
        return null;
    }

    @Override
    public Map<String, Object> splitVoByFlag(String column, String keyWord, Integer currentPage, Integer lineSize, String parameterName, String parameterValue) throws Exception {
//        Map<String, Object> resMap=new HashMap<>();
//        Map<String, Object> pMap=getParameterMap(column,keyWord,currentPage,lineSize,parameterName,parameterValue);
//         resMap.put("allVo",memberDAO.splitVoByFlag(pMap));
//         resMap.put("allRecorders",memberDAO.splitVoByFlagCount(pMap));
//        return resMap;
        return memberDAO.splitVoByFlag(Member.class, column, keyWord, currentPage, lineSize, getCondition(parameterName, "=", parameterValue));
    }


    @Override
    public boolean editSflag(String memberid, int sflag) throws Exception {
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("memberid", memberid);
        parameterMap.put("sflag", sflag);
        return memberDAO.doUpdateSflag(parameterMap);
    }

    @Override
    public boolean plSuoOrJie(int sflag, String[] ids) throws Exception {
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("sflag", sflag);
        parameterMap.put("ids", ids);

        return memberDAO.doUpdateSflagBatch(parameterMap);
    }

    @Override
    public boolean editStyle(Member member) throws Exception {
        return memberDAO.updateStyle(member);
    }

    @Override
    public boolean editFontStyle(Member member) throws Exception {
        return memberDAO.updateSysFont(member);
    }


}
