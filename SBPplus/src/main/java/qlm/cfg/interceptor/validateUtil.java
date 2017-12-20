package qlm.cfg.interceptor;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class validateUtil {
    public static Map<String, String> getErrorsMap(HttpServletRequest request, HandlerMethod o) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        HandlerMethod handlerMethod = o;
        //1。得到获得资源的方法
        Method getResourceValue = handlerMethod.getBeanType().getMethod("getResourceValue", String.class, Object[].class);
        Object object = handlerMethod.getBean();
        String className = handlerMethod.getBeanType().getSimpleName();
        String methodName = handlerMethod.getMethod().getName();
        String key = className + "." + methodName + ".rules";//规则的key,如LoginAction.login.rules=eid:string|password:string|code:rand
        String validateRules = (String) getResourceValue.invoke(object, key, null);//取得验证规则
        if (validateRules != null) {//如果有验证规则

            String nameValue[] = validateRules.split("\\|");//拆分验证规则
            for (String x : nameValue) {
                String name = x.split(":")[0];//第一个是元素name
                String rule = x.split(":")[1];//第二个是元素的正则表达式
                String value = request.getParameter(name);//获得元素的值
                if (isEmpty(value)) {//数据为空，写了验证规则就一定不能为空
                    Map<String, String> errorsMap = new HashMap<>();//用来保存错误的map
                    errorsMap.put(name, (String) getResourceValue.invoke(object, "validate.null", null));//直接增加为空的错误消息

                    putErrorPage(getResourceValue, object, className, methodName, errorsMap);
                    return errorsMap;//只要有一个错误，直接将错误集合返回，后面的没必要继续判断了，这样可以提高程序性能

                } else { //如果有值。再用正则验证
                    if (!value.matches(rule)) {//如果值不匹配正则
                        Map<String, String> errorsMap = new HashMap<>();//用来保存错误的map
                        String msg = (String) getResourceValue.invoke(object, "validate." + name, null);//得到错误提示
                        errorsMap.put(name, msg);//将错误提示增加到错误集合里边
                        putErrorPage(getResourceValue, object, className, methodName, errorsMap);
                        return errorsMap;//只要有一个错误，直接将错误集合返回，后面的没必要继续判断了，这样可以提高程序性能
                    }
                }
            }
        }
        //能走到这里，说明字段验证没有错，开始验证上传文件
        MultipartResolver multipartResolver = new CommonsMultipartResolver();//需要上传包
        if (multipartResolver.isMultipart(request)) {//表示如果有上传文件
            String mimeKey = className + "." + methodName + ".mime.rules";
            String mimeRules = (String) getResourceValue.invoke(object, mimeKey, null);
            if (mimeRules == null) {//表示没有设置单独的验证规则，就用公共的验证规则
                mimeRules = (String) getResourceValue.invoke(object, "mime.rules", null);
            }
            MultipartRequest mreq = (MultipartRequest) request;
            Map<String, MultipartFile> fileMap = mreq.getFileMap();
            if (fileMap.size() > 0) {
                Collection<MultipartFile> collection = fileMap.values();//这里也可以用Map.Entry
                Iterator<MultipartFile> iterator = collection.iterator();
                String[] mimeType = mimeRules.split("\\|");
                while (iterator.hasNext()) {
                    MultipartFile file = iterator.next();
                    if (file.getSize() > 0) {//表示此时有上传文件
                        for (String x : mimeType) {
                            if (!x.equals(file.getContentType())) {//此时文件不符合mime类型，直接返回错误集合
                                Map<String, String> errorsMap = new HashMap<>();
                                errorsMap.put("上传错误", "文件类型不是" + x + "类型");
                                putErrorPage(getResourceValue, object, className, methodName, errorsMap);
                                return errorsMap;
                            }

                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * 增加错误页的路径
     * @param getResourceValue
     * @param object
     * @param className
     * @param methodName
     * @param errorsMap
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private static void putErrorPage(Method getResourceValue, Object object, String className, String methodName, Map<String, String> errorsMap) throws IllegalAccessException, InvocationTargetException {
        String errorsPageKey = className + "." + methodName + ".error.page";//错误页的key,如LoginAction.login.error.page=/login.jsp
        String errorsPageUrl = (String) getResourceValue.invoke(object, errorsPageKey, null);//错误页路径
        if (errorsPageUrl != null) {//如果设置了指定错误页，就增加指定错误页
            errorsMap.put("errorPage", errorsPageUrl);
        } else {//没有设定指定错误页，就用默认的错误页
            errorsMap.put("errorPage", (String) getResourceValue.invoke(object, "error.page", null));
        }
    }

    public static boolean isEmpty(String value) {
        if (value == null || "".equals(value)) {
            return true;
        }
        return false;
    }
}
