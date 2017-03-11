package com.jajinba.mudclient.network;

import android.os.AsyncTask;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client extends AsyncTask<Object, Object, String> {

  private String dstAddress;
  private int dstPort;
  private String response = "";
  private TextView textResponse;

  public Client(String address, int port, TextView textResponse) {
    dstAddress = address;
    dstPort = port;
    this.textResponse = textResponse;
  }

  @Override
  protected String doInBackground(Object... arg0) {
    Socket socket = null;

    try {
      socket = new Socket(dstAddress, dstPort);
      BufferedReader bufferedReader =
          new BufferedReader(new InputStreamReader(socket.getInputStream(), "big5"));
			String rawResult;
      while ((rawResult = bufferedReader.readLine()) != null) {
        response += rawResult;
        /*
        if (!rawResult.isEmpty()) {
          response += rawResult;
          break;
          break;
        }
        */
      }

    } catch (UnknownHostException e) {
      e.printStackTrace();
      response = "UnknownHostException: " + e.toString();
    } catch (IOException e) {
      e.printStackTrace();
      response = "IOException: " + e.toString();
    } finally {
      if (socket != null) {
        try {
          socket.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return response;
  }

  @Override
  protected void onPostExecute(String result) {
    textResponse.setText(response);
    super.onPostExecute(result);
  }

}
