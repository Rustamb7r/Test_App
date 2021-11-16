package com.example.my_sample.framework.presentation.main.multifeature

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.annotation.NavigationRes
import androidx.navigation.fragment.NavHostFragment
import com.example.my_sample.framework.app.BaseApplication
import com.example.my_sample.framework.di.main.MainFragmentScope
import javax.inject.Inject

@MainFragmentScope
class MainNavHostFragment : NavHostFragment() {

    private val TAG: String = "MainNavHostFragment"

    var mainNavController: MainNavController? = null
        get() {
            if (field == null) {
                Log.w(TAG, "NavController is NULL")
            }
            return field
        }

    @Inject
    lateinit var mainFragmentFactory: MainFragmentFactory

    override fun onAttach(context: Context) {
        ((activity?.application) as BaseApplication)
            .getAppComponent()
            .mainComponent()
            .create()
            .inject(this)

        childFragmentManager.fragmentFactory = mainFragmentFactory
        try {
            mainNavController = context as MainNavController
        } catch (e: ClassCastException) {
            Log.e(TAG, "$context must implement MainNavController")
        }
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainNavController?.setNavController(this.navController)
    }

    companion object {

        const val KEY_GRAPH_ID = "android-support-nav:fragment:graphId"

        @JvmStatic
        fun create(
            @NavigationRes graphId: Int = 0
        ): MainNavHostFragment {
            var bundle: Bundle? = null
            if (graphId != 0) {
                bundle = Bundle()
                bundle.putInt(KEY_GRAPH_ID, graphId)
            }
            val result = MainNavHostFragment()
            if (bundle != null) {
                result.arguments = bundle
            }
            return result
        }
    }
}