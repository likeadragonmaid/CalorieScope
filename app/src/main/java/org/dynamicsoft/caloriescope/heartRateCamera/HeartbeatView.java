package org.dynamicsoft.caloriescope.heartRateCamera;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * This class extends the View class and is designed draw the heartbeat image.
 *
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class HeartbeatView extends View {

    private static final Matrix matrix = new Matrix();
    private static final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private final static Bitmap greenBitmap = null;
    private final static Bitmap redBitmap = null;

    private static int parentWidth = 0;
    private static int parentHeight = 0;

    public HeartbeatView(Context context, AttributeSet attr) {
        super(context, attr);
    }

    public HeartbeatView(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        parentHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(parentWidth, parentHeight);
    }
}
