package com.hennge.sankou.sankouview

data class LicenseItem (
    val groupId: String,
    val artifactId: String,
    val version: String,
    val name: String,
    val spdxLicenses: List<SpdxLicense>,
    val scm: Scm
) {
    data class SpdxLicense (
        val identifier: String,
        val name: String,
        val url: String
    )
    data class Scm (
        val url: String
    )
}
