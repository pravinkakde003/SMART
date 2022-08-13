package com.user.smart.views.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.user.smart.R
import com.user.smart.databinding.ActivitySearchStoreBinding
import com.user.smart.utils.AppUtils
import com.user.smart.views.adapters.SearchStoreListAdapter


class SearchStoreListingActivity : BaseActivity() {
    private lateinit var binding: ActivitySearchStoreBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbar()
        setAdapter()
    }

    private fun setAdapter() {
        val storeList =
            AppUtils.getStoreList(AppUtils.getArrayListFromJson(this, R.raw.store_list))

        binding.storeListRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.storeListRecyclerView.setHasFixedSize(true)
        binding.storeListRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.storeListRecyclerView.addItemDecoration(
            DividerItemDecoration(
                binding.storeListRecyclerView.context,
                DividerItemDecoration.VERTICAL
            )
        )
        var mAdapter = SearchStoreListAdapter(storeList) {
            val resultIntent = Intent()
            resultIntent.putExtra("STORE_NAME", it.store_name)
            setResult(RESULT_OK, resultIntent)
            onBackPressed()
        }
        binding.storeListRecyclerView.adapter = mAdapter
    }

    private fun setToolbar() {
        binding.toolbar.title = resources.getString(R.string.select_store)
        binding.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.primary_color))
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }
}