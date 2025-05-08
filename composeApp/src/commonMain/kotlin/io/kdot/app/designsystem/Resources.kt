package io.kdot.app.designsystem

import kdotapp.composeapp.generated.resources.Res
import kdotapp.composeapp.generated.resources.a11y_hide_password
import kdotapp.composeapp.generated.resources.a11y_show_password
import kdotapp.composeapp.generated.resources.action_accept
import kdotapp.composeapp.generated.resources.action_clear
import kdotapp.composeapp.generated.resources.action_continue
import kdotapp.composeapp.generated.resources.action_decline
import kdotapp.composeapp.generated.resources.app_logo
import kdotapp.composeapp.generated.resources.ic_compound_mention
import kdotapp.composeapp.generated.resources.avatar
import kdotapp.composeapp.generated.resources.chats
import kdotapp.composeapp.generated.resources.common_no_room_name
import kdotapp.composeapp.generated.resources.common_password
import kdotapp.composeapp.generated.resources.common_username
import kdotapp.composeapp.generated.resources.dialog_title_error
import kdotapp.composeapp.generated.resources.error_unknown
import kdotapp.composeapp.generated.resources.join
import kdotapp.composeapp.generated.resources.offline
import kdotapp.composeapp.generated.resources.online
import kdotapp.composeapp.generated.resources.public
import kdotapp.composeapp.generated.resources.screen_account_provider_signin_title
import kdotapp.composeapp.generated.resources.screen_invites_invited_you
import kdotapp.composeapp.generated.resources.screen_join_room_knock_sent_title
import kdotapp.composeapp.generated.resources.screen_login_error_deactivated_account
import kdotapp.composeapp.generated.resources.screen_login_error_invalid_credentials
import kdotapp.composeapp.generated.resources.screen_login_form_header
import kdotapp.composeapp.generated.resources.screen_login_subtitle
import kdotapp.composeapp.generated.resources.screen_onboarding_sign_in_manually
import kdotapp.composeapp.generated.resources.screen_onboarding_sign_up
import kdotapp.composeapp.generated.resources.screen_onboarding_welcome_message
import kdotapp.composeapp.generated.resources.screen_onboarding_welcome_title
import kdotapp.composeapp.generated.resources.search
import kdotapp.composeapp.generated.resources.title_app_name
import kdotapp.composeapp.generated.resources.unavailable
import kdotapp.composeapp.generated.resources.unencrypted
import kdotapp.composeapp.generated.resources.common_offline
import kdotapp.composeapp.generated.resources.screen_roomlist_mark_as_read
import kdotapp.composeapp.generated.resources.screen_roomlist_mark_as_unread
import kdotapp.composeapp.generated.resources.common_favourite
import kdotapp.composeapp.generated.resources.common_settings
import kdotapp.composeapp.generated.resources.action_leave_conversation
import kdotapp.composeapp.generated.resources.action_leave_room
import kdotapp.composeapp.generated.resources.action_ok
import kdotapp.composeapp.generated.resources.ic_compound_leave
import kdotapp.composeapp.generated.resources.action_cancel
import kdotapp.composeapp.generated.resources.leave_conversation_alert_subtitle
import kdotapp.composeapp.generated.resources.leave_room_alert_empty_subtitle
import kdotapp.composeapp.generated.resources.leave_room_alert_private_subtitle
import kdotapp.composeapp.generated.resources.leave_room_alert_subtitle
import kdotapp.composeapp.generated.resources.common_leaving_room
import kdotapp.composeapp.generated.resources.action_leave
import kdotapp.composeapp.generated.resources.screen_roomlist_main_space_title
import kdotapp.composeapp.generated.resources.screen_roomlist_a11y_create_message
import kdotapp.composeapp.generated.resources.screen_roomlist_room_directory_button_title
import kdotapp.composeapp.generated.resources.screen_roomlist_filter_unreads_empty_state_title
import kdotapp.composeapp.generated.resources.screen_roomlist_filter_people_empty_state_title
import kdotapp.composeapp.generated.resources.screen_roomlist_filter_rooms_empty_state_title
import kdotapp.composeapp.generated.resources.screen_roomlist_filter_favourites_empty_state_title
import kdotapp.composeapp.generated.resources.screen_roomlist_filter_favourites_empty_state_subtitle
import kdotapp.composeapp.generated.resources.screen_roomlist_filter_invites_empty_state_title
import kdotapp.composeapp.generated.resources.screen_roomlist_filter_mixed_empty_state_subtitle
import kdotapp.composeapp.generated.resources.screen_roomlist_filter_mixed_empty_state_title
import kdotapp.composeapp.generated.resources.ic_compound_compose
import kdotapp.composeapp.generated.resources.screen_roomlist_empty_title
import kdotapp.composeapp.generated.resources.screen_roomlist_empty_message
import kdotapp.composeapp.generated.resources.action_start_chat

object Resources {
    object Icon {
        val app_logo = Res.drawable.app_logo
        val ic_compound_mention = Res.drawable.ic_compound_mention
        val ic_compound_leave = Res.drawable.ic_compound_leave
        val ic_compound_compose = Res.drawable.ic_compound_compose
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
        val screen_invites_invited_you = Res.string.screen_invites_invited_you
        val screen_join_room_knock_sent_title = Res.string.screen_join_room_knock_sent_title
        val common_no_room_name = Res.string.common_no_room_name
        val action_accept = Res.string.action_accept
        val action_decline = Res.string.action_decline
        val common_offline = Res.string.common_offline
        val screen_roomlist_mark_as_read = Res.string.screen_roomlist_mark_as_read
        val screen_roomlist_mark_as_unread = Res.string.screen_roomlist_mark_as_unread
        val common_favourite = Res.string.common_favourite
        val action_leave_conversation = Res.string.action_leave_conversation
        val action_leave_room = Res.string.action_leave_room
        val common_settings = Res.string.common_settings
        val action_ok = Res.string.action_ok
        val action_cancel = Res.string.action_cancel
        val leave_conversation_alert_subtitle = Res.string.leave_conversation_alert_subtitle
        val leave_room_alert_empty_subtitle = Res.string.leave_room_alert_empty_subtitle
        val leave_room_alert_private_subtitle = Res.string.leave_room_alert_private_subtitle
        val leave_room_alert_subtitle = Res.string.leave_room_alert_subtitle
        val common_leaving_room = Res.string.common_leaving_room
        val action_leave = Res.string.action_leave
        val screen_roomlist_main_space_title = Res.string.screen_roomlist_main_space_title
        val screen_roomlist_a11y_create_message = Res.string.screen_roomlist_a11y_create_message
        val screen_roomlist_room_directory_button_title =
            Res.string.screen_roomlist_room_directory_button_title
        val screen_roomlist_filter_unreads_empty_state_title =
            Res.string.screen_roomlist_filter_unreads_empty_state_title
        val screen_roomlist_filter_people_empty_state_title =
            Res.string.screen_roomlist_filter_people_empty_state_title
        val screen_roomlist_filter_rooms_empty_state_title =
            Res.string.screen_roomlist_filter_rooms_empty_state_title
        val screen_roomlist_filter_favourites_empty_state_title =
            Res.string.screen_roomlist_filter_favourites_empty_state_title
        val screen_roomlist_filter_favourites_empty_state_subtitle =
            Res.string.screen_roomlist_filter_favourites_empty_state_subtitle
        val screen_roomlist_filter_invites_empty_state_title =
            Res.string.screen_roomlist_filter_invites_empty_state_title
        val screen_roomlist_filter_mixed_empty_state_subtitle =
            Res.string.screen_roomlist_filter_mixed_empty_state_subtitle
        val screen_roomlist_filter_mixed_empty_state_title = Res.string.screen_roomlist_filter_mixed_empty_state_title
        val screen_roomlist_empty_title = Res.string.screen_roomlist_empty_title
        val screen_roomlist_empty_message = Res.string.screen_roomlist_empty_message
        val action_start_chat = Res.string.action_start_chat

    }

}