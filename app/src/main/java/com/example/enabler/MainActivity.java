package com.example.enabler;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import static com.example.enabler.Constants.INDEX_PAGE;
import static com.example.enabler.Constants.JSCallbackWifiState;
import static com.example.enabler.Constants.JSCheckFirmwareVersion;
import static com.example.enabler.Constants.JSTurnWifiOff;
import static com.example.enabler.Constants.JSTurnWifiOn;
import static com.example.enabler.Constants.JSWifiIsOnOrOff;
import static com.example.enabler.Constants.LOGIN_PAGE;
import static com.example.enabler.Constants.WAIT_PAGE;
import static com.example.enabler.Constants.WIRELESS_PAGE;


public class MainActivity extends AppCompatActivity {

    private Button btn;         // Needed to reference xml obj.
    private Integer onOff = 0;  // Button state: 0 - OFF , 1 - ON
    private WebView webView;

    @SuppressLint("AddJavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webview);
        btn = findViewById(R.id.button);

        givePermissionToWebView(webView);
        webView.addJavascriptInterface(this, "android");
        webView.loadUrl(LOGIN_PAGE);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(final WebView view, String url) {
                super.onPageFinished(view, url);
                Log.d("URL", url);
                switch (url) {
                    case LOGIN_PAGE:
                        view.loadUrl(JSCheckFirmwareVersion);
                        break;
                    case WIRELESS_PAGE:
                        view.loadUrl(JSWifiIsOnOrOff);
                        view.loadUrl(JSCallbackWifiState);

                        btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (onOff == 1) { // wifi is on I'll turn it down
                                    view.loadUrl(JSTurnWifiOff);
                                } else {
                                    view.loadUrl(JSTurnWifiOn);
                                }
                            }
                        });
                        break;
                    case WAIT_PAGE:
                        break;
                    default:
                        view.loadUrl(WIRELESS_PAGE);
                        break;
                }
            }
            // Error Handling
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                Log.d("ERRORE",error.getDescription().toString());
                Toast.makeText(getApplicationContext(),error.getDescription().toString(),Toast.LENGTH_LONG).show();
            }
        });

    }

    public void setButton(Integer state) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                btn.setVisibility(View.VISIBLE);
                if (state == 1) {
                    btn.setBackground(getDrawable(R.drawable.onwifi));
                } else {
                    btn.setBackground(getDrawable(R.drawable.offwifi));
                }
            }
        });
    }
    
    
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @JavascriptInterface
    public void onData(String value) {
        if (value.equals("on")) {
            onOff = 1; // wifi is on
            Log.d("DEBUG", String.valueOf(onOff));
            setButton(onOff);
        } else {
            onOff = 0; // wifi is off
            Log.d("DEBUG", String.valueOf(onOff));
            setButton(onOff);
        }
    }
    
    @SuppressLint("SetJavaScriptEnabled")
    private void givePermissionToWebView(WebView webView) {
        WebSettings webSettings = webView.getSettings();
        // TODO --> Security error?
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setMinimumFontSize(1);
        webSettings.setMinimumLogicalFontSize(1);
    }
}
