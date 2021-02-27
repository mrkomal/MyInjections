package com.example.myinjections.modules

import android.app.Application
import androidx.room.Room
import com.example.myinjections.network.model.UsefulLink
import com.example.myinjections.network.service.UsefulLinksService
import com.example.myinjections.repository.injections.InjectionsRepository
import com.example.myinjections.repository.injections.InjectionsRepositoryImpl
import com.example.myinjections.repository.usefullinks.UsefulLinksRepository
import com.example.myinjections.repository.usefullinks.UsefulLinksRepositoryImpl
import com.example.myinjections.room.database.InjectionsDatabase
import com.example.myinjections.room.model.InjectionsDao
import com.example.myinjections.viewmodel.InjectionsViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val databaseModule = module {
    fun provideDatabase(application: Application): InjectionsDatabase {
        return Room.databaseBuilder(application, InjectionsDatabase::class.java, "injections_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideInjectionsDao(database: InjectionsDatabase): InjectionsDao {
        return  database.injectionsDao()
    }

    single { provideDatabase(androidApplication()) }
    single { provideInjectionsDao(get()) }
}


val repositoryModule = module {
    fun provideInjectionsRepository(dao : InjectionsDao): InjectionsRepository {
        return InjectionsRepositoryImpl(dao)
    }

    fun provideUsefulLinksRepository(service: UsefulLinksService): UsefulLinksRepository {
        return UsefulLinksRepositoryImpl(service)
    }

    single { provideInjectionsRepository(dao = get()) }
    single { provideUsefulLinksRepository(service = get())}
}


val viewModelModule = module {
    // Specific viewModel pattern to tell Koin how to build InjectionsViewModel
    viewModel { InjectionsViewModel(repository = get()) }
}


val networkModule = module {
    fun provideRetrofit() =  Retrofit.Builder()
            .baseUrl("https://my-json-server.typicode.com/mrkomal/JSON_MyInjections_App/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    fun provideUsefulLinkApi(retrofit: Retrofit): UsefulLinksService {
        return retrofit.create(UsefulLinksService::class.java)
    }

    single { provideRetrofit() }
    single { provideUsefulLinkApi(retrofit = get()) }
}