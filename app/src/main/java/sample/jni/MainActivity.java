package sample.jni;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import sample.jni.api.ApiActivity;
import sample.jni.demo.DemoActivity;
import sample.jni.logger.LoggerActivity;
import sample.jni.sign.SignActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  private Button demoButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    demoButton = (Button) findViewById(R.id.demo);

    demoButton.setOnClickListener(this);

  }

  @Override
  public void onClick(View v) {

    // demo
    if (v.getId() == R.id.demo) {
      Intent intent = new Intent(this, DemoActivity.class);
      startActivity(intent);
      finish();
      return;
    }

    // log
    if (v.getId() == R.id.log) {
      Intent intent = new Intent(this, LoggerActivity.class);
      startActivity(intent);
      finish();
      return;
    }

    // api
    if (v.getId() == R.id.api) {
      Intent intent = new Intent(this, ApiActivity.class);
      startActivity(intent);
      finish();
      return;
    }

    // sign
    if (v.getId() == R.id.sign) {
      Intent intent = new Intent(this, SignActivity.class);
      startActivity(intent);
      finish();
      return;
    }


  }

}
