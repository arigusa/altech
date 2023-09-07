package com.ari.apkaltechtest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ApiAdapter (val dataApi: List<ResultsItem?>?) : RecyclerView.Adapter<ApiAdapter.MyViewHolder>() {
    class MyViewHolder (view: View) :RecyclerView.ViewHolder(view){
        val imgApi = view.findViewById<ImageView>(R.id.item_image_Api)
        val nameApi = view.findViewById<TextView>(R.id.item_name_api)
        val statusApi = view.findViewById<TextView>(R.id.item_status_api)
        val speciesApi = view.findViewById<TextView>(R.id.item_species_api)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApiAdapter.MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row_api,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: ApiAdapter.MyViewHolder, position: Int) {
        holder.nameApi.text = dataApi?.get(position)?.name
        holder.statusApi.text = dataApi?.get(position)?.status
        holder.speciesApi.text = dataApi?.get(position)?.species

        Glide.with(holder.imgApi)
            .load(dataApi?.get(position)?.image)
            .error(R.drawable.ic_launcher_background)
            .into(holder.imgApi)

        holder.itemView.setOnClickListener {
            val name = dataApi?.get(position)?.name
            Toast.makeText(holder.itemView.context, "${name}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        if (dataApi != null){
            return dataApi.size
        }
        return 0
    }
}