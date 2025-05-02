package io.kdot.app.ui.rooms

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import io.kdot.app.designsystem.Resources
import io.kdot.app.designsystem.common.Avatar
import io.kdot.app.domain.AvatarData
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoomsTopAppBar(avatarData: AvatarData?) {
       TopAppBar(
        title = { Text(stringResource(Resources.String.chats)) },
        navigationIcon = {
            if (avatarData != null) {
                Avatar(avatarData.avatar, avatarData.initials)
            }
        },
        actions = {
            IconButton(onClick = { }) {
                Icon(Icons.Default.MoreVert, contentDescription = "More")
            }

        },
    )
}