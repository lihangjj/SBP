package lz.cm.service;

import lz.cm.vo.Action;
import lz.cm.vo.Role;

import java.util.List;

public interface IActionService extends IService<Integer, Action> {

    boolean edit(Action action) throws Exception;

    boolean add(Action action) throws Exception;

    List<Role> getAllRoles() throws Exception;

    boolean actionsAddTo(String[]ids,int roleid)throws Exception;

    boolean actionsRemoveFrom(String[]ids,int roleid)throws Exception;

}
