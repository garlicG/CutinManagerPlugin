package com.garlicg.cutin.triggerextension;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Settings for Trigger
 * <p/>
 * There are 2 patterns implementation of Trigger setting.
 * <ul>
 * <li>
 * Creates any class that implements {@link com.garlicg.cutin.triggerextension.TriggerSetting}
 * </li>
 * <li>
 * Implement {@link com.garlicg.cutin.triggerextension.TriggerSetting.Reader} to read the value from any instances
 * </li>
 * </ul>
 * <p/>
 * Easily implement by using {@link com.garlicg.cutin.triggerextension.TriggerSetting} interface.
 * If that can't be implemented in such as method name duplication, frameworks rules, use the {@link com.garlicg.cutin.triggerextension.TriggerSetting.Reader} pattern.<br>
 * Of course, the {@link com.garlicg.cutin.triggerextension.TriggerSetting.Reader} pattern may be use positively.
 *
 * @author Hattsun
 */
public interface TriggerSetting {

    /**
     * Any ID.<br>
     * ID is associated with a particular CUT-IN of the CUT-IN App by the CUT-IN Manager.
     *
     * @return trigger's ID
     */
    long getId();

    /**
     * Any Trigger name. (not application name)<br>
     * This value is displayed as a trigger name in the CUT-IN Manager.
     *
     * @return trigger name
     */
    String getTriggerName();

    /**
     * Any hint text of content title.<br>
     * This value is used in the CUT-IN preview in the CUT-IN Manager.<br>
     * Also, when this app send the trigger broadcast to the CUT-IN Manager, it will be used as the default.
     *
     * @return hint text of the content title
     */
    @Nullable
    String getContentTitleHint();

    /**
     * Any hint text of content message.<br>
     * This value is used in the CUT-IN preview in the CUT-IN Manager.<br>
     * Also, when this app send the trigger broadcast to the CUT-IN Manager, it will be used as the default.
     *
     * @return hint text of the content message
     */
    @Nullable
    String getContentMessageHint();

    /**
     * The implementation for setting values from any instance.
     *
     * @param <E>
     */
    abstract class Reader<E> {

        /**
         * @param e any instance
         * @return trigger's ID
         * @see TriggerSetting#getId()
         */
        public abstract long getId(@NonNull E e);

        /**
         * @param e any instance
         * @return trigger name
         * @see TriggerSetting#getTriggerName()
         */
        public abstract String getTriggerName(@NonNull E e);

        /**
         * @param e any instance
         * @return hint text of the content title
         * @see TriggerSetting#getContentTitleHint()
         */
        @Nullable
        public String getContentTitleHint(@NonNull E e) {
            return null;
        }

        /**
         * @param e any instance
         * @return hint text of the content message
         * @see TriggerSetting#getContentMessageHint()
         */
        @Nullable
        public String getContentMessageHint(@NonNull E e) {
            return null;
        }
    }


    /**
     * {@link com.garlicg.cutin.triggerextension.TriggerSetting.Reader} implementation for TriggerSetting interface.
     */
    Reader<TriggerSetting> READER = new Reader<TriggerSetting>() {

        @Override
        public long getId(@NonNull TriggerSetting setting) {
            return setting.getId();
        }

        @Override
        public String getTriggerName(@NonNull TriggerSetting setting) {
            return setting.getTriggerName();
        }

        @Nullable
        @Override
        public String getContentTitleHint(@NonNull TriggerSetting setting) {
            return setting.getContentTitleHint();
        }

        @Nullable
        @Override
        public String getContentMessageHint(@NonNull TriggerSetting setting) {
            return setting.getContentMessageHint();
        }
    };
}
