package sample.jni.logger;

public class Logger {

  static {
    System.loadLibrary("logger");
  }

  public static native void verbose();

  public static native void debug();

  public static native void info();

  public static native void warn();

  public static native void error();

}
