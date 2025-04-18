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
package com.hennge.oss.sankou.sankouview

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.InputStream

/**
 * Simple util object for handling JSON parsing
 */
object JsonUtils {
    /**
     * Load a json string from the parameter file
     */
    @JvmStatic
    fun readTestJsonFile(fileName: String, defaultValue: String): String {
        val inputStream = this.javaClass.classLoader?.getResourceAsStream(fileName)
        return inputStream?.bufferedReader()?.use(BufferedReader::readText) ?: defaultValue
    }

    /**
     * Load a json string from the parameter file
     */
    @JvmStatic
    fun readAssetJsonFile(context: Context, fileName: String, defaultValue: String): String {

        var inStreamAsset: InputStream? = null
        var outputData: String? = null

        try {
            inStreamAsset = context.assets.open(fileName)
            outputData = inStreamAsset.bufferedReader().use(BufferedReader::readText)

        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("JsonUtils", e.message, e)
        } finally {
            inStreamAsset?.close()
        }

        return outputData ?: defaultValue
    }

    @JvmStatic
    inline fun <reified T> loadDataListFromFile(fileName: String): T {
        val jsonData = readTestJsonFile(fileName, "{}")
        return Gson().fromJson<T> (
            jsonData,
            object : TypeToken<T>() {}.type
        )
    }

    @JvmStatic
    inline fun <reified T> loadDataListFromAssetsFile(context: Context, assetFileName: String): T {
        val jsonData = readAssetJsonFile(context, assetFileName, "[]")
        return Gson().fromJson<T> (
            jsonData,
            object : TypeToken<T>() {}.type
        )
    }
}