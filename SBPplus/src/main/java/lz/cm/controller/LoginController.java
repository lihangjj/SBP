package lz.cm.controller;

import lz.cm.service.IMemberServiceBack;
import lz.cm.vo.Member;
import lz.util.cookie.CookieUtil;
import lz.util.password.EncryptUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.Map;

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
    String login(Model model, Member member, HttpServletRequest request, HttpServletResponse response) {

        //这里表示验证通过
        try {
            Member member1 = memberServiceBack.getMemberById(member.getMemberid());
            if (member1 == null) {
                setMsg("login.failure", model);
            } else {

                if (member1.getSflag() == 2) {//看看用户是否已经被逻辑删除
                    setMsg("login.failure", model);
                    return "login_Page";
                }

                int eflag = member1.getEflag() == null ? 0 : member1.getEflag();
                if (eflag > 4) {//如果错误次数大于4次，启用验证码
                    model.addAttribute("showCode", true);//错误出现4次之后，启用验证框
                    String code = request.getParameter("code");
                    if (code == null || "".equals(code)) {//验证码为空
                        setMsg("code.null", model);
                        return "login_Page";
                    } else {//如果验证码不为空
                        String rand = (String) request.getSession().getAttribute("rand");
                        if (!code.equalsIgnoreCase(rand)) {//验证码不相等
                            setMsg("validate.rand", model);
                            return "login_Page";
                        } else {//验证码也正确，验证密码，shiro验证密码
                            //验证密码之前，先加密，暂时不加密
//                            member.setPassword(EncryptUtil.getEncPwd(member.getPassword()));

                            if (member1.getPassword().equals(member.getPassword())) {//密码正确后，
//                                判断是否被锁和shiro登陆
                                return loginAndAuthentication(model, member, member1, request, response);
                            } else {//密码错误
                                setMsg("login.failure", model);
                            }
                        }
                    }
                } else {//错误还小于5次
                    //验证密码之前，先加密，暂时不加密
//                    member.setPassword(EncryptUtil.getEncPwd(member.getPassword()));

                    if (member1.getPassword().equals(member.getPassword())) {//密码正确
                        return loginAndAuthentication(model, member, member1, request, response);
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

    private String loginAndAuthentication(Model model, Member member, Member member1, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (member1.getSflag() == 999) {//看看用户是否被锁
            setMsg("login.member.lock", model);
            return "login_Page";
        }
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(member.getMemberid(), member1.getPassword());
        subject.login(token);
        //验证密码通过之后，要将eflag变成0
        member1.setEflag(0);
        memberServiceBack.editEflag(member1);
        String rememberMe = request.getParameter("rememberMe");
        if ("true".equals(rememberMe)) {
            CookieUtil.save(response, member.getMemberid(), EncryptUtil.getMyEncPwd(member.getPassword()), 60 * 60 * 24 * 30);
        }
        return "pages/back/index";
    }

    //去首页
    @RequestMapping("/")
    String index() {
        return "index";
    }

    //去注册
    @RequestMapping("/regist")
    String regist() {
        return "pages/front/regist";
    }

    //去服务页
    @RequestMapping("/service")
    String service() {
        return "pages/front/service";
    }
    //去关于页面

    @RequestMapping("/about")
    String about() {
        return "pages/front/about";
    }

    //去登录页
    @RequestMapping("/loginPage")
    String loginUrl(HttpServletRequest request, Model model) {
        System.err.println(getSession().getId());
        Map<String, String> pwdMap = CookieUtil.load(request);
        Iterator<Map.Entry<String, String>> iterable = pwdMap.entrySet().iterator();
        while (iterable.hasNext()) {
            Map.Entry<String, String> me = iterable.next();
            model.addAttribute("memberid", me.getKey());
            model.addAttribute("password", EncryptUtil.getTruePwd(me.getValue()));
        }
        return "login_Page";
    }

    @RequestMapping("/logoutMy")
    String logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();
        CookieUtil.clear(request, response);
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


    @Override
    protected String setUrl() {
        return null;
    }
}
