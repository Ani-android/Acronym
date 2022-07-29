package com.assignment.acronym.ui.fragment.resultfragment

import android.app.Application
import com.assignment.acronym.ui.fragment.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    application: Application
) : BaseViewModel(application)