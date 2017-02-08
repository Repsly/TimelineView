package com.repsly.library.timelineview;

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

    private Paint marker;
    private int markerColor;
    private int mMarkerSize;
    private int markerStrokeWidth;

    private Paint markerText;
    private int textColor;
    private int textSize;
    private String mMarkerText;

    private Paint startLine;
    private Paint endLine;
    private int startLineColor;
    private int endLineColor;
    private int mLineSize;
    private int mLineOrientation;

    public TimelineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            init(attrs);
        }
    }

    private void init(AttributeSet attrs) {
        TypedArray typedArray = getContext()
                .obtainStyledAttributes(attrs, R.styleable.TimelineView);

        mMarkerSize = typedArray.getDimensionPixelSize(R.styleable.TimelineView_markerSize, 25);
        mLineSize = typedArray.getDimensionPixelSize(R.styleable.TimelineView_lineSize, 4);
        mLineOrientation = typedArray.getInt(R.styleable.TimelineView_lineOrientation, 1);
        markerStrokeWidth = typedArray
                .getDimensionPixelSize(R.styleable.TimelineView_markerStrokeWidth, 1);

        marker = new Paint();
        marker.setStyle(Paint.Style.FILL);
        marker.setAntiAlias(true);
        markerColor = typedArray.getColor(R.styleable.TimelineView_markerColor, Color.BLACK);
        marker.setColor(markerColor);
        marker.setStrokeWidth(markerStrokeWidth);

        startLine = new Paint();
        startLine.setStyle(Paint.Style.FILL);
        startLine.setAntiAlias(true);
        startLineColor = typedArray.getColor(R.styleable.TimelineView_lineColor, Color.BLACK);
        startLine.setColor(startLineColor);

        endLine = new Paint();
        endLine.setStyle(Paint.Style.FILL);
        endLine.setAntiAlias(true);
        endLineColor = typedArray.getColor(R.styleable.TimelineView_lineColor, Color.BLACK);
        endLine.setColor(endLineColor);

        markerText = new Paint();
        textColor = typedArray.getColor(R.styleable.TimelineView_textColor, Color.WHITE);
        markerText.setColor(textColor);
        markerText.setStyle(Paint.Style.FILL);
        markerText.setTextAlign(Paint.Align.CENTER);
        markerText.setAntiAlias(true);

        textSize = typedArray
                .getDimensionPixelSize(R.styleable.TimelineView_textSize, mMarkerSize / 2);
        markerText.setTextSize(textSize);

        typedArray.recycle();

        startLine.setStrokeWidth(mLineSize);
        endLine.setStrokeWidth(mLineSize);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //Width measurements of the width and height and the inside view of child controls
        int w = mMarkerSize + getPaddingLeft() + getPaddingRight() + markerStrokeWidth;
        int h = mMarkerSize + getPaddingTop() + getPaddingBottom() + markerStrokeWidth;

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

            canvas.drawLine(0, canvas.getHeight() / 2, canvas.getWidth() / 2 - mMarkerSize / 2,
                            canvas.getHeight() / 2, startLine);

            canvas.drawLine(canvas.getWidth() / 2 + mMarkerSize / 2, canvas.getHeight() / 2,
                            canvas.getWidth(), canvas.getHeight() / 2, endLine);

        } else {
            // Vertical line
            canvas.drawLine(canvas.getWidth() / 2, 0, canvas.getWidth() / 2,
                            canvas.getHeight() / 2 - mMarkerSize / 2, startLine);

            canvas.drawLine(canvas.getWidth() / 2, canvas.getHeight() / 2 + mMarkerSize / 2,
                            canvas.getWidth() / 2, canvas.getHeight(), endLine);
        }

        if (marker.getStyle() == Paint.Style.FILL) {
            canvas.drawCircle(canvas.getWidth() / 2, canvas.getHeight() / 2, mMarkerSize / 2,
                              marker);
        } else {
            canvas.drawCircle(canvas.getWidth() / 2, canvas.getHeight() / 2,
                              mMarkerSize / 2 - markerStrokeWidth / 2, marker);
        }


        if (mMarkerText != null && !mMarkerText.isEmpty() && markerText != null) {
            canvas.drawText(mMarkerText, canvas.getWidth() / 2,
                            canvas.getHeight() / 2 + markerText.getTextSize() / 3,
                            markerText);
        }
    }

    public void setMarkerColor(int color) {
        marker.setColor(color);
        markerColor = color;
        invalidate();
    }

    public int getMarkerColor() {
        return this.markerColor;
    }

    public void setStartLineColor(int color) {
        startLine.setColor(color);
        this.startLineColor = color;
        invalidate();
    }

    public int getStartLineColor() {
        return this.startLineColor;
    }

    public void setEndLineColor(int color) {
        endLine.setColor(color);
        this.endLineColor = color;
        invalidate();
    }

    public void setMarkerSize(int markerSize) {
        mMarkerSize = markerSize;
        invalidate();
    }

    public int getMarkerSize() {
        return this.mMarkerSize;
    }

    public void setMarkerStrokeWidth(int width) {
        markerStrokeWidth = width;
        marker.setStrokeWidth(width);
        invalidate();
    }

    public int getMarkerStrokeWidth() {
        return markerStrokeWidth;
    }

    public void setFillMarker(boolean fill) {
        if (fill) {
            marker.setStyle(Paint.Style.FILL);
        } else {
            marker.setStyle(Paint.Style.STROKE);
        }
        invalidate();
    }

    public void setLineSize(int lineSize) {
        mLineSize = lineSize;
        startLine.setStrokeWidth(lineSize);
        endLine.setStrokeWidth(lineSize);
        invalidate();
    }

    public int getLineSize() {
        return this.mLineSize;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int color) {
        markerText.setColor(color);
        this.textColor = color;
        invalidate();
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        markerText.setTextSize(textSize);
        this.textSize = textSize;
        invalidate();
    }

    public String getText() {
        return mMarkerText;
    }

    public void setText(String text) {
        this.mMarkerText = text;
        invalidate();
    }

    public void setNumber(int number) {
        this.mMarkerText = String.valueOf(number);
        invalidate();
    }

    public void setLineType(LineType lineType) {

        switch (lineType) {
            case BEGIN:
                startLine.setAlpha(0);
                endLine.setAlpha(255);
                break;

            case END:
                startLine.setAlpha(255);
                endLine.setAlpha(0);
                break;

            case ONLYONE:
                startLine.setAlpha(0);
                endLine.setAlpha(0);
                break;

            case NORMAL:
                startLine.setAlpha(255);
                endLine.setAlpha(255);
        }

        invalidate();

    }

}
