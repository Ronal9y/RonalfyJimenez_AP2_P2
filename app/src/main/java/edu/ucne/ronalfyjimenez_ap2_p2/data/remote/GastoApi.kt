package edu.ucne.ronalfyjimenez_ap2_p2.data.remote

import edu.ucne.ronalfyjimenez_ap2_p2.data.remote.dto.GastoDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface GastoApi {
    @GET("api/Gastos")
    suspend fun getGastos(): List<GastoDto>

    @GET("api/Gastos/{id}")
    suspend fun getGastoById(@Path("id") id: Int): GastoDto

    @POST("api/Gastos")
    suspend fun postGasto(@Body gasto: GastoDto): GastoDto

    @PUT("api/Gastos/{id}")
    suspend fun putGasto(@Path("id") id: Int, @Body gasto: GastoDto): GastoDto

    @DELETE("api/Gastos/{id}")
    suspend fun deleteGasto(@Path("id") id: Int)
}