package com.example.enabler;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final WebView webView = findViewById(R.id.webview);
        Button btn = findViewById(R.id.button);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setMinimumFontSize(1);
        webSettings.setMinimumLogicalFontSize(1);


        //webView.loadUrl("http://10.0.2.2:8080/login.html");
        webView.loadUrl("http://192.168.1.2/login.stm");
        webView.loadUrl("http://192.168.1.2/wireless_id.stm");

        // TODO
        /*
            Sto provando a fare js injection appena finisce di caricare la pagina in modo da saltare tutti i controlli
            senza dover toccare niente.
         */
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.d("URL", url);
                if(url.equals("http://192.168.1.2/login.stm")) {
                    webView.loadUrl(
                            "javascript:(function() { " +
                                    "return checkfwVersion()" +
                                    "})()");
                } else if(url.equals("http://192.168.1.2/wireless_id.stm")){
                    webView.loadUrl(
                            "javascript:(function() { " +
                                    "myobj = document.getElementsByName(\"wbr\");" +
                                    "myobj[0].selectedIndex = 2" +
                                    "})()");
                }
            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                Log.d("ERRORE",error.getDescription().toString());
                Toast.makeText(getApplicationContext(),error.getDescription().toString(),Toast.LENGTH_LONG).show();
            }
        });

    }
}
