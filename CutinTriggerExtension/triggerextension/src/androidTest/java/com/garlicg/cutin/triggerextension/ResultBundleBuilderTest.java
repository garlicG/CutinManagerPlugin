package com.garlicg.cutin.triggerextension;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

/**
 * Created by Hattsun on 2015/06/16.
 *
 * @author Hattsun
 */
@RunWith(AndroidJUnit4.class)
public class ResultBundleBuilderTest {

    @Test
    public void for_build() throws Exception {
        ArrayList<TriggerSetting> sample = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            final int finalI = i;
            sample.add(new TriggerSetting() {
                @Override
                public long getId() {
                    return finalI;
                }

                @Nullable
                @Override
                public String getTriggerName() {
                    return String.valueOf(finalI);
                }

                @Nullable
                @Override
                public String getContentTitleHint() {
                    return "TitleHint#" + getId();
                }

                @Nullable
                @Override
                public String getContentMessageHint() {
                    return "MessageHint#" + getId();
                }
            });
        }

        ResultBundleBuilder builder = new ResultBundleBuilder(InstrumentationRegistry.getContext());
        builder.add(sample);

        Bundle bundle = builder.build();

        Assert.assertEquals(bundle.size(), 3);
        Assert.assertEquals(bundle.containsKey(TriggerConst.EXTRA_PACKAGE_NAME), true);
        Assert.assertEquals(bundle.containsKey(TriggerConst.EXTRA_VERSION), true);
        Assert.assertEquals(bundle.containsKey(TriggerConst.EXTRA_SETTINGS), true);

        Assert.assertEquals(bundle.getString(TriggerConst.EXTRA_PACKAGE_NAME), InstrumentationRegistry.getContext().getPackageName());
        Assert.assertEquals(bundle.getInt(TriggerConst.EXTRA_VERSION), TriggerInfo.VERSION);

        String settingsString = bundle.getString(TriggerConst.EXTRA_SETTINGS);
        Assert.assertNotNull(settingsString);

        JSONArray jsonArray = new JSONArray(settingsString);
        Assert.assertEquals(jsonArray.length(), 3);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.optJSONObject(i);

            Assert.assertEquals(obj.has(TriggerConst._ID), true);
            Assert.assertEquals(obj.has(TriggerConst._NAME), true);
            Assert.assertEquals(obj.has(TriggerConst._CONTENT_TITLE_HINT), true);
            Assert.assertEquals(obj.has(TriggerConst._CONTENT_MESSAGE_HINT), true);

            Assert.assertEquals(obj.optLong(TriggerConst._ID), i);
            Assert.assertEquals(obj.optString(TriggerConst._NAME), String.valueOf(i));
            Assert.assertEquals(obj.optString(TriggerConst._CONTENT_TITLE_HINT), "TitleHint#" + i);
            Assert.assertEquals(obj.optString(TriggerConst._CONTENT_MESSAGE_HINT), "MessageHint#" + i);
        }
    }
    @Test
    public void for_build2() throws Exception {
        // without TriggerSetting
        ArrayList<Integer> sample = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            sample.add(i);
        }

        ResultBundleBuilder builder = new ResultBundleBuilder(InstrumentationRegistry.getContext());
        builder.addAll(new TriggerSetting.Reader<Integer>() {
            @Override
            public long getId(@NonNull Integer integer) {
                return integer;
            }

            @Nullable
            @Override
            public String getTriggerName(@NonNull Integer integer) {
                return String.valueOf(integer);
            }

            @Nullable
            @Override
            public String getContentTitleHint(@NonNull Integer integer) {
                return "TitleHint#" + getId(integer);
            }

            @Nullable
            @Override
            public String getContentMessageHint(@NonNull Integer integer) {
                return "MessageHint#" + getId(integer);
            }
        }, sample);

        Bundle bundle = builder.build();

        Assert.assertEquals(bundle.size(), 3);
        Assert.assertEquals(bundle.containsKey(TriggerConst.EXTRA_PACKAGE_NAME), true);
        Assert.assertEquals(bundle.containsKey(TriggerConst.EXTRA_VERSION), true);
        Assert.assertEquals(bundle.containsKey(TriggerConst.EXTRA_SETTINGS), true);

        Assert.assertEquals(bundle.getString(TriggerConst.EXTRA_PACKAGE_NAME), InstrumentationRegistry.getContext().getPackageName());
        Assert.assertEquals(bundle.getInt(TriggerConst.EXTRA_VERSION), TriggerInfo.VERSION);

        String settingsString = bundle.getString(TriggerConst.EXTRA_SETTINGS);
        Assert.assertNotNull(settingsString);

        JSONArray jsonArray = new JSONArray(settingsString);
        Assert.assertEquals(jsonArray.length(), 3);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.optJSONObject(i);

            Assert.assertEquals(obj.has(TriggerConst._ID), true);
            Assert.assertEquals(obj.has(TriggerConst._NAME), true);
            Assert.assertEquals(obj.has(TriggerConst._CONTENT_TITLE_HINT), true);
            Assert.assertEquals(obj.has(TriggerConst._CONTENT_MESSAGE_HINT), true);

            Assert.assertEquals(obj.optLong(TriggerConst._ID), i);
            Assert.assertEquals(obj.optString(TriggerConst._NAME), String.valueOf(i));
            Assert.assertEquals(obj.optString(TriggerConst._CONTENT_TITLE_HINT), "TitleHint#" + i);
            Assert.assertEquals(obj.optString(TriggerConst._CONTENT_MESSAGE_HINT), "MessageHint#" + i);
        }
    }
}
