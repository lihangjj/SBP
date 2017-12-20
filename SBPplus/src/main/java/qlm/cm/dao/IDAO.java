package qlm.cm.dao;

import java.util.List;
import java.util.Set;

public interface IDAO<K, V> {
    boolean doCreate(V vo) throws Exception;

    boolean doUpdate(V vo) throws Exception;

    boolean doRemoveBatch(Set<K> ids) throws Exception;

    V findById(K id) throws Exception;

    List<V> findAll() throws Exception;

    List<V> findAllSplit(String column, String keyWord,
                         Integer currentPage, Integer lineSize) throws Exception;

    Integer findAllSplitCount(String column, String keyWord) throws Exception;
}
