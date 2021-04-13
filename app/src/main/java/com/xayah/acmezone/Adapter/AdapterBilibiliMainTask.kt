package com.xayah.acmezone.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.xayah.acmezone.Class.ClassBilibiliMainTask
import com.xayah.acmezone.R
import com.xayah.acmezone.Util.LogUtil


class AdapterBilibiliMainTask(mContext: Context, mTaskList: MutableList<ClassBilibiliMainTask?>) :
    RecyclerView.Adapter<AdapterBilibiliMainTask.ViewHolderBilibiliMain>() {
    var mContext: Context
    var mBilibiliMainTaskList: MutableList<ClassBilibiliMainTask?>

    init {
        this.mContext = mContext
        this.mBilibiliMainTaskList = mTaskList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderBilibiliMain {
        val itemView: View =
            LayoutInflater.from(mContext).inflate(
                R.layout.recyclerview_bilibili_main_task,
                parent,
                false
            )
        return ViewHolderBilibiliMain(itemView)
    }


    override fun getItemCount(): Int {
        return mBilibiliMainTaskList.size
    }

    class ViewHolderBilibiliMain(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var bilibili_main_item_cardview: CardView
        var bilibili_main_item_textView_title: TextView
        var bilibili_main_item_textView_subtitle: TextView
        var bilibili_main_item_textView_progress: TextView
        var bilibili_main_item_textView_reward: TextView

        init {
            bilibili_main_item_cardview = itemView.findViewById(R.id.bilibili_main_item_cardview)
            bilibili_main_item_textView_title =
                itemView.findViewById(R.id.bilibili_main_item_textView_title)
            bilibili_main_item_textView_subtitle =
                itemView.findViewById(R.id.bilibili_main_item_textView_subtitle)
            bilibili_main_item_textView_progress =
                itemView.findViewById(R.id.bilibili_main_item_textView_progress)
            bilibili_main_item_textView_reward =
                itemView.findViewById(R.id.bilibili_main_item_textView_reward)
        }
    }

    override fun onBindViewHolder(holder: ViewHolderBilibiliMain, position: Int) {
        val mBilibiliMainTask: ClassBilibiliMainTask? = mBilibiliMainTaskList.get(position)
        holder.bilibili_main_item_textView_title.text = mBilibiliMainTask!!.title
        holder.bilibili_main_item_textView_subtitle.text = mBilibiliMainTask.subtitle
        holder.bilibili_main_item_textView_progress.text = mBilibiliMainTask.progress
        holder.bilibili_main_item_textView_reward.text = mBilibiliMainTask.reward
        if (mBilibiliMainTask.state.equals("unknown") || mBilibiliMainTask.state.equals("doing")) {
            holder.bilibili_main_item_textView_reward.visibility = View.GONE

        } else {
            holder.bilibili_main_item_textView_reward.visibility = View.VISIBLE
            val layoutParams: LinearLayout.LayoutParams =
                holder.bilibili_main_item_textView_progress.getLayoutParams() as LinearLayout.LayoutParams
            layoutParams.bottomMargin = 0;
            holder.bilibili_main_item_textView_progress.setLayoutParams(layoutParams)


        }

        if (mBilibiliMainTask.state.equals("doing")) {
            holder.bilibili_main_item_cardview.setCardBackgroundColor(
                ContextCompat.getColor(
                    mContext,
                    R.color.mOrange
                )
            )
            holder.bilibili_main_item_textView_title.setTextColor(
                ContextCompat.getColor(
                    mContext,
                    R.color.trueWhite
                )
            )
            holder.bilibili_main_item_textView_subtitle.setTextColor(
                ContextCompat.getColor(
                    mContext,
                    R.color.mGrayLight
                )
            )
            holder.bilibili_main_item_textView_progress.setTextColor(
                ContextCompat.getColor(
                    mContext,
                    R.color.trueWhite
                )
            )
            holder.bilibili_main_item_textView_reward.setTextColor(
                ContextCompat.getColor(
                    mContext,
                    R.color.trueWhite
                )
            )
        } else if (mBilibiliMainTask.state.equals("waiting")) {
            holder.bilibili_main_item_cardview.setCardBackgroundColor(
                ContextCompat.getColor(
                    mContext,
                    R.color.mBlue
                )
            )
            holder.bilibili_main_item_textView_title.setTextColor(
                ContextCompat.getColor(
                    mContext,
                    R.color.trueWhite
                )
            )
            holder.bilibili_main_item_textView_subtitle.setTextColor(
                ContextCompat.getColor(
                    mContext,
                    R.color.mGrayLight
                )
            )
            holder.bilibili_main_item_textView_progress.setTextColor(
                ContextCompat.getColor(
                    mContext,
                    R.color.trueWhite
                )
            )
            holder.bilibili_main_item_textView_reward.setTextColor(
                ContextCompat.getColor(
                    mContext,
                    R.color.trueWhite
                )
            )
        } else if (mBilibiliMainTask.state.equals("finished")) {
            holder.bilibili_main_item_cardview.setCardBackgroundColor(
                ContextCompat.getColor(
                    mContext,
                    R.color.mGreen
                )
            )
            holder.bilibili_main_item_textView_title.setTextColor(
                ContextCompat.getColor(
                    mContext,
                    R.color.trueWhite
                )
            )
            holder.bilibili_main_item_textView_subtitle.setTextColor(
                ContextCompat.getColor(
                    mContext,
                    R.color.mGrayLight
                )
            )
            holder.bilibili_main_item_textView_progress.setTextColor(
                ContextCompat.getColor(
                    mContext,
                    R.color.trueWhite
                )
            )
            holder.bilibili_main_item_textView_reward.setTextColor(
                ContextCompat.getColor(
                    mContext,
                    R.color.trueWhite
                )
            )
        }


    }

    fun setItem(l_reward: String, l_progress: String, l_state: String, l_position: Int) {
        val mClassBilibiliMainTask: ClassBilibiliMainTask? = mBilibiliMainTaskList.get(l_position)
        mClassBilibiliMainTask!!.reward = l_reward
        mClassBilibiliMainTask.progress = l_progress
        mClassBilibiliMainTask.state = l_state
        mBilibiliMainTaskList.set(l_position, mClassBilibiliMainTask) //在集合中添加这条数据
        notifyItemChanged(l_position)
    }

}