package qlm.cm.controller;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

//控制层切面处理，全局异常处理
@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest request, Exception e) {
        ModelAndView modelAndView = new ModelAndView("error");
        String exception="";
        if (e instanceof UnauthorizedException){
            exception="您没有"+e.getMessage().substring(e.getMessage().lastIndexOf("["))+"权限";
        }
        modelAndView.addObject("exception", exception);
        return modelAndView;
    }
}
