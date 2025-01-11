package leegroup.module.data

import org.junit.rules.TestWatcher
import org.junit.runner.Description

class CoroutineTestRule : TestWatcher() {

    override fun starting(description: Description) {
        super.starting(description)
    }

    override fun finished(description: Description) {
        super.finished(description)
    }
}
