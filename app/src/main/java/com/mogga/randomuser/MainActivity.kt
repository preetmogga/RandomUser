package com.mogga.randomuser

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.View
import android.widget.Toast
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
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@DelicateCoroutinesApi
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: Adapter
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        resultNetwork()
        adapter = Adapter()
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        val apiInterface = ApiUtilities.getInstance().create(UserInterface::class.java)
        val repository = Repository(apiInterface)
        viewModel = ViewModelProvider(this, MainViewModelFactory(repository))[MainViewModel::class.java]
        viewModel.user.observe(this, { list ->
            list?.let {
                adapter.addData(list)
                binding.progressCircular.visibility = View.GONE
            }
        })

        binding.swipeRefresh.setOnRefreshListener {
            GlobalScope.launch(Dispatchers.IO) {
                viewModel.getData()

            }
            binding.swipeRefresh.isRefreshing = false

        }
        binding.progressCircular.visibility = View.VISIBLE


    }

    private fun resultNetwork(){
        if (checkForInternet(this)) {
            Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Disconnected", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkForInternet(context: Context): Boolean {

        // register activity with the connectivity manager service
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
           val network = connectivityManager.activeNetwork

        // Representation of the capabilities of an active network.
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

        return when {
            // Indicates this network uses a Wi-Fi transport,
            // or WiFi has network connectivity
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

            // Indicates this network uses a Cellular transport. or
            // Cellular has network connectivity
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

            // else return false
            else -> false
        }

    }
}