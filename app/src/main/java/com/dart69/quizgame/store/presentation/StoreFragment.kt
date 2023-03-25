package com.dart69.quizgame.store.presentation

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.dart69.quizgame.R
import com.dart69.quizgame.common.coroutines.collectWithLifecycle
import com.dart69.quizgame.common.presentation.BaseFragment
import com.dart69.quizgame.databinding.FragmentStoreBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StoreFragment : BaseFragment<FragmentStoreBinding, StoreViewModel>(R.layout.fragment_store) {
    override val binding: FragmentStoreBinding by viewBinding(FragmentStoreBinding::bind)
    override val viewModel: StoreViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = WallpaperItemAdapter(callbacks = viewModel)
        binding.recyclerView.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(requireContext()).also {
                it.orientation = LinearLayoutManager.HORIZONTAL
            }
            addItemDecoration(MarginItemDecoration(MARGIN_SIZE))
        }
        viewModel.observeStates().collectWithLifecycle(viewLifecycleOwner) {
            binding.progressBar.isVisible = it.isInProgress
            binding.textViewScores.text = getString(R.string.current_score, it.points)
            adapter.submitList(it.wallpapers)
        }
        viewModel.observeEvents().collectWithLifecycle(viewLifecycleOwner) {
            it.applyOn(requireContext())
        }
    }

    private companion object {
        const val MARGIN_SIZE = 8
    }
}