package com.dart69.quizgame.common.presentation

import androidx.annotation.LayoutRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB : ViewBinding, VM : BaseViewModel<*, *>>(
    @LayoutRes layoutRes: Int
) : Fragment(layoutRes) {
    protected abstract val binding: VB
    protected abstract val viewModel: VM
}

fun BaseFragment<*, *>.requireActionBar(): ActionBar? =
    (requireActivity() as AppCompatActivity).supportActionBar