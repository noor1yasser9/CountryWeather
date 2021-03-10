package com.nurbk.ps.countryweather.utils


class Result<T> private constructor(val status: Status, val message: String?, val data: T) {
    enum class Status {
        SUCCESS, ERROR, LOADING, EMPTY
    }

    companion object {
        fun <T> success(data: T): Result<T> {
            return Result(Status.SUCCESS, null, data)
        }

        fun <T> success(data: T, message: String): Result<T> {
            return Result(Status.SUCCESS, message, data)
        }

        fun <T> error(msg: String, data: T): Result<T> {
            return Result(Status.ERROR, msg, data)
        }

        fun <T> loading(data: T): Result<T> {
            return Result(Status.LOADING, null, data)
        }

        fun <T> empty(data: T): Result<T> {
            return Result(Status.EMPTY, null, data)
        }
    }
}