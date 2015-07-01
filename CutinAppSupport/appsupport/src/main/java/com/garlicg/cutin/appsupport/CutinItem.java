package com.garlicg.cutin.appsupport;


import android.app.Service;

/**
 * Model for show on UI , show demonstration and treat data easily for Manager,
 */
public class CutinItem {

    /**
     * Subclass of CutinService.
     * @see com.garlicg.cutin.appsupport.CutinService
     */
    public final Class<? extends Service> serviceClass;


    /**
     * Cutin name to show on UI.
     */
    public final String cutinName;

    /**
     * Option id to specify the CUT-IN.
     */
    public long orderId = 0;

    /**
     * May be null. Option value to emulate trigger's info.
     */
    public TriggerInfo triggerInfo;

    /**
     * @param serviceClass Subclass of CutinService.
     * @param cutinName Cutin name to show on UI.
     */
    public CutinItem(Class<? extends Service> serviceClass,String cutinName) {
        this.serviceClass = serviceClass;
        this.cutinName = cutinName;
    }

    /**
     *
     * @param serviceClass Subclass of CutinService.
     * @param cutinName Cutin name to show on UI.
     * @param orderId Option id to specify the CUT-IN. Default is 0.
     */
    public CutinItem(Class<? extends Service> serviceClass,String cutinName, long orderId) {
        this(serviceClass, cutinName);
        this.orderId = orderId;
    }

    /**
     *
     * @param serviceClass Subclass of CutinService.
     * @param cutinName Cutin name to show on UI.
     * @param orderId Option id to specify the CUT-IN. Default is 0.
     * @param demoTriggerInfo May be null. Option value to emulate trigger's info.
     */
    public CutinItem(Class<? extends Service> serviceClass,String cutinName, long orderId, TriggerInfo demoTriggerInfo) {
        this(serviceClass, cutinName , orderId);
        triggerInfo = demoTriggerInfo;
    }

    @Override
    public String toString() {
        return cutinName;
    }
}
