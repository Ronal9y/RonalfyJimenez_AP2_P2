package edu.ucne.ronalfyjimenez_ap2_p2.domain.usecases

import edu.ucne.ronalfyjimenez_ap2_p2.data.remote.Resource
import edu.ucne.ronalfyjimenez_ap2_p2.domain.model.Gasto
import edu.ucne.ronalfyjimenez_ap2_p2.domain.repository.GastoRepository
import javax.inject.Inject

class PostGastoUseCase @Inject constructor(
    private val repository: GastoRepository
) {
    suspend operator fun invoke(gasto: Gasto): Resource<Gasto> = repository.postGasto(gasto)
}