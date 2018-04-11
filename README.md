## This

主要实现了简单的 Grid 选择颜色以及 SeekBar 的形式选择颜色

* 可以使用 Grid View 的模式来选择颜色
* 可以切换到 SeekBar 模式来选择颜色
* 可以调整透明度
* 整个调整视图适合想要特殊放置调整功能去位置的人，比如我


不足的地方在于

* 三个不同功能之间的操作还不"流畅"，即一定要滑动一下才可以触发透明度选项
* Grid Color 视图的颜色略丑
* 颜色条中，颜色过度得很丑


![screenshot](screenshot/screenshot.gif)


## usage

[![](https://jitpack.io/v/tanzby/EasyColorSelect.svg)](https://jitpack.io/#tanzby/EasyColorSelect)

**Step 1.** Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:

```xml
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```


**Step 2.** Add the dependency

```xml
dependencies {
    compile 'com.github.tanzby:EasyColorSelect:34eddb3a6e'
}
```

** Step 3.**  add to your layout
```xml
<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="#444">

    <TextView
        android:id="@+id/textView"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true"
        android:layout_alignParentStart="true"
        android:layout_marginBottom="8dp"
        android:layout_marginTop="8dp"
        android:gravity="center"
        android:text="TextView"
        android:textColor="#fff"
        android:textSize="24sp"
        android:textStyle="bold"
        app:layout_constraintBottom_toTopOf="@+id/ColorOperateBoard"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />


    <pub.tanzby.easycolorselect.ColorOperateBoard
        android:id="@+id/ColorOperateBoard"
        android:layout_width="0dp"
        android:layout_height="280dp"
        android:layout_marginBottom="8dp"
        android:layout_marginTop="8dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/textView" />

</android.support.constraint.ConstraintLayout>

```


** Step 4.**  add to your class

```java

public class MainActivity extends AppCompatActivity {
    TextView tv;
    ColorOperateBoard board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.textView);
        board=findViewById(R.id.ColorOperateBoard);
        board.setOnColorOperateListener(new ColorOperateBoard.OnColorOperateListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onColorChange(int color) {
                tv.setText("changing: "+ String.format("%x",color));
                tv.setTextColor(color);
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onColorSelect(int color) {
                tv.setText("select: "+ String.format("%x",color));
                tv.setTextColor(color);
            }
        });
    }
}

```