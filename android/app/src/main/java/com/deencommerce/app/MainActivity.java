package com.deencommerce.app;

import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;
import androidx.core.view.WindowCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.getcapacitor.BridgeActivity;
import com.getcapacitor.WebViewListener;

public class MainActivity extends BridgeActivity {
    private long lastBackPressedTime = 0;
    private static final int BACK_PRESS_INTERVAL = 2000;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. Enable Edge-to-Edge display (decor fits system windows = false)
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        // 2. Configure WebView for Real-Time Updates, Session Persistence & Pull-to-Refresh
        if (this.bridge != null && this.bridge.getWebView() != null) {
            final WebView webView = this.bridge.getWebView();
            WebSettings settings = webView.getSettings();

            // Always revalidate with server to guarantee real-time updates
            settings.setCacheMode(WebSettings.LOAD_DEFAULT);
            settings.setDomStorageEnabled(true);
            settings.setDatabaseEnabled(true);

            // Persistent Cookie Management across app restarts
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            cookieManager.setAcceptThirdPartyCookies(webView, true);

            // Setup Native Pull-to-Refresh without any external DB or Firebase
            ViewGroup parent = (ViewGroup) webView.getParent();
            if (parent != null) {
                int index = parent.indexOfChild(webView);
                parent.removeView(webView);

                swipeRefreshLayout = new SwipeRefreshLayout(this);
                swipeRefreshLayout.setColorSchemeColors(
                    Color.parseColor("#0A4D3C"), // DEEN Forest Green
                    Color.parseColor("#EB6508")  // DEEN Brand Amber
                );
                swipeRefreshLayout.setProgressBackgroundColorSchemeColor(Color.parseColor("#FFFFFF"));
                swipeRefreshLayout.addView(webView, new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                ));

                // Only trigger refresh when scrolled to top
                swipeRefreshLayout.setOnChildScrollUpCallback((parentLayout, child) -> webView.getScrollY() > 0);

                swipeRefreshLayout.setOnRefreshListener(() -> {
                    webView.reload();
                });

                parent.addView(swipeRefreshLayout, index, new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                ));

                // Listen for page load completion to dismiss refresh spinner
                this.bridge.addWebViewListener(new WebViewListener() {
                    @Override
                    public void onPageLoaded(WebView view) {
                        if (swipeRefreshLayout != null) {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }

                    @Override
                    public void onReceivedError(WebView view) {
                        if (swipeRefreshLayout != null) {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                });
            }
        }
    }

    @Override
    public void onBackPressed() {
        WebView webView = this.bridge != null ? this.bridge.getWebView() : null;
        if (webView != null && webView.canGoBack()) {
            webView.goBack();
        } else {
            if (System.currentTimeMillis() - lastBackPressedTime < BACK_PRESS_INTERVAL) {
                super.onBackPressed();
            } else {
                lastBackPressedTime = System.currentTimeMillis();
                Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        CookieManager.getInstance().flush();
    }
}
