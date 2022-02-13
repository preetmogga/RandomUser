package com.mogga.randomuser.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mogga.randomuser.R
import com.mogga.randomuser.model.User

class Adapter:RecyclerView.Adapter<ViewHolder>() {
    private val itemView = ArrayList<User>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view,parent,false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = itemView[position].results[position]

        holder.userName.text = currentItem.name.first
        holder.userCity.text = currentItem.location.city
        holder.userGender.text = currentItem.gender
       Glide.with(holder.itemView.context).load(currentItem.picture.large).into(holder.userImage)

    }

    override fun getItemCount(): Int {
        return itemView.size


    }
    @SuppressLint("NotifyDataSetChanged")
    fun addData(list: User){
        itemView.clear()
        itemView.addAll(listOf(list))
        notifyDataSetChanged()
    }


}
class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){

   val userImage: ImageView = itemView.findViewById(R.id.userImage)
    val userName:TextView = itemView.findViewById(R.id.userName)
    val userGender:TextView = itemView.findViewById(R.id.userGender)
    val userCity:TextView = itemView.findViewById(R.id.userCity)


}