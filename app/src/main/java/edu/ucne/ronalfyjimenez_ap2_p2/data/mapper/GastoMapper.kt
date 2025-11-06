package edu.ucne.ronalfyjimenez_ap2_p2.data.mapper

import edu.ucne.ronalfyjimenez_ap2_p2.data.remote.dto.GastoDto
import edu.ucne.ronalfyjimenez_ap2_p2.domain.model.Gasto

fun GastoDto.toDomain(): Gasto {
    return Gasto(
        gastoId = this.gastoId,
        fecha = this.fecha,
        suplidor = this.suplidor,
        ncf = this.ncf,
        itbis = this.itbis,
        monto = this.monto
    )
}

fun Gasto.toDto(): GastoDto {
    return GastoDto(
        gastoId = this.gastoId,
        fecha = this.fecha,
        suplidor = this.suplidor,
        ncf = this.ncf,
        itbis = this.itbis,
        monto = this.monto
    )
}