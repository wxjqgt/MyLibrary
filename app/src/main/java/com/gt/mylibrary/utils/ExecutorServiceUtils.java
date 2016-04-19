package com.gt.mylibrary.utils;

import java.util.concurrent.ExecutorService;

/**
 * Created by Administrator on 2016/4/19.
 */
public class ExecutorServiceUtils {
    public static void shutdown(ExecutorService... service) {
        int length = service.length;
        for (int i = 0; i < length; i++) {
            if (service[i] != null)
                if (!service[i].isShutdown()) {
                    service[i].shutdown();
                    service = null;
                }
        }
    }
}
