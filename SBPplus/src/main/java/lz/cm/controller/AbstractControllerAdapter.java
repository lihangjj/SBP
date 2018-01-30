package lz.cm.controller;


import net.coobird.thumbnailator.Thumbnails;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public abstract class AbstractControllerAdapter extends AbstractController {


    public String createFileName(MultipartFile photo) {
        if (photo.isEmpty()) {
            return setUploadPath() + "nophoto.png";
        }
        return setUploadPath() + UUID.randomUUID() + "." + photo.getContentType().split("/")[1];
    }

    public boolean deleteFile(String oldPhoto, String oldBigPhoto, HttpServletRequest request) {
//        String filePath = ClassUtils.getDefaultClassLoader().getResource("static").getPath() + fileName;
        String realPath = request.getSession().getServletContext().getRealPath("/");
        String minFilePath = realPath + oldPhoto;
        String bigFilePath = realPath + oldBigPhoto;

        File minFile = new File(minFilePath);
        File bigFile = new File(bigFilePath);
        //大小文件都要删除
        return minFile.delete() && bigFile.delete();
    }

    public boolean deleteFile(String oldPhoto, HttpServletRequest request) {
//        String filePath = ClassUtils.getDefaultClassLoader().getResource("static").getPath() + fileName;
        String realPath = request.getSession().getServletContext().getRealPath("/");
        String minFilePath = realPath + oldPhoto;
        File minFile = new File(minFilePath);
        //大小文件都要删除
        return minFile.delete();
    }


    //保存缩略图和原图

    /**
     * @param minPhotoName 小图名称
     * @param bigPhotoName 大图名称
     * @param pic          Multipart名称
     * @param request
     * @return
     */
    public boolean saveFile(String minPhotoName, String bigPhotoName, MultipartFile pic, HttpServletRequest request) {
        String realPath = request.getSession().getServletContext().getRealPath("/");//发布的时候磁盘路径
        String bigPath = realPath + bigPhotoName;//发布的时候磁盘路径
        String minPath = realPath + minPhotoName;//发布的时候磁盘路径

        System.err.println(bigPath);
        System.err.println(minPath);
//        String filePath =  ClassUtils.getDefaultClassLoader().getResource("static").getPath()  + fileName;调试路径
        File file = new File(bigPath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            InputStream inputStream = pic.getInputStream();//取得输入流
            int temp;
            byte[] bytes = new byte[2048];
            while ((temp = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, temp);
            }
            //大图已经保存成功，开始保存小图
            File minFile = new File(minPath);
            if (file.length() > 50 * 1024) {//图片大于50K，就压缩图片
                Thumbnails.of(file).scale(0.3f).toFile(minFile);
                while (minFile.length() > 50 * 1024) {//压缩后仍然大于20k,继续压缩
                    Thumbnails.of(minFile).scale(0.5f).toFile(minFile);
                }
            } else {//如果小于50K，就大图小图相同,不压缩处理
                outputStream = new FileOutputStream(minFile);
                //这里果然要重新得到输入流
                inputStream = pic.getInputStream();
                bytes = new byte[2048];
                while ((temp = inputStream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, temp);
                }
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

    //只保存原图
    public boolean saveFile(String bigPhotoName, MultipartFile pic, HttpServletRequest request) {
        String realPath = request.getSession().getServletContext().getRealPath("/");//发布的时候磁盘路径
        String bigPath = realPath + bigPhotoName;//发布的时候磁盘路径
        System.err.println(bigPath);
//        String filePath =  ClassUtils.getDefaultClassLoader().getResource("static").getPath()  + fileName;调试路径
        File file = new File(bigPath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            InputStream inputStream = pic.getInputStream();//取得输入流
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


    protected abstract String setUploadPath();


}
