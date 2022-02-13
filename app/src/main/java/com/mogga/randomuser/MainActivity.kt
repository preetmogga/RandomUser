package com.mogga.randomuser

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mogga.randomuser.adapter.Adapter
import com.mogga.randomuser.api.ApiUtilities
import com.mogga.randomuser.api.UserInterface
import com.mogga.randomuser.databinding.ActivityMainBinding
import com.mogga.randomuser.mainviewmodel.MainViewModel
import com.mogga.randomuser.mainviewmodel.MainViewModelFactory
import com.mogga.randomuser.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: Adapter
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter= Adapter()
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        val apiInterface = ApiUtilities.getInstance().create(UserInterface::class.java)
        val repository = Repository(apiInterface)
        viewModel= ViewModelProvider(this,MainViewModelFactory(repository))[MainViewModel::class.java]
        viewModel.user.observe(this, {
            list ->
            list?.let {
                adapter.addData(list)
                binding.progressCircular.visibility = View.GONE
            }
        })
        binding.swipeRefresh.setOnRefreshListener {
            GlobalScope.launch(Dispatchers.IO) {
                viewModel.getData()

            }
            binding.swipeRefresh.isRefreshing =false

        }
                   binding.progressCircular.visibility = View.VISIBLE



    }

}