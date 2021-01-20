package com.dts.tom.util;

import com.dts.tom.ZebraPrintTask;

/**
 * Created by ctsims on 7/25/2016.
 */
public interface PrintTaskListener {
    void taskUpdate(ZebraPrintTask task);

    void taskFinished(boolean taskSuccesful);
}
