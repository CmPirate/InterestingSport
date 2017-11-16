package com.tianbao.mi.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tianbao.mi.R;
import com.tianbao.mi.bean.OnDemandCourseBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 直播课程列表信息
 * Created by edianzu on 2017/11/4.
 */
public class OnDemandAdapter extends BaseAdapter {

    private Context mContext;
    private List<OnDemandCourseBean.DataBean> mList;
    private int mIndex;// 焦点位置

    public OnDemandAdapter(Context context, List<OnDemandCourseBean.DataBean> list) {
        this.mContext = context;
        this.mList = list;
    }

    public void setList(List<OnDemandCourseBean.DataBean> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    // 上
    public void up(int index) {
        mIndex = index;
        notifyDataSetChanged();
    }

    // 下
    public void down(int index) {
        mIndex = index;
        notifyDataSetChanged();
    }

    // 获取课程 ID
    public int getCourseId(int index) {
        if (index >= 0 && index < mList.size()) return mList.get(index).getId();
        else return 0;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_on_demand, parent, false);
            holder = new ViewHolder(convertView);
            holder.viewBackground.setAlpha(0.5f);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (mIndex == position) {
            holder.viewBackground.setBackground(mContext.getDrawable(R.drawable.card_white_background));
        } else {
            holder.viewBackground.setBackground(mContext.getDrawable(R.drawable.card_background));
        }

        OnDemandCourseBean.DataBean data = mList.get(position);

        // 教练头像
        String avatar = data.getCoach().getAvatar();
        if (TextUtils.isEmpty(avatar)) {
            holder.imageHead.setImageDrawable(mContext.getDrawable(R.mipmap.image_head_default));// 展示默认头像
        } else {
            Picasso.with(mContext).load(avatar).into(holder.imageHead);
        }

        // 教练名
        String name = data.getCoach().getNick();
        if (TextUtils.isEmpty(name)) name = "课程教练";
        holder.textName.setText(name);

        // 课程标题
        String title = data.getTitle();
        if (TextUtils.isEmpty(title)) title = "健身课程";
        holder.textTitle.setText(title);

        // 课程介绍
        String desc = data.getDescription();
        if (TextUtils.isEmpty(desc)) desc = "课程介绍";
        holder.textDescribe.setText(desc);

        // 课程时间
        String time = data.getTime();
        if (TextUtils.isEmpty(time)) {
            time = "未知";
        }
        holder.textTime.setText(time);
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.view_background)
        View viewBackground;
        @BindView(R.id.image_head)
        ImageView imageHead;// 教练头像
        @BindView(R.id.text_name)
        TextView textName;// 教练名
        @BindView(R.id.text_title)
        TextView textTitle;// 课程标题
        @BindView(R.id.text_describe)
        TextView textDescribe;// 课程描述
        @BindView(R.id.text_time)
        TextView textTime;// 课程时间

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
