package com.fazekasgergo.healthmonitor.pages.questions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
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
                navController.navigate(QuestionFragmentDirections.actionQuestionDestToResultsFragment(viewModel.answers))
            }
        }

        viewModel.eventGoToNextQuestion.observe(viewLifecycleOwner) {
            if (it != null && it) {
                navController.navigate(QuestionFragmentDirections.actionQuestionDestSelf())
            }
        }

        viewModel.currentQuestion.observe(viewLifecycleOwner) {
            binding.root.removeAllViews()
            when (it) {
                is Question.ChooseQuestion -> setChooseQuestion(container, it)
                is Question.InputQuestion -> setInputQuestion(container, it)
            }
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.previousQuestion()
    }

    private fun setChooseQuestion(container: ViewGroup?, question: Question.ChooseQuestion) {
        val chooseQuestionBinding =
            FragmentChooseQuestionBinding.inflate(
                layoutInflater,
                container,
                false
            )     // TODO: should set it to 'true', but that causes error: Views added to a FragmentContainerView must be associated with a Fragment

        chooseQuestionBinding.chooseQuestionText.text = question.questionText
        val adapter = ChooseOptionsAdapter()
        chooseQuestionBinding.chooseOptionsList.adapter = adapter
        adapter.chooseOptions = question.options.toList()
        adapter.chooseOptionIcons = question.resourceIds.toList()
        adapter.context = context
        adapter.setOnItemButtonClickListener(object : ChooseOptionsAdapter.OnItemButtonClickListener {
            override fun onItemButtonClick(position: Int) {
                viewModel.nextQuestion(position)
            }
        })
        adapter.setSelection(viewModel.currentAnswer ?: -1)
        binding.root.addView(chooseQuestionBinding.root.rootView)
    }

    private fun setInputQuestion(container: ViewGroup?, question: Question.InputQuestion) {
        val inputQuestionBinding =
            FragmentInputQuestionBinding.inflate(layoutInflater, container, false)
        inputQuestionBinding.inputQuestionText.text = question.questionText
        inputQuestionBinding.inputQuestionIcon.setImageResource(question.resourceIds.first())
        inputQuestionBinding.inputQuestionEditText.setOnEditorActionListener {_, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE)
                viewModel.nextQuestion(inputQuestionBinding.inputQuestionEditText.text.toString().toInt())
            true
        }
        inputQuestionBinding.inputQuestionEditText.setText(viewModel.currentAnswer?.toString() ?: "")
        binding.root.addView(inputQuestionBinding.root.rootView)
    }
}