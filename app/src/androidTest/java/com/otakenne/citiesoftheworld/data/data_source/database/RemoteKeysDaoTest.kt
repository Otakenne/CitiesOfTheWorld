package com.otakenne.citiesoftheworld.data.data_source.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth
import com.otakenne.citiesoftheworld.domain.model.RemoteKeys
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
@SmallTest
class RemoteKeysDaoTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var database: Database
    private lateinit var dao: RemoteKeysDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            Database::class.java
        ).allowMainThreadQueries().build()
//        hiltRule.inject()
        dao = database.remoteKeysDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertRemoteKeysTest() = runBlocking {
        val numberOfEntries = 5
        val remoteKeys = mutableListOf<RemoteKeys>()

        for (i in 1..numberOfEntries) {
            val remoteKey = RemoteKeys(i.toLong(), i, i + 1)
            remoteKeys.add(remoteKey)
        }

        dao.insertRemoteKeys(remoteKeys)
        val remoteKeyFromDB = dao.getRemoteKeysFrom(numberOfEntries.toLong())
        Truth.assertThat(remoteKeyFromDB).isNotNull()
    }

    @Test
    fun clearRemoteKeysTest() = runBlocking {
        val numberOfEntries = 5
        val remoteKeys = mutableListOf<RemoteKeys>()

        for (i in 1..numberOfEntries) {
            val remoteKey = RemoteKeys(i.toLong(), i, i + 1)
            remoteKeys.add(remoteKey)
        }

        dao.insertRemoteKeys(remoteKeys)
        dao.clearRemoteKeys()
        val remoteKeyFromDB = dao.getRemoteKeysFrom(numberOfEntries.toLong())
        Truth.assertThat(remoteKeyFromDB).isNull()
    }
}