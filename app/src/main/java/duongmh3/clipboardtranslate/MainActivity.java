package duongmh3.clipboardtranslate;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import im.delight.android.webview.AdvancedWebView;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    private TabLayout tabs;
    private AdvancedWebView webViewAnhAnh;
    private AdvancedWebView webViewVietAnh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabs = (TabLayout) findViewById(R.id.tabs);
        webViewAnhAnh = (AdvancedWebView) findViewById(R.id.webViewAnhAnh);
        webViewVietAnh = (AdvancedWebView) findViewById(R.id.webViewVietAnh);
        TabLayout.Tab tab1 = tabs.newTab().setText("English-English");
        tabs.addTab(tab1, true);
        TabLayout.Tab tab2 = tabs.newTab().setText("English-Vietnamese");
        tabs.addTab(tab2, false);

        tabs.addOnTabSelectedListener(this);

        Intent intent = new Intent(this, ClipboardMonitorService.class);
        this.startService(intent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String translateText = intent.getStringExtra("TRANSLATE_TEXT");
        if (translateText != null) {
            String trimText = translateText.trim();
            String query = null;
            try {
                query = URLEncoder.encode(trimText, "utf-8");
                String url = "http://stackoverflow.com/search?q=" + query;
                webViewAnhAnh.loadUrl("https://en.oxforddictionaries.com/definition/" + query);
                webViewVietAnh.loadUrl("https://vdict.com/" + query + ",1,0,0.html");
                //webViewVietAnh.loadUrl("https://www.hellochao.vn/tu-dien-tach-ghep-am/?act=search&type=word&lang=en&sct=" + query + "#ALL");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        if (tab != null) {
            if (tab.getPosition() == 1) {
                webViewAnhAnh.setVisibility(View.VISIBLE);
                webViewVietAnh.setVisibility(View.GONE);
            } else {
                webViewAnhAnh.setVisibility(View.GONE);
                webViewVietAnh.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
