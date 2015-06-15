package com.garlicg.cutinsupportsample;

import android.os.Bundle;

import com.garlicg.cutinsupport.CutinItem;
import com.garlicg.cutinsupport.ui.CategoryItem;
import com.garlicg.cutinsupport.ui.SimpleCutinPanel;

import java.util.ArrayList;

public class SamplePanel extends SimpleCutinPanel {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList<CutinItem> items = new ArrayList<>();
        items.add(new CutinItem(SampleService.class, "Sample1", SampleService.ORDER_SAMPLE_1));
        items.add(new CutinItem(SampleService.class, "Sample2", SampleService.ORDER_SAMPLE_2));
        items.add(new CutinItem(SampleService.class, "Sample3", SampleService.ORDER_SAMPLE_3));
        setCutinItems(items);
    }
}
