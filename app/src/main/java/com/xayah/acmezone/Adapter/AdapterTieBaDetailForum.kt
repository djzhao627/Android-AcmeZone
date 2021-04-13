package com.xayah.acmezone.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.xayah.acmezone.Class.ClassTieBaForum
import com.xayah.acmezone.R


class AdapterTieBaDetailForum(mContext: Context, mForumList: MutableList<ClassTieBaForum?>) :
    RecyclerView.Adapter<AdapterTieBaDetailForum.ViewHolderTieBaMain>() {
    var mContext: Context
    var mTieBaForumList: MutableList<ClassTieBaForum?>

    init {
        this.mContext = mContext
        this.mTieBaForumList = mForumList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderTieBaMain {
        val itemView: View =
            LayoutInflater.from(mContext).inflate(
                R.layout.recyclerview_tieba_detail_forum,
                parent,
                false
            )
        return ViewHolderTieBaMain(itemView)
    }

    override fun getItemCount(): Int {
        return mTieBaForumList.size
    }

    class ViewHolderTieBaMain(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tieba_cardview_forum: CardView
        var tieba_forumTitle: TextView
        var tieba_forumLevel: ImageView
        var tieba_subtitle: TextView
        var tieba_forumScore: TextView
        var tieba_levelupScore: TextView


        init {
            tieba_cardview_forum = itemView.findViewById(R.id.tieba_cardview_forum)
            tieba_forumTitle = itemView.findViewById(R.id.tieba_forumTitle)
            tieba_forumLevel = itemView.findViewById(R.id.tieba_forumLevel)
            tieba_subtitle = itemView.findViewById(R.id.tieba_subtitle)
            tieba_forumScore = itemView.findViewById(R.id.tieba_forumScore)
            tieba_levelupScore = itemView.findViewById(R.id.tieba_levelupScore)
        }
    }

    override fun onBindViewHolder(holder: ViewHolderTieBaMain, position: Int) {
        val mTieBaMainForum: ClassTieBaForum? = mTieBaForumList.get(position)

        holder.tieba_forumTitle.text = mTieBaMainForum!!.name
        holder.tieba_forumScore.text = mTieBaMainForum.cur_score
        holder.tieba_levelupScore.text = "/" + mTieBaMainForum.levelup_score

        when (mTieBaMainForum.level_id) {
            "1" -> {
                holder.tieba_forumLevel.setImageResource(R.drawable.tieba_level1)
            }
            "2" -> {
                holder.tieba_forumLevel.setImageResource(R.drawable.tieba_level2)
            }
            "3" -> {
                holder.tieba_forumLevel.setImageResource(R.drawable.tieba_level3)
            }
            "4" -> {
                holder.tieba_forumLevel.setImageResource(R.drawable.tieba_level4)
            }
            "5" -> {
                holder.tieba_forumLevel.setImageResource(R.drawable.tieba_level5)
            }
            "6" -> {
                holder.tieba_forumLevel.setImageResource(R.drawable.tieba_level6)
            }
            "7" -> {
                holder.tieba_forumLevel.setImageResource(R.drawable.tieba_level7)
            }
            "8" -> {
                holder.tieba_forumLevel.setImageResource(R.drawable.tieba_level8)
            }
            "9" -> {
                holder.tieba_forumLevel.setImageResource(R.drawable.tieba_level9)
            }
            "10" -> {
                holder.tieba_forumLevel.setImageResource(R.drawable.tieba_level10)
            }
            "11" -> {
                holder.tieba_forumLevel.setImageResource(R.drawable.tieba_level11)
            }
            "12" -> {
                holder.tieba_forumLevel.setImageResource(R.drawable.tieba_level12)
            }
            "13" -> {
                holder.tieba_forumLevel.setImageResource(R.drawable.tieba_level13)
            }
            "14" -> {
                holder.tieba_forumLevel.setImageResource(R.drawable.tieba_level14)
            }
            "15" -> {
                holder.tieba_forumLevel.setImageResource(R.drawable.tieba_level15)
            }
            "16" -> {
                holder.tieba_forumLevel.setImageResource(R.drawable.tieba_level16)
            }
            "17" -> {
                holder.tieba_forumLevel.setImageResource(R.drawable.tieba_level17)
            }
            "18" -> {
                holder.tieba_forumLevel.setImageResource(R.drawable.tieba_level18)
            }

        }

        when (mTieBaMainForum.state) {
            "unknown" -> {
                holder.tieba_cardview_forum.setCardBackgroundColor(
                    ContextCompat.getColor(
                        mContext,
                        R.color.mGrayBackground
                    )
                )
                holder.tieba_forumTitle.setTextColor(
                    ContextCompat.getColor(
                        mContext,
                        R.color.black
                    )
                )
                holder.tieba_subtitle.setTextColor(
                    ContextCompat.getColor(
                        mContext,
                        R.color.mGrayDark
                    )
                )
                holder.tieba_forumScore.setTextColor(
                    ContextCompat.getColor(
                        mContext,
                        R.color.black
                    )
                )
                holder.tieba_levelupScore.setTextColor(
                    ContextCompat.getColor(
                        mContext,
                        R.color.black
                    )
                )
            }
            "signed" -> {
                holder.tieba_cardview_forum.setCardBackgroundColor(
                    ContextCompat.getColor(
                        mContext,
                        R.color.mGreen
                    )
                )
                holder.tieba_forumTitle.setTextColor(
                    ContextCompat.getColor(
                        mContext,
                        R.color.trueWhite
                    )
                )
                holder.tieba_subtitle.setTextColor(
                    ContextCompat.getColor(
                        mContext,
                        R.color.mGrayLight
                    )
                )
                holder.tieba_forumScore.setTextColor(
                    ContextCompat.getColor(
                        mContext,
                        R.color.trueWhite
                    )
                )
                holder.tieba_levelupScore.setTextColor(
                    ContextCompat.getColor(
                        mContext,
                        R.color.trueWhite
                    )
                )
            }
            "error" -> {
                holder.tieba_cardview_forum.setCardBackgroundColor(
                    ContextCompat.getColor(
                        mContext,
                        R.color.mRed
                    )
                )
                holder.tieba_forumTitle.setTextColor(
                    ContextCompat.getColor(
                        mContext,
                        R.color.trueWhite
                    )
                )
                holder.tieba_subtitle.setTextColor(
                    ContextCompat.getColor(
                        mContext,
                        R.color.mGrayLight
                    )
                )
                holder.tieba_forumScore.setTextColor(
                    ContextCompat.getColor(
                        mContext,
                        R.color.trueWhite
                    )
                )
                holder.tieba_levelupScore.setTextColor(
                    ContextCompat.getColor(
                        mContext,
                        R.color.trueWhite
                    )
                )
            }

        }
    }

    fun setItem(l_state: String, l_position: Int) {
        val mClassTieBaDetailForum: ClassTieBaForum? = mTieBaForumList.get(l_position)
        mClassTieBaDetailForum!!.state = l_state
        mTieBaForumList.set(l_position, mClassTieBaDetailForum) //在集合中修改这条数据
        notifyItemChanged(l_position)
    }

}