package br.com.ddmsoftware.periodictable;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import br.com.ddmsoftware.periodictable.util.SystemUiHider;

public class WebviewActivity extends Activity {

    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * If set, will toggle the system UI visibility upon interaction. Otherwise,
     * will show the system UI visibility upon interaction.
     */
    private static final boolean TOGGLE_ON_CLICK = true;

    /**
     * The flags to pass to {@link SystemUiHider#getInstance}.
     */
    private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

    /**
     * The instance of the {@link SystemUiHider} for this activity.
     */
    private SystemUiHider mSystemUiHider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hiding Title Bar and Setting FullScreen Mode
        requestWindowFeature( Window.FEATURE_NO_TITLE);
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_webview);

        ImageButton imgBack = (ImageButton)findViewById(R.id.imgBtnWebViewBack);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Intent intent = getIntent();
        Bundle extra = intent.getExtras();
        String url = extra.getString(ActivityResult.URL_MESSAGE);

        WebView browser = (WebView)findViewById(R.id.webView2);

        if (!(url != null && url.equals( "" ))) {
            // Carrega Imagens Automaticamente
            browser.getSettings().setLoadsImagesAutomatically(true);
            // Habilita Suporte ao Java SCript
            //browser.getSettings().setJavaScriptEnabled(true);

            // habilita As barras de rolagem lateral
            browser.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

            // Carrega as configuracoes de Navegacao dentro da WebView -- metodo implementado abaixo
            browser.setWebViewClient(new MyBrowser());
            browser.loadUrl(url);
        }

        // Load Advertisement Banner
        AdView mAdView = (AdView) findViewById(R.id.adViewBrowser);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    private class MyBrowser extends WebViewClient {

        @Override
        // Configura Navegacao dentro do WebView, ao inves de navegacao no Browser
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        String message = getResources().getString(R.string.loading );
        ProgressDialog dialog = ProgressDialog.show(WebviewActivity.this, "", message, true);

        @Override
        public void onPageFinished(WebView view, String url) {
            dialog.dismiss();
        }
    }
}
