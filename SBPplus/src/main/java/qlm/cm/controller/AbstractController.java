package qlm.cm.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

public abstract class AbstractController {
    protected String title = "";

    @Autowired
    private MessageSource messageSource;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }

    public void setMsgAndUrl(String msg, String url, ModelAndView modelAndView) {
        modelAndView.addObject("msg", getResourceValue(msg, title));
        modelAndView.addObject("url", getResourceValue(url, title));
    }
    public void setMsg(String msg, Model model) {
        model.addAttribute("msg", getResourceValue(msg, title));
    }

    public String getResourceValue(String key, Object... agr) {
        try {
            return messageSource.getMessage(key, agr, Locale.getDefault());
        } catch (Exception e) {
            return null;//如果处异常，说明资源没有这key,直接返回null
        }
    }

    public boolean verifyPermission(int actid, HttpServletRequest request) {
        Set<Integer> actids = (Set<Integer>) request.getSession().getAttribute("actids");
        Iterator<Integer> iterator = null;
        try {
            iterator = actids.iterator();
        } catch (Exception e) {
            return false;
        }
        while (iterator.hasNext()) {
            Integer actid2 = iterator.next();
            if (actid2 == actid) {
                return true;
            }
        }
        return false;
    }

}
