package lz.util.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class CacheUtil {

    private static Map<String, Object> resMap = new HashMap<>();

    public static Object getCache(String sql) {

        return resMap.get(sql);
    }

    public static void setCache(String sql, Object o) {//设置缓存时间
        System.gc();
        long freeMemory = Runtime.getRuntime().freeMemory();
        if (freeMemory / 1024 / 1024 > 20) {//如果剩余内存超过20M，就保存
            resMap.put(sql, o);
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    resMap.remove(sql);

                }
            };
            //保存15秒
            timer.schedule(task, 15 * 1000);
        }


    }
}
