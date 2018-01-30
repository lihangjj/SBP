package lz.cm.vo;

import java.io.Serializable;
import java.util.Date;

public class Member implements Serializable {
    private String memberid;
    private String name;
    private String password;
    private String sex;
    private String phone;
    private String note;
    private String photo;
    private String contentColor;
    private String hdColor;
    private String menuColor;
    private String bodyColor;
    private String sysFont;
    private String sysColor;
    private String menuFontColor;
    private String menuSelectedColor;

    public String getSysFont() {
        return sysFont;
    }

    public void setSysFont(String sysFont) {
        this.sysFont = sysFont;
    }

    public String getSysColor() {
        return sysColor;
    }

    public void setSysColor(String sysColor) {
        this.sysColor = sysColor;
    }


    public String getMenuFontColor() {
        return menuFontColor;
    }

    public void setMenuFontColor(String menuFontColor) {
        this.menuFontColor = menuFontColor;
    }

    public String getMenuSelectedColor() {
        return menuSelectedColor;
    }



    public void setMenuSelectedColor(String menuSelectedColor) {
        this.menuSelectedColor = menuSelectedColor;
    }


    public String getBodyColor() {
        return bodyColor;
    }
    public void setBodyColor(String bodyColor) {
        this.bodyColor = bodyColor;
    }

    private String styleValue;


    public String getStyleValue() {
        return styleValue;
    }

    public void setStyleValue(String styleValue) {
        this.styleValue = styleValue;
    }


    public String getContentColor() {
        return contentColor;
    }

    public void setContentColor(String contentColor) {
        this.contentColor = contentColor;
    }



    public String getHdColor() {
        return hdColor;
    }

    public void setHdColor(String hdColor) {
        this.hdColor = hdColor;
    }

    public String getMenuColor() {
        return menuColor;
    }

    public void setMenuColor(String menuColor) {
        this.menuColor = menuColor;
    }


    public String getBigphoto() {
        return bigphoto;
    }

    public void setBigphoto(String bigphoto) {
        this.bigphoto = bigphoto;
    }

    private String bigphoto;
    private Integer sflag, age, eflag;

    public Integer getEflag() {
        return eflag;
    }

    public void setEflag(Integer eflag) {
        this.eflag = eflag;
    }

    private Date regdate;

    public String getMemberid() {
        return memberid;
    }

    public void setMemberid(String memberid) {
        this.memberid = memberid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Integer getSflag() {
        return sflag;
    }

    public void setSflag(Integer sflag) {
        this.sflag = sflag;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getRegdate() {
        return regdate;
    }

    public void setRegdate(Date regdate) {
        this.regdate = regdate;
    }
}
