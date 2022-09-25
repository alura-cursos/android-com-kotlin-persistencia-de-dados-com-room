package br.com.alura.orgs

import android.app.Application
import com.google.android.material.color.DynamicColors

class OrgsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)
    }
}