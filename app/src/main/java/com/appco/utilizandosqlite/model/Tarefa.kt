package com.appco.utilizandosqlite.model

import java.io.Serializable

data class Tarefa(
    val id: Int,
    val descricao: String,
    val dataCriacao: String
) : Serializable
