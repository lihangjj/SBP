package qlm.cm.controller.pages.back.member;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import qlm.cm.controller.AbstractController;

@Controller
@RequestMapping("/pages/back/member")
public class MemberController extends AbstractController {

    @RequestMapping("addPre")
    String addPre() {

        return "pages/back/member/member_addPre";
    }

    @RequestMapping("add")
    @RequiresPermissions("member:add")
    String addP() {

        return "pages/back/member/member_add";
    }

}
