package com.alorma.cityview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

public class BuildingView extends View {

  private Rect rect;
  private Paint paint;

  private Rect doorRect;
  private Paint doorPaint;

  private int windowSize;

  private int doorHeight;
  private int doorWidth;

  private boolean allowWindows;
  private boolean allowDoor;

  public BuildingView(Context context) {
    super(context);
    init(context, null, 0, R.style.BuildingViewTheme);
  }

  public BuildingView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    init(context, attrs, 0, R.style.BuildingViewTheme);
  }

  public BuildingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context, attrs, defStyleAttr, R.style.BuildingViewTheme);
  }

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  public BuildingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init(context, attrs, defStyleAttr, defStyleRes);
  }

  private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    if (!isInEditMode()) {

    }

    rect = new Rect();
    paint = new Paint();

    doorRect = new Rect();
    doorPaint = new Paint();
    doorPaint.setColor(Color.WHITE);

    int buildingsColor = Color.BLUE;
    if (attrs != null) {
      boolean containsBuildColor = containsBuildColor(attrs);

      if (containsBuildColor) {
        buildingsColor = loadColorAttribute(context, attrs, defStyleAttr, buildingsColor, R.styleable.BuildingView, defStyleRes,
            R.styleable.BuildingView_building_color);
      } else {
        buildingsColor = loadColorAttribute(context, attrs, defStyleAttr, buildingsColor, R.styleable.CityView, R.style.CityViewTheme,
            R.styleable.CityView_buildings_color);
      }
    }
    paint.setColor(buildingsColor);

    loadDimensions(context, attrs, defStyleAttr, defStyleRes);
  }

  private void loadDimensions(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    int windowSizeDefault = getResources().getDimensionPixelOffset(R.dimen.build_windows_size);
    windowSize = loadDimensionAttribute(context, attrs, defStyleAttr, windowSizeDefault, R.styleable.BuildingView, defStyleRes,
        R.styleable.BuildingView_building_window_size);

    loadDoorDimensions(context, attrs, defStyleAttr, defStyleRes);
  }

  private void loadDoorDimensions(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    int doorHeightDefault = getResources().getDimensionPixelOffset(R.dimen.build_door_height);
    doorHeight = loadDimensionAttribute(context, attrs, defStyleAttr, doorHeightDefault, R.styleable.BuildingView, defStyleRes,
        R.styleable.BuildingView_building_door_height);

    int doorWidthDefault = getResources().getDimensionPixelOffset(R.dimen.build_door_width);
    doorWidth = loadDimensionAttribute(context, attrs, defStyleAttr, doorWidthDefault, R.styleable.BuildingView, defStyleRes,
        R.styleable.BuildingView_building_door_width);
  }

  private boolean containsBuildColor(AttributeSet attrs) {
    boolean containsBuildColor = false;
    for (int i = 0; i < attrs.getAttributeCount(); i++) {
      if (attrs.getAttributeName(i).equalsIgnoreCase("building_color")) {
        containsBuildColor = true;
        break;
      }
    }
    return containsBuildColor;
  }

  private int loadDimensionAttribute(Context context, AttributeSet set, int defStyleAttr, int defaultValue, int[] attrs, int defStyleRes,
      int attr) {
    TypedArray ta = context.obtainStyledAttributes(set, attrs, defStyleAttr, defStyleRes);
    try {
      defaultValue = ta.getDimensionPixelOffset(attr, defaultValue);
    } finally {
      ta.recycle();
    }
    return defaultValue;
  }

  private int loadColorAttribute(Context context, AttributeSet set, int defStyleAttr, int defaultValue, int[] attrs, int defStyleRes,
      int attr) {
    TypedArray ta = context.obtainStyledAttributes(set, attrs, defStyleAttr, defStyleRes);
    try {
      defaultValue = ta.getColor(attr, defaultValue);
    } finally {
      ta.recycle();
    }
    return defaultValue;
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    canvas.getClipBounds(rect);

    drawBuild(canvas);
    if (allowDoor) {
      drawDoor(canvas);
    }
  }

  private void drawBuild(Canvas canvas) {
    canvas.drawRect(rect, paint);
  }

  private void drawDoor(Canvas canvas) {
    canvas.drawRect(doorRect, doorPaint);
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
  }

  @Override
  protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
    super.onLayout(changed, left, top, right, bottom);

    int height = getMeasuredHeight();
    int width = getMeasuredWidth();

    if (doorHeight == 0 && windowSize == 0) {
      allowDoor = false;
      allowWindows = false;
    }

    if (height > doorHeight + windowSize) {
      allowDoor = true;
      if (height > doorHeight + (windowSize * 3)) {
        allowWindows = true;
      }
    } else {
      allowDoor = false;
      allowWindows = false;
    }

    allowDoor = allowWindows && width > (doorWidth * 3);

    if (changed && allowDoor) {
      int doorTop = getMeasuredHeight() - doorHeight;
      int doorHalf = doorWidth / 2;
      int middle = (width) / 2;
      doorRect.left = middle - doorHalf;
      doorRect.top = doorTop;
      doorRect.right = middle + doorHalf;
      doorRect.bottom = bottom;
    }
  }
}
