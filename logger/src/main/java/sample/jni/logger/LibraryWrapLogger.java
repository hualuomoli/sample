package sample.jni.logger;

// 封装成库
public class LibraryWrapLogger {

  static {
    System.loadLibrary("logger");
  }

  public static native void verbose();

  public static native void debug();

  public static native void info();

  public static native void warn();

  public static native void error();

}
