package com.example.signalstrengthapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.signalstrengthapp.databinding.FragmentSecondBinding

class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    private var macAddress: String? = null // Variable to store MAC address

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Handle button click
        binding.buttonEnter.setOnClickListener {
            macAddress = binding.editTextMacAddress.text.toString() // Get MAC address input
            // You can add logic here to use the macAddress variable, e.g., validating it or passing it to another fragment
            findNavController().navigate(R.id.action_SecondFragment_to_AddSignalStrengthFragment)        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
