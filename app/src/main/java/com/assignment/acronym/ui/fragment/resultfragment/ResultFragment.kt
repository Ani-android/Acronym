package com.assignment.acronym.ui.fragment.resultfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.assignment.acronym.adapter.AcronymMeaningsRecyclerViewAdapter
import com.assignment.acronym.databinding.FragmentResultBinding
import com.assignment.acronym.ui.fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultFragment : BaseFragment() {

    private var binding: FragmentResultBinding? = null

    private val acronymMeaningsRecyclerViewAdapter = AcronymMeaningsRecyclerViewAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResultBinding.inflate(inflater)
        with((activity as AppCompatActivity).supportActionBar) {
            this?.title = arguments?.getString(PARAM_TITLE).orEmpty()
            this?.setDisplayHomeAsUpEnabled(true)
        }
        setHasOptionsMenu(true)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) {
            findNavController().popBackStack()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initViews() {
        binding?.rvMeanings?.adapter = acronymMeaningsRecyclerViewAdapter

        (requireArguments().getSerializable(PARAM_RESPONSE) as? List<*>)?.filterIsInstance<String>()
            ?.let {
                acronymMeaningsRecyclerViewAdapter.setData(it)
            }
    }

    companion object {
        const val PARAM_RESPONSE = "response"
        const val PARAM_TITLE = "title"
    }
}