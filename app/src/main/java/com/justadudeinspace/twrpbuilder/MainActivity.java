
package com.justadudeinspace.twrpbuilder;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.*;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private static final int REQUEST_PERMISSIONS = 100;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkPermissions();

        webView = new WebView(this);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.addJavascriptInterface(new JSBridge(), "AndroidBridge");
        webView.loadUrl("file:///android_asset/index.html");
        setContentView(webView);
    }

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    }, REQUEST_PERMISSIONS);
        }
    }

    public class JSBridge {

        @JavascriptInterface
        public void startLogcat() {
            new Thread(() -> {
                try {
                    Process process = Runtime.getRuntime().exec("logcat");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        final String logLine = line;
                        runOnUiThread(() -> {
                            webView.evaluateJavascript("appendLog(`" + logLine.replace("`", "\`") + "`);", null);
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }

        @JavascriptInterface
        public void backupPartition(String name) {
            new Thread(() -> {
                try {
                    String destDir = Environment.getExternalStorageDirectory().getAbsolutePath() +
                            "/TWRP_Builder/backups/";
                    File dir = new File(destDir);
                    if (!dir.exists()) dir.mkdirs();

                    String outputPath = destDir + name + ".img";
                    String source = "/dev/block/by-name/" + name;

                    Process su = Runtime.getRuntime().exec("su");
                    DataOutputStream os = new DataOutputStream(su.getOutputStream());
                    os.writeBytes("dd if=" + source + " of=" + outputPath + " bs=4096\n");
                    os.writeBytes("exit\n");
                    os.flush();
                    su.waitFor();

                    runOnUiThread(() -> {
                        webView.evaluateJavascript("appendLog(`✔ Backed up " + name + " to " + outputPath + "`);", null);
                    });

                } catch (Exception e) {
                    runOnUiThread(() -> {
                        webView.evaluateJavascript("appendLog(`✘ Error backing up " + name + ": " + e.getMessage() + "`);", null);
                    });
                }
            }).start();
        }
    }
}
