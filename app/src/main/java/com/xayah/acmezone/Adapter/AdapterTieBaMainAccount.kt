package com.xayah.acmezone.Adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.xayah.acmezone.Activity.ActivityTieBaDetail
import com.xayah.acmezone.Class.ClassTieBaAccount
import com.xayah.acmezone.Component.NetImageViewCircle
import com.xayah.acmezone.R


class AdapterTieBaMainAccount(mContext: Context, mAccountList: MutableList<ClassTieBaAccount?>) :
    RecyclerView.Adapter<AdapterTieBaMainAccount.ViewHolderTieBaMain>() {
    var mContext: Context
    var mTieBaAccountList: MutableList<ClassTieBaAccount?>
    var isRefresh = false

    init {
        this.mContext = mContext
        this.mTieBaAccountList = mAccountList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderTieBaMain {
        val itemView: View =
            LayoutInflater.from(mContext).inflate(
                R.layout.recyclerview_tieba_main_account,
                parent,
                false
            )
        return ViewHolderTieBaMain(itemView)
    }

    override fun getItemCount(): Int {
        return mTieBaAccountList.size
    }

    class ViewHolderTieBaMain(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tieBa_account_circleImageView_head: NetImageViewCircle
        var tieBa_account_textView_userName: TextView
        var tieBa_account_textView_signedNum: TextView
        var tieBa_account_textView_like_forumNum: TextView
        var tieBa_account_textView_signTitle: TextView
        var tieba_account_floatingActionButton_sign: FloatingActionButton
        var tieba_cardview_account: CardView

        init {
            tieBa_account_circleImageView_head =
                itemView.findViewById(R.id.tieBa_account_circleImageView_head)
            tieBa_account_textView_userName =
                itemView.findViewById(R.id.tieBa_account_textView_userName)
            tieBa_account_textView_signedNum =
                itemView.findViewById(R.id.tieBa_account_textView_signedNum)
            tieBa_account_textView_like_forumNum =
                itemView.findViewById(R.id.tieBa_account_textView_like_forumNum)
            tieba_account_floatingActionButton_sign =
                itemView.findViewById(R.id.tieba_account_floatingActionButton_sign)
            tieBa_account_textView_signTitle =
                itemView.findViewById(R.id.tieBa_account_textView_signTitle)
            tieba_cardview_account = itemView.findViewById(R.id.tieba_cardview_account)
        }
    }

    override fun onBindViewHolder(holder: ViewHolderTieBaMain, position: Int) {
        val mTieBaMainAccount: ClassTieBaAccount? = mTieBaAccountList.get(position)
        if (!isRefresh) {
            holder.tieBa_account_circleImageView_head.setImageURL(mTieBaMainAccount!!.portrait_url)
        }
        holder.tieBa_account_textView_userName.text = mTieBaMainAccount!!.name
        holder.tieBa_account_textView_signedNum.text = mTieBaMainAccount.signed_forum_num
        holder.tieBa_account_textView_like_forumNum.text = "/" + mTieBaMainAccount.like_forum_num

        holder.tieba_cardview_account.setOnClickListener {
            val intent = Intent(mContext, ActivityTieBaDetail::class.java)
            intent.putExtra("accountName", mTieBaMainAccount.name)
            mContext.startActivity(intent)

        }

        when (mTieBaMainAccount.state) {
            "unknown" -> {
                holder.tieba_cardview_account.setCardBackgroundColor(
                    ContextCompat.getColor(
                        mContext,
                        R.color.mGrayBackground
                    )
                )
                holder.tieBa_account_textView_userName.setTextColor(
                    ContextCompat.getColor(
                        mContext,
                        R.color.black
                    )
                )
                holder.tieBa_account_textView_signTitle.setTextColor(
                    ContextCompat.getColor(
                        mContext,
                        R.color.mGrayDark
                    )
                )
                holder.tieBa_account_textView_signedNum.setTextColor(
                    ContextCompat.getColor(
                        mContext,
                        R.color.black
                    )
                )
                holder.tieBa_account_textView_like_forumNum.setTextColor(
                    ContextCompat.getColor(
                        mContext,
                        R.color.black
                    )
                )
                var colorStateList =
                    ContextCompat.getColorStateList(mContext, R.color.mGrayBackground)
                holder.tieba_account_floatingActionButton_sign.backgroundTintList = colorStateList
                colorStateList = ContextCompat.getColorStateList(mContext, R.color.black)
                holder.tieba_account_floatingActionButton_sign.imageTintList = colorStateList
                holder.tieba_account_floatingActionButton_sign.setImageResource(R.drawable.tieba_ic_sign)
            }
            "signing" -> {
                holder.tieba_cardview_account.setCardBackgroundColor(
                    ContextCompat.getColor(
                        mContext,
                        R.color.mOrange
                    )
                )
                holder.tieBa_account_textView_userName.setTextColor(Color.parseColor("#ffffff"))
                holder.tieBa_account_textView_signTitle.setTextColor(Color.parseColor("#F6F6F6"))
                holder.tieBa_account_textView_signedNum.setTextColor(Color.parseColor("#ffffff"))
                holder.tieBa_account_textView_like_forumNum.setTextColor(Color.parseColor("#ffffff"))
                var colorStateList = ContextCompat.getColorStateList(mContext, R.color.mOrange)
                holder.tieba_account_floatingActionButton_sign.backgroundTintList = colorStateList
                colorStateList = ContextCompat.getColorStateList(mContext, R.color.white)
                holder.tieba_account_floatingActionButton_sign.imageTintList = colorStateList
                holder.tieba_account_floatingActionButton_sign.setImageResource(R.drawable.bilibili_ic_wait)
            }
            "signed" -> {
                holder.tieba_cardview_account.setCardBackgroundColor(
                    ContextCompat.getColor(
                        mContext,
                        R.color.mGreen
                    )
                )
                holder.tieBa_account_textView_userName.setTextColor(Color.parseColor("#ffffff"))
                holder.tieBa_account_textView_signTitle.setTextColor(Color.parseColor("#F6F6F6"))
                holder.tieBa_account_textView_signedNum.setTextColor(Color.parseColor("#ffffff"))
                holder.tieBa_account_textView_like_forumNum.setTextColor(Color.parseColor("#ffffff"))
                var colorStateList = ContextCompat.getColorStateList(mContext, R.color.mGreen)
                holder.tieba_account_floatingActionButton_sign.backgroundTintList = colorStateList
                colorStateList = ContextCompat.getColorStateList(mContext, R.color.white)
                holder.tieba_account_floatingActionButton_sign.imageTintList = colorStateList
                holder.tieba_account_floatingActionButton_sign.setImageResource(R.drawable.bilibili_ic_check)
            }
            "refresh" -> {
                holder.tieba_cardview_account.setCardBackgroundColor(
                    ContextCompat.getColor(
                        mContext,
                        R.color.mBlue
                    )
                )
                holder.tieBa_account_textView_userName.setTextColor(Color.parseColor("#ffffff"))
                holder.tieBa_account_textView_signTitle.setTextColor(Color.parseColor("#F6F6F6"))
                holder.tieBa_account_textView_signedNum.setTextColor(Color.parseColor("#ffffff"))
                holder.tieBa_account_textView_like_forumNum.setTextColor(Color.parseColor("#ffffff"))
                var colorStateList = ContextCompat.getColorStateList(mContext, R.color.mBlue)
                holder.tieba_account_floatingActionButton_sign.backgroundTintList = colorStateList
                colorStateList = ContextCompat.getColorStateList(mContext, R.color.white)
                holder.tieba_account_floatingActionButton_sign.imageTintList = colorStateList
                holder.tieba_account_floatingActionButton_sign.setImageResource(R.drawable.tieba_ic_refresh)
            }
        }
    }

    fun setItem(l_signed_forum_num: String, l_position: Int) {
        val mClassTieBaMainAccount: ClassTieBaAccount? = mTieBaAccountList.get(l_position)
        mClassTieBaMainAccount!!.signed_forum_num = l_signed_forum_num
        isRefresh = true
        mTieBaAccountList.set(l_position, mClassTieBaMainAccount) //在集合中修改这条数据
        isRefresh = false
        notifyItemChanged(l_position)
    }

}