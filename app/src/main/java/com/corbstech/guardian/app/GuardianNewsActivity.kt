package com.corbstech.guardian.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.corbstech.guardian.R
import com.corbstech.guardian.databinding.GuardianNewsActivityBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GuardianNewsActivity : AppCompatActivity() {

    private lateinit var binding: GuardianNewsActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = GuardianNewsActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupToolbar()
    }

    private fun setupToolbar() {
        with(binding.toolbar) {
            val navHostFragment = supportFragmentManager
                .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            setupWithNavController(
                navHostFragment.navController,
                AppBarConfiguration.Builder(setOf(R.id.articleListFragment)).build()
            )
            navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
                isVisible = destination.id != R.id.articleDetailFragment
            }
        }
    }
}
