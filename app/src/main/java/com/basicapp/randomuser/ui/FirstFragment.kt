package com.basicapp.randomuser.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.basicapp.randomuser.adapter.UserAdapter
import com.basicapp.randomuser.databinding.FragmentFirstBinding
import com.basicapp.randomuser.model.*
import com.basicapp.randomuser.server.UserClient
import com.basicapp.randomuser.server.UserInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val userClient: UserInterface by lazy { UserClient.getClient() }
    private lateinit var userAdapter: UserAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.progressBar.visibility = View.VISIBLE
        binding.recyclerView.visibility = View.GONE

        getUsers()
        binding.swiperefresh.setOnRefreshListener {
            getUsers()
            //disable refresh
            binding.swiperefresh.isRefreshing = false
        }
    }

    private fun getUsers() {
        userClient.getUsers(10).enqueue(object : Callback<UserResponse> {
            //onfailure
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Toast.makeText(context, "Failure", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                when {
                    response.isSuccessful -> {
                        //respone to User
                        userAdapter = UserAdapter(
                            response.body()?.userList!!
                        ) {
                            val action =
                                FirstFragmentDirections.actionFirstFragmentToSecondFragment(
                                    lastName = it.name.last,
                                    firstName = it.name.first,
                                    email = it.email,
                                    location = it.location.city,
                                    image = it.picture.large
                                )
                            findNavController().navigate(action)
                        }
                        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()

                        binding.progressBar.visibility = View.GONE

                        binding.recyclerView.apply {
                            visibility = View.VISIBLE
                            adapter = userAdapter
                            setHasFixedSize(true)
                            LinearLayoutManager(context).apply {
                                orientation = LinearLayoutManager.VERTICAL
                                binding.recyclerView.layoutManager = this
                            }

                        }

                    }
                    else -> {
                        //error code
                        Toast.makeText(context, "Error ${response.code()}", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }

        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}