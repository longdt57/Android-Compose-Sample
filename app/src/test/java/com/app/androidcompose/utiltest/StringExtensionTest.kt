package com.app.androidcompose.utiltest

import com.app.androidcompose.support.extensions.formattedUrl
import junit.framework.TestCase.assertEquals
import org.junit.Test

class StringExtensionsTest {

    @Test
    fun `should return the original URL when it starts with http or https`() {
        val httpUrl = "http://example.com"
        val httpsUrl = "https://example.com"

        assertEquals("http://example.com", httpUrl.formattedUrl())
        assertEquals("https://example.com", httpsUrl.formattedUrl())
    }

    @Test
    fun `should add http prefix when URL does not start with http or https`() {
        val url = "example.com"
        assertEquals("http://example.com", url.formattedUrl())
    }
}
