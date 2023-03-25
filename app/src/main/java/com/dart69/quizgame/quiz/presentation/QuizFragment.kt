package com.dart69.quizgame.quiz.presentation

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.core.view.forEach
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.dart69.quizgame.R
import com.dart69.quizgame.common.coroutines.collectWithLifecycle
import com.dart69.quizgame.common.presentation.BaseFragment
import com.dart69.quizgame.databinding.FragmentQuizBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuizFragment : BaseFragment<FragmentQuizBinding, QuizViewModel>(R.layout.fragment_quiz) {
    override val binding: FragmentQuizBinding by viewBinding(FragmentQuizBinding::bind)
    override val viewModel: QuizViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.observeStates().collectWithLifecycle(viewLifecycleOwner) {
            binding.textViewQuestion.text = it.quizStats.quiz.question
            binding.textViewHint.text =
                getString(R.string.current_score_with_time_left, it.quizStats.points, it.timeLeft)
            binding.buttonAnswer1.text = it.quizStats.quiz.answers.component1()
            binding.buttonAnswer2.text = it.quizStats.quiz.answers.component2()
            binding.buttonAnswer3.text = it.quizStats.quiz.answers.component3()
        }
        binding.root.forEach { v ->
            if (v is Button) {
                v.setOnClickListener { viewModel.answerTheQuestion(v.text.toString()) }
            }
        }
    }
}

