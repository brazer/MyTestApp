package com.salanevich.testapp.ui.activity

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.salanevich.testapp.R
import com.salanevich.testapp.databinding.ActivityMainBinding
import android.view.View
import android.widget.RadioButton

import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.salanevich.testapp.cache.sorting.Sorting
import com.salanevich.testapp.ui.fragment.SearchFragment
import java.lang.IllegalStateException

class MainActivity : AppCompatActivity(), SearchFragment.BottomSheetListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<*>
    private val uploadDateBtn: RadioButton by lazy { findViewById(R.id.radioButtonUploadDate) }
    private val relevanceBtn: RadioButton by lazy { findViewById(R.id.radioButtonRelevance) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBottomSheet()
        initNavigation()
    }

    private fun initBottomSheet() {
        val clBottomSheet = findViewById<View>(R.id.bottom_sheet) as ConstraintLayout
        bottomSheetBehavior = BottomSheetBehavior.from(clBottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        bottomSheetBehavior.isDraggable = false
        bottomSheetBehavior.peekHeight = 180
    }

    private fun initNavigation() {
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)
    }

    override fun showBottomSheet(parameter: Sorting.Parameter) {
        when (parameter) {
            Sorting.Parameter.UPLOAD_DATE -> uploadDateBtn.isChecked = true
            Sorting.Parameter.RELEVANCE -> relevanceBtn.isChecked = true
        }
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    override fun hideBottomSheet(): Sorting.Parameter {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        if (uploadDateBtn.isChecked) {
            return Sorting.Parameter.UPLOAD_DATE
        }
        if (relevanceBtn.isChecked) {
            return Sorting.Parameter.RELEVANCE
        }
        throw IllegalStateException("All radio buttons are unchecked")
    }

}