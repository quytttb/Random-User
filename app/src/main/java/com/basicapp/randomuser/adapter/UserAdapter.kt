package com.basicapp.randomuser.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.basicapp.randomuser.R
import com.basicapp.randomuser.databinding.ItemsBinding
import com.basicapp.randomuser.model.User

//class RandomUserAdapter with on click listener
//filtering the data

class UserAdapter(var users: List<User>, val onItemClicked: (User) -> Unit) :
    ListAdapter<User, UserAdapter.ViewHolder>(UserDiffUtil) {

    //view holder
    class ViewHolder(val binding: ItemsBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        /**
         * apply và with là 2 hàm giúp viết code ngắn gọn hơn
         * apply dùng để gọi các hàm của 1 object
         * with dùng để gọi các hàm của 1 object và trả về giá trị của nó
         */
        holder.binding.apply {
            with(users[position]) {
                userName.text = "${this.name.first} ${this.name.last}"
                userGender.text = this.gender
                circleImageView.load(this.picture.large) {
                    placeholder(R.mipmap.ic_launcher)
                }
                //set on click listener
                root.setOnClickListener { onItemClicked(this) }
            }

        }
    }

    override fun getItemCount(): Int = users.size

    object UserDiffUtil : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }

    }


}
