package com.example.my_sample.framework.presentation.main.multifeature

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.example.my_sample.framework.di.main.MainFragmentScope
import javax.inject.Inject
import javax.inject.Provider

@MainFragmentScope
class MainFragmentFactory
@Inject
constructor(
    private val creators: Map<Class<out Fragment>, @JvmSuppressWildcards Provider<Fragment>>
) : FragmentFactory() {

    private val TAG: String = "MainFragmentFactory"

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        val fragmentClass = loadFragmentClass(classLoader, className)
        val creator = creators[fragmentClass]
            ?: return createFragmentAsFallback(classLoader, className)

        try {
            return creator.get()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    private fun createFragmentAsFallback(classLoader: ClassLoader, className: String): Fragment {
        Log.d(TAG, "No creator found for class: $className. Using default constructor")
        return super.instantiate(classLoader, className)
    }

    companion object {

        const val FRAGMENT_FACTORY_NAME = "MainFragmentFactory"
    }
}