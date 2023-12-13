package com.example.rebuy.di
import com.example.rebuy.Model.Repository.AuthRepository
import com.example.rebuy.Model.Repository.AuthRepositoryImp
import com.example.rebuy.Model.Repository.ProductRepository
import com.example.rebuy.Model.Repository.ProductRepositoryImp
import com.example.rebuy.Model.Repository.QuoteRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(
        auth: FirebaseAuth,
        database: FirebaseFirestore,
    ): AuthRepository
    {
        return AuthRepositoryImp(auth, database)
    }

    @Provides
    @Singleton
    fun provideProductRepository(
        auth: FirebaseAuth,
        database: FirebaseFirestore,
        storage : StorageReference
    ): ProductRepository
    {
        return ProductRepositoryImp(auth, database, storage)
    }

 }