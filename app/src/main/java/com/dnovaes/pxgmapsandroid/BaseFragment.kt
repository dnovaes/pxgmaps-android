package com.dnovaes.pxgmapsandroid

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment

abstract class BaseFragment: Fragment() {

    abstract fun fragmentName(): String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("FragmentLifecycle", "${fragmentName()} created")
    }

    override fun onResume() {
        super.onResume()
        Log.d("FragmentLifecycle", "${fragmentName()} resumed")
    }

    override fun onPause() {
        Log.d("FragmentLifecycle", "${fragmentName()} paused")
        super.onPause()
    }

    override fun onStop() {
        Log.d("FragmentLifecycle", "${fragmentName()} stopped")
        super.onStop()
    }
}