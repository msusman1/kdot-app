package io.kdot.app.data

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.coroutines.SuspendSettings


@OptIn(ExperimentalSettingsApi::class)
expect fun createSetting(): SuspendSettings