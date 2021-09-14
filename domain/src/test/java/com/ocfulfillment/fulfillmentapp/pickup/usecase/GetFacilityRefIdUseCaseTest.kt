package com.ocfulfillment.fulfillmentapp.pickup.usecase

import com.ocfulfillment.fulfillmentapp.data.pickup.FacilityRef
import com.ocfulfillment.fulfillmentapp.data.pickup.repository.UserConfigRepository
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(MockKExtension::class)
internal class GetFacilityRefIdUseCaseTest {

    private val dispatcher = TestCoroutineDispatcher()

    @MockK
    private val repository : UserConfigRepository = mockk()

    private lateinit var useCase: GetFacilityRefIdUseCase

    private val facilityRef = FacilityRef("facilityRef")

    @Before
    fun beforeTest() {

        coEvery { repository.getFacilityReferenceId() } returns flowOf(facilityRef)
        useCase = GetFacilityRefIdUseCase(repository, dispatcher)

    }

    @After
    fun afterTest() {

        clearAllMocks()
        dispatcher.cancel()
        dispatcher.cancelChildren()
    }

    @Test
    fun useCaseInvoke() {

        runBlockingTest {
            val id = useCase.invoke().first()
            assertEquals(facilityRef, id)
        }
    }
}