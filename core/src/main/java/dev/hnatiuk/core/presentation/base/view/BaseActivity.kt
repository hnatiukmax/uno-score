package dev.hnatiuk.core.presentation.base.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import dev.hnatiuk.core.presentation.base.LayoutInflate
import dev.hnatiuk.core.presentation.base.viewmodel.BaseViewModel
import dev.hnatiuk.core.presentation.base.viewmodel.Event
import dev.hnatiuk.core.presentation.extensions.toast

abstract class BaseActivity<VB : ViewBinding, VM : BaseViewModel<E>, E : Event> :
    AppCompatActivity(), View<VB, VM, E> {

    protected lateinit var binding: VB

    protected abstract val bindingFactory: LayoutInflate<VB>
    protected abstract val viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(bindingFactory.invoke(layoutInflater)) {
            binding = this
            setContentView(root)
            initUI()
        }
        handleViewModel()
    }

    private fun handleViewModel() = with(viewModel) {
        observeViewModel()
        event.observe(this@BaseActivity, ::handleEvent)
        onShowToastMessage.observe(this@BaseActivity) {
            when (it) {
                is BaseViewModel.Message.StringMessage -> toast(it.value)
                is BaseViewModel.Message.ResourceMessage -> toast(getString(it.resId, it.args))
            }
        }
    }

    override fun VB.initUI() {
        //no op
    }

    override fun VM.observeViewModel() {
        //no op
    }

    override fun handleEvent(event: Event) {
        //no op
    }
}