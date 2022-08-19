package com.user.smart.api

import com.user.smart.models.GetStoreListResponse
import com.user.smart.utils.ApiConstants
import retrofit2.Response
import retrofit2.http.GET

interface StoreApiServices {

    @GET(ApiConstants.STORE_LIST_END_POINT)
    suspend fun getStoreList(): Response<GetStoreListResponse>
}