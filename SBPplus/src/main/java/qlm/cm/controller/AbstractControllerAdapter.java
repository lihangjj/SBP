package qlm.cm.controller;


import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public abstract class AbstractControllerAdapter extends AbstractController {
    protected String keyWord = "";
    protected String column = "";
    protected int currentPage = 1;
    protected int lineSize = 5;
    protected String columnData;//格式为："申请标题:title|状态:status";


    public String createFileName(MultipartFile photo) {
        if (photo.isEmpty()) {
            return "nophoto.png";
        }
        return UUID.randomUUID() + "." + photo.getContentType().split("/")[1];
    }

    public boolean deleteFile(String fileName, HttpServletRequest request) {
        String filePath = request.getSession().getServletContext().getRealPath("/") + setUploadPath() + fileName;
        File file = new File(filePath);
        return file.delete();
    }

    public boolean saveFile(String fileName, MultipartFile photo, HttpServletRequest request) {
        String filePath = request.getSession().getServletContext().getRealPath("/") + setUploadPath() + fileName;
        File file = new File(filePath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            InputStream inputStream = photo.getInputStream();//取得输入流
            int temp;
            byte[] bytes = new byte[2048];
            while ((temp = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, temp);
            }
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }



    public void handSplit(HttpServletRequest request) {

        keyWord = request.getParameter("keyWord");
        column = request.getParameter("column");

        try {
            currentPage = Integer.parseInt(request.getParameter("currentPage"));
        } catch (NumberFormatException e) {
        }
        try {
            lineSize = Integer.parseInt(request.getParameter("lineSize"));
        } catch (NumberFormatException e) {
        }
        request.setAttribute("currentPage",currentPage);
        request.setAttribute("keyWord",keyWord);
        request.setAttribute("column",column);
        request.setAttribute("lineSize",lineSize);
        request.setAttribute("columnData", columnData);
        request.setAttribute("url", setUrl());
    }

    abstract String setUploadPath();
    abstract String setUrl();

}
