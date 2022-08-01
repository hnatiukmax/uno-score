package dev.hnatiuk.core.presentation.base.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import dev.hnatiuk.core.presentation.base.viewmodel.BaseViewModel
import dev.hnatiuk.core.presentation.base.viewmodel.Event
import dev.hnatiuk.core.presentation.base.Inflate

abstract class BaseFragment<VB : ViewBinding, VM : BaseViewModel<E>, E : Event> : Fragment(),
    View<VB, VM, E> {

    protected abstract val viewModel: VM
    protected abstract val inflate: Inflate<VB>

    protected lateinit var binding: VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): android.view.View {
        binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: android.view.View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.initUI()
        binding.bind()
        viewModel.onViewLoaded()
        handleViewModel()
    }

    private fun handleViewModel() = with(viewModel) {
        observeViewModel()
        event.observe(viewLifecycleOwner, ::handleEvent)
    }

    override fun VB.initUI() {
        //no op
    }

    override fun VB.bind() {
        //no op
    }

    override fun VM.observeViewModel() {
        //no op
    }

    override fun handleEvent(event: Event) {
        //no op
    }
}