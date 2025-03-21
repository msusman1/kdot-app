package io.kdot.app.libraries.designsystem

import kdotapp.libraries.designsystem.generated.resources.Res
import kdotapp.libraries.designsystem.generated.resources.a11y_hide_password
import kdotapp.libraries.designsystem.generated.resources.a11y_show_password
import kdotapp.libraries.designsystem.generated.resources.action_clear
import kdotapp.libraries.designsystem.generated.resources.action_continue
import kdotapp.libraries.designsystem.generated.resources.app_logo
import kdotapp.libraries.designsystem.generated.resources.common_password
import kdotapp.libraries.designsystem.generated.resources.common_username
import kdotapp.libraries.designsystem.generated.resources.dialog_title_error
import kdotapp.libraries.designsystem.generated.resources.error_unknown
import kdotapp.libraries.designsystem.generated.resources.screen_account_provider_signin_title
import kdotapp.libraries.designsystem.generated.resources.screen_login_form_header
import kdotapp.libraries.designsystem.generated.resources.screen_login_error_invalid_credentials
import kdotapp.libraries.designsystem.generated.resources.screen_login_error_deactivated_account
import kdotapp.libraries.designsystem.generated.resources.screen_login_subtitle
import kdotapp.libraries.designsystem.generated.resources.screen_onboarding_sign_in_manually
import kdotapp.libraries.designsystem.generated.resources.screen_onboarding_sign_up
import kdotapp.libraries.designsystem.generated.resources.screen_onboarding_welcome_message
import kdotapp.libraries.designsystem.generated.resources.screen_onboarding_welcome_title
import kdotapp.libraries.designsystem.generated.resources.title_app_name

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
    }

}