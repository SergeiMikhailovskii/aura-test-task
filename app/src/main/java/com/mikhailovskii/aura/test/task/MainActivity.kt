package com.mikhailovskii.aura.test.task

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.work.WorkManager
import com.mikhailovskii.aura.test.task.data.worker.ShowNotificationWorker
import com.mikhailovskii.aura.test.task.databinding.ActivityMainBinding
import com.mikhailovskii.aura.test.task.presentation.MainState
import com.mikhailovskii.aura.test.task.presentation.MainViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by inject()
    private val appNotificationManager: AppNotificationManager by inject()

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val notificationsPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (!isGranted) {
                Log.d("MainActivity", "Permission not granted")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        startScreenStateListening()
        requestNotificationsPermission()
        launchNotificationsTask()
    }

    private fun startScreenStateListening() {
        lifecycleScope.launch {
            viewModel.uiState.collectLatest { state ->
                if (state == MainState.Empty) {
                    binding.tvBootInfo.text = getString(R.string.no_boots_detected)
                } else {
                    binding.tvBootInfo.text = "Not empty list"
                }
            }
        }
    }

    private fun requestNotificationsPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            notificationsPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    private fun launchNotificationsTask() {
        val workManager = WorkManager.getInstance(applicationContext)
        workManager.cancelAllWorkByTag(ShowNotificationWorker.TAG)
        workManager.enqueue(ShowNotificationWorker.buildShowNotificationRequest(true))
    }
}
