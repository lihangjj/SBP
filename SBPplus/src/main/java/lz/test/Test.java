package lz.test;


import lz.util.password.EncryptUtil;

import java.util.Collection;
import java.util.HashSet;

public class Test<K> {


    public static void main(String[] args) throws Exception {
        String s= EncryptUtil.getMyEncPwd("ldfsvdfh");

        Collection<String> collection=new HashSet<>();
        System.err.println(s);
        System.err.println(EncryptUtil.getTruePwd(s));
    }

}
