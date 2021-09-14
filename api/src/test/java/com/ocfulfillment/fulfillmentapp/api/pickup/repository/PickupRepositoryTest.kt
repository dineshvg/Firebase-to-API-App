package com.ocfulfillment.fulfillmentapp.api.pickup.repository

import com.ocfulfillment.fulfillmentapp.api.pickup.mapper.PickupLineItemMapper
import com.ocfulfillment.fulfillmentapp.api.pickup.model.RemotePickupLineItem
import com.ocfulfillment.fulfillmentapp.api.pickup.remote.PickupJobsRemote
import com.ocfulfillment.fulfillmentapp.api.pickup.remote.PickupRemote
import com.ocfulfillment.fulfillmentapp.data.pickup.*
import com.ocfulfillment.fulfillmentapp.data.pickup.repository.PickupRepository
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith
import retrofit2.Response

@ExperimentalCoroutinesApi
@ExtendWith(MockKExtension::class)
internal class PickupRepositoryTest {

    @MockK
    private val pickupRemote: PickupRemote = mockk()

    @MockK
    private val pickupJobsRemote: PickupJobsRemote = mockk()

    private lateinit var repository: PickupRepository

    private val mapper = PickupLineItemMapper()

    private val facilityRef = FacilityRef("facilityRef")

    private val jobId = PickupJobId("job-id")

    private val version = Version(99)

    private val status = StatusSelection(Status.OPEN)

    @Before
    fun beforeTest() {

        repository = PickupRepositoryImpl(
            pickupRemote,
            pickupJobsRemote,
            mapper
        )

        coEvery { pickupRemote.getPickupItems(facilityRef) } returns flowOf(listOf(REMOTE_MOCK))

        coEvery { pickupRemote.togglePickupItem(DOMAIN_MOCK) } returns flowOf(true)

        coEvery {
            pickupJobsRemote.patchPickupJobOnToggle(
                jobId,
                version,
                status
            )
        } returns SUCCESS_RESPONSE
    }

    @Test
    fun fetchPickupV1Items() {

        runBlockingTest {
            val items = repository.fetchPickupV1Items(facilityRef).first()
            assertEquals(listOf(DOMAIN_MOCK), items)
        }
    }

    @Test
    fun togglePickupLineItemStatus() {

        runBlockingTest {
            val state = repository.togglePickupLineItemStatus(DOMAIN_MOCK).first()
            assertEquals(true, state)
        }
    }

    @Test
    fun patchPickupJobForToggle() {

        runBlockingTest {
            val response = repository.patchPickupJobForToggle(jobId, version, status)
            assertEquals(SUCCESS_RESPONSE, response)
        }
    }


    companion object {

        private val REMOTE_MOCK = RemotePickupLineItem(
            facilityRef = "facilityRef",
            id = "id",
            imageUrl = "imageUrl",
            pickJobRef = "pickJobRef",
            pickJobVersion = 99,
            pickedArticleCount = 100,
            status = "OPEN",
            title = "title"
        )

        private val DOMAIN_MOCK = PickupLineItem(
            id = "id",
            pickJobRef = "pickJobRef",
            tenantArticleId = "",
            status = Status.OPEN,
            facilityRef = "facilityRef",
            title = "title",
            imageUrl = "imageUrl",
            version = 99
        )
    }

    private val SUCCESS_RESPONSE = Response.success(Unit)


}