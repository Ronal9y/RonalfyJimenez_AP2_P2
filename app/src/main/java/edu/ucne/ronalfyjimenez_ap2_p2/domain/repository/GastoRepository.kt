package edu.ucne.ronalfyjimenez_ap2_p2.domain.repository

import edu.ucne.ronalfyjimenez_ap2_p2.data.remote.Resource
import edu.ucne.ronalfyjimenez_ap2_p2.domain.model.Gasto
import kotlinx.coroutines.flow.Flow

interface GastoRepository {
    fun getGastos(): Flow<Resource<List<Gasto>>>
    suspend fun getGastoById(id: Int): Resource<Gasto>
    suspend fun postGasto(gasto: Gasto): Resource<Gasto>
    suspend fun putGasto(id: Int, gasto: Gasto): Resource<Gasto>
    suspend fun deleteGasto(id: Int): Resource<Unit>
}