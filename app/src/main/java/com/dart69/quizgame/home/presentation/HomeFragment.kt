package com.dart69.quizgame.home.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.dart69.quizgame.R
import com.dart69.quizgame.common.coroutines.collectWithLifecycle
import com.dart69.quizgame.common.presentation.BaseFragment
import com.dart69.quizgame.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {
    override val binding: FragmentHomeBinding by viewBinding(FragmentHomeBinding::bind)
    override val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.observeStates().collectWithLifecycle(viewLifecycleOwner) {
            binding.textViewPoints.text = getString(R.string.current_score, it.points)
        }

        viewModel.observeEvents().collectWithLifecycle(viewLifecycleOwner) {
            it.apply(findNavController())
        }

        binding.radioGroup.setOnCheckedChangeListener { _, difficulty ->
            viewModel.setDifficulty(difficulty)
        }
        binding.buttonStart.setOnClickListener {
            viewModel.startQuiz()
        }
        binding.buttonStore.setOnClickListener {
            viewModel.goToStore()
        }
    }
}