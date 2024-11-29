package com.artforyou.testcodesoulparking.di

import com.artforyou.testcodesoulparking.data.repo.AppRepositoryImpl
import com.artforyou.testcodesoulparking.domain.repo.AppRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

}