package lz.realm;

import lz.cm.service.IMemberServiceBack;
import lz.cm.vo.Member;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;

public class MemberRealm extends AuthorizingRealm {
    @Resource
    private IMemberServiceBack memberServiceBack;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(//这个是验证用户名密码的
                                                         AuthenticationToken token) throws AuthenticationException {
        System.out.println("============== 1、进行认证操作处理 ==============");
        String mid = token.getPrincipal().toString(); // 用户名//在登陆控制器已经验证完毕
        // 取得用户名之后就需要通过业务层获取用户对象以确定改用户名是否可用
        // 通过用户名获取用户信息
        String password = new String((char[]) token.getCredentials());

        Member member = memberServiceBack.getMemberById(mid);
//        if (member == null) { // 表示该用户信息不存在，不存在则应该抛出一个异常
//            throw new UnknownAccountException("搞什么搞，用户名不存在！");
//        }
//        // 用户名如果存在了，那么就需要确定密码是否正确
//        if (!password.equals(member.getPassword())) { // 密码验证
//            throw new IncorrectCredentialsException("密码都记不住，去死吧！");
//        }
//        // 随后还需要考虑用户被锁定的问题
//        if (member.getSflag().equals(999)) { // 0是超级管理员，999是锁住用户
//            throw new LockedAccountException("被锁了，求解锁去吧！");
//        }
        // 定义需要进行返回的操作数据信息项，返回的认证信息使用应该是密文
        SimpleAuthenticationInfo auth = new SimpleAuthenticationInfo(
                token.getPrincipal(), password, "memberRealm");

        // 在认证完成之后可以直接取得用户所需要的信息内容，保存在Session之中
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute("name", member.getName());//这里可以保存用户需要保存的
        session.setAttribute("memberid", member.getMemberid());//这里可以保存用户需要保存的
        session.setAttribute("sessionid", session.getId());//这里可以保存用户需要保存的
        session.setAttribute("photo", member.getPhoto());//用户的小头像
        session.setAttribute("bigphoto", member.getBigphoto());//用户的大头像
        session.setAttribute("contentColor", member.getContentColor());//内容颜色或者图片
        session.setAttribute("hdColor", member.getHdColor());//头部颜色或者图片
        session.setAttribute("menuColor", member.getMenuColor());//菜单颜色或者图片
        session.setAttribute("bodyColor", member.getBodyColor());//用户自定义的颜色或者图片
        session.setAttribute("styleValue", member.getStyleValue());//用户选中系统提供的风格
        session.setAttribute("sysColor", member.getSysColor());//系统颜色
        session.setAttribute("sysFont", member.getSysFont());//系统字体
        session.setAttribute("menuFontColor", member.getMenuFontColor());//菜单字体颜色
        session.setAttribute("menuSelectedColor", member.getMenuSelectedColor());//菜单选中背景颜色
        try {
            session.setAttribute("allRoles", memberServiceBack.getRolesAndActions(member.getMemberid()));//该用户的所有权限
        } catch (Exception e) {
            e.printStackTrace();
        }
        return auth;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(//这个是验证权限的
                                                       PrincipalCollection principals) {
        System.out.println("++++++++++++++ 2、进行授权操作处理 ++++++++++++++");
        // 该操作的主要目的是取得授权信息，说的直白一点就是角色和权限数据
        SimpleAuthorizationInfo auth = new SimpleAuthorizationInfo();
        // 执行到此方法的时候一定是已经进行过用户认证处理了（用户名和密码一定是正确的）
        String mid = (String) principals.getPrimaryPrincipal(); // 取得用户名
        try {
            auth.setRoles(memberServiceBack.getAllMemberRolesFlag(mid)); // 保存所有的角色flag到shiro
            auth.setStringPermissions(memberServiceBack.getAllMemberActionsFlag(mid)); // 保存所有的权限flag到shiro
        } catch (Exception e) {
            e.printStackTrace();
        }
        return auth;
    }
}
