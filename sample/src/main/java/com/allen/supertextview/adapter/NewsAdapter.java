package com.allen.supertextview.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.allen.supertextview.R;
import com.allen.supertextview.bean.NewsBean;
import com.squareup.picasso.Picasso;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import me.koterwong.androidstv.SuperTextView;

/**
 * Created by allen on 2016/10/31.
 */

public class NewsAdapter extends CommonAdapter<NewsBean> {
    private Context mContext;
    private SuperTextView superTextView;
    public NewsAdapter(Context context, List<NewsBean> datas) {
        super(context, R.layout.item, datas);
        mContext = context;
    }

    @Override
    protected void convert(ViewHolder holder, NewsBean newsBean, int position) {
        if (superTextView ==null){
            superTextView = holder.getView(R.id.super_tv);
        }
        holder.setText(superTextView.getViewId(SuperTextView.leftTopTextViewId),newsBean.getTitle());
        holder.setText(superTextView.getViewId(SuperTextView.leftBottomTextViewId),newsBean.getTime());
        Picasso.with(mContext).load(newsBean.getImgUrl()).into((ImageView) superTextView.getView(SuperTextView.leftImageViewId));
    }
}
