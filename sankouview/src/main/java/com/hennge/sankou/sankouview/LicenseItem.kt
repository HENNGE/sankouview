/*
 * Copyright (C) 2025 HENNGE K.K.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hennge.sankou.sankouview

import com.google.gson.annotations.SerializedName

data class LicenseItem (
    @SerializedName("groupId")
    val groupId: String?,
    @SerializedName("artifactId")
    val artifactId: String?,
    @SerializedName("version")
    val version: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("spdxLicenses")
    val spdxLicenses: List<SpdxLicense>,
    @SerializedName("scm")
    val scm: Scm?
) {
    data class SpdxLicense (
        @SerializedName("identifier")
        val identifier: String?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("url")
        val url: String?
    )
    data class Scm (
        @SerializedName("url")
        val url: String?
    )
}
