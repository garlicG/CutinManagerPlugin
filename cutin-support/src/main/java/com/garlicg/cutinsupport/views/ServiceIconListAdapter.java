package com.garlicg.cutinsupport.views;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.widget.TextView;

import com.garlicg.cutinsupport.CutinItem;

import java.util.List;

public class ServiceIconListAdapter extends SimpleCutinListAdapter {
    private final PackageManager mPackageManager;

    public ServiceIconListAdapter(Context context, List<CutinItem> objects, boolean checkable) {
        super(context, objects, checkable);
        mPackageManager = context.getPackageManager();
    }

    @Override
    protected void bindContent(TextView textView, CutinItem item) {
        textView.setText(item.cutinName);
        Drawable icon = getServiceIcon(item.serviceClass , mPackageManager);
        textView.setCompoundDrawables(icon ,null , null ,null);
    }

    private Drawable getServiceIcon(Class<? extends Service> serviceClass, PackageManager pm) {
        Drawable icon = null;
        if (serviceClass != null) {
            try {
                Resources res = getContext().getResources();
                ServiceInfo si = pm.getServiceInfo(new ComponentName(getContext(), serviceClass), 0);
                if (si.icon != 0) {
                    icon = res.getDrawable(si.icon);
                    int bond = (int) (res.getDisplayMetrics().density * 48 + 0.5f);
                    icon.setBounds(0, 0, bond, bond);
                }
            } catch (PackageManager.NameNotFoundException e) {
                // none
            } catch (Resources.NotFoundException e) {
                // none
            }
        }
        return icon;
    }
}
