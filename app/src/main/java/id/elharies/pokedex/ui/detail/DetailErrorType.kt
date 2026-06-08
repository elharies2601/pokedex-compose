package id.elharies.pokedex.ui.detail

sealed class DetailErrorType {
    data object NoConnection : DetailErrorType()
    data object NotFoundInCache : DetailErrorType()
    data object ServerError : DetailErrorType()
    data object Unknown : DetailErrorType()
}
