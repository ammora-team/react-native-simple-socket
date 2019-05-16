
package com.ammora;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.GuardedAsyncTask;
import java.util.concurrent.Executor;
import java.net.*;
import java.io.*;

public class RNSimpleSocketModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;

  private class SerialExecutor implements Executor {
    private final ArrayDeque<Runnable> mTasks = new ArrayDeque<Runnable>();
    private Runnable mActive;
    private final Executor executor;

    SerialExecutor(Executor executor) {
      this.executor = executor;
    }

    public synchronized void execute(final Runnable r) {
      mTasks.offer(new Runnable() {
        public void run() {
          try {
            r.run();
          } finally {
            scheduleNext();
          }
        }
      });
      if (mActive == null) {
        scheduleNext();
      }
    }
    synchronized void scheduleNext() {
      if ((mActive = mTasks.poll()) != null) {
        executor.execute(mActive);
      }
    }
  }

  private final SerialExecutor executor;

  public RNSimpleSocketModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
    this.executor = new SerialExecutor(executor);
  }

  @ReactMethod
  public String send(String hostname, int port, String data, final Callback callback) {
    new GuardedAsyncTask<Void, Void>(getReactApplicationContext()) {
      @Override
      protected void doInBackgroundGuarded(Void... params) {
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
          callback.invoke(null, result);
        } catch (UnknownHostException ex) {
          callback.invoke(ex, null);
        } catch (IOException ex) {
          callback.invoke(ex, null);
        }
      }
    }.executeOnExecutor(executor);
  }

  @Override
  public String getName() {
    return "RNSimpleSocket";
  }
}