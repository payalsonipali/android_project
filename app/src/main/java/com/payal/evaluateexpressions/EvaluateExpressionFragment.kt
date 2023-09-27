package com.payal.evaluateexpressions

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.work.WorkManager
import com.payal.evaluateexpressions.databinding.FragmentEvaluateExpressionBinding
import com.payal.evaluateexpressions.viewmodel.MathApiViewModel
import com.payal.evaluateexpressions.viewmodel.MathApiViewModelFactory

class EvaluateExpressionFragment : Fragment() {
    private lateinit var binding: FragmentEvaluateExpressionBinding
    private lateinit var mathApiViewModel: MathApiViewModel
    var isCardVisible = false
    private lateinit var workManager: WorkManager

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // Initialize the MathApiViewModel with the MathApiRepository and HistoryViewModel.
        workManager = WorkManager.getInstance(context)
        mathApiViewModel = ViewModelProvider(
            this,
            MathApiViewModelFactory(workManager)
        ).get(MathApiViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_evaluate_expression, container, false)
        binding.viewModel = mathApiViewModel
        binding.lifecycleOwner = viewLifecycleOwner // for LiveData updates
        // Set the visibility based on the flag to show result
        setCardVisibility()

        mathApiViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        mathApiViewModel.errorExpressions.observe(viewLifecycleOwner) { errorExpressions ->
            Log.d("taggg","errors $errorExpressions")
            if (errorExpressions.isNotEmpty()) {
                val errorText = errorExpressions.joinToString("\n")
                mathApiViewModel.showSnackBar(binding.root, "\n$errorText", requireContext())
            }
        }

        // Handle the evaluate Button Click
        binding.evaluateButton.setOnClickListener {
            val expressionsText = binding.expressionsEditText.text.toString()
            if (expressionsText.isEmpty()) {
                mathApiViewModel.showSnackBar(it,"• Please type something to evaluate", requireContext())
            } else {
                isCardVisible = true
                binding.resultCard.visibility= View.VISIBLE
                mathApiViewModel.onEvaluateButtonClick(expressionsText)
            }
        }

        // Handle the History Button Click
        binding.history.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, HistoryFragment())
                .addToBackStack(null)
                .commit()
        }

        return binding.root
    }

    fun setCardVisibility(){
        if (isCardVisible) {
            binding.resultCard.visibility = View.VISIBLE
        } else {
            binding.resultCard.visibility = View.GONE
        }
    }
}