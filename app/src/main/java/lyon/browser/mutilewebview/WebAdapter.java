package lyon.browser.mutilewebview;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class WebAdapter extends RecyclerView.Adapter<WebViewHolder> {

    private Context context;
    private List<String> urls;
    int max=6;
    public WebAdapter(Context context) {
        this.context = context;
        this.urls = new ArrayList<>();
        // 添加6個Google首頁的URL
        for (int i = 0; i < max; i++) {
            urls.add(getLastUrlFromSharedPreferences(context,i));
        }

    }

    @NonNull
    @Override
    public WebViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_webview, parent, false);
        // 設置每個項目的寬度為視窗的一半
        view.getLayoutParams().width = parent.getResources().getDisplayMetrics().widthPixels / 2;
        view.getLayoutParams().height = parent.getResources().getDisplayMetrics().widthPixels / 2;
        return new WebViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WebViewHolder holder, int position) {
        // 在這裡設置 WebView 的內容
        // 設置 WebView 的內容
        holder.webView.loadUrl(getLastUrlFromSharedPreferences(context,position));
        holder.webView.setTag(position);
        // 設置 WebView 的 WebViewClient 以便監聽 URL 變化
        holder.webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // 在頁面載入完成後，設置網址
                holder.urlTextView.setText(url);
                // 開始加載網址的圖示
                new LoadFaviconTask(holder.iconImageView).execute("https://www.google.com/s2/favicons?sz=64&domain=" + url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                // 當 URL 變化時，記錄 URL
                String newUrl = request.getUrl().toString();
                holder.urlTextView.setText(newUrl);
                // 開始加載網址的圖示
                new LoadFaviconTask(holder.iconImageView).execute("https://www.google.com/s2/favicons?sz=64&domain=" + newUrl);

                // 使用唯一的鍵值保存 URL 到 SharedPreferences
                saveUrlToSharedPreferences(context, (int)holder.webView.getTag(), newUrl);

                return super.shouldOverrideUrlLoading(view, request);
            }
        });
    }

    private void saveUrlToSharedPreferences(Context context, int position, String url) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        // 使用不同的鍵值保存每個 WebView 的 URL
        editor.putString("lastUrl" + position, url);
        editor.apply();
    }

    private String getLastUrlFromSharedPreferences(Context context, int position) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        // 如果找不到對應的 lastUrl，返回空字符串
        return sharedPreferences.getString("lastUrl" + position, "https://www.google.com");
    }

    @Override
    public int getItemCount() {
        return urls.size(); // 顯示6個WebView
    }

    private static class LoadFaviconTask extends AsyncTask<String, Void, Bitmap> {

        private ImageView imageView;

        public LoadFaviconTask(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            String url = params[0];
            try {
                InputStream inputStream = new java.net.URL(url).openStream();
                return BitmapFactory.decodeStream(inputStream);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (bitmap != null) {
                // 設置圖示
                imageView.setImageBitmap(bitmap);
            }
        }
    }
}