package qlm.cm.service.impl;

import qlm.cm.dao.IMemberDAO;
import qlm.cm.service.IMemberServiceBack;
import qlm.cm.vo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class MemberServiceBackImpl implements IMemberServiceBack {

    @Autowired
    private IMemberDAO memberDAO;

    @Override
    public Map<String, Object> editPre(String mid) {
        return null;
    }

    @Override
    public boolean edit(Member vo, Set<Integer> rid) {
        return false;
    }

    @Override
    public Map<String, Object> addPre() {
        return null;
    }

    @Override
    public boolean add(Member vo, Set<Integer> rid) {
        return false;
    }

    @Override
    public boolean editLocked(String mid, int locked) {
        return false;
    }

    @Override
    public List<Member> list() {
        return null;
    }

    @Override
    public boolean editPasswordByAdmin(String mid, String password) {
        return false;
    }

    @Override
    public boolean editPassword(String mid, String oldPassword, String newPassword) {
        return false;
    }

    @Override
    public Member get(String mid) {
        try {
            return memberDAO.findById(mid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Map<String, Object> listAuthByMember(String mid) {
        return null;
    }
}
