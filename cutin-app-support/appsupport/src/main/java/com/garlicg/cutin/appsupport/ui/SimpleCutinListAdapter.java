package com.garlicg.cutin.appsupport.ui;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.garlicg.cutin.appsupport.CutinItem;

import java.util.List;

import com.garlicg.cutin.appsupport.R;

public class SimpleCutinListAdapter extends ArrayAdapter<CutinItem> {

    private final int mResourceId;
    private final int mResourceCategoryId;
    private final LayoutInflater mInflater;
    private final boolean mCheckable;

    public SimpleCutinListAdapter(Context context, List<CutinItem> objects , boolean checkable) {
        super(context, 0,objects);
        mResourceId = R.layout.cutin_simple_list_item;
        mResourceCategoryId = R.layout.cutin_simple_list_item_section;
        mInflater = LayoutInflater.from(context);
        mCheckable = checkable;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CutinItem item = getItem(position);

        // category
        if(item instanceof SectionItem){

            // dont care about viewholder because cost of findViewById is none.
            if(convertView == null){
                convertView = mInflater.inflate(mResourceCategoryId, parent , false);
            }
            bindCategory((TextView) convertView , (SectionItem)item);
        }

        // content
        else{
            // dont care about viewholder because cost of findViewById is none.
            if(convertView == null){
                convertView = mInflater.inflate(mResourceId, parent , false);
                if(!mCheckable) ((CheckedTextView)convertView).setCheckMarkDrawable(null);
            }
            bindContent((TextView) convertView , item);
        }

        return convertView;
    }

    protected void bindCategory(TextView textView , SectionItem item){
        textView.setText(item.cutinName);
    }

    protected void bindContent(TextView textView , CutinItem item){
        textView.setText(item.cutinName);
    }

    @Override
    public boolean isEnabled(int position) {
        Object obj = getItem(position);
        return !(obj instanceof SectionItem);
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        CutinItem item = getItem(position);
        return item instanceof SectionItem ? 1 : 0;
    }
}