package com.user.smart.api

import com.user.smart.models.FuelPriceAPIResponse
import com.user.smart.models.GetStoreListResponse
import com.user.smart.models.POSLiveDataResponse
import com.user.smart.utils.ApiConstants
import com.user.smart.utils.ApiConstants.POS_LIVE_PARAMETER
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface StoreApiServices {

    @GET(ApiConstants.STORE_LIST_END_POINT)
    suspend fun getStoreList(): Response<GetStoreListResponse>

    @GET(ApiConstants.POS_LIVE_END_POINT + "{storeID}/" + POS_LIVE_PARAMETER + "/{date}")
    suspend fun getPOSLiveData(
        @Path("storeID") storeID: String,
        @Path("date") date: String
    ): Response<POSLiveDataResponse>


    @GET(ApiConstants.FUEL_PRICE_API_END_POINT + "{storeID}")
    suspend fun getFuelPriceData(
        @Path("storeID") storeID: String
    ): Response<FuelPriceAPIResponse>
}