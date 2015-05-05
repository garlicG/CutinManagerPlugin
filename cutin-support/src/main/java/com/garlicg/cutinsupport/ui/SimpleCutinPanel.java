package com.garlicg.cutinsupport.ui;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.garlicg.cutinsupport.CutinItem;
import com.garlicg.cutinsupport.R;

import java.util.ArrayList;

/**
 * Created by goto.t on 2015/04/15.
 */
public abstract class SimpleCutinPanel extends CutinPanel implements AdapterView.OnItemClickListener , View.OnClickListener{

    private TextView mOptionButton;
    public TextView getOptionButton(){
        return mOptionButton;
    }

    private ListView mListView;
    public ListView getListView(){
        return mListView;
    }

    private TextView mOkButton;
    public TextView getOkButton(){
        return mOkButton;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.cp_simple_screen);

        // title
        PackageManager pm = getPackageManager();
        ApplicationInfo info = getApplicationInfo();
        Drawable icon = info.loadIcon(pm);
        CharSequence label = info.loadLabel(pm);
        int rect = (int) (getResources().getDisplayMetrics().density * 40 + 0.5f);
        icon.setBounds(0,0,rect , rect);

        TextView titleView = findById(R.id.title);
        titleView.setText(label);
        titleView.setCompoundDrawables(icon , null , null , null);

        // list
        mListView = findById(android.R.id.list);
        mListView.setOnItemClickListener(this);

        // OK button
        mOkButton = findById(android.R.id.button2);
        mOkButton.setOnClickListener(this);

        // Option button
        mOptionButton = findById(android.R.id.button1);
        mOptionButton.setVisibility(View.GONE);

        if(isPickable()){
            if(savedInstanceState == null){
                setOkButtonEnable(getCheckedItem() != null);
            }
            mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }
        else {
            setOkButtonEnable(true);
        }

        // resize window size like dialog
        resizeWindow();
    }



    private CutinItem getCheckedItem(){
        int position = mListView.getCheckedItemPosition();
        Object item = mListView.getItemAtPosition(position);
        if(item != null && item instanceof CutinItem){
            return (CutinItem)item;
        }
        else{
            return null;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Object item = adapterView.getItemAtPosition(i);
        if(item instanceof CutinItem){
            CutinItem ci = (CutinItem)item;
            getDemo().play(ci);
            setOkButtonEnable(true);
        }
    }

    @Override
    public void onClick(View view) {
        handleOKClick(getCheckedItem());
    }

    public void setOkButtonEnable(boolean enable){
        mOkButton.setEnabled(enable);
    }


    private boolean mEnableServiceIcon = false;

    public void setServiceIconEnable(boolean enable){
        mEnableServiceIcon = enable;
    }

    protected ListAdapter getListAdapter(ArrayList<CutinItem> list) {
        if(mEnableServiceIcon){
            return new ServiceIconListAdapter(this,list , isPickable());
        }else{
            return new SimpleCutinListAdapter(this,list , isPickable());
        }
    }

    public void setOptionButton(CharSequence text , View.OnClickListener listener){
        mOptionButton.setVisibility(View.VISIBLE);
        mOptionButton.setOnClickListener(listener);
        if(!TextUtils.isEmpty(text)){
            mOptionButton.setText(text);
        }
    }

    public void setCutinItems(ArrayList<CutinItem> list){
        // Set ListView with SingleChoiceMode.
        mListView.setAdapter(getListAdapter(list));
    }

    private <T extends View> T findById(int id){
        return (T)findViewById(id);
    }
}
