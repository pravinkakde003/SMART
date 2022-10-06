package com.user.smart.views.activity

import android.os.Bundle
import com.user.smart.R
import com.user.smart.databinding.ActivityDetailsBinding
import com.user.smart.utils.AppConstant
import com.user.smart.views.fragments.account.AccountFragment
import com.user.smart.views.fragments.checkinout.CheckInOutFragment
import com.user.smart.views.fragments.daysalesrecon.DaySalesReconFragment
import com.user.smart.views.fragments.financials.FinancialFragment
import com.user.smart.views.fragments.fuelinventory.FuelInventoryFragment
import com.user.smart.views.fragments.fuelprice.FuelPriceFragment
import com.user.smart.views.fragments.groups.GroupsFragment
import com.user.smart.views.fragments.inventory.InventoryFragment
import com.user.smart.views.fragments.lottery.LotteryFragment
import com.user.smart.views.fragments.order.OrdersFragment
import com.user.smart.views.fragments.posclosingsales.POSClosingSalesFragment
import com.user.smart.views.fragments.poslive.POSLiveFragment
import com.user.smart.views.fragments.purchases.PurchasesFragment
import com.user.smart.views.fragments.sendtopos.SendToPosFragment
import com.user.smart.views.fragments.transactions.TransactionFragment

class DetailsActivity : BaseActivity() {
    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navigateToFragment()
    }

    private fun navigateToFragment() {
        var data = intent.getIntExtra(AppConstant.SELECTED_DASHBOARD_ITEM_KEY, 1)

        when (data) {
            1 -> {
                supportFragmentManager.beginTransaction()
                    .add(R.id.fragmentContainer, POSLiveFragment())
                    .commit()
            }
            2 -> {
                supportFragmentManager.beginTransaction()
                    .add(R.id.fragmentContainer, POSClosingSalesFragment())
                    .commit()
            }
            3 -> {
                supportFragmentManager.beginTransaction()
                    .add(R.id.fragmentContainer, DaySalesReconFragment())
                    .commit()
            }
            4 -> {
                supportFragmentManager.beginTransaction()
                    .add(R.id.fragmentContainer, TransactionFragment())
                    .commit()
            }
            5 -> {
                supportFragmentManager.beginTransaction()
                    .add(R.id.fragmentContainer, FuelPriceFragment())
                    .commit()
            }
            6 -> {
                supportFragmentManager.beginTransaction()
                    .add(R.id.fragmentContainer, FuelInventoryFragment())
                    .commit()
            }
            7 -> {
                supportFragmentManager.beginTransaction()
                    .add(R.id.fragmentContainer, AccountFragment())
                    .commit()
            }
            8 -> {
                supportFragmentManager.beginTransaction()
                    .add(R.id.fragmentContainer, InventoryFragment())
                    .commit()
            }
            9 -> {
                supportFragmentManager.beginTransaction()
                    .add(R.id.fragmentContainer, LotteryFragment())
                    .commit()
            }
            10 -> {
                supportFragmentManager.beginTransaction()
                    .add(R.id.fragmentContainer, FinancialFragment())
                    .commit()
            }
            11 -> {
                supportFragmentManager.beginTransaction()
                    .add(R.id.fragmentContainer, PurchasesFragment())
                    .commit()
            }
            12 -> {
                supportFragmentManager.beginTransaction()
                    .add(R.id.fragmentContainer, GroupsFragment())
                    .commit()
            }
            13 -> {
                supportFragmentManager.beginTransaction()
                    .add(R.id.fragmentContainer, OrdersFragment())
                    .commit()
            }
            14 -> {
                supportFragmentManager.beginTransaction()
                    .add(R.id.fragmentContainer, CheckInOutFragment())
                    .commit()
            }
            15 -> {
                supportFragmentManager.beginTransaction()
                    .add(R.id.fragmentContainer, SendToPosFragment())
                    .commit()
            }
        }
    }

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        if (count == 0) {
            super.onBackPressed()
            overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out)
        } else {
            supportFragmentManager.popBackStack()
        }
    }
}