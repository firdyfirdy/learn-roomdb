package com.firdy.kalbeapps.view.pembelian

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.firdy.kalbeapps.R
import com.firdy.kalbeapps.adapter.PembelianAdapter
import com.firdy.kalbeapps.model.data.Pembelian
import com.firdy.kalbeapps.model.db.AppDatabase
import com.firdy.kalbeapps.model.repository.PembelianRepository
import kotlinx.android.synthetic.main.activity_pembelian.*

class PembelianActivity : AppCompatActivity() {

    private lateinit var repository: PembelianRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pembelian)

        getData()

        floatingActionButton.setOnClickListener {
            startActivity(Intent(this, PembelianAddActivity::class.java))
        }
    }

    private fun getData(){

        val dao = AppDatabase.getInstance(application).pembelianDao()
        repository = PembelianRepository(dao)

        val listItem = arrayListOf<Pembelian>()
        listItem.clear()
        listItem.addAll(repository.allData)

        if (listItem.isNotEmpty()){
            txtEmpty.visibility = View.GONE
            rcvPembelian.visibility = View.VISIBLE

            /* Apply adapter Recycler View */
            setupRecyclerView(listItem)
        }
        else{
            txtEmpty.visibility = View.VISIBLE
            rcvPembelian.visibility = View.GONE
        }
    }

    private fun setupRecyclerView(listPembelian: ArrayList<Pembelian>){
        rcvPembelian.apply {
            adapter = PembelianAdapter(listPembelian, object : PembelianAdapter.PembelianListener {
                override fun onItemClicked(data: Pembelian) {
                    val intent = Intent(this@PembelianActivity, PembelianAddActivity::class.java)
                    intent.putExtra(PembelianAddActivity().editPembelian, data)
                    startActivity(intent)
                }
            })
            layoutManager = LinearLayoutManager(this@PembelianActivity)
        }
    }

    override fun onResume() {
        super.onResume()
        getData()
    }
}