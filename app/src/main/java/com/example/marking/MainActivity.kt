package com.example.marking

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marking.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val processingFileStream = MutableStateFlow(false)
    private val currentDataModels = MutableStateFlow<List<DataModel>>(emptyList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bindContent()
    }

    private fun ActivityMainBinding.bindContent() {
        bindLoading()
        bindMarks()
        bindTableView()
        bindBarchart()

        binding.btnImport.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
            intent.action = Intent.ACTION_GET_CONTENT
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            importExcelFileLauncher.launch(intent)
        }

        binding.btnExit.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
    }

    private fun ActivityMainBinding.bindLoading() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                processingFileStream.collectLatest { viewLoading.isVisible = it }
            }
        }
    }

    private fun ActivityMainBinding.bindMarks() {
        val marks = listOf(
            Mark.One(), Mark.Two(), Mark.Three(), Mark.Four(), Mark.Five()
        )
        val manager = GridLayoutManager(
            this@MainActivity,
            marks.size,
            GridLayoutManager.VERTICAL,
            false
        )
        val adapter = MarksAdapter(
            width = RecyclerView.LayoutParams.MATCH_PARENT,
            height = dpToPx(50),
            writeRange = true,
            onItemClicked = null
        ).also { it.submitList(marks) }
        rvMarks.layoutManager = manager
        rvMarks.adapter = adapter
    }

    private fun ActivityMainBinding.bindTableView() {
        val adapter = TableViewAdapter(binding.subScrollContainer)
        rvTable.adapter = adapter

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                currentDataModels.filterNotNull()
                    .collectLatest { adapter.submitList(it) }
            }
        }
    }

    private fun ActivityMainBinding.bindBarchart() {
        val adapter = BarchartAdapter(binding.subScrollContainer)
        rvBarchart.adapter = adapter

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                currentDataModels.filterNotNull()
                    .collectLatest { adapter.submitList(it) }
            }
        }
    }

    private val importExcelFileLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        val fileUri = it?.data?.data
        fileUri?.let { processFile(fileUri) }
    }

    private fun processFile(uri: Uri) {
        try {
            currentFocus?.clearFocus()
        } catch (e: Exception) {
            // ignore
        }

        lifecycleScope.launch(Dispatchers.IO) {
            processingFileStream.emit(true)
            try {
                contentResolver.openInputStream(uri).use {
                    val dataModels = ExcelUtils.extractDataModelsFrom(it!!)
                    currentDataModels.emit(dataModels)
                }
            } catch (t: Throwable) {
                t.printStackTrace()
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, R.string.error_occurred, Toast.LENGTH_SHORT)
                        .show()
                }
            } finally {
                processingFileStream.emit(false)
            }
        }
    }
}