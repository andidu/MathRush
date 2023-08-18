package com.adorastudios.mathrush

import android.app.Application
import android.content.Context
import com.adorastudios.mathrush.data.DataRepositoryImpl
import com.adorastudios.mathrush.data.RecordRepositoryImpl
import com.adorastudios.mathrush.domain.DataRepository
import com.adorastudios.mathrush.domain.RecordRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideRecordRepository(app: Application): RecordRepository {
        return RecordRepositoryImpl(app.getSharedPreferences("records", Context.MODE_PRIVATE))
    }

    @Provides
    @Singleton
    fun provideDataRepository(app: Application): DataRepository {
        return DataRepositoryImpl(app.getSharedPreferences("data", Context.MODE_PRIVATE))
    }
}
