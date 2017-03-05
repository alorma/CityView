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
  private int windowSize;
  private boolean allowWindows;
  private int buildWindowsBottomPadding;

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

    int buildingsColor = Color.BLUE;
    if (attrs != null) {
      boolean containsBuildColor = containsBuildColor(attrs);

      if (containsBuildColor) {
        buildingsColor = loadAttribute(context, attrs, defStyleAttr, buildingsColor, R.styleable.BuildingView, defStyleRes,
            R.styleable.BuildingView_building_color);
      } else {
        buildingsColor = loadAttribute(context, attrs, defStyleAttr, buildingsColor, R.styleable.CityView, R.style.CityViewTheme,
            R.styleable.CityView_buildings_color);
      }
    }

    paint.setColor(buildingsColor);
    buildWindowsBottomPadding = getResources().getDimensionPixelOffset(R.dimen.build_windows_padding);
    windowSize = getResources().getDimensionPixelOffset(R.dimen.build_windows_size);
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

  private int loadAttribute(Context context, AttributeSet set, int defStyleAttr,
      int buildingsColor, int[] attrs, int defStyleRes,
      int attr) {
    TypedArray ta = context.obtainStyledAttributes(set, attrs, defStyleAttr, defStyleRes);
    try {
      buildingsColor = ta.getColor(attr, buildingsColor);
    } finally {
      ta.recycle();
    }
    return buildingsColor;
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    canvas.getClipBounds(rect);

    canvas.drawRect(rect, paint);
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);

    allowWindows = (h > (windowSize * 3) + buildWindowsBottomPadding) && w > (windowSize * 2);
  }

  @Override
  protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
    super.onLayout(changed, left, top, right, bottom);
  }
}
