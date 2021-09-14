package com.ocfulfillment.fulfillmentapp.api.pickup.repository

import com.ocfulfillment.fulfillmentapp.api.pickup.remote.UserConfigRemote
import com.ocfulfillment.fulfillmentapp.data.pickup.FacilityRef
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(MockKExtension::class)
internal class UserConfigRepositoryTest {

    @MockK
    val remote = mockk<UserConfigRemote>()

    private lateinit var repository: UserConfigRepositoryImpl

    @Before
    fun beforeTests() {
        repository = UserConfigRepositoryImpl(remote)

        coEvery { remote.getFacilityRefForUser() } returns flowOf(FacilityRef("value"))
    }


    @Test
    fun getFacilityReferenceId() {

        runBlockingTest {
            val id = repository.getFacilityReferenceId().first()
            assertEquals(FacilityRef("value"), id)
        }
    }
}
