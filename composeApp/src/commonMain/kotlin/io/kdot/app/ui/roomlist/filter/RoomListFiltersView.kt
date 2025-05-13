package io.kdot.app.ui.roomlist.filter

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp


@Composable
fun RoomListFiltersView(
    roomListFilterState: RoomListFilterState,
    eventSink: (RoomListFilterEvents) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        contentPadding = PaddingValues(start = 8.dp, end = 16.dp),
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        item("clear_filters") {
            if (roomListFilterState.any { it.isSelected }) {
                RoomListClearFiltersButton(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .testTag("clear_filter"),
                    onClick = { eventSink(RoomListFilterEvents.Clear) }
                )
            }
        }
        roomListFilterState.forEach {

            item(it.roomListFilter) {
                RoomListFilterView(
                    modifier = Modifier
                        .animateItem(),
                    roomListFilter = it.roomListFilter,
                    selected = it.isSelected,
                    onClick = { eventSink(RoomListFilterEvents.Toggle(it)) }
                )
            }
        }
    }
}

@Composable
private fun RoomListClearFiltersButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primary).padding(4.dp)
            .clickable(onClick = onClick)
    ) {
        Icon(
            modifier = Modifier.align(Alignment.Center),
            imageVector = Icons.Default.Clear,
            tint = MaterialTheme.colorScheme.onPrimary,
            contentDescription = "clear",
        )
    }
}


@Composable
private fun RoomListFilterView(
    roomListFilter: RoomListFilter,
    selected: Boolean,
    onClick: (RoomListFilter) -> Unit,
    modifier: Modifier = Modifier
) {
    val background = animateColorAsState(
        targetValue = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary,
        animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
        label = "chip background colour",
    )
    val textColour = animateColorAsState(
        targetValue = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary,
        animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
        label = "chip text colour",
    )

    FilterChip(
        selected = selected,
        onClick = { onClick(roomListFilter) },
        modifier = modifier.height(36.dp),
        shape = CircleShape,
        colors = FilterChipDefaults.filterChipColors(
            containerColor = background.value,
            selectedContainerColor = background.value,
            labelColor = textColour.value,
            selectedLabelColor = textColour.value
        ),
        label = {
            Text(text = roomListFilter.readable)
        }
    )
}
