package com.payal.evaluateexpressions

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.payal.evaluateexpressions.adapter.GroupedHistoryAdapter
import com.payal.evaluateexpressions.dao.HistoryItemDao
import com.payal.evaluateexpressions.database.HistoryDatabase
import com.payal.evaluateexpressions.databinding.FragmentHistoryBinding
import com.payal.evaluateexpressions.repository.HistoryItemRepository
import com.payal.evaluateexpressions.viewmodel.HistoryViewModel
import com.payal.evaluateexpressions.viewmodel.HistoryViewModelFactory

class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var viewModel: HistoryViewModel
    private lateinit var groupedAdapter: GroupedHistoryAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val historyItemDao: HistoryItemDao = HistoryDatabase.getInstance(context).historyItemDao()
        val historyRepository = HistoryItemRepository(historyItemDao)
        viewModel = ViewModelProvider(this, HistoryViewModelFactory(historyRepository))
            .get(HistoryViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_history, container, false
        )

        groupedAdapter = GroupedHistoryAdapter(emptyList()) // Initialize with an empty map
        binding.recyclerView.adapter = groupedAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.getHistoryItemsGroupedByDate().observe(viewLifecycleOwner) { groupedData ->
            groupedAdapter.groupedData = groupedData
            groupedAdapter.notifyDataSetChanged()
            binding.progressBar.visibility = View.GONE
        }

        binding.progressBar.visibility = View.VISIBLE

        return binding.root
    }
}
