package uz.gita.memorygame.presentation.viewmodels.impl

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.gita.memorygame.domain.AppRepository
import uz.gita.memorygame.presentation.viewmodels.LevelViewModel
import javax.inject.Inject

@HiltViewModel
class LevelViewModelImpl
@Inject constructor(
    private val repository: AppRepository
) : ViewModel(), LevelViewModel