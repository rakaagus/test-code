package com.artforyou.testcodesoulparking.view.add_detail

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.RadioButton
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doAfterTextChanged
import com.artforyou.testcodesoulparking.R
import com.artforyou.testcodesoulparking.data.local.entities.TodoEntities
import com.artforyou.testcodesoulparking.databinding.ActivityAddAndDetailBinding
import com.artforyou.testcodesoulparking.domain.model.Todos
import com.artforyou.testcodesoulparking.utils.DateUtils
import com.artforyou.testcodesoulparking.utils.PriorityTodo
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class AddAndDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddAndDetailBinding
    private var typeData = ""
    private var isAllValid: Boolean = false

    private val viewModel: AddAndDetailViewModel by viewModels()
    private var todo: TodoEntities? = null

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
        todo = intent.getParcelableExtra(KEY_EXTRA_PARCEL)
        if(typeData == "Add"){
            binding.tvBtnSubmit.text = "Simpan Todo"

            binding.btnSubmit.setOnClickListener {
                if(getPriorityFromRadio() != null){
                    if (isAllValid){
                        val title = binding.etTitleTodo.text.toString()
                        val description = binding.etDescription.text.toString()
                        val date = binding.tvDateTodo.text.toString()
                        val priority = getPriorityFromRadio()

                        val newTodo = TodoEntities(
                            title = title,
                            description = description,
                            date = date,
                            priority = priority ?: PriorityTodo.LOW,
                            isTrash = false
                        )

                        viewModel.addTodo(newTodo)
                        finish()
                    }
                }
            }
        }else if (typeData == "Update" && todo != null){
            binding.tvBtnSubmit.text = "Update Todo"

            binding.etTitleTodo.setText(todo?.title)
            binding.etDescription.setText(todo?.description)
            binding.tvDateTodo.text = todo?.date

            when(todo?.priority){
                PriorityTodo.LOW -> binding.rbLow.isChecked = true
                PriorityTodo.MEDIUM -> binding.rbMedium.isChecked = true
                PriorityTodo.HIGH -> binding.rbHigh.isChecked = true
                else -> {
                    binding.rbLow.isChecked = false
                    binding.rbMedium.isChecked = false
                    binding.rbHigh.isChecked = false
                }
            }

            binding.btnSubmit.setOnClickListener {
                if (typeData == "Update") {
                    if (isFormChangeForUpdate() && isAllValid) {
                        val updatedTodo = todo?.copy(
                            title = binding.etTitleTodo.text.toString(),
                            description = binding.etDescription.text.toString(),
                            date = binding.tvDateTodo.text.toString(),
                            priority = getSelectedPriority() ?: PriorityTodo.LOW
                        )

                        if (updatedTodo != null) {
                            viewModel.updateTodo(updatedTodo)
                            finish()
                        } else {
                            println("Error: Data todo null saat update.")
                        }
                    } else {
                        println("Tidak ada perubahan atau input tidak valid.")
                    }
                }
            }
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
        val priorityData = getPriorityFromRadio()

        println("Title valid: $titleTodo")
        println("Description valid: $descriptionTodo")
        println("Priority valid: $priorityTodo")
        println("Date valid: $isDateFilled")
        println("Priority: $priorityData")


        val isValid = titleTodo && descriptionTodo && priorityTodo && isDateFilled && isFormChangeForUpdate()

        isAllValid = isValid

        println("Form valid: $isValid")
        binding.btnSubmit.apply {
            isClickable = isValid
            isEnabled = isValid

            println("Button clickable: $isClickable")
        }

        binding.tvBtnSubmit.setTextColor(
            ContextCompat.getColor(this, if (isValid) R.color.white else R.color.black_main)
        )
    }

    private fun isDateFilled(): Boolean {
        return binding.tvDateTodo.text.toString() != "Bulan Tahun"
    }

    private fun getPriorityFromRadio(): PriorityTodo? {
        val selectedId = binding.radioGroup.checkedRadioButtonId
        if(selectedId != -1){
            val selectionButton = findViewById<RadioButton>(selectedId)
            val tagValue = selectionButton.tag.toString()
            return try {
                PriorityTodo.valueOf(tagValue)
            }catch (e: IllegalArgumentException){
                null
            }
        }
        return null
    }

    private fun getSelectedPriority() : PriorityTodo? {
        return when(binding.radioGroup.checkedRadioButtonId) {
            R.id.rbLow -> PriorityTodo.LOW
            R.id.rbMedium -> PriorityTodo.MEDIUM
            R.id.rbHigh -> PriorityTodo.HIGH
            else -> null
        }
    }

    private fun isFormChangeForUpdate(): Boolean {
        if (todo == null) return true

        val title = binding.etTitleTodo.text.toString() != (todo?.title ?: "")
        val description = binding.etDescription.text.toString() != (todo?.description ?: "")
        val date = binding.tvDateTodo.text.toString() != (todo?.date ?: "")
        val priority = getSelectedPriority() != (todo?.priority ?: PriorityTodo.LOW)

        return title || description || date || priority
    }

    companion object{
        const val KEY_ID_ADD_OR_UPDATE = "KEY_ID_ADD_OR_UPDATE_ID"
        const val KEY_EXTRA_PARCEL = "KEY_EXTRA_PARCEL"
    }
}