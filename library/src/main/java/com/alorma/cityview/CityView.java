package com.alorma.cityview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class CityView extends ViewGroup {

  private int buildingsColor;

  public CityView(Context context) {
    super(context);
    init(context, null, 0);
  }

  public CityView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    init(context, attrs, 0);
  }

  public CityView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context, attrs, defStyleAttr);
  }

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  public CityView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init(context, attrs, defStyleAttr);
  }

  private void init(Context context, AttributeSet attrs, int defStyleAttrs) {
    if (!isInEditMode()) {

    }
    if (attrs != null) {
      buildingsColor = readBuildColorsAttr(context, attrs, defStyleAttrs, Color.GRAY);
    }
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    int totalWidth = MeasureSpec.getSize(widthMeasureSpec);
    int totalHeight = MeasureSpec.getSize(heightMeasureSpec);
    int childWidth = totalWidth / getChildCount();

    for (int i = 0; i < getChildCount(); i++) {
      final View child = getChildAt(i);
      child.measure(MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY), heightMeasureSpec);
    }

    setMeasuredDimension(totalWidth, totalHeight);
  }

  @Override
  protected void onLayout(boolean changed, int l, int t, int r, int b) {
    int left = 0;

    for (int i = 0; i < getChildCount(); i++) {
      View view = getChildAt(i);
      view.layout(left, 0, left + view.getMeasuredWidth(), view.getMeasuredHeight());

      left = left + view.getMeasuredWidth();
    }
  }

  @Override
  public void addView(View child, int index, ViewGroup.LayoutParams params) {
    if (child instanceof BuildingView) {
      super.addView(child, index, params);
      ((BuildingView) child).setBuildingColor(buildingsColor);
    }
  }

  private int readBuildColorsAttr(Context context, AttributeSet attrs, int defStyleAttr, int buildingColor) {
    TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CityView, defStyleAttr, R.style.CityViewTheme);
    try {
      return ta.getColor(R.styleable.CityView_buildings_color, buildingColor);
    } finally {
      ta.recycle();
    }
  }
}