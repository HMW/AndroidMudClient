package com.jajinba.mudclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.jajinba.mudclient.network.Client;

public class MainActivity extends AppCompatActivity {

  @BindView(R.id.address_edittext)
  EditText addressEditText;
  @BindView(R.id.port_edittext)
  EditText portEditText;
  @BindView(R.id.connect_button)
  Button connectButton;
  @BindView(R.id.clear_button)
  Button clearButton;
  @BindView(R.id.response_textview)
  TextView responseTextView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    connectButton.setOnClickListener(onClickListener);
    clearButton.setOnClickListener(onClickListener);
  }

  private View.OnClickListener onClickListener = new OnClickListener() {
    @Override
    public void onClick(View v) {
      switch(v.getId()) {
        case R.id.connect_button:
          Client myClient = new Client(
              "218.161.10.205",
              8888,
              responseTextView);
          myClient.execute();
          break;
        case R.id.clear_button:
          responseTextView.setText("");
          break;
      }
    }
  };
}
