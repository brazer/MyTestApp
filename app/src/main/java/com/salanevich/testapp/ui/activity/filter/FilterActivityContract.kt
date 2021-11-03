package com.salanevich.testapp.ui.activity.filter

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

class FilterActivityContract: ActivityResultContract<Nothing?, Boolean>() {
    override fun createIntent(context: Context, input: Nothing?): Intent {
        return FilterActivity.createIntent(context)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Boolean {
        return Activity.RESULT_OK == resultCode
    }
}