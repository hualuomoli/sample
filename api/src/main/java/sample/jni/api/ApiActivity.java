package sample.jni.api;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class ApiActivity extends AppCompatActivity {

  private static final String TAG = "ApiActivity";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.api);
  }

  public void click(View v) {

    // 静态方法
    if (v.getId() == R.id.api_static) {
      String result = Demo.call();
      Toast.makeText(this, "返回的数据为" + result, Toast.LENGTH_LONG).show();
      return;
    }

    // 实例方法
    if (v.getId() == R.id.api_instance) {
      Demo demo = new Demo("api");
      demo.invoke();
      return;
    }

    // 异常
    if (v.getId() == R.id.api_exception) {
      try {
        Demo.error();
      } catch (Exception e) {
        Log.e(TAG, "出错了", e);
      }
      return;
    }

  }

}
