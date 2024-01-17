package lyon.browser.mutilewebview;


import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class WebViewHolder extends RecyclerView.ViewHolder {

    WebView webView;
    TextView urlTextView;
    ImageView iconImageView;
    public WebViewHolder(View itemView) {
        super(itemView);
        webView = itemView.findViewById(R.id.webView);
        urlTextView = itemView.findViewById(R.id.urlTextView);
        iconImageView = itemView.findViewById(R.id.iconImageView);
        // 設置 WebView 寬度為視窗的一半
        webView.getLayoutParams().width = itemView.getResources().getDisplayMetrics().widthPixels / 2;
        webView.getLayoutParams().height = itemView.getResources().getDisplayMetrics().widthPixels / 2;
    }
}