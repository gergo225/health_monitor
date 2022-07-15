package com.fazekasgergo.healthmonitor.pages.results

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.navArgs
import com.fazekasgergo.healthmonitor.R
import com.fazekasgergo.healthmonitor.databinding.DiseaseNameTextViewBinding
import com.fazekasgergo.healthmonitor.databinding.FragmentResultsBinding
import com.fazekasgergo.healthmonitor.databinding.TestDescriptionTextViewBinding
import com.fazekasgergo.healthmonitor.databinding.TestNameTextViewBinding

class ResultsFragment : Fragment() {

    private lateinit var viewModel: ResultsViewModel

    private var _binding: FragmentResultsBinding? = null
    private val binding get() = _binding!!

    val args: ResultsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultsBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this)[ResultsViewModel::class.java]

        resetActionBarSubtitle()

        val answers = args.answers

        viewModel.bmiResultText.observe(viewLifecycleOwner) { bmiText -> binding.resultText.text = bmiText }

        viewModel.bmiVal.observe(viewLifecycleOwner) { bmi ->
            binding.bmiText.text = getString(R.string.your_bmi, bmi)
        }

        viewModel.diseaseTestsArray.observe(viewLifecycleOwner) { diseaseTestsArray ->
            diseaseTestsArray.forEach { diseaseTests -> addDiseaseTestsToLayout(diseaseTests) }
        }

        viewModel.processAnswers(answers)

        return binding.root
    }

    private fun resetActionBarSubtitle() {
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.subtitle = ""
    }

    private fun addDiseaseTestsToLayout(diseaseTests: DiseaseTests) {
        val diseaseNameText = DiseaseNameTextViewBinding.inflate(
            layoutInflater,
            binding.diseaseTestsContainer,
            false
        ).root
        diseaseNameText.text = getString(diseaseTests.diseaseNameId)
        binding.diseaseTestsContainer.addView(diseaseNameText)

        val testNames = resources.getStringArray(diseaseTests.testNamesId)
        val testDescriptions = resources.getStringArray(diseaseTests.testDescriptionsId)

        for (i in testNames.indices) {
            val name = testNames[i]
            val description = testDescriptions[i]

            val nameText = TestNameTextViewBinding.inflate(
                layoutInflater,
                binding.diseaseTestsContainer,
                false
            ).root
            nameText.text = name
            val descriptionText = TestDescriptionTextViewBinding.inflate(
                layoutInflater,
                binding.diseaseTestsContainer,
                false
            ).root
            descriptionText.text = description

            binding.diseaseTestsContainer.addView(nameText)
            binding.diseaseTestsContainer.addView(descriptionText)
        }
    }

}