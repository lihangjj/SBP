package lz.cm.controller.pages.back.role;

import lz.cm.controller.AbstractController;
import lz.cm.service.IRoleService;
import lz.cm.vo.Action;
import lz.cm.vo.Role;
import lz.util.str.StrUtil;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/pages/back/role")
public class RoleController extends AbstractController {

    @Autowired
    private IRoleService roleService;


    @RequestMapping("add")
    @ResponseBody
    @RequiresRoles("roleAndAction")
    boolean add(Role role) {
        System.err.println(role);
        try {
            return roleService.add(role);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return false;
    }

    @RequestMapping("list")
    @RequiresRoles("roleAndAction")
    String list(HttpServletRequest request, Model model) throws Exception {
        String columnData = "标记:flag|名称:title|角色ID:roleid";
        setColumnMap(request, columnData);
        return "pages/back/role/role-list";
    }

    @RequestMapping("listAjax")
    @RequiresRoles("roleAndAction")
    @ResponseBody
    Map listAjax(HttpServletRequest request) {
        try {
            //反射调用分页方法
            Map<String, Object> map = handSplit(request, roleService);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @RequestMapping("edit")
    @RequiresRoles("roleAndAction")
    @ResponseBody
    boolean edit(Role role) {
        System.err.println(role);
        if (!getMid().equals(StrUtil.SUPER_ADMIN)) {//不是超级管理员，直接返回错
            return false;
        }
        try {
            return roleService.edit(role);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //批量为用户增加角色
    @RequestMapping("plAddRoleToMembersOrRemove")
    @RequiresRoles("roleAndAction")
    @ResponseBody
    boolean plAddRoleToMembers(HttpServletRequest request) {
        if (!getMid().equals(StrUtil.SUPER_ADMIN)) {//不是超级管理员，直接返回错
            return false;
        }
        String type = request.getParameter("type");
        String roleids = request.getParameter("roleids");
        String memberids = request.getParameter("memberids");
        String[] rids = roleids.split("-");
        String[] mids = memberids.split("-");

        if ("add".equals(type)) {
            try {
                return roleService.plAddRoleToMembers(rids, mids);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            try {
                return roleService.plRemoveRoleFromMembers(rids, mids);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


        return false;
    }

    @RequestMapping("getActionsByRoleId")
    @ResponseBody
    List<Action> getActionsByRoleId(Role role) {
        try {
            return roleService.getActionsByRoleId(role.getRoleid());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected String setUrl() {//分页的路径
        return "/pages/back/cost/list";
    }


}
