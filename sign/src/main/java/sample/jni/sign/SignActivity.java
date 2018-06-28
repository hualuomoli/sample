package sample.jni.sign;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class SignActivity extends AppCompatActivity {

  private static final String TAG = "SignActivity";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.sign);

  }

  public void click(View v) throws Exception {

    // sign check
    if (v.getId() == R.id.sign_check) {

      // 获取上下文
      Context context = Sign.getContext();
      Log.i(TAG, "context=" + context);

      Application javaContext = getApplication();

      Log.i(TAG, "是否为同一个Application?" + (context == javaContext));

      if (context == null) {
        Toast.makeText(this, "无法获取上下文", Toast.LENGTH_LONG).show();
      } else {
        Toast.makeText(context, "获取上下文成功", Toast.LENGTH_LONG).show();
      }


      // 获取签名
      String sign = Sign.getSign();
      Log.i(TAG, "sign=" + sign);
      String javaSign = this.getSign();
      if (sign.equals(javaSign)) {
        Toast.makeText(this, "验证成功", Toast.LENGTH_LONG).show();
      } else {
        Toast.makeText(this, "验证失败", Toast.LENGTH_LONG).show();
      }

      // 调用
      String result = Sign.say("jni");
      Log.i(TAG, "result=" + result);

      // end
    }

  }

  private String getSign() throws Exception {
    PackageManager packageManager = this.getPackageManager();
    String packageName = this.getPackageName();
    PackageInfo packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
    return packageInfo.signatures[0].toCharsString();
  }


}
