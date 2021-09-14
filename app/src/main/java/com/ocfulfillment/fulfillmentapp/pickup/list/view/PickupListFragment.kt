package com.ocfulfillment.fulfillmentapp.pickup.list.view

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.ocfulfillment.fulfillmentapp.R
import com.ocfulfillment.fulfillmentapp.data.pickup.PickupLineItemsViewState
import com.ocfulfillment.fulfillmentapp.data.pickup.PickupLineItemsViewState.*
import com.ocfulfillment.fulfillmentapp.databinding.FragmentPickupListBinding
import com.ocfulfillment.fulfillmentapp.navigation.Navigation
import com.ocfulfillment.fulfillmentapp.pickup.list.view.adapter.PickupLineItemAdapter
import com.ocfulfillment.fulfillmentapp.pickup.list.viewmodel.PickupListViewModel
import org.koin.android.ext.android.inject

class PickupListFragment: Fragment(R.layout.fragment_pickup_list) {

    private val navigation by lazy { Navigation(findNavController()) }

    private lateinit var binding: FragmentPickupListBinding

    private val viewModel: PickupListViewModel by inject()

    private val itemsAdapter: PickupLineItemAdapter by inject()

    private val linearLayoutManager: LinearLayoutManager by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPickupListBinding.bind(view)

        binding.pickLineItemsRecyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = itemsAdapter
            itemAnimator = null
        }

        binding.toolbar.setOnClickListener {
            viewModel.logoutUser()
            navigation.toLogin()
        }

        itemsAdapter.onChangeStatusRequested = { lineItem ->
            context?.let { ctx ->
                StatusChangeDialog
                    .buildAlertDialog(
                        ctx,
                    title = getString(R.string.status_change_dialog_title),
                    description =  getString(R.string.status_change_dialog_desc, lineItem.status.name.lowercase()),
                    clickActions = StatusChangeDialog.ClickActions(
                        onNegativeClickText = getString(R.string.status_change_dialog_negative),
                        onPositiveClickText = getString(R.string.status_change_dialog_positive),
                        onNegativeWithDialogClickAction = { it.dismiss() },
                        onPositiveWithDialogClickAction = {
                            it.dismiss()
                            viewModel.toggleStatus(lineItem)
                        }
                    ))
                    .show()
            }
        }

        viewModel.initialize()

        viewModel.state.observe(viewLifecycleOwner, Observer(::onStateChanged))
    }

    private fun onStateChanged(state: PickupLineItemsViewState) {
        when(state) {
            is Content -> {
                itemsAdapter.submitList(state.items)
                binding.count.text = getString(R.string.content_view_sub_title, state.count)
                binding.loadingIndicator.isVisible = false
                binding.pickLineItemsRecyclerView.isVisible = true
            }

            Error -> {
                binding.loadingIndicator.isVisible = false
                binding.pickLineItemsRecyclerView.isVisible = true
                Snackbar.make(
                    binding.root,
                    getString(R.string.pickup_list_error_text),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
            Loading -> {
                binding.loadingIndicator.isVisible = true
                binding.pickLineItemsRecyclerView.isVisible = false
            }
        }
    }
}