package com.user.smart.api

import com.user.smart.models.ForgetPasswordResponseModel
import com.user.smart.models.UserModel
import com.user.smart.utils.ApiConstants.EMAIL_KEY
import com.user.smart.utils.ApiConstants.FORGET_PASSWORD_END_POINT
import com.user.smart.utils.ApiConstants.LOGIN_END_POINT
import com.user.smart.utils.ApiConstants.PASSWORD_KEY
import com.user.smart.utils.ApiConstants.USERNAME_KEY
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST(LOGIN_END_POINT)
    suspend fun login(
        @Field(USERNAME_KEY) username: String,
        @Field(PASSWORD_KEY) password: String
    ): Response<UserModel>

    @FormUrlEncoded
    @POST(FORGET_PASSWORD_END_POINT)
    suspend fun forgetPassword(
        @Field(EMAIL_KEY) emailID: String,
    ): Response<ForgetPasswordResponseModel>
}