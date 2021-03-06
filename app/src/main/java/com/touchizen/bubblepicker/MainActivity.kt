package com.touchizen.bubblepicker

import android.graphics.Typeface
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.igalata.bubblepicker.BubblePickerListener
import com.igalata.bubblepicker.adapter.BubblePickerAdapter
import com.igalata.bubblepicker.model.BubbleGradient
import com.igalata.bubblepicker.model.PickerItem
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val boldTypeface by lazy { Typeface.createFromAsset(assets, ROBOTO_BOLD) }
    private val mediumTypeface by lazy { Typeface.createFromAsset(assets, ROBOTO_MEDIUM) }
    private val regularTypeface by lazy { Typeface.createFromAsset(assets, ROBOTO_REGULAR) }
    private var nextPicker = 0

    val string2DArray: Array<Array<String>> = arrayOf(
            arrayOf("Argentina","Bolivia","Brazil","Chile","Costa Rica",
                    "Dominican Republic","Mexico","Nicaragua","Peru","Venezuela",
                    "Cuba","Ecuador","El Salvador","Haiti","Panama","Paraguay"),
            arrayOf("Argentina","Bolivia","Brazil","Chile","Costa Rica",
                    "Dominican Republic","Mexico","Nicaragua","Peru","Venezuela",
                    "Cuba","Ecuador","El Salvador","Haiti","Panama","Paraguay","Korea"),
            arrayOf("PAKISTAN","Argentina","Bolivia"),
            arrayOf("PAKISTAN","Argentina"),
            arrayOf("Argentina","Bolivia","Brazil","Chile","Costa Rica",
                    "Dominican Republic","Mexico","Nicaragua","Peru","Venezuela",
                    "Cuba","Ecuador","El Salvador","Haiti","Panama","Paraguay","Cananda","Korea","Japan"),
            arrayOf("apple", "orange", "avocado", "mango", "banana","pear"),
            arrayOf("_", "!", ":", "?"),
            arrayOf("1", "2", "3", "4", "5","6","7","8", "10"),
    )

    val handler = Handler(Looper.getMainLooper()) {
        when (it.what) {
            EVENT_NEXT_PICKER ->  doNextBubblePicker()
        }
        true
    }
    companion object {
        private const val ROBOTO_BOLD = "roboto_bold.ttf"
        private const val ROBOTO_MEDIUM = "roboto_medium.ttf"
        private const val ROBOTO_REGULAR = "roboto_regular.ttf"
        private const val EVENT_NEXT_PICKER = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        titleTextView.typeface = mediumTypeface
        subtitleTextView.typeface = boldTypeface
        hintTextView.typeface = regularTypeface
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            subtitleTextView.letterSpacing = 0.06f
            hintTextView.letterSpacing = 0.05f
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
                setTurnScreenOn(true)
            }
        }
        setupBubblePicker()
    }

    private fun doNextBubblePicker() {
        nextPicker++
        Log.i("TAG", "doNextBubblePicker: Next bubble running now....")
        if (nextPicker < string2DArray.size) {
            setupBubblePicker()
            picker.onPause()
            picker.onResume()
            // this trick is told by the real developer of the library so i just went through and saw it.
//                 after this i have done another bug fix because it was crashing if string2DArray[nextPicker].size
//                is greater than the previous array. hope you understand
        } else{
            toast("Finished")
        }
    }

    private fun setupBubblePicker() {
        //val titles = resources.getStringArray(R.array.countries)
        val colors = resources.obtainTypedArray(R.array.colors)
        //val images = resources.obtainTypedArray(R.array.images)

        picker.adapter = object : BubblePickerAdapter {

            override val totalCount = string2DArray[nextPicker].size

            override fun getItem(position: Int): PickerItem {
                return PickerItem().apply {

                    title = string2DArray[nextPicker][position]
                    gradient = BubbleGradient(colors.getColor((position * 2) % 8, 0),
                            colors.getColor((position * 2) % 8 + 1, 0), BubbleGradient.VERTICAL)
                    typeface = mediumTypeface
                    textColor = ContextCompat.getColor(this@MainActivity, android.R.color.white)
                    Log.d("TAG", "getItem: " + position + " total " + string2DArray[nextPicker].size + title)
                    // if you are changing the size of the array you also need to place its image in the drawable unless
//                    donot touch this as the array sizes are differnet here.
//                    backgroundImage = ContextCompat.getDrawable(this@MainActivity, images.getResourceId((position), 0))
                }
            }
        }
        colors.recycle()
        //images.recycle()
        picker.bubbleSize = 20
        picker.listener = object : BubblePickerListener {
            override fun onBubbleSelected(item: PickerItem) {
                toast("${item.title} selected")
            }

            override fun onBubbleDeselected(item: PickerItem) {
                toast("${item.title} deselected")
            }

            override fun onBubbleRemoved(item: PickerItem, nCount: Int) {
//                toast("${item.title} removed")
                if (nCount == 0) {
                    Log.d("TAG", "onBubbleRemoved: Show the next one ")
                    handler.obtainMessage(EVENT_NEXT_PICKER).sendToTarget()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        picker.onResume()
    }

    override fun onPause() {
        super.onPause()
        picker.onPause()
    }

    private fun toast(text: String) = Toast.makeText(this, text, Toast.LENGTH_SHORT).show()

}