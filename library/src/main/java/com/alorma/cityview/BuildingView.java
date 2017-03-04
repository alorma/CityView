package com.alorma.cityview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

public class BuildingView extends View {

  private int buildingColor = Color.GRAY;

  public BuildingView(Context context) {
    super(context);
    init(context, null, 0);
  }

  public BuildingView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    init(context, attrs, 0);
  }

  public BuildingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context, attrs, defStyleAttr);
  }

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  public BuildingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init(context, attrs, defStyleAttr);
  }

  private void init(Context context, AttributeSet attrs, int defStyleAttr) {
    if (!isInEditMode()) {

    }

    if (attrs != null) {
      buildingColor = readViewAttrs(context, attrs, defStyleAttr, buildingColor);
    }

    setBackgroundColor(buildingColor);
  }

  private int readViewAttrs(Context context, AttributeSet attrs, int defStyleAttr, int buildingColor) {
    TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.BuildingView, defStyleAttr, R.style.BuildViewTheme);
    try {
      return ta.getColor(R.styleable.BuildingView_building_color, buildingColor);
    } finally {
      ta.recycle();
    }
  }

  public void setBuildingColor(@ColorInt int buildingColor) {
    this.buildingColor = buildingColor;
    setBackgroundColor(buildingColor);
  }
}
