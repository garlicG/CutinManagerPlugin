# CutinTriggerExtension

<img src="../picture/anim/demo_stopwatch1.gif" width="33%"> <img src="../picture/anim/demo_stopwatch2.gif" width="33%">

This is the development kit to add extension trigger to the CUT-IN Manager.

Try out the sample Stopwatch app on the [Google Play](https://play.google.com/store/apps/details?id=com.garlicg.sample.stopwatchtrigger).


# Usage

Write build.gradle
--

```groovy

dependencies {
    compile 'com.garlicg:cutin-trigger-extension:0.9.2'
}

```

Create selectable settings and register triggers
--

### 1.Pickable `intent-filter` from the CUT-IN Manager

```xml

<activity
    android:name=".TriggerSettingsActivity"
    android:label="@string/app_name">
    <intent-filter>
        <action android:name="com.garlicg.cutin.action.PICK" />
        <category android:name="com.garlicg.cutin.category.TRIGGER" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
</activity>
```

### 2.Return trigger settings to the CUT-IN Manager

Pickable Activity need to set `TriggerSetting` results using `ResultBundleBuilder`.


`TriggerSetting` parameters:

type | name | description
--- | --- | ---
long | triggerId | Unique value in the app.
String | triggerName | Trigger name.
String<br>(Optional) | contentTitleHint | CUT-IN Apps can handle it as a content ingredient at the time of demo playback. When it is not defined, `Activity label` is substituted for it.
String<br>(Optional) | contentMessageHint | CUT-IN Apps can handle it as a content ingredient at the time of demo playback. When it is not defined, `triggerName` is substituted for it.

Sample of setting result using `ResultBundleBuilder` on pickable Activity:

```java

@Override
public void onBackPressed() {
    ArrayList<TriggerSetting> list = getEnableTriggerList();
    
    Intent intent = new Intent();
    ResultBundleBuilder builder = new ResultBundleBuilder(this);
    intent.putExtras(builder.addAll(list).build());
    
    setResult(RESULT_OK, intent);
    finish();
}
```


### TIPS: Simple UI with `TriggerSetting` object

Create object implements `TriggerSetting` interface:

```java

public class TriggerObject implements TriggerSetting{

    private final long mTriggerId;
    private final String mTriggerName;
    private final String mContentTitle;
    private final String mContentMessage;

    public TriggerObject(long triggerId, String triggerName, @Nullable String contentTitleHint, @Nullable String contentMessageHint) {
        mTriggerId = triggerId;
        mTriggerName = triggerName;
        mContentTitle = contentTitleHint;
        mContentMessage = contentMessageHint;
    }

    @Override
    public long getId() {
        return mTriggerId;
    }

    @Override
    public String getTriggerName() {
        return mTriggerName;
    }

    @Nullable
    @Override
    public String getContentTitleHint() {
        return mContentTitle;
    }

    @Nullable
    @Override
    public String getContentMessageHint() {
        return mContentMessage;
    }


    @Override
    public String toString() {
        return mTriggerName;
    }

```

Show selectable `ListView` on pickable Activity:

```java
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Create all trigger list this app has.
    ArrayList<TriggerSetting> list = new ArrayList<>();
    list.add(new TriggerObject(0L, "Name0" , "TitleHint0" , "MessageHint0"));
    list.add(new TriggerObject(1L, "Name1", "TitleHint1", "MessageHint1");
    list.add(new TriggerObject(2L, "Name2" , null , null));

    // Create listView
    mListView = new ListView(this);
    mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    setContentView(mListView);
    ArrayAdapter<TriggerSetting> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_checked, list);
    mListView.setAdapter(adapter);
}

/**
 * Get current checkOn list from listView.
 */
private ArrayList<TriggerSetting> getEnableTriggerList() {
    ArrayList<TriggerSetting> triggerList = new ArrayList<>();
    SparseBooleanArray items = mListView.getCheckedItemPositions();
    for (int position = 0; position < items.size(); position++) {
        if (items.get(position)) {
            triggerList.add((TriggerSetting) mListView.getItemAtPosition(position));
        }
    }
    return triggerList;
}

```

### TIPS: TriggerSetting.Reader

If implementing `TriggerSetting` interface have probrems such as method name duplication or frameworks rules, use the `TriggerSetting.Reader` pattern for building.

Sample of reading `Integer`:

```java

ResultBundleBuilder builder = new ResultBundleBuilder(context);
builder.add(new Reader<Integer>() {

    public long getId(Integer integer) {
        return integer.longvalue();
    }
    
    public String getTriggerName(Integer integer) {
        return "name#" + integer;
    }
    
}, 1, 2, 3);


```


3. Fire trigger
--

Use `Context#sendBroadcast` and `ResultBundleBuilder` to send a trigger to the CUT-IN Manager.

Trigger parameters on fire:

type | name | description
--- | --- | ---
long | triggerId | Registered triggerId.
String<br>(Optional) | contentTitle | CUT-IN Apps can handle it as a content ingredient at the time of playback. When it is not defined, `contentTitleHint` is substituted for it.
String<br>(Optional) | contentMessage | CUT-IN Apps can handle it as a content ingredient at the time of playback. When it is not defined, `contentMessageHint` is substituted for it.

Sample of sending a trigger:

``` java

FireIntentBuilder builder = new FireIntentBuilder(context, triggerId);
builder.setContentTitle("TITLE");
builder.setContentMessage("Content message here");
context.sendBroadcast(builder.intent());

```


4. Handle the CUT-IN Manager uninstall
--

To control such as database or preferences, create `Broadcast Receiver` which receives the CUT-IN Manager uninstall.


```java

public class ManagerRemovedReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // ignore case
        if (intent == null || intent.getBooleanExtra(Intent.EXTRA_REPLACING, false)) {
            return;
        }

        if(TriggerConst.MANAGER_PACKAGE.equals(getPackageName(intent))){
            // TODO Inavtive CUT-IN Manager specific.
        }
    }

    private String getPackageName(Intent intent){
        Uri uri = intent.getData();
        return uri == null ? null : uri.getSchemeSpecificPart();
    }
}
```

AndroidManifest.xml:

```xml
<receiver android:name=".ManagerRemovedReceiver">
    <intent-filter>
        <action android:name="android.intent.action.PACKAGE_REMOVED" />
        <data android:scheme="package" />
    </intent-filter>
</receiver>
```


# API Level

CutinTriggerExtension works on API level 10+.


# License


    Copyright 2015 Takahiro GOTO
    Copyright 2015 Hattsun
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
