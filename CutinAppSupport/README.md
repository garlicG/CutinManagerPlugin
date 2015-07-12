# CutinAppSupport


<img src="../picture/anim/demo_garlin1.gif" width="33%"> <img src="../picture/anim/demo_garlin2.gif" width="33%">

Supports to make CUT-IN App as the plugin of the CUT-IN Manager.

Sample app on Google play [here]().[FIXME]

# Quick Start For Android Studio

<img src="../picture/anim/demo_flow1.gif" width="33%"> <img src="../picture/anim/demo_flow2.gif" width="33%">



Create a simple CUT-IN App which is the following configuration.

- `CutinEngine` for simple animation using ic-launcher.
- `CutinService` to controll `CutinEngine`.
- `CutinPanel UI` works with the CUT-IN Manager.


1. New Project
--

Create a new android project with choosing "Add No Activity".


2. Write build.gradle
--

```groovy

dependencies {
    compile 'com.android.support:appcompat-v7:21.1.1'
    compile 'com.garlicg:cutin-app-support:3.0.4'
}

```


3. Add permission to AndroidManifest.xml
--

```xml

<uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />
```




4. Create CutinEngine for simple animation
--

`CutinEngine` is abstract class to set layout and show animation. You must to call `finishCutin()` when animation end.

SampleEngine.java

```java

public class SampleEngine extends CutinEngine{

    public SampleEngine(CutinService cutinService) {
        super(cutinService);
    }

    private ImageView mImageView;

    @Override
    public View onCreateLayout(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View root = inflater.inflate(R.layout.engine_sample , null);
        mImageView = (ImageView)root.findViewById(R.id.image);
        return root;
    }

    @Override
    public void onStart() {
        int centerX = mImageView.getWidth()/2;
        int centerY = mImageView.getHeight()/2;

        // Rotate a second.
        RotateAnimation rotate = new RotateAnimation(0, 1170 , centerX , centerY);
        rotate.setDuration(1000);
        rotate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // must finish cutin when end of anim.
                finishCutin();
            }
        });

        mImageView.startAnimation(rotate);
    }
}
```

layout/engine_sample.xml

```xml 

<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <ImageView
        android:id="@+id/image"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center"
        android:src="@drawable/ic_launcher" />

</FrameLayout>
```


5. Create CutinService to controll CutinEngine
--

SampleService.java

```java

public class SampleService extends CutinService {

    public static final long ORDER_SAMPLE = 0;  

    @Override
    protected CutinEngine onCreateEngine(Intent intent, long orderId) {
        if(orderId == ORDER_SAMPLE){
            return new SampleEngine(this);
        }
         // can define other engine if need
        else {
        }
        
        // stop service immediately and show nothing.
        return null;
    }
}
``` 


AndroidManifest.xml

```xml

<service
    android:name=".SampleService"
    android:icon="@drawable/ic_launcher">
    <intent-filter>
        <!-- action name must to be full path of Service Class -->
        <action android:name="yourpackage.SampleService" />
    </intent-filter>
</service>
```



6. Create CutinPanel UI
--

`SimpleCutinPanel` is subclass of `android.app.Activity`. It has feature belows

- Light and Dark Panel Theme depends on CUT-IN Manager Theme.
- Registration `CutinService`, `label` , and `orderId` to the CUT-IN Manager.
- Controll CUT-IN Demo playing.


SamplePanel.java

```java

public class SamplePanel extends SimpleCutinPanel{

    @Override
    protected void onCreateCutins(CutinScreen cutinScreen) {
        ArrayList<CutinItem> items = new ArrayList<>();

        // serviceClass , label , order id 
        items.add(new CutinItem(SampleService.class, "Sample" , 0);
        // can add other CUT-IN
        // items.add(new CutinItem(SampleService.class, "Other", 1);

        cutinScreen.setCutinList(items);
    }
}
```


AndroidManifest.xml

```xml
<activity
    android:name=".SamplePanel"
    android:exported="true"
    android:theme="@style/CutinPanel"
    android:label="@string/app_name" >

    <!-- for CUT-IN Manager -->
    <intent-filter>
        <action android:name="com.garlicg.cutin.action.PICK" />
        <category android:name="com.garlicg.cutin.category.RESOURCE" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
</activity>

<!-- android:enabled - false do not show launcher icon -->
<activity-alias
    android:name=".LauncherActivityAlias"
    android:enabled="false"
    android:targetActivity=".SamplePanel">
    <intent-filter>
        <action android:name="android.intent.action.MAIN" />
        <category android:name="android.intent.category.LAUNCHER" />
    </intent-filter>
</activity-alias>
```

7. Confirmation
--

Launch the CUT-IN Manager and confirm to register and play CUT-IN.


# About UI

If you do not want to use CUT-IN Panel , you should create by your way.
How to work with CUT-IN Manager is here.[FIXME]


# Proguard

```
-dontwarn com.garlicg.cutinsupport.**
```


# API Level

CutinAppSupport works on API level 10+.





# License


    Copyright 2015 Takahiro GOTO

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

