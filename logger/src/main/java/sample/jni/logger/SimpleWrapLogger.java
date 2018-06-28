package sample.jni.logger;

// 简单封装
public class SimpleWrapLogger {

  static {
    System.loadLibrary("logger");
  }

  public static native void verbose();

  public static native void debug();

  public static native void info();

  public static native void warn();

  public static native void error();

}
