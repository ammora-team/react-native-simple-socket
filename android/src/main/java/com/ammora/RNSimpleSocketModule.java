
package com.ammora;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Promise;
import com.facebook.common.logging.FLog;

import java.net.*;
import java.io.*;

public class RNSimpleSocketModule extends ReactContextBaseJavaModule {
  private static final String TAG = "RNSimpleSocket";

  public RNSimpleSocketModule(ReactApplicationContext reactContext) {
    super(reactContext);
  }

  @ReactMethod
  public void send(final String hostname, final int port, final String data, Promise promise) {
    try {
      // check connection
      Boolean Check = InetAddress.getByName(hostname).isReachable(1000);
      if (Check == false) {
        promise.reject("");
        return;
      }
      InetSocketAddress socketAddress = new InetSocketAddress(hostname, port);
      Socket socket = new Socket();
      socket.connect(socketAddress, 5000);
      OutputStream output = socket.getOutputStream();
      PrintWriter writer = new PrintWriter(output, true);
      writer.println(data);

      InputStream input = socket.getInputStream();
      BufferedReader reader = new BufferedReader(new InputStreamReader(input));

      String line;
      String result = "";

      while ((line = reader.readLine()) != null) {
        result = result.concat(line);
      }

      socket.close();
      promise.resolve(result);
    } catch (SocketException ex) {
      FLog.e(TAG, ex.toString());
      promise.reject(ex);
    } catch (IOException ex) {
      FLog.e(TAG, ex.toString());
      promise.reject(ex);
    }
  }

  @Override
  public String getName() {
    return "RNSimpleSocket";
  }
}