package cn.itcast.utils;

import java.util.UUID;

/**
 * Created by tanshuai on 2018/10/23.
 */
public class UUIDUtils {
    
    public static String getUuid(){
        return UUID.randomUUID().toString().replace("-","");
    }
    
}
