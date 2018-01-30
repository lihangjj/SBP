package lz.util.str;

public class StrUtil {
    public static final String SUPER_ADMIN="lh";
    public static final String BASIC_ROLE="3-4-5-7-8";
    //大写首字母
    public  static String initCap(String s){
        return s.substring(0,1).toUpperCase()+s.substring(1);
    }

}
