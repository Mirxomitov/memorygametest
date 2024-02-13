package uz.gita.memorygame.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.memorygame.domain.AppRepository
import uz.gita.memorygame.domain.impl.AppRepositoryImpl


@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun getAppRepository(impl: AppRepositoryImpl): AppRepository

}