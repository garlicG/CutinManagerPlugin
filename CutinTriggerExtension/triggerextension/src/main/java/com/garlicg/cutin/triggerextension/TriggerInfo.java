package com.garlicg.cutin.triggerextension;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

/**
 * Base Settings for Trigger.
 *
 * @author Hattsun
 */
public final class TriggerInfo {

    public static final int VERSION = 1;

    private final String packageName;

    private final int version;

    TriggerInfo(String packageName, int version) {
        this.packageName = packageName;
        this.version = version;
    }

    TriggerInfo(@NonNull Context context) {
        this(context.getPackageName(), VERSION);
    }

    public String getPackageName() {
        return packageName;
    }

    public int getVersion() {
        return version;
    }

    public Bundle build(Bundle bundle) {
        bundle = bundle != null ? bundle : new Bundle();
        bundle.putString(TriggerConst.EXTRA_PACKAGE_NAME, packageName);
        bundle.putInt(TriggerConst.EXTRA_VERSION, version);

        return bundle;
    }

    public Bundle build() {
        return build(null);
    }

}
