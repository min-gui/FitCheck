package com.m_gui.android.fitnote.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.m_gui.android.fitnote.R
import com.m_gui.android.fitnote.data.api.ApiHelperImpl
import com.m_gui.android.fitnote.data.api.RetrofitBuilder
import com.m_gui.android.fitnote.data.model.User
import com.m_gui.android.fitnote.ui.adapter.MainAdapter
import com.m_gui.android.fitnote.ui.intent.MainIntent
import com.m_gui.android.fitnote.ui.viewmodel.MainViewModel
import com.m_gui.android.fitnote.ui.viewstate.MainState
import com.m_gui.android.fitnote.util.ViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.recyclerView
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.*
import kotlin.math.log
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity() {
    val TAG = javaClass.simpleName
    private lateinit var mainViewModel: MainViewModel
    private var adapter = MainAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUI()
        setupViewModel()
        observeViewModel()
        setupClicks()

    }
    private fun setupClicks(){
        buttonFetchUser.setOnClickListener {
            lifecycleScope.launch {
                mainViewModel.userIntent.send(MainIntent.FetchUser)
            }
        }
    }

    private fun setupUI(){
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.run {
            addItemDecoration(
                DividerItemDecoration(
                    recyclerView.context,
                    (recyclerView.layoutManager as LinearLayoutManager).orientation
                )
            )

        }
        recyclerView.adapter = adapter
    }

    private fun setupViewModel(){
        mainViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(
                ApiHelperImpl(
                    RetrofitBuilder.apiService
                )
            )
        ).get(MainViewModel::class.java)
    }
    private fun observeViewModel(){
        lifecycleScope.launch {
            mainViewModel.state.collect{
                when (it){
                    is MainState.Idle -> {

                    }

                    is MainState.Loading -> {
                        buttonFetchUser.visibility = View.GONE
                        progressBar.visibility = View.VISIBLE
                    }

                    is MainState.User -> {
                        Log.e("TAG","onsuces")
                        progressBar.visibility = View.GONE
                        buttonFetchUser.visibility = View.GONE
                        renderList(it.user)

                    }
                    is MainState.Error -> {
                        Log.e("TAG","Error")
                        progressBar.visibility = View.GONE
                        buttonFetchUser.visibility = View.VISIBLE
                        Toast.makeText(this@MainActivity, it.error, Toast.LENGTH_SHORT).show()
                    }
                }

            }

        }
    }

    private fun renderList(users: List<User>){
        recyclerView.visibility = View.VISIBLE
        users.let { listofUsers -> listofUsers.let { adapter.addData(it) } }
        adapter.notifyDataSetChanged()
    }

}


