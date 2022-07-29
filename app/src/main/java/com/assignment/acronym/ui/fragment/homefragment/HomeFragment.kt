package com.assignment.acronym.ui.fragment.homefragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.assignment.acronym.R
import com.assignment.acronym.databinding.FragmentHomeBinding
import com.assignment.acronym.ui.fragment.BaseFragment
import com.assignment.acronym.ui.fragment.resultfragment.ResultFragment.Companion.PARAM_RESPONSE
import com.assignment.acronym.ui.fragment.resultfragment.ResultFragment.Companion.PARAM_TITLE
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private val viewModel by viewModels<HomeViewModel>()
    private var binding: FragmentHomeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        initObservers()
    }

    override fun onResume() {
        super.onResume()
        with((activity as AppCompatActivity).supportActionBar) {
            this?.title = getString(R.string.app_name)
            this?.setDisplayHomeAsUpEnabled(false)
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    private fun initListeners() {
        binding?.btnSearch?.setOnClickListener {
            binding?.etSearch?.text?.toString()?.let {
                if (binding?.radioShort?.isChecked == true) {
                    viewModel.fetchByShortForm(it)
                } else if (binding?.radioLong?.isChecked == true) {
                    viewModel.fetchByLongForm(it)
                } else {
                    // no-op
                }
            }
        }
        binding?.etSearch?.addTextChangedListener {
            updateButtonState()
        }
    }

    private fun initObservers() {
        with(viewModel) {
            response.observe(viewLifecycleOwner) {
                if (it?.isNotEmpty() == true) {
                    findNavController().navigate(
                        R.id.action_navigation_landing_to_navigation_meaning, bundleOf(
                            PARAM_RESPONSE to it,
                            PARAM_TITLE to binding?.etSearch?.text?.toString().orEmpty()
                        )
                    )
                } else {
                    showErrorAlertDialog(getString(R.string.not_found))
                }
            }

            loading.observe(viewLifecycleOwner) { isLoading ->
                with(binding) {
                    this?.progress?.isVisible = isLoading
                    this?.etSearch?.isVisible = !isLoading
                    this?.radioLong?.isVisible = !isLoading
                    this?.radioShort?.isVisible = !isLoading
                }
                updateButtonState()
            }
        }
    }

    private fun showErrorAlertDialog(message: String) {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.error))
            .setMessage(message)
            .setPositiveButton(
                getString(R.string.ok)
            ) { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    private fun updateButtonState() {
        binding?.btnSearch?.isEnabled =
            viewModel.loading.value == false &&
                    binding?.etSearch?.text?.toString().isNullOrBlank().not()
    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}