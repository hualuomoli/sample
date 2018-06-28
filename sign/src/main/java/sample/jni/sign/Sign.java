package sample.jni.sign;

import android.content.Context;

// javah -bootclasspath ${ANDROID_HOME}/platforms/android-${version}/android.jar -encoding UTF-8 -d ../jni sample.jni.sign.Sign
// javah -bootclasspath  D:/android-sdk/platforms/android-25/android.jar -encoding UTF-8 -d ../jni sample.jni.sign.Sign
public class Sign {

  private static final int FLAG = 64;

  public static boolean init = false;
  public static boolean success = false;

  static {
    System.loadLibrary("sign");
  }

  // 获取上下文
  public static native Context getContext();

  // 获取签名
  public static native String getSign();

  public static native String say(String world);

}
