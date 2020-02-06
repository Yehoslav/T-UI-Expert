package de.reckendrees.systems.tui.expert.tuils;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.TextView;

import java.lang.reflect.Field;

import de.reckendrees.systems.tui.expert.R;

public class OutlineEditText extends android.support.v7.widget.AppCompatEditText {

    private int drawTimes = -1;

    public OutlineEditText(Context context) {
        super(context);

    }

    public OutlineEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OutlineEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    public void draw(Canvas canvas) {
        if(drawTimes == -1) {
            drawTimes = getTag() == null ? 1 : OutlineTextView.redrawTimes;
        }

        for(int c = 0; c < drawTimes; c++) super.draw(canvas);
    }
}
