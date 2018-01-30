package lz.cm.controller.pages.back.post;

import lz.cm.controller.AbstractController;
import lz.cm.service.IPostService;
import lz.cm.vo.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;


@Controller
@RequestMapping("/pages/back/post")
public class PostController extends AbstractController {


    @Autowired
    private IPostService postService;

    @RequestMapping("addPre")
    String addPre() {
        return "pages/back/post/post-addPre";
    }

    @RequestMapping("add")
    String add(Model model, Post post) {
        post.setPubDate(new Date());
        post.setMemberid(getMid());
        try {
            if (postService.addPost(post)) {
                setMsg("vo.add.success", "职位", true, model);
            } else {
                setMsg("vo.add.failure", "职位", false, model);

            }
            return "pages/back/post/post-addPre";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "pages/back/post/post-addPre";
    }

    @RequestMapping("list")
    String list(HttpServletRequest request, Model model) throws Exception {
        String columnData = "岗位:name|薪资范围:salRange|学历:education|工作经验:experience";
        setColumnMap(request, columnData);
        return "pages/back/post/post-list";
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

    @RequestMapping("editDflag")
    @ResponseBody
    boolean editDflag(Post post) {
        try {//这样完全交给前台是不安全的，但是删除功能不是很重要，逻辑删除，可以加权限验证就安全

            return postService.editDflag(post);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @RequestMapping("editPre")
    @ResponseBody
    Post editPre(Post post) {
        try {//这样完全交给前台是不安全的，但是删除功能不是很重要，逻辑删除，可以加权限验证就安全
            return postService.getVoById(post.getPostid());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("edit")
    @ResponseBody
    boolean edit(Post post) {
        post.setMemberid(getMid());
        try {
            return postService.edit(post);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected String setUrl() {//分页的路径
        return "/pages/back/cost/list";
    }


}
