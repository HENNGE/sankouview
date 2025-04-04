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

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

private val PurplePrimaryLight = Color(0xFF7135D2)
private val PurplePrimaryDark = Color(0xFFAA79F9)
private val OffWhiteBackground = Color(0xFFF5F5F5)
private val GreyOnBackground = Color(0xFF666666)
private val OnSurfaceVariant = Color(0xDE000000)
private val DarkBackground = Color(0xFF000000)
private val DarkOnBackground = Color(0x99FFFFFF)
private val DarkSecondary = Color(0xFF3D3A44)
private val DarkSurface = Color(0x0DFFFFFF)
private val DarkOnSurface = Color(0x99FFFFFF)
private val DarkOnSurfaceVariant = Color(0xDEFFFFFF)

val DarkColors = darkColorScheme(
    primary = PurplePrimaryDark,
    onPrimary = Color.White,
    secondary = DarkSecondary,
    onSecondary = Color.White,
    surface = DarkSurface,
    onSurface = DarkOnSurface,
    onSurfaceVariant = DarkOnSurfaceVariant,
    background = DarkBackground,
    onBackground = DarkOnBackground,
    error = Color.Red,
    onError = Color.White
)
val LightColors = lightColorScheme(
    primary = PurplePrimaryLight,
    onPrimary = Color.White,
    secondary = Color.White,
    onSecondary = Color.Black,
    surface = Color.White,
    onSurface = Color.Black,
    onSurfaceVariant = OnSurfaceVariant,
    background = OffWhiteBackground,
    onBackground = GreyOnBackground,
    error = Color.Red,
    onError = Color.White
)
