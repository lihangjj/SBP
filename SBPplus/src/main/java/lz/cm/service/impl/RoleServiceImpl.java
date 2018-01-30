package lz.cm.service.impl;

import lz.cm.dao.IMemberDAO;
import lz.cm.dao.IRoleDAO;
import lz.cm.service.IRoleService;
import lz.cm.vo.Action;
import lz.cm.vo.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RoleServiceImpl implements IRoleService {
    @Autowired
    private IRoleDAO roleDAO;
    @Autowired
    private IMemberDAO memberDAO;

    @Override
    public boolean edit(Role action) throws Exception {
        return roleDAO.doUpdate(action);
    }

    @Override
    public boolean add(Role action) throws Exception {
        return roleDAO.doCreate(action);
    }

    @Override
    public List<Action> getActionsByRoleId(int roleid) throws Exception {
        return roleDAO.getAllActionsByRoleId(roleid);
    }

    @Override
    public boolean plAddRoleToMembers(String[] roleids, String[] memberids) throws Exception {
        Set<String> rids = new HashSet<>();
        Set<String> mids = new HashSet<>();
        Collections.addAll(rids, roleids);
        Collections.addAll(mids, memberids);
        Iterator<String> riter = rids.iterator();
        Iterator<String> miter = mids.iterator();
        int x = 0;//member的成功数
        Map<String, Object> paraMap = new HashMap<>();
        while (miter.hasNext()) {
            String mid = miter.next();
            paraMap.put("memberid", mid);

            //得到每个用户对应的拥有的角色id
            List<Integer> allRoleids = memberDAO.getAllRoleIdByMemberId(mid);
            while (riter.hasNext()) {//剔除用户本来就存在的角色
                String roleid = riter.next();//要删除的角色
                for (Integer n : allRoleids) {
                    if (n == Integer.valueOf(roleid)) {//该用户已经有此角色
                        riter.remove();
                        break;//继续判断下一个增加的角色
                    }
                }
            }
            int y = 0;//每个member的角色增加成功数
            for (String r : rids) {
                paraMap.put("roleid", r);
                if (memberDAO.addRoleToMember(paraMap)) {
                    y++;
                }
            }
            if (y == rids.size()) {
                x++;
            }
            //第个用户的时候，将角色集合重新回到初始状态，重新判断
            Collections.addAll(rids, roleids);
            riter=rids.iterator();

        }
        return x == mids.size();
    }

    @Override
    public boolean plRemoveRoleFromMembers(String[] roleids, String[] memberids) throws Exception {
        Set<String> rids = new HashSet<>();
        Set<String> mids = new HashSet<>();
        Collections.addAll(rids, roleids);//将字符串数组变为Set集合，还能去重
        Collections.addAll(mids, memberids);
        Iterator<String> riter = rids.iterator();
        Iterator<String> miter = mids.iterator();
        int x = 0;//member的成功数
        Map<String, Object> paraMap = new HashMap<>();
        List<Integer> deleteList=new ArrayList<>();
        while (miter.hasNext()) {
            String mid = miter.next();
            paraMap.put("memberid", mid);

            //得到每个用户对应的拥有的角色id
            List<Integer> allRoleids = memberDAO.getAllRoleIdByMemberId(mid);
            while (riter.hasNext()) {//剔除用户本来就存在的角色
                String roleid = riter.next();//要删除的角色
                for (Integer n : allRoleids) {
                    if (n == Integer.valueOf(roleid)) {//该用户有此角色，将角色增加到待删除的集合
                        deleteList.add(n);
                        break;//后面的肯定是不相等的，没必要再判断了，直接进行第二个要删除的角色判断，是否用户真的有这个角色，没这个角色就不用删除
                    }
                }
            }
            int y = 0;//每个member的角色增加成功数
            for (Integer r : deleteList) {
                paraMap.put("roleid", r);
                if (memberDAO.removeRoleFromMember(paraMap)) {
                    y++;
                }
            }
            if (y == deleteList.size()) {//用户要删除的角色等于删除角色的集合长度，就完成一个用户
                x++;
            }
            //清空要删除的集合角色，开始第二个人的角色删除
            deleteList.clear();
            //iter已经迭代到了最后，要重置iter
            riter=rids.iterator();
        }
        return x == mids.size();


    }

    @Override
    public Role getVoById(Integer id) {
        return roleDAO.findById(id, Role.class);
    }

    @Override
    public Map<String, Object> splitVoByFlag(String column, String keyWord, Integer currentPage, Integer lineSize, String parameterName, String parameterValue) throws Exception {
        Map<String, Object> resMap = roleDAO.splitVoByFlag(Role.class, column, keyWord, currentPage, lineSize, getCondition(parameterName,"=",parameterValue));
        List<Role> roles = (List<Role>) resMap.get("allVo");
        System.err.println(roles.size());
        for (Role r : roles) {
            r.setActions(roleDAO.getAllActionsByRoleId(r.getRoleid()));
        }
        resMap.put("allVo", roles);
        return resMap;
    }
}
