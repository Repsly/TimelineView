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
    private Paint markerActive;
    private Paint markerTextPaint;
    private Paint startLine;
    private Paint endLine;

    private int markerSize;
    private String markerText;
    private int lineOrientation;
    private boolean active;

    public TimelineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            init(attrs);
        }
    }

    private void init(AttributeSet attrs) {
        TypedArray typedArray = getContext()
                .obtainStyledAttributes(attrs, R.styleable.TimelineView);

        markerSize = typedArray.getDimensionPixelSize(R.styleable.TimelineView_markerSize, 25);
        lineOrientation = typedArray.getInt(R.styleable.TimelineView_lineOrientation, 1);
        active = typedArray.getBoolean(R.styleable.TimelineView_markerActive, false);

        int lineSize = typedArray.getDimensionPixelSize(R.styleable.TimelineView_lineSize, 4);
        int markerStrokeWidth = typedArray
                .getDimensionPixelSize(R.styleable.TimelineView_markerStrokeWidth, 1);
        int markerColor = typedArray.getColor(R.styleable.TimelineView_markerColor, Color.BLACK);
        int markerActiveColor = typedArray
                .getColor(R.styleable.TimelineView_markerActiveColor, Color.BLACK);
        int markerActiveStrokeWidth = typedArray
                .getDimensionPixelSize(R.styleable.TimelineView_markerActiveStrokeWidth, 0);
        int startLineColor = typedArray.getColor(R.styleable.TimelineView_lineColor, Color.BLACK);
        int endLineColor = typedArray.getColor(R.styleable.TimelineView_lineColor, Color.BLACK);
        int textSize = typedArray
                .getDimensionPixelSize(R.styleable.TimelineView_markerTextSize, markerSize / 2);
        int textColor = typedArray.getColor(R.styleable.TimelineView_markerTextColor, Color.WHITE);

        marker = new Paint();
        marker.setStyle(Paint.Style.FILL);
        marker.setAntiAlias(true);
        marker.setColor(markerColor);
        marker.setStrokeWidth(markerStrokeWidth);

        markerTextPaint = new Paint();
        markerTextPaint.setColor(textColor);
        markerTextPaint.setStyle(Paint.Style.FILL);
        markerTextPaint.setTextAlign(Paint.Align.CENTER);
        markerTextPaint.setAntiAlias(true);
        markerTextPaint.setTextSize(textSize);

        markerActive = new Paint();
        markerActive.setStyle(Paint.Style.STROKE);
        markerActive.setAntiAlias(true);
        markerActive.setColor(markerActiveColor);
        markerActive.setStrokeWidth(markerActiveStrokeWidth);

        startLine = new Paint();
        startLine.setStyle(Paint.Style.FILL);
        startLine.setAntiAlias(true);
        startLine.setColor(startLineColor);
        startLine.setStrokeWidth(lineSize);

        endLine = new Paint();
        endLine.setStyle(Paint.Style.FILL);
        endLine.setAntiAlias(true);
        endLine.setColor(endLineColor);
        endLine.setStrokeWidth(lineSize);

        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //Width measurements of the width and height and the inside view of child controls
        int w = (int) (markerSize + getPaddingLeft() + getPaddingRight() + marker.getStrokeWidth()
                + markerActive.getStrokeWidth());
        int h = (int) (markerSize + getPaddingTop() + getPaddingBottom() + marker.getStrokeWidth()
                + markerActive.getStrokeWidth());

        // Width and height to determine the final view through a systematic approach to decision-making
        int widthSize = resolveSizeAndState(w, widthMeasureSpec, 0);
        int heightSize = resolveSizeAndState(h, heightMeasureSpec, 0);

        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Horizontal line
        if (lineOrientation == 0) {

            canvas.drawLine(0, canvas.getHeight() / 2, canvas.getWidth() / 2 - markerSize / 2,
                            canvas.getHeight() / 2, startLine);

            canvas.drawLine(canvas.getWidth() / 2 + markerSize / 2, canvas.getHeight() / 2,
                            canvas.getWidth(), canvas.getHeight() / 2, endLine);

        } else {
            // Vertical line
            canvas.drawLine(canvas.getWidth() / 2, 0, canvas.getWidth() / 2,
                            canvas.getHeight() / 2 - markerSize / 2, startLine);

            canvas.drawLine(canvas.getWidth() / 2, canvas.getHeight() / 2 + markerSize / 2,
                            canvas.getWidth() / 2, canvas.getHeight(), endLine);
        }

        // Marker
        if (marker.getStyle() == Paint.Style.FILL) {
            canvas.drawCircle(canvas.getWidth() / 2, canvas.getHeight() / 2, markerSize / 2,
                              marker);
        } else {
            canvas.drawCircle(canvas.getWidth() / 2, canvas.getHeight() / 2,
                              markerSize / 2 - marker.getStrokeWidth() / 2, marker);
        }

        // Text inside marker
        if (markerText != null && !markerText.isEmpty() && markerTextPaint != null) {
            canvas.drawText(markerText, canvas.getWidth() / 2,
                            canvas.getHeight() / 2 + markerTextPaint.getTextSize() / 3,
                            markerTextPaint);
        }

        // Active marker
        if (active) {
            canvas.drawCircle(canvas.getWidth() / 2, canvas.getHeight() / 2, markerSize / 2,
                              markerActive);
        }
    }

    /*------------------
    Getters and setters
     -------------------*/

    public int getMarkerColor() {
        return marker.getColor();
    }

    public void setMarkerColor(int color) {
        marker.setColor(color);
        invalidate();
    }

    public void setMarkerSize(int markerSize) {
        this.markerSize = markerSize;
        invalidate();
    }

    public int getMarkerSize() {
        return this.markerSize;
    }

    public float getMarkerStrokeWidth() {
        return marker.getStrokeWidth();
    }

    public void setMarkerStrokeWidth(int width) {
        marker.setStrokeWidth(width);
        invalidate();
    }

    public void setFillMarker(boolean fill) {
        if (fill) {
            marker.setStyle(Paint.Style.FILL);
        } else {
            marker.setStyle(Paint.Style.STROKE);
        }
        invalidate();
    }

    public int getMarkerActiveColor() {
        return markerActive.getColor();
    }

    public void setMarkerActiveColor(int color) {
        markerActive.setColor(color);
        invalidate();
    }

    public float getMarkerActiveStrokeWidth() {
        return markerActive.getStrokeWidth();
    }

    public void setMarkerActiveStrokeWidth(int width) {
        markerActive.setStrokeWidth(width);
        invalidate();
    }

    public int getStartLineColor() {
        return startLine.getColor();
    }

    public void setStartLineColor(int color) {
        startLine.setColor(color);
        invalidate();
    }

    public int getEndLineColor() {
        return endLine.getColor();
    }

    public void setEndLineColor(int color) {
        endLine.setColor(color);
        invalidate();
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
        invalidate();
    }

    public float getLineSize() {
        return startLine.getStrokeWidth();
    }

    public void setLineSize(int lineSize) {
        startLine.setStrokeWidth(lineSize);
        endLine.setStrokeWidth(lineSize);
        invalidate();
    }

    public int getTextColor() {
        return markerTextPaint.getColor();
    }

    public void setTextColor(int color) {
        markerTextPaint.setColor(color);
        invalidate();
    }

    public float getTextSize() {
        return markerTextPaint.getTextSize();
    }

    public void setTextSize(int textSize) {
        markerTextPaint.setTextSize(textSize);
        invalidate();
    }

    public String getText() {
        return markerText;
    }

    public void setText(String text) {
        this.markerText = text;
        invalidate();
    }

    public void setNumber(int number) {
        this.markerText = String.valueOf(number);
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
