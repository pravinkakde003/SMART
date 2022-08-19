package com.user.smart.api

import com.user.smart.models.StoreListModelItem
import com.user.smart.models.UserModel
import com.user.smart.utils.ApiConstants.LOGIN_END_POINT
import com.user.smart.utils.ApiConstants.PASSWORD_KEY
import com.user.smart.utils.ApiConstants.STORE_LIST_END_POINT
import com.user.smart.utils.ApiConstants.USERNAME_KEY
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST(LOGIN_END_POINT)
    suspend fun login(
        @Field(USERNAME_KEY) username: String,
        @Field(PASSWORD_KEY) password: String
    ): Response<UserModel>
}