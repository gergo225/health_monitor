package com.fazekasgergo.healthmonitor.pages.questions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.fazekasgergo.healthmonitor.databinding.FragmentChooseQuestionBinding
import com.fazekasgergo.healthmonitor.databinding.FragmentInputQuestionBinding
import com.fazekasgergo.healthmonitor.databinding.FragmentQuestionBinding

class QuestionFragment : Fragment() {

    private lateinit var viewModel: QuestionViewModel

    private var _binding: FragmentQuestionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
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
            if (it != null && it) {
                navController.navigate(QuestionFragmentDirections.actionQuestionDestToResultsFragment())
            }
        }

        viewModel.eventGoToNextQuestion.observe(viewLifecycleOwner) {
            if (it != null && it) {
                navController.navigate(QuestionFragmentDirections.actionQuestionDestSelf())
            }
        }

        viewModel.currentQuestion.observe(viewLifecycleOwner) {
            when (it) {
                is Question.ChooseQuestion -> setChooseQuestion(container)
                is Question.InputQuestion -> setInputQuestion(container)
            }
        }

        return binding.root
    }

    override fun onDestroy() {
        viewModel.previousQuestion()
        super.onDestroy()
    }

    private fun setChooseQuestion(container: ViewGroup?) {
        val chooseQuestionBinding =
            FragmentChooseQuestionBinding.inflate(
                layoutInflater,
                container,
                false
            )     // TODO: should set it to 'true', but that causes error: Views added to a FragmentContainerView must be associated with a Fragment
        chooseQuestionBinding.nextButton.setOnClickListener { viewModel.nextQuestion() }
        binding.root.addView(chooseQuestionBinding.root.rootView)
    }

    private fun setInputQuestion(container: ViewGroup?) {
        val inputQuestionBinding =
            FragmentInputQuestionBinding.inflate(layoutInflater, container, false)
        inputQuestionBinding.nextButton2.setOnClickListener { viewModel.nextQuestion() }
        binding.root.addView(inputQuestionBinding.root.rootView)
    }
}