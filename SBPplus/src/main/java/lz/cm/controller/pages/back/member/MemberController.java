package lz.cm.controller.pages.back.member;

import lz.cm.controller.AbstractControllerAdapter;
import lz.cm.service.IMemberServiceBack;
import lz.cm.vo.Member;
import lz.cm.vo.Role;
import lz.util.str.StrUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/pages/back/member")
public class MemberController extends AbstractControllerAdapter {
    private Map<String, String> columnMap = new HashMap<>();

    @Autowired
    private IMemberServiceBack memberServiceBack;


    @RequestMapping("checkMemberid")
    @ResponseBody
    boolean checkMemberid(Member member) {

        return memberServiceBack.getMemberById(member.getMemberid()) == null;
    }

    @RequestMapping("addPre")
    String addPre() {
        return "pages/back/member/member_addPre";
    }

    @RequestMapping("add")
    @RequiresPermissions("member:add")
    String add(Model model, Member member, MultipartFile pic, HttpServletRequest request) {
        //MD5加密，暂时不加密
//        member.setPassword(EncryptUtil.getEncPwd(member.getPassword()));

        String photo = createFileName(pic);
        String bigphoto = createFileName(pic);
        member.setPhoto(photo);
        member.setBigphoto(bigphoto);
        member.setRegdate(new Date());
        member.setSflag(1);
        try {
            String title = "用户";
            if (memberServiceBack.addMember(member)) {
                setMsg("vo.add.success", title, true, model);
                if (!pic.isEmpty()) {
                    saveFile(photo, bigphoto, pic, request);
                }
            } else {
                setMsg("vo.add.failure", title, false, model);
            }
            System.err.println(member);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        return "redirect:/pages/back/member/addPre";到另外的controller
        return "pages/back/member/member_addPre";
    }


    @RequestMapping("editPre")
    String editPre(Member memberAjax, Model model) {
        String mid;
        if (memberAjax.getMemberid() != null) {//请求来自ajax
            mid = memberAjax.getMemberid();
            if (StrUtil.SUPER_ADMIN.equals(mid)) {//如果要修改的是超级管理员
                if (!StrUtil.SUPER_ADMIN.equals(getMid())) {//如果当前用户不是超级管理员
                    setMsg("noPermission", false, model);
                    return "redirect:/pages/back/member/list?edit=n";
                }
            }

        } else {//修改自己
            mid = (String) SecurityUtils.getSubject().getSession().getAttribute("memberid");
        }
        Member member = memberServiceBack.getMemberById(mid);
        model.addAttribute(member);
        return "pages/back/member/member_editPre";
    }

    @RequestMapping("edit")
//修改用户
    String edit(Model model, MultipartFile pic, Member member, HttpServletRequest request) {
        //先加密再说，暂时不加密
//        member.setPassword(EncryptUtil.getEncPwd(member.getPassword()));

        Member oMember = memberServiceBack.getMemberById(member.getMemberid());

        if (StrUtil.SUPER_ADMIN.equals(member.getMemberid())) {//如果要修改的是超级管理员
            if (!StrUtil.SUPER_ADMIN.equals(getMid())) {//如果当前用户不是超级管理员
                setMsg("noPermission", false, model);
                return "pages/back/index";
            }
        }

        String title = "用户信息";
        if ((member.getPassword() == null
                || "".equals(member.getPassword())//密码为空
                || oMember.getPassword().equals(member.getPassword()))//密码跟原来设置成一样
                && pic.isEmpty()//没有上传图片
                && oMember.getName().equals(member.getName())//名字一样
                && oMember.getAge().equals(member.getAge())//年龄一样
                && oMember.getSex().equals(member.getSex())//性别一样
                && oMember.getPhone().equals(member.getPhone())) {//电话一样


            setMsg("vo.edit.success", title, true, model);
            if (member.getMemberid().equals(request.getSession().getAttribute("memberid"))) {
                return "pages/back/index";
            } else {
                return "redirect:/pages/back/member/list?edit=y";
            }

        }

        if (member.getPassword() == null || "".equals(member.getPassword())) {//如果没有设置密码
            member.setPassword(oMember.getPassword());//保留原来的密码
        }
        String photo = createFileName(pic);//创建一个照片名字
        String bigphoto = createFileName(pic);//创建一个大图照片名字
        if (!pic.isEmpty()) {//如果有照片上传
            member.setPhoto(photo);
            member.setBigphoto(bigphoto);
        } else {
            member.setPhoto(oMember.getPhoto());//没有照片就用原来的
            member.setBigphoto(oMember.getBigphoto());//没有照片就用原来的
        }
        try {

            if (memberServiceBack.editMember(member)) {
                if (!pic.isEmpty()) {//如果改变了照片
                    saveFile(photo, bigphoto, pic, request);
                    deleteFile(oMember.getPhoto(), oMember.getBigphoto(), request);
                }

                if (member.getMemberid().equals(SecurityUtils.getSubject().getSession().getAttribute("memberid"))) {//如果改变的是当前用户
                    SecurityUtils.getSubject().getSession().setAttribute("photo", member.getPhoto());//从新设置照片
                    SecurityUtils.getSubject().getSession().setAttribute("bigphoto", member.getBigphoto());//从新设置照片
                    if (!member.getName().equals(oMember.getName())) {//新名字不等于来的名字
                        SecurityUtils.getSubject().getSession().setAttribute("name", member.getName());//从新设置名字
                    }
                }
                setMsg("vo.edit.success", title, true, model);
            } else {
                setMsg("vo.edit.failure", title, false, model);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (member.getMemberid().equals(request.getSession().getAttribute("memberid"))) {
            return "pages/back/index";
        } else {

            return "redirect:/pages/back/member/list?edit=y";
        }

    }

    @RequestMapping("list")
//这只是简单的设置查询列，因为查询列是不会改变的，真正的列表是用ajax动态生成分页控制条和总数当前页等
    String list(Model model, HttpServletRequest request) throws Exception {
        String columnData = "用户名:memberid|姓名:name|电话:phone";
        setColumnMap(request, columnData);
        try {
            if ("y".equals(request.getParameter("edit"))) {
                setMsg("vo.edit.success", "用户信息", true, model);
            } else if ("n".equals(request.getParameter("edit"))) {
                setMsg("noPermission", false, model);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "pages/back/member/member_list";


    }

    @RequestMapping("listAjax")
    @ResponseBody
//真正的利用ajax分页
    Map listAjax(HttpServletRequest request) {
        try {
            //反射调用分页方法
            return handSplit(request, memberServiceBack);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("lockOrUnLock")
    @ResponseBody
    boolean lockOrUnLock(Member member, HttpServletRequest request) {
        try {
            int sflag = Integer.parseInt(request.getParameter("sflag"));
            if (StrUtil.SUPER_ADMIN.equals(member.getMemberid())) {//不能锁定超级管理员
                return false;
            }
            return memberServiceBack.editSflag(member.getMemberid(), sflag);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @RequestMapping("delete")
    @ResponseBody
    boolean delete(Member member) {
        try {
            if (StrUtil.SUPER_ADMIN.equals(member.getMemberid())) {//不能删除超级管理员
                return false;
            }
            return memberServiceBack.editSflag(member.getMemberid(), 2);//删除用户
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @RequestMapping("plSuoOrJie")
    @ResponseBody
    boolean plSuo(HttpServletRequest request) {
        String str = request.getParameter("data");

        String[] ids = str.split("\\|");
        for (int x = 0; x < ids.length; x++) {
            if (StrUtil.SUPER_ADMIN.equals(ids[x])) {//屏蔽超级管理员锁
                ids[x] = "";
            }
        }
        int sflag = Integer.parseInt(request.getParameter("sflag"));
        try {
            return memberServiceBack.plSuoOrJie(sflag, ids);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @RequestMapping("getRolesByMemberid")
    @ResponseBody
    List<Role> getRolesByMemberid(Member member) {
        System.err.println(member.getMemberid());
        try {
            return memberServiceBack.getRolesAndActions(member.getMemberid());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected String setUploadPath() {
        return "/upload/member/";
    }


    @Override
    protected String setUrl() {
        return null;
    }
}
