package com.zhumingwei.bond.tool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtil {
    private static Logger logger = LoggerFactory.getLogger("MyLog");

    public static void loge(Object s){
        if (s==null){
            logger.error("null");
        }else {
            logger.error(s.toString());
        }
    }
}
