package com.garlicg.cutinsupport;


import android.app.Service;


public class CutinItem {
    public final Class<? extends Service> serviceClass;
    public final String cutinName;
    public long orderId = -1;
    public int triggerId = CutinManagerUtils.TRIGGER_ID_DEMO;

    /**
     * CutinManager uses the serviceClass as identifying for service
     * intent.
     */
    public CutinItem(Class<? extends Service> serviceClass,String cutinName) {
        this.serviceClass = serviceClass;
        this.cutinName = cutinName;
    }

    /**
     * CutinManager app uses the serviceClass as identifying for service
     * intent.
     */
    public CutinItem(Class<? extends Service> serviceClass,String cutinName, long orderId) {
        this(serviceClass, cutinName);
        this.orderId = orderId;
    }

    public CutinItem(Class<? extends Service> serviceClass,String cutinName, long orderId, int triggerId) {
        this(serviceClass, cutinName , orderId);
        this.triggerId = triggerId;
    }

    @Override
    public String toString() {
        return cutinName;
    }
}
