package id.elharies.pokedex.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

abstract class BaseViewModel<State: Any>(initialState: State): ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<State> = _state

    protected fun reduce(reducer: State.() -> State) {
        _state.update { currentState ->
            currentState.reducer()
        }
    }
}