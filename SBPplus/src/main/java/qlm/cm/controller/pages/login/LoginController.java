package qlm.cm.controller.pages.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import qlm.cm.controller.AbstractController;
import qlm.cm.service.IMemberServiceBack;
import qlm.cm.vo.Member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/pages")
public class LoginController extends AbstractController {
    @Autowired
    private IMemberServiceBack memberServiceBack;
    @RequestMapping("/login")
    String login(Model model, Member member, HttpServletRequest request, HttpServletResponse response) {

        System.err.println(member);
        try {
            Member member1 = memberServiceBack.getMemberById(member.getMemberid());
            if (member1 == null) {
                setMsg("login.failure", model);
            } else {
                int eflag = member1.getEflag() == null ? 0 : member1.getEflag();
                if (eflag > 4) {//如果错误次数大于4次，启用验证码

                    String code = request.getParameter("code");
                    if (code == null || "".equals(code)) {
                        setMsg("code.null", model);
                        model.addAttribute("showCode", true);//错误出现4次之后，启用验证框
                        return "login";
                    } else {//如果验证码不为空
                        String rand = (String) request.getSession().getAttribute("rand");
                        if (!code.equalsIgnoreCase(rand)) {
                            setMsg("validate.rand", model);
                            model.addAttribute("showCode", true);//错误出现4次之后，启用验证框
                            return "login";
                        } else {//验证码也正确，验证密码
                            if (member.getPassword().equals(member1.getPassword())) {//密码正确
                                //验证密码通过之后，要将eflag变成0
                                    member1.setEflag(0);
                                    memberServiceBack.editEflag(member1);

                                return "pages/back/index";
                            } else {
                                setMsg("login.failure", model);
                                model.addAttribute("showCode", true);//错误出现4次之后，启用验证框
                                return "login";
                            }
                        }
                    }
                } else {//错误还小于5次
                    if (member.getPassword().equals(member1.getPassword())) {
                        if (eflag!=0){
                            member1.setEflag(0);
                            memberServiceBack.editEflag(member1);
                        }
                        return "pages/back/index";
                    } else {
                        member1.setEflag(eflag + 1);
                        memberServiceBack.editEflag(member1);//更新数据库错误次数
                        setMsg("login.failure", model);
                        if (eflag > 3) {
                            model.addAttribute("showCode", true);//错误出现4次之后，启用验证框
                        }
                        return "login";
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "login";
    }
}
