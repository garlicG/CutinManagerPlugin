package com.garlicg.cutin.triggerextension;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * The Builder for Trigger's Setting, build the Result Bundle.
 * <p/>
 * <h1>Examples</h1>
 * <h2>{@link TriggerSetting} interface case</h2>
 * <pre>
 *     BundleBuilder builder = new BundleBuilder(context);
 *     builder.add(triggerSetting);
 *
 *     Intent intent = new Intent().putExtras(builder.build());
 *     setResult(Activity.RESULT_OK, intent);
 * </pre>
 * <h2>TriggerSetting.Reader case</h2>
 * Trigger's Setting by int value.
 * <p/>
 * <pre>
 *     BundleBuilder builder = new BundleBuilder(context);
 *     builder.add(new Reader<Integer>() {
 *
 *         public long getId(Integer integer) {
 *             return integer.longvalue();
 *         }
 *
 *         public String getTriggerName(Integer integer) {
 *             return "name#" + integer;
 *         }
 *     }, 1, 2, 3);
 *
 *     Intent intent = new Intent().putExtras(builder.build());
 *     setResult(Activity.RESULT_OK, intent);
 * </pre>
 *
 * @author Hattsun
 */
public class ResultBundleBuilder {

    private TriggerInfo mInfo;

    private final ElementSet<TriggerSetting> mPreSet = new ElementSet<>(TriggerSetting.READER, null);

    private final List<ElementSet<?>> mElements = new ArrayList<>();

    {
        mElements.add(mPreSet);
    }

    public ResultBundleBuilder(Context context) {
        if (context == null) {
            throw new NullPointerException("context must be not null.");
        }

        mInfo = new TriggerInfo(context);
    }

    /**
     * Add any settings.
     *
     * @param reader  {@link TriggerSetting.Reader}
     * @param objects E
     * @param <E>     any
     * @return this
     * @see TriggerSetting.Reader
     */
    @SafeVarargs
    public final <E> ResultBundleBuilder add(@NonNull TriggerSetting.Reader<E> reader, E... objects) {
        if (objects != null && objects.length > 0) {
            mElements.add(new ElementSet<>(reader, Arrays.asList(objects)));
        }

        return this;
    }

    /**
     * Add any settings.
     *
     * @param reader  {@link TriggerSetting.Reader}
     * @param objects Collection of E
     * @param <E>     any
     * @return this
     * @see TriggerSetting.Reader
     */
    public <E> ResultBundleBuilder addAll(@NonNull TriggerSetting.Reader<E> reader, Collection<E> objects) {
        if (objects != null && !objects.isEmpty()) {
            mElements.add(new ElementSet<>(reader, objects));
        }

        return this;
    }

    /**
     * Add settings.
     *
     * @param settings {@link TriggerSetting} instances
     * @return this
     * @see TriggerSetting
     */
    public ResultBundleBuilder add(TriggerSetting... settings) {
        if (settings != null && settings.length > 0) {
            mPreSet.getList().addAll(Arrays.asList(settings));
        }

        return this;
    }

    /**
     * Add settings.
     *
     * @param settings Collection of {@link TriggerSetting}
     * @return this
     * @see TriggerSetting
     */
    public ResultBundleBuilder addAll(Collection<TriggerSetting> settings) {
        if (settings != null && !settings.isEmpty()) {
            mPreSet.getList().addAll(settings);
        }

        return this;
    }

    /**
     * Build result {@link Bundle}
     *
     * @return {@link Bundle}
     */
    public Bundle build() {
        Bundle bundle = new Bundle();
        mInfo.build(bundle);

        JSONArray jsonArray = new JSONArray();
        for (ElementSet element : mElements) {
            if (element == null) continue;

            try {
                element.supply(jsonArray);

            } catch (JSONException ignore) {
            }
        }

        bundle.putString(TriggerConst.EXTRA_SETTINGS, jsonArray.toString());
        return bundle;
    }


    static class ElementSet<E> {

        private TriggerSetting.Reader<E> mReader;

        private List<E> mList;

        public ElementSet(@NonNull TriggerSetting.Reader<E> reader, Collection<E> list) {
            mReader = reader;
            if (list != null) {
                mList = new ArrayList<>(list);

            } else {
                mList = new ArrayList<>();
            }
        }

        public List<E> getList() {
            return mList;
        }

        void supply(@NonNull JSONArray jsonArray) throws JSONException {
            for (E e : mList) {
                jsonArray.put(
                        new JSONObject()
                                .put(TriggerConst._ID, mReader.getId(e))
                                .put(TriggerConst._NAME, mReader.getTriggerName(e))
                                .put(TriggerConst._CONTENT_TITLE_HINT, mReader.getContentTitleHint(e))
                                .put(TriggerConst._CONTENT_MESSAGE_HINT, mReader.getContentMessageHint(e))
                );
            }
        }
    }
}
