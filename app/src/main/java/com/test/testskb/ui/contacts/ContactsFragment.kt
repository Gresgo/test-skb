package com.test.testskb.ui.contacts

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.testskb.R
import com.test.testskb.adapter.ContactsAdapter
import com.test.testskb.databinding.FragmentContactsBinding
import com.test.testskb.ui.ContactsCallback
import com.test.testskb.ui.MainActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar

class ContactsFragment : Fragment() {

    private var mContactsCallback: ContactsCallback? = null
    private lateinit var binding: FragmentContactsBinding
    private lateinit var viewModel: ContactsViewModel
    private lateinit var adapter: ContactsAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)

        mContactsCallback = context as? MainActivity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // too lazy for custom factory, there is no need it
        viewModel = ViewModelProvider(activity!!).get(ContactsViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_contacts, container, false)
        binding.viewModel = viewModel
        binding.executePendingBindings()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()

        binding.swipeLayout.setOnRefreshListener {
            viewModel.loadData()
            binding.swipeLayout.isRefreshing = false
        }

        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })
    }

    private fun setupAdapter() {
        adapter = ContactsAdapter(arrayListOf(), mContactsCallback)
        binding.contactsRecycler.layoutManager = LinearLayoutManager(activity)
        binding.contactsRecycler.adapter = adapter

        viewModel.contactsList.observe(viewLifecycleOwner, Observer {
            it?.let { adapter.update(it) }
        })

        viewModel.snackbar.observe(viewLifecycleOwner, Observer {
            it?.let{ Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show() }
        })
    }

}