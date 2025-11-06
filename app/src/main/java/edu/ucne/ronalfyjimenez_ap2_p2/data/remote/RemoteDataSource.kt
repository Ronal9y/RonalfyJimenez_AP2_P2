package edu.ucne.ronalfyjimenez_ap2_p2.data.remote

import edu.ucne.ronalfyjimenez_ap2_p2.data.remote.dto.GastoDto
import javax.inject.Inject
class RemoteDataSource @Inject constructor(
        private val api: GastoApi
    ) {
        suspend fun getGastos(): List<GastoDto> = api.getGastos()

        suspend fun getGastoById(id: Int): GastoDto = api.getGastoById(id)

        suspend fun postGasto(gasto: GastoDto): GastoDto = api.postGasto(gasto)

        suspend fun putGasto(id: Int, gasto: GastoDto): GastoDto = api.putGasto(id, gasto)

        suspend fun deleteGasto(id: Int) = api.deleteGasto(id)
    }