package com.aslansoft.caffesist.fonts

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.aslansoft.caffesist.R

val Economica = FontFamily(
    fonts = listOf(
        Font(resId = R.font.economica_bold,
            weight = FontWeight.Bold,
            style = FontStyle.Normal
        ),
        Font(
            resId = R.font.economica_italic,
            weight = FontWeight.Medium,
            style = FontStyle.Italic
        ),
        Font(
            resId = R.font.economica_regular,
            weight = FontWeight.Normal,
            style = FontStyle.Normal
        ),
        Font(resId = R.font.economica_bolditalic,
            weight = FontWeight.Bold,
            style = FontStyle.Italic
       )
    )
)
val IBMPlexSans = FontFamily(
    fonts = listOf(
        Font(resId = R.font.ibmplexsans_wdth_wght,
            weight = FontWeight.Normal,
            style = FontStyle.Normal),
        Font(resId = R.font.ibmplexsans_italic_wdth_wght,
            weight = FontWeight.Normal,
            style = FontStyle.Italic
       )
    )
)