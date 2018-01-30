package lz.cm.controller.pages.front;

import lz.cm.controller.AbstractController;
import lz.cm.service.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/pages/front/post")
public class PostControllerFront extends AbstractController {
    private Map<String, String> columnMap = new HashMap<>();


    @Autowired
    private IPostService postService;


    @RequestMapping("list")
    String list(HttpServletRequest request, Model model) throws Exception {
        String columnData = "岗位:name|薪资范围:salRange|学历:education|工作经验:experience";
        setColumnMap(request, columnData);
        return "pages/front/join";
    }

    @RequestMapping("listAjax")
    @ResponseBody
    Map listAjax(HttpServletRequest request) {
        try {
            //反射调用分页方法
            Map<String, Object> map = handSplit(request, postService);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected String setUrl() {
        return null;
    }
}
