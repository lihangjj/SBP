package lz.cm.service.impl;

import lz.cm.dao.IMemberDAO;
import lz.cm.dao.IPostDAO;
import lz.cm.service.IPostService;
import lz.cm.vo.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class PostServiceImpl implements IPostService {
    @Autowired
    private IPostDAO postDAO;
    @Autowired
    private IMemberDAO memberDAO;

    @Override
    public boolean addPost(Post post) throws Exception {
        return postDAO.doCreate(post);
    }

    @Override
    public boolean editDflag(Post post) throws Exception {
        return postDAO.updateDflag(post);
    }

    @Override
    public boolean edit(Post post) throws Exception {
        return postDAO.doUpdate(post);
    }

    @Override
    public Post getVoById(Integer id) {
        return postDAO.findById(id, Post.class);
    }

    @Override
    public Map<String, Object> splitVoByFlag(String column, String keyWord, Integer currentPage, Integer lineSize, String parameterName, String parameterValue) throws Exception {
        Map<String, Object> resMap = postDAO.splitVoByFlag(Post.class, column, keyWord, currentPage, lineSize, getCondition(parameterName,"=",parameterValue));
        List<Post> postList = (List<Post>) resMap.get("allVo");
        for (Post x : postList) {
            x.setMember(memberDAO.findById(x.getMemberid()));
        }
        resMap.put("allVo", postList);
        return resMap;
    }
}
