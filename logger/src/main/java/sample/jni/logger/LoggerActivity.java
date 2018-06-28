package sample.jni.logger;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class LoggerActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.logger);
  }

  public void click(View v) {

    // verbose
    if (v.getId() == R.id.logger_verbose) {
      Logger.verbose();
      SimpleWrapLogger.verbose();
      LibraryWrapLogger.verbose();
      return;
    }

    // debug
    if (v.getId() == R.id.logger_debug) {
      Logger.debug();
      SimpleWrapLogger.debug();
      LibraryWrapLogger.debug();
      return;
    }

    // info
    if (v.getId() == R.id.logger_info) {
      Logger.info();
      SimpleWrapLogger.info();
      LibraryWrapLogger.info();
      return;
    }

    // warn
    if (v.getId() == R.id.logger_warn) {
      Logger.warn();
      SimpleWrapLogger.warn();
      LibraryWrapLogger.warn();
      return;
    }

    // error
    if (v.getId() == R.id.logger_error) {
      Logger.error();
      SimpleWrapLogger.error();
      LibraryWrapLogger.error();
      return;
    }

    // type
    if (v.getId() == R.id.logger_type) {
      byte bt = 1;
      TypeLogger.print(20, 3.5f, 6.9d, 5L, false//
              //
              , bt
              , 'A'
              //
              , new String("str")
              //
              , new Integer("3"), new Float("1.2"), new Double("5.8"), new Long("100"), new Boolean("true"));
      return;
    }

  }

}
