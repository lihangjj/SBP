package qlm.cm.vo;

import java.io.Serializable;
import java.util.Date;

public class Client implements Serializable {
    private String claim, phone, budget, name, address, company, note;
    private Integer clientid;
    private Date pubdate;

    @Override
    public String toString() {
        return "Client{" +
                "claim='" + claim + '\'' +
                ", phone='" + phone + '\'' +
                ", budget='" + budget + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", company='" + company + '\'' +
                ", note='" + note + '\'' +
                ", clientid=" + clientid +
                ", pubdate=" + pubdate +
                '}';
    }

    public String getClaim() {
        return claim;
    }

    public void setClaim(String claim) {
        this.claim = claim;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getClientid() {
        return clientid;
    }

    public void setClientid(Integer clientid) {
        this.clientid = clientid;
    }

    public Date getPubdate() {
        return pubdate;
    }

    public void setPubdate(Date pubdate) {
        this.pubdate = pubdate;
    }
}
