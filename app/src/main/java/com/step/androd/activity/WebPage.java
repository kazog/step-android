package com.step.androd.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.ConsoleMessage;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.step.androd.R;

/**
 * Create By: Meng
 * Create Date: 2022/04/10
 * Desc:
 */
public class WebPage extends AppCompatActivity {
    private WebView webView;
    private String url = "http://192.168.253.132:9000/";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_page);

        initView();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initView() {
        webView = findViewById(R.id.app_webpage);
        Log.i("WebViewPage", url);
        webView.loadUrl(url);
        // \可以使用 loadDataWithBaseURL() 并设置由自定义架构和有效主机组成的基准网址，例如 "example-app://<valid_host_name>/"。
//        webView.loadDataWithBaseURL("example-app://example.co.uk/", "HTML_DATA", null, "UTF-8", null);


//        webView.setWebContentsDebuggingEnabled(true); // 以通过桌面设备上的 Chrome 启用远程调试
    }

    private void bridge() {
        /**  暴露js调用原生
         * function showAndroidToast(toast) {
         *   在Web页面中调用Android方法
         *   window.bridge.toast(toast); -> 通过桥梁调用方法
         * }
         */
        webView.addJavascriptInterface(new WebAppInterface(this), "bridge"); // 建立通讯桥梁
        webView.removeJavascriptInterface("bridge"); // 移除通讯桥梁
//        String unencodedHtml =
//                "&lt;html&gt;&lt;body&gt;'%23' is the percent code for ‘#‘ &lt;/body&gt;&lt;/html&gt;";
//        String encodedHtml = Base64.encodeToString(unencodedHtml.getBytes(),
//                Base64.NO_PADDING);
//        webView.loadData(encodedHtml, "text/html", "base64");


        /**
         * function setter(name){
         *     this.name = name;
         * }
         *
         * function getter(){
         *     return this.name;
         * }
         */
        String setter = "javascript:setter('"+"wjx"+"');";
        webView.loadUrl(setter); // 调用有参无返回值的函数

        webView.evaluateJavascript("getter()", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String s) { // 调用无参有返回值的函数
                System.out.println("my name is "+s);
            }
        });
    }

    private void back() {
        boolean back = webView.canGoBack(); //  判断网页是否可以回退
        webView.goBack(); // 回退一页

        boolean forward = webView.canGoForward(); // 判断网页是否可以前进
        webView.goForward(); // 前进一页

        webView.goBackOrForward(1); // 正数为前进
        webView.goBackOrForward(-1); // 负数为后退
    }

    private void log() {
        webView.setWebChromeClient(new WebChromeClient() {
            public boolean onConsoleMessage(ConsoleMessage cm) {
                Log.d("MyApplication", cm.message() + " -- From line "
                        + cm.lineNumber() + " of "
                        + cm.sourceId() );
                return true;
            }
        });
    }

    private void setting() {
        WebSettings webSettings = webView.getSettings();

        webSettings.setJavaScriptEnabled(true); // 是否开启JS支持
//        webSettings.setPluginsEnabled(true); // 是否开启插件支持
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); // 是否允许JS打开新窗口

        webSettings.setUseWideViewPort(true); // 缩放至屏幕大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕大小
        webSettings.setSupportZoom(true); // 是否支持缩放
        webSettings.setBuiltInZoomControls(true); // 是否支持缩放变焦，前提是支持缩放
        webSettings.setDisplayZoomControls(false); // 是否隐藏缩放控件

        webSettings.setAllowFileAccess(true); // 是否允许访问文件
        webSettings.setDomStorageEnabled(true); // 是否节点缓存
        webSettings.setDatabaseEnabled(true); // 是否数据缓存
        webSettings.setAppCacheEnabled(true); // 是否应用缓存
        webSettings.setAppCachePath(url); // 设置缓存路径

        webSettings.setMediaPlaybackRequiresUserGesture(false); // 是否要手势触发媒体
        webSettings.setStandardFontFamily("sans-serif"); // 设置字体库格式
        webSettings.setFixedFontFamily("monospace"); // 设置字体库格式
        webSettings.setSansSerifFontFamily("sans-serif"); // 设置字体库格式
        webSettings.setSerifFontFamily("sans-serif"); // 设置字体库格式
        webSettings.setCursiveFontFamily("cursive"); // 设置字体库格式
        webSettings.setFantasyFontFamily("fantasy"); // 设置字体库格式
        webSettings.setTextZoom(100); // 设置文本缩放的百分比
        webSettings.setMinimumFontSize(8); // 设置文本字体的最小值(1~72)
        webSettings.setDefaultFontSize(16); // 设置文本字体默认的大小

//        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); // 弃用
        webSettings.setLoadsImagesAutomatically(false); // 是否自动加载图片
        webSettings.setDefaultTextEncodingName("UTF-8"); // 设置编码格式
        webSettings.setNeedInitialFocus(true); // 是否需要获取焦点
        webSettings.setGeolocationEnabled(false); // 设置开启定位功能
        webSettings.setBlockNetworkLoads(false); // 是否从网络获取资源
    }

    private void webClient() {
        WebViewClient webViewClient = new WebViewClient() {
            // 页面开始加载时调用，这时候可以显示加载进度条，让用户耐心等待页面的加载。
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

            }

            // 页面完成加载时调用，这时候可以隐藏加载进度条，提醒用户页面已经完成加载。
            @Override
            public void onPageFinished(WebView view, String url) {

            }

            // 页面每次加载资源时调用。
            @Override
            public void onLoadResource(WebView view, String url) {

            }

            // WebView加载url默认会调用系统的浏览器，通过重写该方法，实现在当前应用内完成页面加载。
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 如果是授信的地址则直接加载
                if ("www.example.com".equals(Uri.parse(url).getHost())) {
                    return false;
                }
                // 非授信的链接调用系统浏览器
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                return true; // 消费事件终止传递
            }

            // 页面加载发生错误时调用，这时候可以跳转到自定义的错误提醒页面，总比系统默认的错误页面美观，优化用户体验。
            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                view.loadUrl("file:///android_assets/error.html"); // assets目录下放置文件
            }

            /**
             * onReceivedHttpError()：页面加载请求时发生错误。
             * onReceivedSslError()：页面加载资源时发生错误。
             * shouldOverrideKeyEvent()：覆盖按键默认的响应事件，这时候可以根据自身的需求在点击某些按键时加入相应的逻辑。
             * onScaleChanged()：页面的缩放比例发生变化时调用，这时候可以根据当前的缩放比例来重新调整WebView中显示的内容，如修改字体大小、图片大小等。
             * shouldInterceptRequest()：可以根据请求携带的内容来判断是否需要拦截请求
             */
        };
        webView.setWebViewClient(webViewClient);
    }

    private void chromeClient() {
        WebChromeClient webChromeClient = new WebChromeClient(){
            /**
             * onProgressChanged()：页面加载进度发生变化时调用，可以通过该方法实时向用户反馈加载情况，如显示进度条等。
             * onReceivedIcon()：接收Web页面的图标，可以通过该方法把图标设置在原生的控件上，如Toolbar等。
             * onReceivedTitle()：接收Web页面的标题，可以通过该方法把图标设置在原生的控件上，如Toolbar等。
             * onJsAlert()：处理JS的Alert对话框。
             * onJsPrompt()：处理JS的Prompt对话框。
             * onJsConfirm()：处理JS的Confirm对话框。
             * onPermissionRequest()：Web页面请求Android权限时调用。
             * onPermissionRequestCanceled()：Web页面请求Android权限被取消时调用。
             * onShowFileChooser()：Web页面上传文件时调用。
             * getVideoLoadingProgressView()：自定义媒体文件播放加载时的进度条。
             * getDefaultVideoPoster()：设置媒体文件默认的预览图。
             * onShowCustomView()：媒体文件进入全屏时调用。
             * onHideCustomView()：媒体文件退出全屏时调用
             */
        };
        webView.setWebChromeClient(webChromeClient);
    }


    public class WebAppInterface {
        Context mContext;

        WebAppInterface(Context c) {
            mContext = c;
        }

        // 标记为Js接口
        @JavascriptInterface
        public void showToast(String toast) {
            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
        }
    }
}
