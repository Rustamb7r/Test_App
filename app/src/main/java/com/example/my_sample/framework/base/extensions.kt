package com.example.my_sample.framework.base

import android.content.res.Resources
import android.os.Bundle
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.navigation.NavArgs
import androidx.navigation.NavArgsLazy
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

@Suppress("UNCHECKED_CAST")
fun <T> Any.getGenericType(): Class<T> {
    fun Type.getParameterizedType(): ParameterizedType? {
        if (this is ParameterizedType) return this
        if (this is Class<*>) return genericSuperclass?.getParameterizedType()
        return null
    }

    javaClass.getParameterizedType()?.actualTypeArguments?.forEach {
        return it as? Class<T> ?: return@forEach
    }
    throw IllegalStateException("No generic found for $this")
}