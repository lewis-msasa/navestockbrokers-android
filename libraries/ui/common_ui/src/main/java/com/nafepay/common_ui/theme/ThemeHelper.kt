package com.nafepay.common_ui.theme

import android.content.res.Resources
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import com.nafepay.common_ui.R

object ThemeHelper {

    @Composable
    fun isDarkTheme(): Boolean {
        return true//isSystemInDarkTheme()
    }
    @Composable
    fun getLogoResource() : Int {

        return if(isDarkTheme()){
            R.drawable.nave_logo
        } else{
            R.drawable.nave_logo
        }
    }
    @Composable
    fun getPxFromDP(sizeInDp: Dp) : Float{
        val scale = Resources.getSystem().displayMetrics.density
        val dpAsPixels = (sizeInDp.value * scale + 0.5f).toFloat();
        return dpAsPixels;
    }


}