package com.hennge.sankou.sankouview

import org.junit.Test

/**
 * Basic tests to verify our json test data is functional
 *
 */
class LicenseJSONUnitTest {
    /**
     * Basic test of our data. Make sure it loads and is shaped as we expect.
     */
    @Test
    fun test_load_json_data() {
        val sourceFile = "sample_license_data.json"
        val testUrlList = JsonUtils.loadDataListFromFile<Array<LicenseItem>>(sourceFile)

        assert(testUrlList.isNotEmpty())

        //Test some random values
        assert(testUrlList[0].name == "Activity")
        assert(testUrlList[1].groupId == "androidx.activity")
        assert(testUrlList[1].spdxLicenses[0].name == "Apache License 2.0")
        assert(testUrlList[2].version == "1.10.1")
        assert(testUrlList[2].scm?.url == "https://cs.android.com/androidx/platform/frameworks/support")
    }
}