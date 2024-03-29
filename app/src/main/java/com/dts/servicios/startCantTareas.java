package com.dts.servicios;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.PersistableBundle;

public class startCantTareas {

    public static void startService(Context context, String params) {

        try {

            ComponentName serviceComponent = new ComponentName(context, srvCantTareas.class);

            PersistableBundle extras = new PersistableBundle();
            extras.putString("params",params);
            JobInfo.Builder builder = new JobInfo.Builder(0, serviceComponent).setOverrideDeadline(0L).setExtras(extras);

            builder.setMinimumLatency(25 * 1000); // wait
            builder.setOverrideDeadline(20 * 1000); //  delay

            JobScheduler jobScheduler = context.getSystemService(JobScheduler.class);
            jobScheduler.schedule(builder.build());

        } catch (Exception e) {
            String ss=e.getMessage();
        }
    }

}
