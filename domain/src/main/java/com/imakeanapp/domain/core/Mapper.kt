package com.imakeanapp.domain.core

/**
 * This abstract class should be subclassed by any class whose responsibility
 * is mapping model classes of each layer/module.
 *
 * @param E type to convert
 * @param T converted type
 */
abstract class Mapper<in E, T> {

    /**
     * Convert's a class to different model type
     *
     * @param from the model class to be converted
     * @return T the converted mode class
     */
    abstract fun mapFrom(from: E): T
}