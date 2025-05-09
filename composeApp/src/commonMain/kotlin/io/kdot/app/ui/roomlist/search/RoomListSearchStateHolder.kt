package io.kdot.app.ui.roomlist.search

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class RoomListSearchStateHolder {
    private val _state = MutableStateFlow(RoomListSearchState())
    val state = _state.asStateFlow()

    fun handleEvent(event: RoomListSearchEvents){
        when(event){

        }
    }
}