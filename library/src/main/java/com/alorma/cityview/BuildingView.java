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

  private int buildingColor = Color.GRAY;

  public BuildingView(Context context) {
    super(context);
    init(context, null);
  }

  public BuildingView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    init(context, attrs);
  }

  public BuildingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context, attrs);
  }

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  public BuildingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init(context, attrs);
  }

  private void init(Context context, AttributeSet attrs) {
    if (!isInEditMode()) {

    }

    if (attrs != null) {
      TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.BuildingView, 0, 0);
      try {
        buildingColor = ta.getColor(R.styleable.BuildingView_building_color, buildingColor);
      } finally {
        ta.recycle();
      }
    }

    setBackgroundColor(buildingColor);
  }
}
