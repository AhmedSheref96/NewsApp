package com.el3sas.data.di

import com.el3sas.data.reposImpl.NewsListRepoImpl
import com.el3sas.data.repos.NewsListRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Qualifier

@Module
@InstallIn(ViewModelComponent::class)
abstract class reposDi {

    @Binds
    @NewsListRepoForHome
    @ViewModelScoped
    abstract fun bindNewsListRepo(repo: NewsListRepoImpl): NewsListRepo

}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
public annotation class NewsListRepoForHome