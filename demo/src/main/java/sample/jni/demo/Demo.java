package sample.jni.demo;

public class Demo {

  static {
    System.loadLibrary("demo");
  }

  public static native String say(String world);

}
