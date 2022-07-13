package com.fazekasgergo.healthmonitor.pages.questions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.fazekasgergo.healthmonitor.databinding.FragmentQuestionBinding

class QuestionFragment : Fragment() {

    private lateinit var viewModel: QuestionViewModel

    private var _binding: FragmentQuestionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuestionBinding.inflate(layoutInflater, container, false)

        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        viewModel = ViewModelProvider(requireActivity())[QuestionViewModel::class.java]
        viewModel.questionNumber.observe(viewLifecycleOwner) {
            actionBar?.subtitle = "${it + 1} of ${viewModel.totalQuestions}"
        }

        val navController = findNavController()

        viewModel.eventFinishedQuestions.observe(viewLifecycleOwner) {
            if (it) {
                navController.navigate(QuestionFragmentDirections.actionQuestionDestToResultsFragment())
                viewModel.onFinishedQuestionsEventCompleted()
            }
        }

        viewModel.eventGoToNextQuestion.observe(viewLifecycleOwner) {
            if (it) {
                navController.navigate(QuestionFragmentDirections.actionQuestionDestSelf())
                viewModel.onGoToNextQuestionEventCompleted()
            }
        }

        binding.nextButton.setOnClickListener {
            viewModel.nextQuestion()
        }

        return binding.root
    }

    override fun onDestroy() {
        viewModel.previousQuestion()
        super.onDestroy()
    }
}