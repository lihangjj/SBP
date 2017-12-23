package qlm.cm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController extends AbstractController {

    @RequestMapping("/")
    String index() {
        return "index";
    }

    @RequestMapping("/loginUrl")
    String loginUrl(HttpServletRequest request, Model model) {
        return "login";
    }

    @RequestMapping("/interceptor_error")
    String interceptor_error() {//拦截器跳转路径，用这个才能跳转到页面，跟index是一样的
        return "interceptor_error";
    }
}
