package com.user.smart.views.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.user.smart.models.*
import com.user.smart.repository.DailySalesReconRepository
import com.user.smart.repository.NetworkResult
import com.user.smart.views.adapters.DayReconViewItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DaySalesReconViewModel @Inject constructor(private val dailySalesReconRepository: DailySalesReconRepository) :
    ViewModel() {

    val daySalesReconLiveData: LiveData<NetworkResult<DayReconResponse>>
        get() = dailySalesReconRepository.dailySalesReconResponseLiveData


    fun callDailySalesReconAPI(
        storeID: String,
        date: String
    ) {
        viewModelScope.launch {
            dailySalesReconRepository.getDailySalesReconResponseLiveData(
                storeID = storeID,
                date = date
            )
        }
    }

    fun getStoreID(selectedStoreObject: StoreListResponseItem?): String {
        return if (null != selectedStoreObject && selectedStoreObject._id.isNotEmpty()) return selectedStoreObject._id else ""
    }

    fun getPreparedItemList(apiResponseList: List<Data>): List<DayReconViewItem> {
        val dataItemsList = mutableListOf<DayReconViewItem>()
        dataItemsList.add(DayReconViewItem.Title(title = "Merchandise Sales"))
        dataItemsList.add(
            DayReconViewItem.SubTitle(
                subTitleOne = "Department",
                subTitleTwo = "Items Sold",
                subTitleThree = "Amount"
            )
        )

        val inDataIndex = apiResponseList.indexOfFirst { it.In is In }
        if (inDataIndex >= 0) {
            if (!apiResponseList[inDataIndex].In.MCM.isNullOrEmpty()) {
                for (dataItem in apiResponseList[inDataIndex].In.MCM) {
                    dataItemsList.add(
                        DayReconViewItem.ListDataItem(
                            dataItem.MerchandiseCodeDescription,
                            String.format(
                                "%.2f",
                                dataItem.SalesQuantity.toFloat()
                            ),
                            String.format(
                                "%.2f",
                                dataItem.SalesAmount.toFloat()
                            )
                        )
                    )
                }
            }

            if (!apiResponseList[inDataIndex].In.MCM_total.isNullOrEmpty()) {
                for (dataItem in apiResponseList[inDataIndex].In.MCM_total) {
                    dataItemsList.add(
                        DayReconViewItem.Total(
                            "Total Grocery",
                            "",
                            String.format(
                                "%.2f",
                                dataItem.Original.toFloat()
                            )
                        )
                    )
                }
            }

            dataItemsList.add(DayReconViewItem.Title(title = "Sales Tax"))
            if (!apiResponseList[inDataIndex].In.SalesTax.isNullOrEmpty()) {
                for (dataItem in apiResponseList[inDataIndex].In.SalesTax) {
                    dataItemsList.add(
                        DayReconViewItem.Total(
                            "Total",
                            "",
                            String.format(
                                "%.2f",
                                dataItem.Original.toFloat()
                            )
                        )
                    )
                }
            } else {
                dataItemsList.add(
                    DayReconViewItem.Total(
                        "Total",
                        "",
                        "0.00"
                    )
                )
            }


            dataItemsList.add(DayReconViewItem.Title(title = "Fuel Sold (Gallons)"))
            if (!apiResponseList[inDataIndex].In.FGMVol.isNullOrEmpty()) {
                for (dataItem in apiResponseList[inDataIndex].In.FGMVol) {
                    dataItemsList.add(
                        DayReconViewItem.ListDataItem(
                            dataItem.MerchandiseCodeDescription,
                            "",
                            String.format(
                                "%.2f",
                                dataItem.FuelGradeSalesVolume.toFloat()
                            )
                        )
                    )
                }
            }
            if (!apiResponseList[inDataIndex].In.FGM_total_volume.isNullOrEmpty()) {
                for (dataItem in apiResponseList[inDataIndex].In.FGM_total_volume) {
                    dataItemsList.add(
                        DayReconViewItem.Total(
                            "Total Gas Volume",
                            "",
                            String.format(
                                "%.2f",
                                dataItem.Original.toFloat()
                            )
                        )
                    )
                }
            }

            dataItemsList.add(DayReconViewItem.Title(title = "Fuel Sold (Amount)"))
            if (!apiResponseList[inDataIndex].In.FGM.isNullOrEmpty()) {
                for (dataItem in apiResponseList[inDataIndex].In.FGM) {
                    dataItemsList.add(
                        DayReconViewItem.ListDataItem(
                            dataItem.MerchandiseCodeDescription,
                            "",
                            String.format(
                                "%.2f",
                                dataItem.FuelGradeSalesAmount.toFloat()
                            )
                        )
                    )
                }
            }
            if (!apiResponseList[inDataIndex].In.FGM_total_amount.isNullOrEmpty()) {
                for (dataItem in apiResponseList[inDataIndex].In.FGM_total_amount) {
                    dataItemsList.add(
                        DayReconViewItem.Total(
                            "Total Gas Amt Sold",
                            "",
                            String.format(
                                "%.2f",
                                dataItem.Original.toFloat()
                            )
                        )
                    )
                }
            }
        }

        dataItemsList.add(DayReconViewItem.Title(title = "Total in"))
        val totalInAmtDataIndex = apiResponseList.indexOfFirst { it.totalinamt is List<Totalinamt> }

        if (!apiResponseList[totalInAmtDataIndex].totalinamt.isNullOrEmpty()) {
            for (dataItem in apiResponseList[totalInAmtDataIndex].totalinamt) {
                dataItemsList.add(
                    DayReconViewItem.Total(
                        "Total",
                        "",
                        String.format(
                            "%.2f",
                            dataItem.Original.toFloat()
                        )
                    )
                )
            }
        }
        return dataItemsList
    }
}