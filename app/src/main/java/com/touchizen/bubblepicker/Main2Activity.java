package com.touchizen.bubblepicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.igalata.bubblepicker.BubblePickerListener;
import com.igalata.bubblepicker.adapter.BubblePickerAdapter;
import com.igalata.bubblepicker.model.BubbleGradient;
import com.igalata.bubblepicker.model.PickerItem;
import com.igalata.bubblepicker.rendering.BubblePicker;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity implements BubblePickerListener {

    TypedArray bubbleColors;
    private boolean isPickerShowed=false;
    private BubblePicker mBubblePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBubblePicker = (BubblePicker) findViewById(R.id.picker);
        bubbleColors = getResources().obtainTypedArray(R.array.colors);

        setupPickerListener();
    }

    private void setupPickerListener() {

        isPickerShowed=true;
        String sText = "Argentina Bolivia Brazil Chile Costa Rica Dominican Republic Mexico Nicaragua Peru Venezuela Cuba Ecuador El Salvador Haiti Panama Paraguay";
        String[] words = sText.split(" ");

        //Log.d(TAG,"=== DEBUG ====> setupPickerListener()"+ words.length);

        mBubblePicker.setAdapter(new  BubblePickerAdapter() {
            @Override
            public int getTotalCount () {
                return words.length;
            }

            @NotNull
            @Override
            public PickerItem getItem (int position){
                PickerItem item = new PickerItem();
                item.setTitle(words[position]);
                item.setGradient(new BubbleGradient(bubbleColors.getColor((position * 2) % 8, 0),
                        bubbleColors.getColor((position * 2) % 8 + 1, 0), BubbleGradient.VERTICAL));
//                item.setTypeface(mediumTypeface);
                item.setTextColor(ContextCompat.getColor(getApplicationContext(), android.R.color.white));
//                item.setBackgroundImage(ContextCompat.getDrawable(DemoActivity.this, images.getResourceId(position, 0)));
                return item;
            }
        });

        bubbleColors.recycle();
        mBubblePicker.setBubbleSize(70);
        mBubblePicker.setListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mBubblePicker.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mBubblePicker.onPause();
    }

    @Override
    public void onBubbleSelected(@NotNull PickerItem item) {

    }

    @Override
    public void onBubbleDeselected(@NotNull PickerItem item) {

    }

    @Override
    public void onBubbleRemoved(@NotNull PickerItem item, int nCount) {

    }
}