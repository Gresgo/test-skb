package com.test.testskb.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.test.testskb.databinding.ItemContactBinding
import com.test.testskb.model.ContactModel
import com.test.testskb.ui.ContactsCallback
import java.util.*
import kotlin.collections.ArrayList

class ContactsAdapter(private val contacts: ArrayList<ContactModel>,
                      private val mContactsCallback: ContactsCallback?
): RecyclerView.Adapter<ContactsAdapter.ViewHolder>(), Filterable {

    private var fullList = ArrayList(contacts)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact = contacts[position]
        holder.bind(contact)

        holder.itemView.setOnClickListener {
            mContactsCallback?.onContactSelected(contact)
        }
    }

    fun update(newData: List<ContactModel>) {
        contacts.clear()
        contacts.addAll(newData)
        fullList = ArrayList(contacts)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = contacts.size

    override fun getFilter(): Filter = filter

    private var filter = object : Filter(){

        override fun performFiltering(constraint: CharSequence?): FilterResults {
            var filteredList = ArrayList<ContactModel>()
            if (constraint == null || constraint.isEmpty()) {
                filteredList.addAll(fullList)
            } else {
                val pattern = constraint.toString().toLowerCase(Locale.ROOT).trim()
                filteredList = fullList.filter {
                    it.name.toLowerCase(Locale.ROOT).contains(pattern)
                            || it.phone.contains(pattern)
                } as ArrayList<ContactModel>
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            contacts.clear()
            contacts.addAll(results?.values as List<ContactModel>)
            notifyDataSetChanged()
        }
    }

    class ViewHolder(private val binding: ItemContactBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(contact: ContactModel) {
            binding.contact = contact
            binding.executePendingBindings()
        }
    }
}