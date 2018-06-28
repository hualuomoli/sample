package sample.jni.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DemoActivity extends AppCompatActivity implements View.OnClickListener {

  private Button button;
  private EditText paramEditText;
  private TextView resultTextView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.demo);

    button = (Button) findViewById(R.id.demo_button);
    paramEditText = (EditText) findViewById(R.id.demo_param);
    resultTextView = (TextView) findViewById(R.id.demo_result);

    button.setOnClickListener(this);

  }

  @Override
  public void onClick(View v) {

    // button
    if (v.getId() == R.id.demo_button) {
      String result = Demo.say(paramEditText.getText().toString());
      resultTextView.setText(result);
      return;
    }

  }

}
