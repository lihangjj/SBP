package lz.cm.controller;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public abstract class AbstractController {

    @Autowired
    private MessageSource messageSource;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }


    public void setMsg(String msg, String title, Model model) {
        model.addAttribute("msg", getResourceValue(msg, title));
    }

    public void setMsg(String msg, String title, Object type, Model model) {
        model.addAttribute("msg", getResourceValue(msg, title));
        model.addAttribute("type", type);
    }

    public void setMsg(String msg, Model model) {
        model.addAttribute("msg", getResourceValue(msg, ""));
    }

    public void setMsg(String msg, Object type, Model model) {
        model.addAttribute("msg", getResourceValue(msg, ""));
        model.addAttribute("type", type);
    }

    public String getResourceValue(String key, Object... agr) {
        try {
            return messageSource.getMessage(key, agr, Locale.getDefault());
        } catch (Exception e) {
            return null;//如果处异常，说明资源没有这key,直接返回null
        }
    }

    //设置分页的查询列
    protected void setColumnMap(HttpServletRequest request, String columnData) {
        Map<String, String> columnMap = new LinkedHashMap<>();
        String[] clomunAndflag = columnData.split("\\|");
        for (String x : clomunAndflag) {
            String temp[] = x.split(":");
            columnMap.put(temp[0], temp[1]);
        }
        request.setAttribute("columnMap", columnMap);
    }
    /**
     * @param request
     * @param serviceImpl 具体服务层实现类
     * @return
     */
    protected Map<String, Object> handSplit(HttpServletRequest request, Object serviceImpl) {

        String parameterName = request.getParameter("parameterName");
        String parameterValue = request.getParameter("parameterValue");
        String keyWord = request.getParameter("keyWord");
        String column = request.getParameter("column");
        Integer currentPage = null;
        Integer lineSize = null;
        try {
            currentPage = Integer.parseInt(request.getParameter("currentPage"));
        } catch (NumberFormatException e) {
        }
        try {
            lineSize = Integer.parseInt(request.getParameter("lineSize"));
        } catch (NumberFormatException e) {

        }
//        request.setAttribute("currentPage", currentPage);
//        request.setAttribute("keyWord", keyWord);
//        request.setAttribute("column", column);
//        request.setAttribute("lineSize", lineSize);
//        request.setAttribute("parameterName", parameterName);
//        request.setAttribute("parameterValue", parameterValue);
        try {
            //反射得到分页方法，而后执行
            Method splitM = serviceImpl.getClass().getMethod("splitVoByFlag", String.class, String.class, Integer.class, Integer.class, String.class, String.class);
            Map<String, Object> map = (Map<String, Object>) splitM.invoke(serviceImpl, column, keyWord, currentPage, lineSize, parameterName, parameterValue);
            double allRecorders = (int) map.get("allRecorders");
            if (lineSize!=null){
                int pageSize = (int) Math.ceil(allRecorders / lineSize);
                request.setAttribute("pageSize", pageSize);
            }
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @return 得到 shiro 的Session
     */
    protected Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }

    protected String getMid() {
        return (String) getSession().getAttribute("memberid");
    }
    /**
     * 分页的跳转路径
     *
     * @return
     */
    protected abstract String setUrl();

}
