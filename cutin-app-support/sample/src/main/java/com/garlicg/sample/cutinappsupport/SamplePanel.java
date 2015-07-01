package com.garlicg.sample.cutinappsupport;

import android.os.Bundle;

import com.garlicg.cutin.appsupport.CutinItem;
import com.garlicg.cutin.appsupport.ui.CategoryItem;
import com.garlicg.cutin.appsupport.ui.SimpleCutinPanel;

import java.util.ArrayList;

public class SamplePanel extends SimpleCutinPanel {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList<CutinItem> items = new ArrayList<>();
        items.add(new CategoryItem("Simple Samples"));
        items.add(new CutinItem(SampleService.class, "Sample1", SampleService.ORDER_SAMPLE_1));
        items.add(new CutinItem(SampleService.class, "Sample2", SampleService.ORDER_SAMPLE_2));
        items.add(new CutinItem(SampleService.class, "Sample3", SampleService.ORDER_SAMPLE_3));
        items.add(new CategoryItem("Engine Samples"));
        items.add(new CutinItem(SampleService.class, "Banner", SampleService.ORDER_BANNER));
        items.add(new CutinItem(SampleService.class, "Surface", SampleService.ORDER_SURFACE));
        items.add(new CutinItem(SampleService.class, "GLSurface", SampleService.ORDER_GL_SURFACE));

        setCutinItems(items);
    }
}
