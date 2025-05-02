package io.kdot.app.designsystem

import kdotapp.composeapp.generated.resources.Res
import kdotapp.composeapp.generated.resources.a11y_hide_password
import kdotapp.composeapp.generated.resources.a11y_show_password
import kdotapp.composeapp.generated.resources.action_clear
import kdotapp.composeapp.generated.resources.action_continue
import kdotapp.composeapp.generated.resources.app_logo
import kdotapp.composeapp.generated.resources.common_password
import kdotapp.composeapp.generated.resources.common_username
import kdotapp.composeapp.generated.resources.dialog_title_error
import kdotapp.composeapp.generated.resources.error_unknown
import kdotapp.composeapp.generated.resources.screen_login_error_deactivated_account
import kdotapp.composeapp.generated.resources.screen_login_error_invalid_credentials
import kdotapp.composeapp.generated.resources.screen_login_form_header
import kdotapp.composeapp.generated.resources.screen_onboarding_sign_up
import kdotapp.composeapp.generated.resources.title_app_name
import kdotapp.composeapp.generated.resources.screen_account_provider_signin_title
import kdotapp.composeapp.generated.resources.screen_login_subtitle
import kdotapp.composeapp.generated.resources.screen_onboarding_welcome_title
import kdotapp.composeapp.generated.resources.screen_onboarding_welcome_message
import kdotapp.composeapp.generated.resources.screen_onboarding_sign_in_manually
import kdotapp.composeapp.generated.resources.unencrypted
import kdotapp.composeapp.generated.resources.public
import kdotapp.composeapp.generated.resources.join
import kdotapp.composeapp.generated.resources.avatar
import kdotapp.composeapp.generated.resources.chats
import kdotapp.composeapp.generated.resources.online
import kdotapp.composeapp.generated.resources.offline
import kdotapp.composeapp.generated.resources.search
import kdotapp.composeapp.generated.resources.unavailable

object Resources {
    object Icon {
        val app_logo = Res.drawable.app_logo
    }

    object String {
        val title_app_name = Res.string.title_app_name
        val screen_login_form_header = Res.string.screen_login_form_header
        val action_continue = Res.string.action_continue
        val common_username = Res.string.common_username
        val action_clear = Res.string.action_clear
        val common_password = Res.string.common_password
        val a11y_hide_password = Res.string.a11y_hide_password
        val a11y_show_password = Res.string.a11y_show_password
        val error_unknown = Res.string.error_unknown
        val screen_login_error_invalid_credentials =
            Res.string.screen_login_error_invalid_credentials
        val screen_login_error_deactivated_account =
            Res.string.screen_login_error_deactivated_account
        val dialog_title_error = Res.string.dialog_title_error
        val screen_account_provider_signin_title = Res.string.screen_account_provider_signin_title
        val screen_login_subtitle = Res.string.screen_login_subtitle
        val screen_onboarding_welcome_title = Res.string.screen_onboarding_welcome_title
        val screen_onboarding_welcome_message = Res.string.screen_onboarding_welcome_message
        val screen_onboarding_sign_in_manually = Res.string.screen_onboarding_sign_in_manually
        val screen_onboarding_sign_up = Res.string.screen_onboarding_sign_up
        val unencrypted = Res.string.unencrypted
        val public = Res.string.public
        val join = Res.string.join
        val avatar = Res.string.avatar
        val online = Res.string.online
        val offline = Res.string.offline
        val unavailable = Res.string.unavailable
        val chats = Res.string.chats
        val search = Res.string.search
    }

}