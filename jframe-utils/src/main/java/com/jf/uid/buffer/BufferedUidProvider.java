package com.jf.uid.buffer;

import java.util.List;

/**
 * Buffered UID provider(Lambda supported), which provides UID in the same one second
 * 
 * @author yutianbao
 */
@FunctionalInterface
public interface BufferedUidProvider {

    /**
     * Provides UID in one second
     * 
     * @param momentInSecond
     * @return
     */
    List<Long> provide(long momentInSecond);
}
