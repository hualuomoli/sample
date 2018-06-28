package sample.jni.logger;

// 数据类型
public class TypeLogger {

  static {
    System.loadLibrary("logger");
  }

  public static native void print(
          // 普通整数
          int i,
          // 普通浮点数
          float f,
          // 普通高精度
          double d,
          // 普通长整数
          long l,
          // 普通布尔
          boolean b,
          // 字节
          byte bt,
          // 字符
          char c,

          // 字符串
          String s,
          // 包装整数
          Integer wi,
          // 包装浮点数
          Float wf,
          // 包装高精度
          Double wd,
          // 包装长整数
          Long wl,
          // 包装布尔
          Boolean wb
  );

}
