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
import java.util.Random;

public class BuildingView extends View {

  private static final int HEIGHT_PARTS = 4;

  private Rect rect;
  private Paint paint;
  private Random random;
  private int maxHeight;
  private int heightParts = HEIGHT_PARTS;

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

    random = new Random();

    rect = new Rect();
    paint = new Paint();

    if (attrs != null) {
      TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CityView, defStyleAttr, R.style.CityViewTheme);
      try {
        int buildingsColor = ta.getColor(R.styleable.CityView_buildings_color, Color.BLUE);
        paint.setColor(buildingsColor);
        heightParts = ta.getInt(R.styleable.CityView_buildings_height_parts, HEIGHT_PARTS);
      } finally {
        ta.recycle();
      }
    }
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    canvas.getClipBounds(rect);
    rect.top = maxHeight;

    canvas.drawRect(rect, paint);
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);

    maxHeight = (h / heightParts) * getRandom();
  }

  private int getRandom() {
    int value;
    do {
      value = random.nextInt(heightParts);
    } while (value == heightParts);
    return value;
  }
}
