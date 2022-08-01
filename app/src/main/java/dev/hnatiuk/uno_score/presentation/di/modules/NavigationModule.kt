package dev.hnatiuk.uno_score.presentation.di.modules;

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.hnatiuk.core.presentation.navigation.ApplicationRouter
import dev.hnatiuk.core.presentation.navigation.CiceroneFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NavigationModule {

    @Singleton
    @Provides
    fun provideCicerone(): Cicerone<ApplicationRouter> {
        return CiceroneFactory.create()
    }

    @Provides
    fun provideNavigatorHolder(cicerone: Cicerone<ApplicationRouter>): NavigatorHolder {
        return cicerone.getNavigatorHolder()
    }

    @Provides
    fun provideApplicationRouter(cicerone: Cicerone<ApplicationRouter>): ApplicationRouter {
        return cicerone.router
    }
}