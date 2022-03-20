package me.atrin.humidweather.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.dylanc.viewbinding.base.ViewBindingUtil
import com.zackratos.ultimatebarx.ultimatebarx.navigationBar

abstract class BaseBindingActivity<VB : ViewBinding> : AppCompatActivity() {

    lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ViewBindingUtil.inflateWithGeneric(this, layoutInflater)
        setContentView(binding.root)

        defineView()
        initSystemBar()
    }

    protected open fun defineView() {}

    /**
     * 初始化状态栏或导航栏
     */
    protected open fun initSystemBar() {
        // 初始化导航栏
        navigationBar {
            light = true
            transparent()
        }
    }

}