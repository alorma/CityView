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

    rect = new Rect();
    paint = new Paint();

    if (attrs != null) {
      TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CityView, defStyleAttr, R.style.CityViewTheme);
      try {
        int buildingsColor = ta.getColor(R.styleable.CityView_buildings_color, Color.BLUE);
        paint.setColor(buildingsColor);
      } finally {
        ta.recycle();
      }
    }

    buildWindowsBottomPadding = getResources().getDimensionPixelOffset(R.dimen.build_windows_padding);
    windowSize = getResources().getDimensionPixelOffset(R.dimen.build_windows_size);
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
