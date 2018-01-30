package lz.cm.vo;

import java.io.Serializable;
import java.util.Date;

public class Post implements Serializable {
    private Integer postid, dflag;
    private String memberid;
    private String name;
    private String duty;
    private String note;
    private String welfare;
    private String workLoc;
    private String education;
    private String other;
    private String phone;
    private String recruits;

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    private String experience;

    public String getSalRange() {
        return salRange;
    }

    public void setSalRange(String salRange) {
        this.salRange = salRange;
    }

    private String salRange;
    private Date pubDate;
    private Member member;

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Integer getPostid() {
        return postid;
    }

    public void setPostid(Integer postid) {
        this.postid = postid;
    }

    public Integer getDflag() {
        return dflag;
    }

    public void setDflag(Integer dflag) {
        this.dflag = dflag;
    }

    public String getRecruits() {
        return recruits;
    }

    public void setRecruits(String recruits) {
        this.recruits = recruits;
    }

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

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getWelfare() {
        return welfare;
    }

    public void setWelfare(String welfare) {
        this.welfare = welfare;
    }

    public String getWorkLoc() {
        return workLoc;
    }

    public void setWorkLoc(String workLoc) {
        this.workLoc = workLoc;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

}
