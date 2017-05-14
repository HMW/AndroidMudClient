package com.jajinba.mudclient.network;


import android.util.Log;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class SocketClient {

  private static final String TAG = SocketClient.class.toString();

  public interface Callback {
    void onServerResponse(String response);
  }

  private Thread thread;
  private Socket socket;
  private BufferedReader reader;
  private BufferedWriter writer;

  private boolean flag = false;
  private String ipAddress;
  private int port;
  private Callback callback;

  private Runnable marsRunnable = new Runnable() {
    @Override
    public void run() {
      try {
        socket = new Socket(ipAddress, port);
        reader = new BufferedReader(
            new InputStreamReader(socket.getInputStream(), "big5"));
        writer = new BufferedWriter(
            new OutputStreamWriter(socket.getOutputStream()));
      } catch (IOException e) {
        Log.e(TAG, e.toString());
      }

      while(flag) {
        try {
          // read response
          String response = "";
          String rawResponse;
          while((rawResponse = reader.readLine()) != null) {
            response += rawResponse;
            if (callback != null) {
              callback.onServerResponse(rawResponse);
            }
          }
        } catch (IOException e) {
          Log.e(TAG, e.toString());
        }
      }
    }
  };

  public SocketClient(String ipAddress, int port) {
    this.ipAddress = ipAddress;
    this.port = port;
  }

  public void setCallback(Callback callback) {
    if (callback != null) {
      this.callback = callback;
    }
  }

  public void start() {
    flag = true;
    thread = new Thread(marsRunnable);
    thread.start();
  }

  public void sendCommand(String command) {
    try {
      writer.write(command);
    } catch (IOException e) {
      Log.e(TAG, e.toString());
    }
  }

  public void stop() {
    flag = false;
  }
}
