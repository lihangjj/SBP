package lz.cm.controller;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


//控制层切面处理，控制层抛出的所有异常都会到这里来全局异常处理
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String defaultErrorHandler(Exception e, Model model) {//这个跳转只能是正常ip地址来的，ajax是不会跳转的
        String exception = "";
        System.err.println(e.getMessage());
        if (e instanceof UnauthorizedException) {//没有权限异常
            exception = "您没有" + e.getMessage().substring(e.getMessage().lastIndexOf("[")) + "权限";
        }
        model.addAttribute("exception", exception);

        return "unAuthPage";
    }
}
