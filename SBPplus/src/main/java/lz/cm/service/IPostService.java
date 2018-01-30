package lz.cm.service;

import lz.cm.vo.Post;

public interface IPostService extends IService<Integer, Post> {
    boolean addPost(Post post) throws Exception;
    boolean editDflag(Post post)throws Exception;
    boolean edit(Post post)throws Exception;
}
