package com.basicapp.randomuser.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import coil.load
import com.basicapp.randomuser.R
import com.basicapp.randomuser.databinding.FragmentSecondBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //get data from FirstFragment
        val args = SecondFragmentArgs.fromBundle(requireArguments())

        binding.usernameDetail.text = "${args.lastName} ${args.lastName}"
        binding.emailDetail.text = args.email
        binding.addressDetail.text = args.location
        binding.circleImageViewDetail.load(args.image) {
            crossfade(true)
            crossfade(1000)
        }



    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}