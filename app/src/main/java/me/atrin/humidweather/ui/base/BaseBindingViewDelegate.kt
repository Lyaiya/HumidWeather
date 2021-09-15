package me.atrin.humidweather.ui.base

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.drakeet.multitype.ItemViewDelegate
import com.dylanc.viewbinding.base.inflateBindingWithGeneric

abstract class BaseBindingViewDelegate<T, VB : ViewBinding> :
    ItemViewDelegate<T, BaseBindingViewDelegate.BindingViewHolder<VB>>() {

    override fun onCreateViewHolder(context: Context, parent: ViewGroup) =
        BindingViewHolder(inflateBindingWithGeneric<VB>(parent))

    class BindingViewHolder<VB : ViewBinding>(val binding: VB) :
        RecyclerView.ViewHolder(binding.root)

}