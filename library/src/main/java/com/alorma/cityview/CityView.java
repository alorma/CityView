package com.alorma.cityview;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import java.util.Random;

public class CityView extends ViewGroup {

  private static final int HEIGHT_PARTS = 4;

  private int buildsPadding;
  private int heightParts = HEIGHT_PARTS;
  private Random random;

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

  private void init(Context context, AttributeSet attrs, int defStyleAttr) {
    if (!isInEditMode()) {

    }
    random = new Random();
    if (attrs != null) {
      TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CityView, defStyleAttr, R.style.CityViewTheme);
      int buildingsNumber;
      try {
        buildingsNumber = ta.getInt(R.styleable.CityView_buildings_number, 4);
        int defaultPadding = getContext().getResources().getDimensionPixelOffset(R.dimen.build_padding);
        buildsPadding = ta.getDimensionPixelOffset(R.styleable.CityView_buildings_padding, defaultPadding);
        heightParts = ta.getInt(R.styleable.CityView_buildings_height_parts, HEIGHT_PARTS);
      } finally {
        ta.recycle();
      }

      for (int i = 0; i < buildingsNumber; i++) {
        addView(new BuildingView(context, attrs, defStyleAttr));
      }
    }
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    int totalWidth = MeasureSpec.getSize(widthMeasureSpec);
    int totalHeight = MeasureSpec.getSize(heightMeasureSpec);
    int childWidth = (totalWidth / getChildCount()) - buildsPadding;

    for (int i = 0; i < getChildCount(); i++) {
      int childHeight = (totalHeight / heightParts) * getRandom();
      final View child = getChildAt(i);
      int childWidthSpec = MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY);
      int childHeightSpec = MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY);
      child.measure(childWidthSpec, childHeightSpec);
    }

    setMeasuredDimension(totalWidth, totalHeight);
  }

  private int getRandom() {
    int value;
    do {
      value = random.nextInt(heightParts);
    } while (value == heightParts);
    return value;
  }

  @Override
  protected void onLayout(boolean changed, int l, int t, int r, int b) {
    int left = 0;

    for (int i = 0; i < getChildCount(); i++) {
      View view = getChildAt(i);
      view.layout(left, (b - t) - view.getMeasuredHeight(), left + view.getMeasuredWidth(), b);

      left = left + view.getMeasuredWidth() + buildsPadding;
    }
  }

  @Override
  public void addView(View child, int index, ViewGroup.LayoutParams params) {
    if (child instanceof BuildingView) {
      super.addView(child, index, params);
    }
  }
}