
package com.ammora;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Promise;
import java.net.*;
import java.io.*;

public class RNSimpleSocketModule extends ReactContextBaseJavaModule {
  public RNSimpleSocketModule(ReactApplicationContext reactContext) {
    super(reactContext);
  }

  @ReactMethod
  public void send(final String hostname, final int port, final String data, Promise promise) {
    try (Socket socket = new Socket(hostname, port)) {
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
      promise.resolve(result);
    } catch (UnknownHostException ex) {
      promise.reject(ex);
    } catch (IOException ex) {
      promise.reject(ex);
    }
  }

  @Override
  public String getName() {
    return "RNSimpleSocket";
  }
}