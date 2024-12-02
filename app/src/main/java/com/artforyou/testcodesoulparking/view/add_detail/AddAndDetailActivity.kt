package com.artforyou.testcodesoulparking.view.add_detail

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doAfterTextChanged
import com.artforyou.testcodesoulparking.R
import com.artforyou.testcodesoulparking.data.local.entities.TodoEntities
import com.artforyou.testcodesoulparking.databinding.ActivityAddAndDetailBinding
import com.artforyou.testcodesoulparking.domain.model.Todos
import com.artforyou.testcodesoulparking.utils.DateUtils
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class AddAndDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddAndDetailBinding
    private var typeData = ""

    private val viewModel: AddAndDetailViewModel by viewModels()
    private var note: Todos? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddAndDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpValidation()

        binding.liearDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->

                val originalFormat = "yyyy-MM-dd"
                val targetFormat = "dd MMM yyyy"

                val selectedDateString = String.format(
                    Locale.getDefault(),
                    "%04d-%02d-%02d",
                    selectedYear,
                    selectedMonth + 1,
                    selectedDay
                )

                val formattedDate = DateUtils.formatDate(selectedDateString, originalFormat, targetFormat)

                binding.tvDateTodo.text = formattedDate
            }, year, month, day)

            datePickerDialog.setOnCancelListener {
                binding.tvDateTodo.text = "Bulan Tahun"
            }

            datePickerDialog.show()
        }

        typeData = intent.getStringExtra(KEY_ID_ADD_OR_UPDATE) ?: ""
        if(typeData == "Add"){
            binding.tvBtnSubmit.text = "Simpan Todo"
        }else if (typeData == "Update"){
            binding.tvBtnSubmit.text = "Update Todo"
        }
    }

    private fun setUpValidation(){
        val fieldValidationList = listOf(binding.etTitleTodo, binding.etDescription)

        fieldValidationList.forEach { field ->
            if (field is EditText) {
                field.doAfterTextChanged {
                    validateForm()
                }
            }
        }

        binding.radioGroup.setOnCheckedChangeListener { _, _ ->
            validateForm()
        }

        validateForm()
    }

    private fun validateForm() {
        val titleTodo = !binding.etTitleTodo.text.isNullOrEmpty()
        val descriptionTodo = !binding.etDescription.text.isNullOrEmpty()
        val priorityTodo = binding.radioGroup.checkedRadioButtonId != -1
        val isDateFilled = isDateFilled()

        println("Title valid: $titleTodo")
        println("Description valid: $descriptionTodo")
        println("Priority valid: $priorityTodo")
        println("Date valid: $isDateFilled")

        val isValid = titleTodo && descriptionTodo && priorityTodo && isDateFilled

        println("Form valid: $isValid")
        binding.btnSubmit.apply {
            isClickable = isValid
            isEnabled = isValid

            println("Button clickable: $isClickable")
        }
    }
    private fun isDateFilled(): Boolean {
        return binding.tvDateTodo.text.toString() != "Bulan Tahun"
    }


    companion object{
        const val KEY_ID_ADD_OR_UPDATE = "KEY_ID_ADD_OR_UPDATE_ID"
        const val KEY_EXTRA_PARCEL = "KEY_EXTRA_PARCEL"
    }
}