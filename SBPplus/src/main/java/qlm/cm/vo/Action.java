package qlm.cm.vo;

import java.io.Serializable;

public class Action implements Serializable {
    private String title, url;
    private Integer actid, gid, sflag;

    @Override
    public String toString() {
        return "Action{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", actid=" + actid +
                ", gid=" + gid +
                ", sflag=" + sflag +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getActid() {
        return actid;
    }

    public void setActid(Integer actid) {
        this.actid = actid;
    }

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public Integer getSflag() {
        return sflag;
    }

    public void setSflag(Integer sflag) {
        this.sflag = sflag;
    }
}
