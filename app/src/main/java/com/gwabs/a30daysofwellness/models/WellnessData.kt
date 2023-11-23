package com.gwabs.a30daysofwellness.models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class WellnessData(
    val day: Int,
    @StringRes val title: Int,
    @DrawableRes val imageRes: Int,
    @StringRes val body: Int

)


