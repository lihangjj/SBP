package lz.cm.service.impl;

import lz.cm.dao.IActionDAO;
import lz.cm.dao.IRoleDAO;
import lz.cm.service.IActionService;
import lz.cm.vo.Action;
import lz.cm.vo.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ActionServiceImpl implements IActionService {
    @Autowired
    private IActionDAO actionDAO;
    @Autowired
    private IRoleDAO roleDAO;

    @Override
    public Action getVoById(Integer id) {
        return actionDAO.findById(id, Action.class);
    }

    @Override
    public Map<String, Object> splitVoByFlag(String column, String keyWord, Integer currentPage, Integer lineSize, String parameterName, String parameterValue) throws Exception {

        return actionDAO.splitVoByFlag(Action.class, column, keyWord, currentPage, lineSize, getCondition(parameterName,"=",parameterValue));
    }

    @Override
    public boolean edit(Action action) throws Exception {
        return actionDAO.doUpdate(action);
    }

    @Override
    public boolean add(Action action) throws Exception {
        return actionDAO.doCreate(action);
    }

    @Override
    public List<Role> getAllRoles() throws Exception {

        Map<String, Object> resMap = roleDAO.splitVoByFlag(Role.class, null, null, null, null, null);
        List<Role> allRole = (List<Role>) resMap.get("allVo");
        return allRole;
    }

    @Override
    public boolean actionsAddTo(String[] ids, int roleid) throws Exception {
        List<Integer> actionsId = roleDAO.getAllActionIdByRoleId(roleid);
        List<String> idsList = new ArrayList<>();
        Collections.addAll(idsList, ids);//把数组变成list
        System.err.println(idsList.size());
        Iterator<String> iterator = idsList.iterator();
        while (iterator.hasNext()) {
            String id = iterator.next();
            for (Integer acid : actionsId) {
                if (acid == Integer.valueOf(id)) {//如果查询出来的权限id已经存在，则移除要增加的权限id
                    iterator.remove();
                    break;
                }
            }
        }
        int x = 0;
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("roleid", roleid);
        for (String id : idsList) {
            parameterMap.put("actionid", Integer.valueOf(id));
            if (roleDAO.actionsAddTo(parameterMap)) {
                x += 1;
            }
        }
        return x == idsList.size();
    }

    @Override
    public boolean actionsRemoveFrom(String[] ids, int roleid) throws Exception {
        List<Integer> actionsId = roleDAO.getAllActionIdByRoleId(roleid);
        List<Integer> idsList = new ArrayList<>();
        System.err.println(idsList.size());
        for (String id : ids) {
            for (Integer acid : actionsId) {
                if (acid == Integer.valueOf(id)) {
                    idsList.add(acid);
                }

            }

        }
        int x = 0;
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("roleid", roleid);
        for (Integer id : idsList) {
            parameterMap.put("actionid", id);
            if (roleDAO.actionsRemoveFrom(parameterMap)) {
                x += 1;
            }
        }
        return x == idsList.size();
    }
}
