package lz.cm.vo;

import java.io.Serializable;

public class Action implements Serializable {
    private String title, flag;
    private Role role;
    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }

    public Integer getSflag() {
        return sflag;
    }

    public void setSflag(Integer sflag) {
        this.sflag = sflag;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String url;
    private Integer actionid, sflag;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Integer getActionid() {
        return actionid;
    }

    public void setActionid(Integer actionid) {
        this.actionid = actionid;
    }
}
