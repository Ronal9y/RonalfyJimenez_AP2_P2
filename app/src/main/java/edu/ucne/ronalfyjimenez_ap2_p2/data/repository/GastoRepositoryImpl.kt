package edu.ucne.ronalfyjimenez_ap2_p2.data.repository

import edu.ucne.ronalfyjimenez_ap2_p2.data.mapper.toDomain
import edu.ucne.ronalfyjimenez_ap2_p2.data.mapper.toDto
import edu.ucne.ronalfyjimenez_ap2_p2.data.remote.RemoteDataSource
import edu.ucne.ronalfyjimenez_ap2_p2.data.remote.Resource
import edu.ucne.ronalfyjimenez_ap2_p2.domain.model.Gasto
import edu.ucne.ronalfyjimenez_ap2_p2.domain.repository.GastoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class GastoRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : GastoRepository {

    override fun getGastos(): Flow<Resource<List<Gasto>>> = flow {
        try {
            emit(Resource.Loading())
            val gastosDto = remoteDataSource.getGastos()
            val gastos = gastosDto.map { dto -> dto.toDomain() }
            emit(Resource.Success(gastos))
        } catch (e: HttpException) {
            emit(Resource.Error("Error de conexión: ${e.message()}"))
        } catch (e: Exception) {
            emit(Resource.Error("Ocurrió un error inesperado: ${e.message}"))
        }
    }

    override suspend fun getGastoById(id: Int): Resource<Gasto> {
        return try {
            val gastoDto = remoteDataSource.getGastoById(id)
            Resource.Success(gastoDto.toDomain())
        } catch (e: HttpException) {
            Resource.Error("Error de conexión: ${e.message()}")
        } catch (e: Exception) {
            Resource.Error("Ocurrió un error inesperado: ${e.message}")
        }
    }

    override suspend fun postGasto(gasto: Gasto): Resource<Gasto> {
        return try {
            val gastoDto = remoteDataSource.postGasto(gasto.toDto())
            Resource.Success(gastoDto.toDomain())
        } catch (e: HttpException) {
            Resource.Error("Error de conexión: ${e.message()}")
        } catch (e: Exception) {
            Resource.Error("Ocurrió un error inesperado: ${e.message}")
        }
    }

    override suspend fun putGasto(id: Int, gasto: Gasto): Resource<Gasto> {
        return try {
            val gastoDto = remoteDataSource.putGasto(id, gasto.toDto())
            Resource.Success(gastoDto.toDomain())
        } catch (e: HttpException) {
            Resource.Error("Error de conexión: ${e.message()}")
        } catch (e: Exception) {
            Resource.Error("Ocurrió un error inesperado: ${e.message}")
        }
    }

    override suspend fun deleteGasto(id: Int): Resource<Unit> {
        return try {
            remoteDataSource.deleteGasto(id)
            Resource.Success(Unit)
        } catch (e: HttpException) {
            Resource.Error("Error de conexión: ${e.message()}")
        } catch (e: Exception) {
            Resource.Error("Ocurrió un error inesperado: ${e.message}")
        }
    }
}

