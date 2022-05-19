package co.thepeer.sdk

import android.content.Intent
import android.util.Log
import co.thepeer.sdk.model.ThePeerEvent
import co.thepeer.sdk.model.ThePeerResult
import co.thepeer.sdk.utils.Logger
import co.thepeer.sdk.utils.ThePeerConstants
import co.thepeer.sdk.utils.WebInterface
import io.mockk.*
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Ignore


class WebInterfaceTest {

    private lateinit var webInterface: WebInterface

    @Before
    fun setup() {
        webInterface = WebInterface(mockk())
        mockkStatic(Log::class)
        mockk<Throwable>(relaxed = true)
        every { Log.d(any(), any()) } returns 0
        every { Log.e(any(), any()) } returns 0


    }


    @Test
    fun `handle send event - success`() {
        val event = webInterface.convertToGsonFromString(MockData.sendSuccess) as ThePeerEvent
        webInterface.sendResponse(MockData.sendSuccess)
        verify {
            webInterface.handleSendEvent(event)
        }

        assertEquals("send.success", event.event)

    }

    @Test
    fun `handle send event - close`() {
        val event = webInterface.convertToGsonFromString(MockData.sendClose) as ThePeerEvent
        webInterface.sendResponse(MockData.sendClose)
        verify {
            webInterface.handleSendEvent(event)
        }

        assertEquals("send.close", event.event)

    }

    @Test
    fun `handle send event - is error`() {
        val event = webInterface.convertToGsonFromString(MockData.sendError) as ThePeerEvent
        webInterface.sendResponse(MockData.sendError)
        verify {
            webInterface.handleSendEvent(event)
        }

        assertEquals("send.user_insufficient_funds", event.event)

    }

    @Test
    fun `handle checkout event - success`() {
        val event = webInterface.convertToGsonFromString(MockData.checkoutSuccess) as ThePeerEvent
        webInterface.sendResponse(MockData.checkoutSuccess)
        verify {
            webInterface.handleCheckoutEvent(event)
        }

        assertEquals("checkout.success", event.event)

    }

    @Test
    fun `handle checkout event - close`() {
        val event = webInterface.convertToGsonFromString(MockData.checkoutClose) as ThePeerEvent
        webInterface.sendResponse(MockData.checkoutClose)
        verify {
            webInterface.handleCheckoutEvent(event)
        }

        assertEquals("checkout.close", event.event)

    }

    @Test
    fun `handle direct debit event - success`() {
        val event = webInterface.convertToGsonFromString(MockData.directDebitSuccess) as ThePeerEvent
        webInterface.sendResponse(MockData.directDebitSuccess)
        verify {
            webInterface.handleDirectDebitEvent(event)
        }

        assertEquals("direct_debit.success", event.event)

    }

    @Test
    fun `handle direct debit event - close`() {
        val event = webInterface.convertToGsonFromString(MockData.directDebitClose) as ThePeerEvent
        webInterface.sendResponse(MockData.directDebitClose)
        verify {
            webInterface.handleDirectDebitEvent(event)
        }

        assertEquals("direct_debit.close", event.event)

    }

}