package lz.util.password;


import lz.util.MD5Code;

public class EncryptUtil {
    public static String getEncPwd(String password) {
        return new MD5Code().getMD5ofStr(password + "{cqliz.cn}");
    }

    public static String getMyEncPwd(String password) {
        char[] chars = password.toCharArray();
        int[] e = new int[chars.length];
        String pwd = "";
        for (int x = 0; x < chars.length; x++) {
            int y = chars[x];
            e[x] = (y * 1991 + 1002 - 5451);
            pwd += e[x] + "-";
        }
        return "cqliz-"+pwd;
    }

    public static String getTruePwd(String password) {
        password=password.replace("cqliz-","");
        String[] strings = password.split("-");
        int[] m = new int[strings.length];
        char[] res = new char[strings.length];
        for (int x = 0; x < strings.length; x++) {
            m[x] = Integer.valueOf(strings[x]);
            res[x] = (char) ((m[x] + 5451 - 1002) / 1991);
        }
        return new String(res);
    }

}
