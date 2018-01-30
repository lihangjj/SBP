package lz.task;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class CacheClear {
    public void clearCache(){

        System.err.println(new SimpleDateFormat("hh:mm:ss").format(new Date()));
        System.err.println(System.currentTimeMillis());
    }

}
