package lyon.browser.mutilewebview;

import android.graphics.Bitmap;
import android.webkit.WebView;

public class WebviewModel {
    WebView webView;
    String url = "";
    String title ="";
    Bitmap favicon;

    public void setWebView(WebView webView){
        this.webView=webView;
    }
    public WebView getWebView(){
        return this.webView;
    }

    public void setUrl(String url){
        this.url=url;
    }

    public String getUrl(){
        return url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle(){
        return title;
    }

    public void setFavicon(Bitmap favicon) {
        this.favicon = favicon;
    }

    public Bitmap getFavicon() {
        return favicon;
    }
}
