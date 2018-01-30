package lz.cm.dao;

import lz.cm.vo.Post;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IPostDAO extends IDAO<Integer, Post> {
    boolean updateDflag(Post post);
}
