package qlm.cm.service;

import qlm.cm.vo.Member;

import java.util.List;
import java.util.Map;
import java.util.Set;


public interface IMemberServiceBack {

    /**
     * 进行增加前数据查询的操作，本操作将执行如下调用：<br>
     * 1、调用IRoleDAO.findAll()取得全部的角色信息；<br>
     * 2、调用IMemberDAO.findById()取得指定用户的信息；<br>
     * 3、调用IMemberDAO.findAllRoleByMember()方法取得用户已经具备的角色信息；<br>
     *
     * @param mid 要更新的用户id数据；
     * @return 返回的内容包含有如下组成：<br>
     * 1、key = allRoles、value = 全部的角色；<br>
     * 2、key = memberRoles、value = 用户的全部的角色编号；<br>
     * 3、key = member、value = 查询出来的用户信息；<br>
     */
    Map<String, Object> editPre(String mid);

    /**
     * 描述数据追加的操作处理，本操作将执行如下调用：<br>
     * 1、查询要追加的mid的数据是否存在，调用IMemberDAO.findById()方法；<br>
     * 2、调用IMemberDAO.doUpdate()保存用户信息；<br>
     * 3、调用IMemberDAO.doRemoveMemberAndRole()方法删除掉已经存在的关系；<br>
     * 4、调用IMemberDAO.doCreateMemberAndRole()保存用户和角色的关系；<br>
     *
     * @param vo
     * @param rid 包括要追加的rid数据
     * @return 用户保存成功返回true，否则返回false
     */
    boolean edit(Member vo, Set<Integer> rid);

    /**
     * 进行增加前数据查询的操作，本操作将执行如下调用：<br>
     * 1、调用IRoleDAO.findAll()取得全部的角色信息；<br>
     *
     * @return 返回的内容包含有如下组成：<br>
     * 1、key = allRoles、value = 全部的角色；<br>
     */
    Map<String, Object> addPre();

    /**
     * 描述数据追加的操作处理，本操作将执行如下调用：<br>
     * 1、查询要追加的mid的数据是否存在，调用IMemberDAO.findById()方法；<br>
     * 2、调用IMemberDAO.doCreate()保存用户信息；<br>
     * 3、调用IMemberDAO.doCreateMemberAndRole()保存用户和角色的关系；<br>
     *
     * @param vo
     * @param rid 包括要追加的rid数据
     * @return 用户保存成功返回true，否则返回false
     */
    boolean add(Member vo, Set<Integer> rid);

    /**
     * 实现用户的状态的更新处理，调用IMemberDAO.doUpdateLocked()方法
     *
     * @param mid    要更新的用户编号
     * @param locked 表示要更新后的状态
     * @return
     */
    boolean editLocked(String mid, int locked);

    /**
     * 实现全部用户的列表显示，调用IMemberDAO.findAll()查询
     *
     * @return 如果没有数据则集合长度为0（size() == 0）
     */
    List<Member> list();

    /**
     * 此处为修改指定用户密码的操作处理，调用IMemberDAO.doUpdatePasswordByAdmin()方法
     *
     * @param mid      要修改的用户的编号
     * @param password 经过加密后的新密码
     * @return
     */
    boolean editPasswordByAdmin(String mid, String password);

    /**
     * 实现密码的变更处理
     *
     * @param mid         要修改密码的当前用户
     * @param oldPassword 原始密码（加密后的数据）
     * @param newPassword 新的密码（加密后的数据）
     * @return 修改成功返回true，否则返回false
     */
    boolean editPassword(String mid, String oldPassword, String newPassword);

    /**
     * 根据用户的id查询出用户的完整数据，调用：IMemberDAO.findById()
     *
     * @param mid
     * @return
     */
    Member get(String mid);

    /**
     * 是进行用户对应的角色以及所有权限数据的查询操作，要调用如下的接口方法：<br>
     * 1、查询所有的角色：IRoleDAO.findAllRoleFlag()；<br>
     * 2、查询所有的权限：IActionDAO.findAllActionFlag()；<br>
     *
     * @param mid
     * @return 返回的结果包含有如下的几个内容：<br>
     * 1、key = allRoles = value = IRoleDAO.findAllRoleFlag()，Set集合；<br>
     * 2、key = allActions = value = IActionDAO.findAllActionFlag()，Set集合；<br>
     */
    Map<String, Object> listAuthByMember(String mid);
}
