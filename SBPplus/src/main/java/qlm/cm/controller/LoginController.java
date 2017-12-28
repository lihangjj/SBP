package qlm.cm.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import qlm.cm.service.IMemberServiceBack;
import qlm.cm.vo.Member;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController extends AbstractController {
    @Autowired
    private IMemberServiceBack memberServiceBack;

    @RequestMapping("/pages/back/loginSuccess")
    String loginSuccess() {
        return "pages/back/index";
    }

    //执行登陆检测
    @RequestMapping("/login")
    String login(Model model, Member member, HttpServletRequest request) {

        //这里表示验证通过
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

                    } else {//如果验证码不为空
                        String rand = (String) request.getSession().getAttribute("rand");
                        if (!code.equalsIgnoreCase(rand)) {
                            setMsg("validate.rand", model);
                            model.addAttribute("showCode", true);//错误出现4次之后，启用验证框
                        } else {//验证码也正确，验证密码，shiro验证密码

                            if (member.getPassword().equals(member1.getPassword())) {//密码正确
                                Subject subject = SecurityUtils.getSubject();
                                UsernamePasswordToken token = new UsernamePasswordToken(member.getMemberid(), member.getPassword());
                                subject.login(token);

                                //验证密码通过之后，要将eflag变成0
                                member1.setEflag(0);
                                memberServiceBack.editEflag(member1);

                                return "pages/back/index";
                            } else {
                                setMsg("login.failure", model);
                                model.addAttribute("showCode", true);//错误出现4次之后，启用验证框
                            }
                        }
                    }
                } else {//错误还小于5次
                    if (member.getPassword().equals(member1.getPassword())) {
                        Subject subject = SecurityUtils.getSubject();
                        UsernamePasswordToken token = new UsernamePasswordToken(member.getMemberid(), member.getPassword());
                        subject.login(token);

                        return "pages/back/index";
                    } else {
                        member1.setEflag(eflag + 1);
                        memberServiceBack.editEflag(member1);//更新数据库错误次数
                        setMsg("login.failure", model);
                        if (eflag > 3) {
                            model.addAttribute("showCode", true);//错误出现4次之后，启用验证框
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "login_Page";
    }

    //去首页
    @RequestMapping("/")
    String index() {
        return "index";
    }
    //去服务页
    @RequestMapping("/service")
    String service() {
        return "pages/front/service";
    }
    //去登录页
    @RequestMapping("/loginPage")
    String loginUrl(HttpServletRequest request, Model model) {
        return "login_Page";
    }

    //拦截后去提示页面
    @RequestMapping("/interceptor_error")
    String interceptor_error() {//拦截器跳转路径，用这个才能跳转到页面，跟index是一样的
        return "interceptor_error";
    }

    //授权错误后执行此方法
    @RequestMapping("/unauthUrl")
    String unauthUrl() {
        return "unauthPage";
    }


}
