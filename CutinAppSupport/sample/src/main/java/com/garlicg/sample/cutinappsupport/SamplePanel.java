package com.garlicg.sample.cutinappsupport;

import android.os.Bundle;

import com.garlicg.cutin.appsupport.CutinItem;
import com.garlicg.cutin.appsupport.ui.SectionItem;
import com.garlicg.cutin.appsupport.ui.SimpleCutinPanel;

import java.util.ArrayList;

public class SamplePanel extends SimpleCutinPanel {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList<CutinItem> items = new ArrayList<>();
        items.add(new SectionItem("Tutorial"));
        items.add(new CutinItem(SampleService.class, "Sample1", SampleService.ORDER_SAMPLE));
        items.add(new SectionItem("Simple Samples"));
        items.add(new CutinItem(SampleService.class, "Garlin1", SampleService.ORDER_GARLIN_1));
        items.add(new CutinItem(SampleService.class, "Garlin2", SampleService.ORDER_GARLIN_2));
        items.add(new CutinItem(SampleService.class, "Garlin3", SampleService.ORDER_GARLIN_3));
        items.add(new SectionItem("Engine Samples"));
        items.add(new CutinItem(SampleService.class, "Banner", SampleService.ORDER_BANNER));
        items.add(new CutinItem(SampleService.class, "Surface", SampleService.ORDER_SURFACE));
        items.add(new CutinItem(SampleService.class, "GLSurface", SampleService.ORDER_GL_SURFACE));

        setCutinItems(items);
    }
}
