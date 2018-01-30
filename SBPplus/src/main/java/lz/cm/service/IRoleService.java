package lz.cm.service;

import lz.cm.vo.Action;
import lz.cm.vo.Role;

import java.util.List;

public interface IRoleService extends IService<Integer, Role> {

    boolean edit(Role action) throws Exception;

    boolean add(Role action) throws Exception;
    List<Action> getActionsByRoleId(int roleid)throws Exception;
    boolean plAddRoleToMembers(String[] roleids, String[] memberids) throws Exception;
    boolean plRemoveRoleFromMembers(String[] roleids, String[] memberids) throws Exception;

}
