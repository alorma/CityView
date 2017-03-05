package com.alorma.cityview.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.alorma.cityview.CityView;

public class MainActivity extends AppCompatActivity {

  private CityView cityView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    //
    //cityView = (CityView) findViewById(R.id.cityView);
    //
    //findViewById(R.id.random).setOnClickListener(new View.OnClickListener() {
    //  @Override
    //  public void onClick(View v) {
    //    cityView.requestLayout();
    //  }
    //});
  }
}
