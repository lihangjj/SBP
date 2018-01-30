package lz.cm.controller.pages.back.cost;

import lz.cm.controller.AbstractControllerAdapter;
import lz.cm.service.ICostServiceBack;
import lz.cm.vo.Cost;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


@Controller
@RequestMapping("/pages/back/cost")
public class CostController extends AbstractControllerAdapter {
    @InitBinder
    public void initBinder(WebDataBinder binder) {//这里再次复写一次就完事了
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
    }

    @Autowired
    private ICostServiceBack costServiceBack;


    @RequestMapping("addPre")
    String addPre() {
        return "pages/back/cost/cost_addPre";
    }

    @RequestMapping("add")
    String add(Model model, Cost cost, MultipartFile pic, HttpServletRequest request) {
        String photo = createFileName(pic);
        String bigphoto = createFileName(pic);

        cost.setPhoto(photo);
        cost.setBigphoto(bigphoto);

        if (cost.getTime() == null) {
            cost.setTime(new Date());
        }
        cost.setMemberid(getMid());
        try {
            String title = "成本";

            if (costServiceBack.addCost(cost)) {
                setMsg("vo.add.success", title, true, model);
                if (!pic.isEmpty()) {//这里指有上传文件，就保存
                    saveFile(photo, bigphoto, pic, request);
                }
            } else {
                setMsg("vo.add.failure", title, false, model);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

//        return "redirect:/pages/back/member/addPre";到另外的controller
        return "pages/back/cost/cost_addPre";
    }

    @RequestMapping("myCostList")
    String myCostList(HttpServletRequest request, Model model) {
        String columnData = "标题:title|金额:cost|说明:note|时间:time|";
        setColumnMap(request, columnData);
        try {
            model.addAttribute("allCost", costServiceBack.getAllCostByMember(getMid()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "pages/back/cost/myCost-list";
    }

    @RequestMapping("myCostListAjax")
    @ResponseBody
    Map myCostListAjax(HttpServletRequest request) {
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
        try {
            //反射调用分页方法
            Map<String, Object> map = costServiceBack.selectMyCost(getMid(), column, keyWord, currentPage, lineSize, parameterName, parameterValue);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @RequestMapping("list")
    String list(HttpServletRequest request, Model model) throws Exception {
        String columnData = "标题:title|用户:memberid|金额:cost|说明:note|时间:time|";

        setColumnMap(request, columnData);
        String result = request.getParameter("edit");
        model.addAttribute("allCost", costServiceBack.getAllCost());

        if ("y".equals(result)) {//来自ajax修改之后
            setMsg("vo.edit.success", "成本", true, model);
        } else if ("n".equals(result)) {
            setMsg("vo.edit.failure", "成本", false, model);
        } else if ("e".equals(result)) {
            setMsg("service.failure.error", "成本修改", false, model);
        }
        return "pages/back/cost/cost_list";
    }

    @RequestMapping("listAjax")
    @RequiresRoles("finance")
    @ResponseBody
    Map listAjax(HttpServletRequest request) {


        try {
            //反射调用分页方法
            Map<String, Object> map = handSplit(request, costServiceBack);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("deleteOrRestore")
    @ResponseBody
    boolean deleteOrRestore(Cost cost) {
        try {//这样完全交给前台是不安全的，但是删除功能不是很重要，逻辑删除，可以加权限验证就安全
            return costServiceBack.updateDflag(cost.getDflag(), new String[]{String.valueOf(cost.getCostid())});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @RequestMapping("bxOrNobx")
    @ResponseBody
    boolean bxOrNobx(Cost cost) {
        try {//这样完全交给前台是不安全的，但是删除功能不是很重要，逻辑删除，可以加权限验证就安全
            return costServiceBack.updateBxflag(cost.getBxflag(), new String[]{String.valueOf(cost.getCostid())});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    @RequestMapping("editPre")
    @ResponseBody
    Cost editPre(Cost cost) {
        try {//这样完全交给前台是不安全的，但是删除功能不是很重要，逻辑删除，可以加权限验证就安全
            return costServiceBack.getVoById(cost.getCostid());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @RequestMapping("pldeleteOrRestore")
    @ResponseBody
    boolean pldeleteOrRestore(HttpServletRequest request) {
        String str = request.getParameter("data");
        String[] ids = str.split("\\|");
        String dflag = request.getParameter("dflag");
        try {
            return costServiceBack.updateDflag(Integer.valueOf(dflag), ids);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @RequestMapping("plbxOrNobx")
    @ResponseBody
    boolean plbxOrNobx(HttpServletRequest request) {
        String str = request.getParameter("data");
        String[] ids = str.split("\\|");
        String bxflag = request.getParameter("bxflag");
        try {
            return costServiceBack.updateBxflag(Integer.valueOf(bxflag), ids);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @RequestMapping("edit")
    String edit(Cost cost, MultipartFile pic, HttpServletRequest request) {
        String oldP = cost.getPhoto();
        String oldBP = cost.getBigphoto();
        String photo = createFileName(pic);
        String bigphoto = createFileName(pic);
        if (!pic.isEmpty()) {
            cost.setPhoto(photo);
            cost.setBigphoto(bigphoto);
        }
        try {

            if (costServiceBack.edit(cost)) {
                if (!pic.isEmpty()) {
                    saveFile(photo, bigphoto, pic, request);
                    deleteFile(oldP, oldBP, request);
                }
                return "redirect:/pages/back/cost/list?edit=y";
            } else {
                return "redirect:/pages/back/cost/list?edit=n";
            }

        } catch (Exception e) {
            return "redirect:/pages/back/cost/list?edit=e";
        }
    }

    @Override
    protected String setUploadPath() {
        return "/upload/cost/";
    }

    @Override
    protected String setUrl() {//分页的路径
        return "/pages/back/cost/list";
    }


}
