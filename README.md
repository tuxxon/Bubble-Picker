# Bubble-Picker for removing items when clicking or touching.

```
  I thought Irina Galata doesn't work on this project for more.
  So, I created this repo for bubble-picker to upgrade.
```

<img src="shot.gif"/>

## Requirements
- Android SDK 16+

## Usage

Add to your root build.gradle:
```Groovy
allprojects {
	repositories {
	...
	maven { url "https://jitpack.io" }
	}
}
```


## How to use this library

Add `BubblePicker` to your xml layout

```xml
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <com.igalata.bubblepicker.rendering.BubblePicker
        android:id="@+id/picker"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:backgroundColor="@android:color/white" />

</FrameLayout>
```

Override onResume() and onPause() methods to call the same methods from the `BubblePicker`

Kotlin
```kotlin
override fun onResume() {
      super.onResume()
      picker.onResume()
}

override fun onPause() {
      super.onPause()
      picker.onPause()
}
```

Java
```java
@Override
protected void onResume() {
      super.onResume();
      picker.onResume();
}

@Override
protected void onPause() {
      super.onPause();
      picker.onPause();
}
```

Pass the `PickerItem` list to the `BubblePicker`

Kotlin
```kotlin
val titles = resources.getStringArray(R.array.countries)
val colors = resources.obtainTypedArray(R.array.colors)
val images = resources.obtainTypedArray(R.array.images)

picker.adapter = object : BubblePickerAdapter {

            override val totalCount = titles.size

            override fun getItem(position: Int): PickerItem {
                return PickerItem().apply {
                    title = titles[position]
                    gradient = BubbleGradient(colors.getColor((position * 2) % 8, 0),
                            colors.getColor((position * 2) % 8 + 1, 0), BubbleGradient.VERTICAL)
                    typeface = mediumTypeface
                    textColor = ContextCompat.getColor(this@DemoActivity, android.R.color.white)
                    backgroundImage = ContextCompat.getDrawable(this@DemoActivity, images.getResourceId(position, 0))
                }
            }
}
```

Java
```java
final String[] titles = getResources().getStringArray(R.array.countries);
final TypedArray colors = getResources().obtainTypedArray(R.array.colors);
final TypedArray images = getResources().obtainTypedArray(R.array.images);

picker.setAdapter(new BubblePickerAdapter() {
            @Override
            public int getTotalCount() {
                return titles.length;
            }

            @NotNull
            @Override
            public PickerItem getItem(int position) {
                PickerItem item = new PickerItem();
                item.setTitle(titles[position]);
                item.setGradient(new BubbleGradient(colors.getColor((position * 2) % 8, 0),
                        colors.getColor((position * 2) % 8 + 1, 0), BubbleGradient.VERTICAL));
                item.setTypeface(mediumTypeface);
                item.setTextColor(ContextCompat.getColor(DemoActivity.this, android.R.color.white));
                item.setBackgroundImage(ContextCompat.getDrawable(DemoActivity.this, images.getResourceId(position, 0)));
                return item;
            }
});
```

Specify the `BubblePickerListener` to get notified about events

Kotlin
```kotlin
picker.listener = object : BubblePickerListener {
            override fun onBubbleSelected(item: PickerItem) {

            }

            override fun onBubbleDeselected(item: PickerItem) {

            }

            override fun onBubbleRemoved(item: PickerItem) {

            }

}
```

Java
```java
picker.setListener(new BubblePickerListener() {
            @Override
            public void onBubbleSelected(@NotNull PickerItem item) {
                
            }

            @Override
            public void onBubbleDeselected(@NotNull PickerItem item) {

            }

            @Override
            public void onBubbleRemoved(@NotNull PickerItem item) {

            }

});
```

To get all selected items use `picker.selectedItems` variable in Kotlin or `picker.getSelectedItems()` method in Java.

For more usage examples please review the sample app

## Changelog

 -- This branch is for removing items in Bubble-Picker.

* `icon` parameter added to place an image on a bubble along with the title 
* `iconOnTop` parameter added to control position of the icon on a bubble
* `textSize` parameter added
* `BubblePicker.bubbleSize` variable now can be changed from 1 to 100

## Reference

Check this [Original Bubble-Picker](https://github.com/igalata/Bubble-Picker)


