package io.kdot.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp

val LocalAppColors =
    compositionLocalOf<AppColors> { error("Composition Local not defined for App Colors") }

interface AppColors {
    val success: Color

    val presenceOnline: Color
    val presenceOffline: Color
    val presenceUnavailable: Color

    val verificationTrusted: Color
    val verificationUntrusted: Color
    val verificationNeutral: Color


    val bgAccentHovered: Color
    val bgAccentPressed: Color
    val bgAccentRest: Color
    val bgActionPrimaryDisabled: Color
    val bgActionPrimaryHovered: Color
    val bgActionPrimaryPressed: Color
    val bgActionPrimaryRest: Color
    val bgActionSecondaryHovered: Color
    val bgActionSecondaryPressed: Color
    val bgActionSecondaryRest: Color
    val bgCanvasDefault: Color
    val bgCanvasDefaultLevel1: Color
    val bgCanvasDisabled: Color
    val bgCriticalHovered: Color
    val bgCriticalPrimary: Color
    val bgCriticalSubtle: Color
    val bgCriticalSubtleHovered: Color
    val bgDecorative1: Color
    val bgDecorative2: Color
    val bgDecorative3: Color
    val bgDecorative4: Color
    val bgDecorative5: Color
    val bgDecorative6: Color
    val bgInfoSubtle: Color
    val bgSubtlePrimary: Color
    val bgSubtleSecondary: Color
    val bgSubtleSecondaryLevel0: Color
    val bgSuccessSubtle: Color
    val borderCriticalHovered: Color
    val borderCriticalPrimary: Color
    val borderCriticalSubtle: Color
    val borderDisabled: Color
    val borderFocused: Color
    val borderInfoSubtle: Color
    val borderInteractiveHovered: Color
    val borderInteractivePrimary: Color
    val borderInteractiveSecondary: Color
    val borderSuccessSubtle: Color
    val iconAccentPrimary: Color
    val iconAccentTertiary: Color
    val iconCriticalPrimary: Color
    val iconDisabled: Color
    val iconInfoPrimary: Color
    val iconOnSolidPrimary: Color
    val iconPrimary: Color
    val iconPrimaryAlpha: Color
    val iconQuaternary: Color
    val iconQuaternaryAlpha: Color
    val iconSecondary: Color
    val iconSecondaryAlpha: Color
    val iconSuccessPrimary: Color
    val iconTertiary: Color
    val iconTertiaryAlpha: Color
    val textActionAccent: Color
    val textActionPrimary: Color
    val textCriticalPrimary: Color
    val textDecorative1: Color
    val textDecorative2: Color
    val textDecorative3: Color
    val textDecorative4: Color
    val textDecorative5: Color
    val textDecorative6: Color
    val textDisabled: Color
    val textInfoPrimary: Color
    val textLinkExternal: Color
    val textOnSolidPrimary: Color
    val textPrimary: Color
    val textSecondary: Color
    val textSuccessPrimary: Color
    val snackBarLabelColor: Color
    val isLight: Boolean
}

val MaterialTheme.appColors: AppColors
    @Composable
    @ReadOnlyComposable
    get() = LocalAppColors.current


val AppColors.unreadIndicator
    get() = iconAccentTertiary

val AppColors.placeholderBackground
    get() = bgSubtleSecondary

// This color is not present in Semantic color, so put hard-coded value for now
val AppColors.messageFromMeBackground
    get() = if (isLight) {
        // We want LightDesignTokens.colorGray400
        Color(0xFFE1E6EC)
    } else {
        // We want DarkDesignTokens.colorGray500
        Color(0xFF323539)
    }

// This color is not present in Semantic color, so put hard-coded value for now
val AppColors.messageFromOtherBackground
    get() = if (isLight) {
        // We want LightDesignTokens.colorGray300
        Color(0xFFF0F2F5)
    } else {
        // We want DarkDesignTokens.colorGray400
        Color(0xFF26282D)
    }

// This color is not present in Semantic color, so put hard-coded value for now
val AppColors.progressIndicatorTrackColor
    get() = if (isLight) {
        // We want LightDesignTokens.colorAlphaGray500
        Color(0x33052448)
    } else {
        // We want DarkDesignTokens.colorAlphaGray500
        Color(0x25F4F7FA)
    }

// This color is not present in Semantic color, so put hard-coded value for now
val AppColors.iconSuccessPrimaryBackground
    get() = if (isLight) {
        // We want LightDesignTokens.colorGreen300
        Color(0xffe3f7ed)
    } else {
        // We want DarkDesignTokens.colorGreen300
        Color(0xff002513)
    }

// This color is not present in Semantic color, so put hard-coded value for now
val AppColors.bgSubtleTertiary
    get() = if (isLight) {
        // We want LightDesignTokens.colorGray100
        Color(0xfffbfcfd)
    } else {
        // We want DarkDesignTokens.colorGray100
        Color(0xff14171b)
    }

// Temporary color, which is not in the token right now
val AppColors.temporaryColorBgSpecial
    get() = if (isLight) Color(0xFFE4E8F0) else Color(0xFF3A4048)

// This color is not present in Semantic color, so put hard-coded value for now
val AppColors.pinDigitBg
    get() = if (isLight) {
        // We want LightDesignTokens.colorGray300
        Color(0xFFF0F2F5)
    } else {
        // We want DarkDesignTokens.colorGray400
        Color(0xFF26282D)
    }

val AppColors.currentUserMentionPillText
    get() = if (isLight) {
        // We want LightDesignTokens.colorGreen1100
        Color(0xff005c45)
    } else {
        // We want DarkDesignTokens.colorGreen1100
        Color(0xff1fc090)
    }

val AppColors.currentUserMentionPillBackground
    get() = if (isLight) {
        // We want LightDesignTokens.colorGreenAlpha400
        Color(0x3b07b661)
    } else {
        // We want DarkDesignTokens.colorGreenAlpha500
        Color(0xff003d29)
    }

val AppColors.mentionPillText
    get() = textPrimary

val AppColors.mentionPillBackground
    get() = if (isLight) {
        // We want LightDesignTokens.colorGray400
        Color(0x1f052e61)
    } else {
        // We want DarkDesignTokens.colorGray500
        Color(0x26f4f7fa)
    }

val AppColors.bigCheckmarkBorderColor
    get() = if (isLight) LightColorTokens.colorGray400 else DarkColorTokens.colorGray400

val AppColors.highlightedMessageBackgroundColor
    get() = if (isLight) LightColorTokens.colorGreen300 else DarkColorTokens.colorGreen300

// Badge colors

val AppColors.badgePositiveBackgroundColor
    get() = if (isLight) LightColorTokens.colorAlphaGreen300 else DarkColorTokens.colorAlphaGreen300

val AppColors.badgePositiveContentColor
    get() = if (isLight) LightColorTokens.colorGreen1100 else DarkColorTokens.colorGreen1100

val AppColors.badgeNeutralBackgroundColor
    get() = if (isLight) LightColorTokens.colorAlphaGray300 else DarkColorTokens.colorAlphaGray300

val AppColors.badgeNeutralContentColor
    get() = if (isLight) LightColorTokens.colorGray1100 else DarkColorTokens.colorGray1100

val AppColors.badgeNegativeBackgroundColor
    get() = if (isLight) LightColorTokens.colorAlphaRed300 else DarkColorTokens.colorAlphaRed300

val AppColors.badgeNegativeContentColor
    get() = if (isLight) LightColorTokens.colorRed1100 else DarkColorTokens.colorRed1100


val AppColors.pinnedMessageBannerIndicator
    get() = if (isLight) LightColorTokens.colorAlphaGray600 else DarkColorTokens.colorAlphaGray600

val AppColors.pinnedMessageBannerBorder
    get() = if (isLight) LightColorTokens.colorAlphaGray400 else DarkColorTokens.colorAlphaGray400


val MaterialTheme.appTypography
    get() = AppTypography