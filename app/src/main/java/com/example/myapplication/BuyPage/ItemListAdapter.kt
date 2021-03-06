package com.example.myapplication.BuyPage

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.opengl.Visibility
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Data.Product
import com.example.myapplication.Data.ProductData
import com.example.myapplication.R

class ItemListAdapter(var items:ArrayList<Product>, val context:Context, var hasCount:Boolean,var isCheck:Boolean,var checkedList:ArrayList<Product>?):RecyclerView.Adapter<ItemListAdapter.ViewHolder>() {

    var itemClickListener:OnItemClickListener ?= null
    var itemCheckedListener:OnItemCheckListener ?= null
    lateinit var it_view:View
    var checkNow = false
    var totalIndex = 0

    interface OnItemClickListener{
        fun OnItemClick(holder:ItemListAdapter.ViewHolder, view:View, data: Product, position:Int)
    }

    interface OnItemCheckListener{
        fun OnItemChecked(holder:ItemListAdapter.ViewHolder,view:View,data:Product,position:Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_list,parent,false)
        it_view = v
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return items.size
    }

    @SuppressLint("ResourceType")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        //이미지 설정
        holder.name.text = items[position].name
        if(hasCount){
            holder.count.text = items[position].num.toString()
        }
        else{
            holder.count.text = ""
        }
        holder.price.text = items[position].price.toString()+"원"
        var total = 0
        if(hasCount){
            if(  checkedList != null && checkedList!!.size > 0){
                if(items[position] == checkedList!![0]){
                    //checkedList first
                    totalIndex = position
                }
                if(checkedList!!.contains(items[position])){
                    holder.backGround.setBackgroundColor(Color.parseColor("#ffc000"))
                }else{
                    holder.backGround.setBackgroundColor(Color.parseColor("#ffffff"))
                }

            }
            for (i in totalIndex..position){
                total += (items[i].price * items[i].num)
            }
        }

        holder.totalPrice.text = total.toString()
        holder.img.setImageBitmap(items[position].img)

        if(isCheck){
         holder.check.visibility =  View.VISIBLE
            if(checkedList!!.contains(items[position])){
                holder.check.isChecked = true
            }
            holder.check.setOnCheckedChangeListener { compoundButton, b ->
                if(holder.check.isChecked){
                    itemCheckedListener!!.OnItemChecked(holder,it_view,items[position],position)
                }else{
                    itemCheckedListener!!.OnItemChecked(holder,it_view,items[position],position)
                }
            }
        }else{
            holder.check.visibility = View.INVISIBLE
        }
    }

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var img:ImageView
        var name:TextView
        var price:TextView
        var count:TextView
        var totalPrice:TextView
        var check:CheckBox
        var backGround:LinearLayout
        init{
            img = itemView.findViewById(R.id.item_img)
            name = itemView.findViewById(R.id.item_name)
            price = itemView.findViewById(R.id.item_price)
            count = itemView.findViewById(R.id.item_count)
            totalPrice = itemView.findViewById(R.id.total_price_list)
            check = itemView.findViewById(R.id.seperate_item)
            backGround = itemView.findViewById(R.id.itemBackGround)
            itemView.setOnClickListener {
                val position = adapterPosition
                itemClickListener?.OnItemClick(this,it,items[position],position)
                itemCheckedListener?.OnItemChecked(this,it,items[position],position)
            }
        }
    }

}