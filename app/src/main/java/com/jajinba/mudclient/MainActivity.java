package com.jajinba.mudclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.jajinba.mudclient.network.SocketClient;
import com.jajinba.mudclient.network.SocketClient.Callback;

public class MainActivity extends AppCompatActivity {

  private static final String MARS_IP = "218.161.10.205";
  private static final int MARS_PORT = 8888;

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

  private SocketClient.Callback callback = new Callback() {
    @Override
    public void onServerResponse(String response) {
      String currentText = responseTextView.getText().toString();
      response = currentText + response;
      final String finalResponse = response;
      responseTextView.post(new Runnable() {
        @Override
        public void run() {
          responseTextView.append(finalResponse);
        }
      });
    }
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
  }

  @OnClick({R.id.connect_button, R.id.clear_button})
  void onClickListener(View view) {
    switch(view.getId()) {
      case R.id.connect_button:
        SocketClient socketClient = new SocketClient(MARS_IP, MARS_PORT);
        socketClient.setCallback(callback);
        socketClient.start();

        /*
        Client myClient = new Client(
            "218.161.10.205",
            8888,
            responseTextView);
        myClient.execute();
        */
        break;
      case R.id.clear_button:
        responseTextView.setText("");
        break;
    }
  }
}
