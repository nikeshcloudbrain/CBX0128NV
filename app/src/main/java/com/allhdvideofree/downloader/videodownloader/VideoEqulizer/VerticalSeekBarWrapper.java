package com.allhdvideofree.downloader.videodownloader.VideoEqulizer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.core.view.ViewCompat;

import com.google.android.exoplayer2.C;


public class VerticalSeekBarWrapper extends FrameLayout {
    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public VerticalSeekBarWrapper(Context context) {
        this(context, (AttributeSet) null, 0);
        AttributeSet attributeSet = null;
    }

    public VerticalSeekBarWrapper(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public VerticalSeekBarWrapper(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void onSizeChanged(int i, int i2, int i3, int i4) {
        if (useViewRotation()) {
            onSizeChangedUseViewRotation(i, i2, i3, i4);
        } else {
            onSizeChangedTraditionalRotation(i, i2, i3, i4);
        }
    }

    private void onSizeChangedTraditionalRotation(int i, int i2, int i3, int i4) {
        VerticalSeekBar childSeekBar = getChildSeekBar();
        if (childSeekBar != null) {
            int paddingLeft = getPaddingLeft() + getPaddingRight();
            int paddingTop = getPaddingTop() + getPaddingBottom();
            LayoutParams layoutParams = (LayoutParams) childSeekBar.getLayoutParams();
            layoutParams.width = -2;
            int i5 = i2 - paddingTop;
            layoutParams.height = Math.max(0, i5);
            childSeekBar.setLayoutParams(layoutParams);
            childSeekBar.measure(0, 0);
            int measuredWidth = childSeekBar.getMeasuredWidth();
            int i6 = i - paddingLeft;
            childSeekBar.measure(MeasureSpec.makeMeasureSpec(Math.max(0, i6), Integer.MIN_VALUE), MeasureSpec.makeMeasureSpec(Math.max(0, i5), C.BUFFER_FLAG_ENCRYPTED));
            layoutParams.gravity = 51;
            layoutParams.leftMargin = (Math.max(0, i6) - measuredWidth) / 2;
            childSeekBar.setLayoutParams(layoutParams);
        }
        super.onSizeChanged(i, i2, i3, i4);
    }

    private void onSizeChangedUseViewRotation(int i, int i2, int i3, int i4) {
        VerticalSeekBar childSeekBar = getChildSeekBar();
        if (childSeekBar != null) {
            childSeekBar.measure(MeasureSpec.makeMeasureSpec(Math.max(0, i2 - (getPaddingTop() + getPaddingBottom())), C.BUFFER_FLAG_ENCRYPTED), MeasureSpec.makeMeasureSpec(Math.max(0, i - (getPaddingLeft() + getPaddingRight())), Integer.MIN_VALUE));
        }
        applyViewRotation(i, i2);
        super.onSizeChanged(i, i2, i3, i4);
    }

    public void onMeasure(int i, int i2) {
        int i3;
        int i4;
        VerticalSeekBar childSeekBar = getChildSeekBar();
        int mode = MeasureSpec.getMode(i);
        int mode2 = MeasureSpec.getMode(i2);
        int size = MeasureSpec.getSize(i);
        int size2 = MeasureSpec.getSize(i2);
        if (childSeekBar == null || mode == 1073741824) {
            super.onMeasure(i, i2);
            return;
        }
        int paddingLeft = getPaddingLeft() + getPaddingRight();
        int paddingTop = getPaddingTop() + getPaddingBottom();
        int makeMeasureSpec = MeasureSpec.makeMeasureSpec(Math.max(0, size - paddingLeft), mode);
        int makeMeasureSpec2 = MeasureSpec.makeMeasureSpec(Math.max(0, size2 - paddingTop), mode2);
        if (useViewRotation()) {
            childSeekBar.measure(makeMeasureSpec2, makeMeasureSpec);
            i3 = childSeekBar.getMeasuredHeight();
            i4 = childSeekBar.getMeasuredWidth();
        } else {
            childSeekBar.measure(makeMeasureSpec, makeMeasureSpec2);
            i3 = childSeekBar.getMeasuredWidth();
            i4 = childSeekBar.getMeasuredHeight();
        }
        setMeasuredDimension(resolveSizeAndState(i3 + paddingLeft, i, 0), resolveSizeAndState(i4 + paddingTop, i2, 0));
    }

    private void applyViewRotation(int i, int i2) {
        float f;
        VerticalSeekBar childSeekBar = getChildSeekBar();
        if (childSeekBar != null) {
            boolean z = ViewCompat.getLayoutDirection(this) == 0;
            int rotationAngle = childSeekBar.getRotationAngle();
            int measuredWidth = childSeekBar.getMeasuredWidth();
            int measuredHeight = childSeekBar.getMeasuredHeight();
            float max = ((float) (Math.max(0, i - (getPaddingLeft() + getPaddingRight())) - measuredHeight)) * 0.5f;
            ViewGroup.LayoutParams layoutParams = childSeekBar.getLayoutParams();
            int paddingTop = i2 - (getPaddingTop() + getPaddingBottom());
            layoutParams.width = Math.max(0, paddingTop);
            layoutParams.height = -2;
            childSeekBar.setLayoutParams(layoutParams);
            if (z) {
                f = 0.0f;
            } else {
                f = (float) Math.max(0, paddingTop);
            }
            childSeekBar.setPivotX(f);
            childSeekBar.setPivotY(0.0f);
            if (rotationAngle == 90) {
                childSeekBar.setRotation(90.0f);
                if (z) {
                    childSeekBar.setTranslationX(((float) measuredHeight) + max);
                    childSeekBar.setTranslationY(0.0f);
                    return;
                }
                childSeekBar.setTranslationX(-max);
                childSeekBar.setTranslationY((float) measuredWidth);
            } else if (rotationAngle == 270) {
                childSeekBar.setRotation(270.0f);
                if (z) {
                    childSeekBar.setTranslationX(max);
                    childSeekBar.setTranslationY((float) measuredWidth);
                    return;
                }
                childSeekBar.setTranslationX(-(((float) measuredHeight) + max));
                childSeekBar.setTranslationY(0.0f);
            }
        }
    }

    private VerticalSeekBar getChildSeekBar() {
        View childAt = getChildCount() > 0 ? getChildAt(0) : null;
        if (childAt instanceof VerticalSeekBar) {
            return (VerticalSeekBar) childAt;
        }
        return null;
    }

    private boolean useViewRotation() {
        VerticalSeekBar childSeekBar = getChildSeekBar();
        if (childSeekBar != null) {
            return childSeekBar.useViewRotation();
        }
        return false;
    }
}
