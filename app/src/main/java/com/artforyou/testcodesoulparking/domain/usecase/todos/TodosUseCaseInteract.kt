package com.artforyou.testcodesoulparking.domain.usecase.todos

import com.artforyou.testcodesoulparking.domain.repo.AppRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TodosUseCaseInteract @Inject constructor(
    private val appRepository: AppRepository
): TodosUseCase {
}