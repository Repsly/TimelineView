package com.repsly.android.timelineview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * View used to display Timeline. It is possible to put different line and marker sizes and colors,
 * text inside markers, vertical and horizontal orientations.
 */

public class TimelineView extends View {

    private Paint mMarker;
    private Paint mStartLine;
    private Paint mEndLine;
    private Paint mMarkerTextPaint;
    private int startLineColor;
    private int endLineColor;
    private int markerColor;
    private int textColor;
    private int mMarkerSize;
    private int mLineSize;
    private int mLineOrientation;

    private String mMarkerText;

    public TimelineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            init(attrs);
        }
    }

    private void init(AttributeSet attrs) {
        TypedArray typedArray = getContext()
                .obtainStyledAttributes(attrs, R.styleable.TimelineView);

        mMarker = new Paint();
        mMarker.setStyle(Paint.Style.FILL);
        mMarker.setAntiAlias(true);
        markerColor = typedArray.getColor(R.styleable.TimelineView_markerColor, Color.BLACK);
        mMarker.setColor(markerColor);

        mStartLine = new Paint();
        mStartLine.setStyle(Paint.Style.FILL);
        mStartLine.setAntiAlias(true);
        startLineColor = typedArray.getColor(R.styleable.TimelineView_lineColor, Color.BLACK);
        mStartLine.setColor(startLineColor);

        mEndLine = new Paint();
        mEndLine.setStyle(Paint.Style.FILL);
        mEndLine.setAntiAlias(true);
        endLineColor = typedArray.getColor(R.styleable.TimelineView_lineColor, Color.BLACK);
        mEndLine.setColor(endLineColor);

        mMarkerSize = typedArray.getDimensionPixelSize(R.styleable.TimelineView_markerSize, 25);
        mLineSize = typedArray.getDimensionPixelSize(R.styleable.TimelineView_lineSize, 4);
        mLineOrientation = typedArray.getInt(R.styleable.TimelineView_lineOrientation, 1);

        mMarkerTextPaint = new Paint();
        textColor = typedArray.getColor(R.styleable.TimelineView_textColor, Color.WHITE);
        mMarkerTextPaint.setColor(textColor);
        mMarkerTextPaint.setStyle(Paint.Style.FILL);
        mMarkerTextPaint.setTextAlign(Paint.Align.CENTER);
        mMarkerTextPaint.setAntiAlias(true);
        mMarkerTextPaint.setTextSize(typedArray.getDimensionPixelSize(
                R.styleable.TimelineView_textSize, mMarkerSize / 2));

        typedArray.recycle();

        mStartLine.setStrokeWidth(mLineSize);
        mEndLine.setStrokeWidth(mLineSize);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //Width measurements of the width and height and the inside view of child controls
        int w = mMarkerSize + getPaddingLeft() + getPaddingRight();
        int h = mMarkerSize + getPaddingTop() + getPaddingBottom();

        // Width and height to determine the final view through a systematic approach to decision-making
        int widthSize = resolveSizeAndState(w, widthMeasureSpec, 0);
        int heightSize = resolveSizeAndState(h, heightMeasureSpec, 0);

        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Horizontal line
        if (mLineOrientation == 0) {

            canvas.drawLine(0, canvas.getHeight() / 2, canvas.getWidth() / 2,
                            canvas.getHeight() / 2,
                            mStartLine);

            canvas.drawLine(canvas.getWidth() / 2, canvas.getHeight() / 2, canvas.getWidth(),
                            canvas.getHeight() / 2, mEndLine);

        } else {
            // Vertical line
            canvas.drawLine(canvas.getWidth() / 2, 0, canvas.getWidth() / 2, canvas.getHeight() / 2,
                            mStartLine);

            canvas.drawLine(canvas.getWidth() / 2, canvas.getHeight() / 2, canvas.getWidth() / 2,
                            canvas.getHeight(), mEndLine);
        }

        canvas.drawCircle(canvas.getWidth() / 2, canvas.getHeight() / 2, mMarkerSize / 2, mMarker);

        if (mMarkerText != null && !mMarkerText.isEmpty() && mMarkerTextPaint != null) {
            canvas.drawText(mMarkerText, canvas.getWidth() / 2,
                            canvas.getHeight() / 2 + mMarkerTextPaint.getTextSize() / 3,
                            mMarkerTextPaint);
        }
    }

    public void setMarkerColor(int color) {
        mMarker.setColor(color);
        markerColor = color;
    }

    public void setStartLineColor(int color) {
        this.startLineColor = color;
    }

    public void setEndLineColor(int color) {
        this.endLineColor = color;
    }

    public void setMarkerSize(int markerSize) {
        mMarkerSize = markerSize;
    }

    public void setLineSize(int lineSize) {
        mLineSize = lineSize;
    }

    public String getText() {
        return mMarkerText;
    }

    public void setText(String text) {
        this.mMarkerText = text;
    }

    public void setLineType(LineType lineType) {

        switch (lineType) {
            case BEGIN:
                mStartLine.setAlpha(0);
                break;

            case END:
                mEndLine.setAlpha(0);
                break;

            case ONLYONE:
                mStartLine.setAlpha(0);
                mEndLine.setAlpha(0);
                break;

            case NORMAL:
                mStartLine.setAlpha(255);
                mEndLine.setAlpha(255);
        }

    }

}
