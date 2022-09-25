package com.basicapp.randomuser.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.basicapp.randomuser.adapter.UserAdapter
import com.basicapp.randomuser.databinding.FragmentFirstBinding
import com.basicapp.randomuser.model.User
import com.basicapp.randomuser.repository.UserRepository
import com.basicapp.randomuser.server.UserClient
import com.basicapp.randomuser.server.UserService
import com.basicapp.randomuser.viewmodel.UserViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    //userList
    private lateinit var userList: List<User>

    private val userService: UserService by lazy { UserClient.getClient() }
    private lateinit var userAdapter: UserAdapter
    private val viewModel: UserViewModel by lazy {
        ViewModelProvider(
            this,
            UserViewModel.NoteViewModelFactory(UserRepository(userService))
        )[UserViewModel::class.java]
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //save instance state when navigating to other fragment
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getUsers()
        binding.swiperefresh.setOnRefreshListener {
            getUsers()
            //disable refresh
            binding.swiperefresh.isRefreshing = false
        }
    }

    private fun getUsers() {
        viewModel.errorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(context, "Error $it", Toast.LENGTH_SHORT).show()
        }

        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }
        //getUsersViewModel
        viewModel.getUsersViewModel()

        userList = viewModel.userList.value?: emptyList()

        //set adapter
        userAdapter = UserAdapter(userList) {
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


/*
        userService.getUsers(10).enqueue(object : Callback<UserResponse> {
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
*/
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}