package com.test.testskb.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.test.testskb.R
import com.test.testskb.databinding.FragmentDetailsBinding
import com.test.testskb.model.ContactModel

class DetailsFragment: Fragment() {

    companion object {
        fun newInstance(contact: ContactModel): DetailsFragment {
            val fragment = DetailsFragment()
            val args = Bundle()
            args.putParcelable("contact", contact)
            fragment.arguments = args
            return fragment
        }
    }
    private lateinit var binding: FragmentDetailsBinding
    private lateinit var viewModel: DetailsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProvider(activity!!).get(DetailsViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)
        binding.viewModel = viewModel
        binding.executePendingBindings()
        viewModel.setContact(arguments?.getParcelable("contact") as ContactModel)
        return binding.root
    }

}