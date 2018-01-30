package lz.cm.vo;

import java.io.Serializable;
import java.util.Date;

public class Cost implements Serializable {
    private String title;
    private String photo;
    private String bigphoto;

    public String getBigphoto() {
        return bigphoto;
    }

    public void setBigphoto(String bigphoto) {
        this.bigphoto = bigphoto;
    }

    private String note;
    private String memberid;
    private Integer costid;
    private Integer amount;
    private Integer dflag;

    public Integer getBxflag() {
        return bxflag;
    }

    public void setBxflag(Integer bxflag) {
        this.bxflag = bxflag;
    }

    private Integer bxflag;
    private Date time;
    private Double cost;
    private Member member;

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getMemberid() {
        return memberid;
    }

    public void setMemberid(String memberid) {
        this.memberid = memberid;
    }

    public Integer getCostid() {
        return costid;
    }

    public void setCostid(Integer costid) {
        this.costid = costid;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getDflag() {
        return dflag;
    }

    public void setDflag(Integer dflag) {
        this.dflag = dflag;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }
}
