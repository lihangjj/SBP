package lz.cm.controller.pages.back.system;

import lz.cm.controller.AbstractControllerAdapter;
import lz.cm.service.IMemberServiceBack;
import lz.cm.vo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/pages/back/system")
public class SystemSetController extends AbstractControllerAdapter {
    @Autowired
    private IMemberServiceBack memberServiceBack;

    @RequestMapping("/setStylePre")
    String setStylePre() {
        return "pages/back/system/setStylePre";
    }

    @RequestMapping("/setStyle")
    String setStyle(Member member, HttpServletRequest request, Model model) {
        if (request instanceof MultipartHttpServletRequest) {
            MultipartHttpServletRequest mrequest = (MultipartHttpServletRequest) request;
            List<MultipartFile> files = mrequest.getFiles("bodypic");
        }
        String style = request.getParameter("style");
        switch (style) {
            case "default":
                member.setStyleValue("default:null");
                member.setHdColor("color:" + getResourceValue("default.head.color"));
                member.setMenuColor("color:" + getResourceValue("default.menu.color"));
                member.setContentColor("color:" + getResourceValue("default.content.color"));
                member.setBodyColor("color:" + getResourceValue("default.body.color"));
                break;
            case "tdcq":
                member.setStyleValue(member.getStyleValue());
                member.setHdColor("color:" + "rgba(0,0,0,0)");
                member.setMenuColor("color:" + "rgba(0,0,0,0)");
                member.setContentColor("color:" + "rgba(0,0,0,0)");
                member.setBodyColor("img:" + null);

                break;
            case "hktk":
                member.setStyleValue(member.getStyleValue());

                member.setHdColor("color:" + "rgba(0,0,0,0)");
                member.setMenuColor("color:" + "rgba(0,0,0,0)");
                member.setContentColor("color:" + "rgba(0,0,0,0)");
                member.setBodyColor("img:" + null);


                break;
            case "zqbx":
                member.setStyleValue(member.getStyleValue());

                member.setHdColor("color:" + "rgba(0,0,0,0)");
                member.setMenuColor("color:" + "rgba(0,0,0,0)");
                member.setContentColor("color:" + "rgba(0,0,0,0)");
                member.setBodyColor("img:" + null);

                break;
            case "zdy":
                member.setStyleValue("zdy:null");
                if (request instanceof MultipartHttpServletRequest) {
                    MultipartHttpServletRequest mrequest = (MultipartHttpServletRequest) request;
                    List<MultipartFile> files = mrequest.getFiles("bodypic");
                    if (files.isEmpty()) {//说明没有文件上传，再看有没有颜色设置
                        if (member.getBodyColor() == null || "".equals(member.getBodyColor())) {//如果整体页面没设置颜色值，也没有设置图片
                            member.setBodyColor("color:null");
                            files = mrequest.getFiles("hdpic");
                            if (files.isEmpty()) {
                                if ("".equals(member.getHdColor())) {
                                    member.setHdColor("color:" + getResourceValue("default.head.color"));
                                } else {
                                    member.setHdColor("color:" + member.getHdColor());
                                }
                            } else {
                                if (files.get(0).getSize() == 0) {
                                    if ("".equals(member.getHdColor())) {
                                        member.setHdColor("color:" + getResourceValue("default.head.color"));
                                    } else {
                                        member.setHdColor("color:" + member.getHdColor());
                                    }
                                } else {//这是真的头部图片
                                    String hdpic = createFileName(files.get(0));
                                    member.setHdColor("img:" + hdpic);
                                }
                            }


                            files = mrequest.getFiles("menupic");
                            if (files.isEmpty()) {
                                if ("".equals(member.getMenuColor())) {
                                    member.setMenuColor("color:" + getResourceValue("default.menu.color"));
                                } else {
                                    member.setMenuColor("color:" + member.getMenuColor());
                                }
                            } else {
                                if (files.get(0).getSize() == 0) {
                                    if ("".equals(member.getMenuColor())) {
                                        member.setMenuColor("color:" + getResourceValue("default.menu.color"));
                                    } else {
                                        member.setMenuColor("color:" + member.getMenuColor());
                                    }
                                } else {
                                    String menupic = createFileName(files.get(0));
                                    member.setMenuColor("img:" + menupic);
                                }
                            }

                            files = mrequest.getFiles("ctpic");

                            if (files.isEmpty()) {
                                if ("".equals(member.getContentColor())) {
                                    member.setContentColor("color:" + getResourceValue("default.content.color"));
                                } else {
                                    member.setContentColor("color:" + member.getContentColor());
                                }
                            } else {
                                if (files.get(0).getSize() == 0) {
                                    if ("".equals(member.getContentColor())) {
                                        member.setContentColor("color:" + getResourceValue("default.content.color"));
                                    } else {
                                        member.setContentColor("color:" + member.getContentColor());
                                    }
                                } else {
                                    String ctpic = createFileName(files.get(0));
                                    member.setContentColor("img:" + ctpic);

                                }
                            }
                        } else {//没有上传文件但是设置了颜色
                            member.setHdColor("color:" + "rgba(0,0,0,0)");
                            member.setMenuColor("color:" + "rgba(0,0,0,0)");
                            member.setContentColor("color:" + "rgba(0,0,0,0)");
                            if ("".equals(member.getBodyColor())) {
                                member.setBodyColor("color:" + getResourceValue("default.body.color"));
                            } else {
                                member.setBodyColor("color:" + member.getBodyColor());
                            }

                        }
                    } else {//文件长度大于0，有可能有文件上传
                        if (files.get(0).getSize() > 0) {//确实有上传,把其他的都设置成透明，并且要设置styleValue,进入自定义就已经设置了
                            member.setHdColor("color:" + "rgba(0,0,0,0)");
                            member.setMenuColor("color:" + "rgba(0,0,0,0)");
                            member.setContentColor("color:" + "rgba(0,0,0,0)");
                            String bodyPic = createFileName(files.get(0));
                            member.setBodyColor("img:" + bodyPic);
                        } else {//看来没有文件上传
                            if ("".equals(member.getHdColor())) {
                                member.setHdColor("color:" + getResourceValue("default.head.color"));
                            } else {
                                member.setHdColor("color:" + member.getHdColor());
                            }
                            if ("".equals(member.getMenuColor())) {
                                member.setMenuColor("color:" + getResourceValue("default.menu.color"));
                            } else {
                                member.setMenuColor("color:" + member.getMenuColor());
                            }
                            if ("".equals(member.getContentColor())) {
                                member.setContentColor("color:" + getResourceValue("default.content.color"));
                            } else {
                                member.setContentColor("color:" + member.getContentColor());
                            }
                            member.setBodyColor("color:null");
                        }
                    }
                }


                break;
        }

        member.setMemberid(getMid());

        try {
            Member oMmeber = memberServiceBack.getMemberById(getMid());
            if (memberServiceBack.editStyle(member)) {
                if (request instanceof MultipartHttpServletRequest) {//保存上传图片
                    //不管如何操作，都应该吧之前的图片删了再说
                    deleteFile(oMmeber.getMenuColor().split(":")[1], request);
                    deleteFile(oMmeber.getContentColor().split(":")[1], request);
                    deleteFile(oMmeber.getHdColor().split(":")[1], request);
                    deleteFile(oMmeber.getBodyColor().split(":")[1], request);


                    MultipartHttpServletRequest mrequest = (MultipartHttpServletRequest) request;
                    List<MultipartFile> files = mrequest.getFiles("hdpic");


                    if (!files.isEmpty()) {
                        if (files.get(0).getSize() != 0) {
                            String hdpic = member.getHdColor().split(":")[1];
                            saveFile(hdpic, files.get(0), request);

                        }
                    }

                    files = mrequest.getFiles("menupic");
                    if (!files.isEmpty()) {
                        if (files.get(0).getSize() != 0) {
                            String menupic = member.getMenuColor().split(":")[1];
                            saveFile(menupic, files.get(0), request);

                        }
                    }

                    files = mrequest.getFiles("ctpic");
                    if (!files.isEmpty()) {
                        if (files.get(0).getSize() != 0) {
                            String ctpic = member.getContentColor().split(":")[1];
                            saveFile(ctpic, files.get(0), request);

                        }
                    }
                    files = mrequest.getFiles("bodypic");
                    if (!files.isEmpty()) {
                        if (files.get(0).getSize() != 0) {
                            String bodypic = member.getBodyColor().split(":")[1];
                            saveFile(bodypic, files.get(0), request);
                        }
                    }
                }
                setMsg("vo.edit.success", "风格", true, model);
                getSession().setAttribute("contentColor", member.getContentColor());
                getSession().setAttribute("hdColor", member.getHdColor());
                getSession().setAttribute("menuColor", member.getMenuColor());
                getSession().setAttribute("bodyColor", member.getBodyColor());
                getSession().setAttribute("styleValue", member.getStyleValue());
            } else {
                setMsg("vo.edit.failure", "风格", false, model);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "pages/back/system/setStylePre";
    }

    @RequestMapping("/setFontPre")
    String setFontPre() {
        return "pages/back/system/setFontPre";
    }

    @RequestMapping("/setFont")
    String setFont(Member member, Model model) {
        if (member.getMenuColor()==null||"".equals(member.getMenuColor())){
            member.setMenuColor("color:null");
        }else {
            member.setMenuColor("color:"+member.getMenuColor());
        }
        member.setMemberid(getMid());

        try {
            if (memberServiceBack.editFontStyle(member)) {
                getSession().setAttribute("sysColor",member.getSysColor());
                getSession().setAttribute("sysFont",member.getSysFont());
                getSession().setAttribute("menuColor",member.getMenuColor());
                getSession().setAttribute("menuFontColor",member.getMenuFontColor());
                getSession().setAttribute("menuSelectedColor",member.getMenuSelectedColor());
            } else {
                setMsg("vo.edit.failure", "风格", false, model);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "pages/back/system/setFontPre";
    }


    @Override
    protected String setUploadPath() {
        return "/upload/system/";
    }

    @Override
    protected String setUrl() {
        return null;
    }
}
