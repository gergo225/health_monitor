package com.fazekasgergo.healthmonitor.pages.results

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.fazekasgergo.healthmonitor.databinding.FragmentResultsBinding

class ResultsFragment : Fragment() {

    private lateinit var viewModel: ResultsViewModel

    private var _binding: FragmentResultsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultsBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this)[ResultsViewModel::class.java]

        resetActionBarSubtitle()

        return binding.root
    }

    private fun resetActionBarSubtitle() {
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.subtitle = ""
    }

}