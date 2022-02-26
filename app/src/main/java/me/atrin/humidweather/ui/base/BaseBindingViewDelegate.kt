package me.atrin.humidweather.ui.base

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.drakeet.multitype.ItemViewDelegate
import com.dylanc.viewbinding.base.ViewBindingUtil

abstract class BaseBindingViewDelegate<T, VB : ViewBinding> :
    ItemViewDelegate<T, BaseBindingViewDelegate.BindingViewHolder<VB>>() {

    override fun onCreateViewHolder(context: Context, parent: ViewGroup) =
        BindingViewHolder(ViewBindingUtil.inflateWithGeneric<VB>(this, parent))

    override fun onBindViewHolder(holder: BindingViewHolder<VB>, item: T) {
        onBindViewHolder(holder.binding, item, holder.bindingAdapterPosition)
    }

    abstract fun onBindViewHolder(binding: VB, item: T, position: Int)

    class BindingViewHolder<VB : ViewBinding>(val binding: VB) :
        RecyclerView.ViewHolder(binding.root)

}