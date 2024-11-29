package com.artforyou.testcodesoulparking.di

import com.artforyou.testcodesoulparking.domain.usecase.todos.TodosUseCase
import com.artforyou.testcodesoulparking.domain.usecase.todos.TodosUseCaseInteract
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindAppRepository(todoUseCaseInteract: TodosUseCaseInteract): TodosUseCase

}