package sample.jni.api;

import android.util.Log;

public class Demo {

  private static final String TAG = "Demo";

  private static String value = "java call jni";

  private String name;

  static {
    System.loadLibrary("api");
  }

  public Demo(String name) {
    this.name = name;
  }

  // 静态方法
  public static native String call();

  // 成员方法
  public native void invoke();

  // 抛出异常
  public static native void error() throws RuntimeException;

  // 打印静态属性
  public static void printStatic() {
    Log.i(TAG, "static value is " + value);
  }

  // 打印成员属性
  public void print() {
    Log.i(TAG, "name is " + this.name);
  }

}
