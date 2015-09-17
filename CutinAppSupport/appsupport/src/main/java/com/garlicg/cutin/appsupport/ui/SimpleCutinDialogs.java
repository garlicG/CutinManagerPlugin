package com.garlicg.cutin.appsupport.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;

import com.garlicg.cutin.appsupport.ManagerUtils;
import com.garlicg.cutin.appsupport.R;

public class SimpleCutinDialogs {

    public static abstract class SimpleAlert extends DialogFragment implements DialogInterface.OnClickListener{

        public void showSingly(FragmentManager manager) {
            final String tag = getDialogTag();
            if(manager.findFragmentByTag(tag) != null)return;
            super.show(manager, tag);
        }

        protected abstract String getDialogTag();
        protected abstract String getMessage();
        protected abstract String getButton();

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(getMessage());
            builder.setPositiveButton(getButton(), this);
            return builder.create();
        }

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {}
    }

    public static class DownloadAlert extends SimpleAlert implements DialogInterface.OnClickListener{
        @Override
        protected String getDialogTag() {
            return "DownloadAlert";
        }

        @Override
        protected String getMessage() {
            return getString(R.string.cp_message_need_manager);
        }

        @Override
        protected String getButton() {
            return getString(R.string.cp_download);
        }

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            Intent intent = ManagerUtils.buildMarketIntent();
            startActivity(intent);
            getActivity().finish();
        }
    }


    public static class LaunchAlert extends SimpleAlert implements DialogInterface.OnClickListener{

        @Override
        protected String getDialogTag() {
            return "LaunchAlert";
        }

        @Override
        protected String getMessage() {
            return getString(R.string.cp_message_launch_manager);
        }

        @Override
        protected String getButton() {
            return getString(R.string.cp_launch);
        }

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            Intent startManager = ManagerUtils.buildLauncherIntent(getActivity());
            startActivity(startManager);
            getActivity().finish();
        }
    }




}
