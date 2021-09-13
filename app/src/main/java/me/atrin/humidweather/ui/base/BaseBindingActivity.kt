package me.atrin.humidweather.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.dylanc.viewbinding.base.inflateBindingWithGeneric
import com.zackratos.ultimatebarx.ultimatebarx.navigationBar

abstract class BaseBindingActivity<VB : ViewBinding> : AppCompatActivity() {

    lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflateBindingWithGeneric(layoutInflater)
        setContentView(binding.root)

        initBar()
    }

    /**
     * 初始化状态栏或导航栏
     */
    protected open fun initBar() {
        // 初始化导航栏
        navigationBar  {
            light = true
            transparent()
        }
    }
}