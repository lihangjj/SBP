package lz.cm.controller.pages.back.action;

import lz.cm.controller.AbstractController;
import lz.cm.service.IActionService;
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
@RequestMapping("/pages/back/action")
public class ActionController extends AbstractController {
    @Autowired
    private IActionService actionService;


    @RequestMapping("add")
    @ResponseBody
    @RequiresRoles("roleAndAction")
    boolean add(Action action) {


        System.err.println(action);
        action.setSflag((action.getSflag() == null || "".equals(action.getSflag())) ? 0 : 1);
        try {
            return actionService.add(action);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @RequestMapping("actionsAddTo")
    @RequiresRoles("roleAndAction")
    @ResponseBody
    boolean actionsAddTo(HttpServletRequest request) {
        String[] ids = request.getParameter("ids").split("-");
        int roleid = Integer.parseInt(request.getParameter("roleid"));
        try {
            return actionService.actionsAddTo(ids, roleid);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @RequestMapping("actionsRemoveFrom")
    @RequiresRoles("roleAndAction")
    @ResponseBody
    boolean actionsRemoveFrom(HttpServletRequest request) {
        String[] ids = request.getParameter("ids").split("-");
        int roleid = Integer.parseInt(request.getParameter("roleid"));
        try {
            return actionService.actionsRemoveFrom(ids, roleid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    @RequiresRoles("roleAndAction")
    @RequestMapping("list")
    String list(HttpServletRequest request, Model model) throws Exception {
        String columnData = "标记:flag|权限ID:actionid|名称:title|路径:cost|sflag:sflag";
        setColumnMap(request, columnData);
        List<Role> allRole = actionService.getAllRoles();
        System.err.println(allRole.size());
        model.addAttribute("allRole", allRole);
        return "pages/back/action/action-list";
    }
    @RequiresRoles("roleAndAction")
    @RequestMapping("listAjax")
    @ResponseBody
    Map listAjax(HttpServletRequest request) {
        try {
            //反射调用分页方法
            Map<String, Object> map = handSplit(request, actionService);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @RequestMapping("edit")
    @ResponseBody
    @RequiresRoles("roleAndAction")
    boolean edit(Action action, HttpServletRequest request) {
        if (!getMid().equals(StrUtil.SUPER_ADMIN)) {//不是超级管理员，直接返回错
            return false;
        }
        String roleid = request.getParameter("roleid");
        System.err.println(roleid);
        try {
            return actionService.edit(action);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    protected String setUrl() {//分页的路径
        return "/pages/back/cost/list";
    }


}
