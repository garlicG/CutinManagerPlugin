package com.garlicg.cutinsupportsample;

import android.os.Bundle;

import com.garlicg.cutinsupport.CutinItem;
import com.garlicg.cutinsupport.ui.SimpleCutinPanel;

import java.util.ArrayList;

public class SamplePanel extends SimpleCutinPanel {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList<CutinItem> items = new ArrayList<>();
        items.add(new CutinItem(SampleCutinService.class, "Sample1", 1));
        items.add(new CutinItem(SampleCutinService.class, "Sample2", 2));
        items.add(new CutinItem(SampleCutinService.class, "Sample3", 3));

        setCutinItems(items);
    }
}
