package com.sample.todo.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.sample.todo.databinding.SettingSeedInputFragmentBinding
import com.sample.todo.util.extension.observeEvent
import com.sample.todo.worker.seeddatabase.SeedDatabase2Worker
import dagger.android.support.DaggerDialogFragment
import javax.inject.Inject

class SeedInputFragment : DaggerDialogFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val seedInputViewModel: SeedInputViewModel by viewModels { viewModelFactory }
    lateinit var binding: SettingSeedInputFragmentBinding
    @Inject
    lateinit var workManager: WorkManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SettingSeedInputFragmentBinding.inflate(inflater, container, false).apply {
            viewModel = seedInputViewModel
            lifecycleOwner = viewLifecycleOwner
        }
        seedInputViewModel.run {
            requestSeedDatabaseEvent.observeEvent(viewLifecycleOwner) { parameter ->
                workManager.enqueueUniqueWork(
                    SeedDatabase2Worker.WORK_MANE,
                    ExistingWorkPolicy.REPLACE,
                    OneTimeWorkRequestBuilder<SeedDatabase2Worker>()
                        .setInputData(parameter.toData())
                        .build()
                )
            }
        }
        return binding.root
    }

    companion object {
        fun showNewInstance(fragmentManager: FragmentManager) {
            // TODO what tag mean?
            SeedInputFragment().show(fragmentManager, "aaa")
        }
    }
}