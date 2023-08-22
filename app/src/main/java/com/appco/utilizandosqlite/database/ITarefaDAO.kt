package com.appco.utilizandosqlite.database

import com.appco.utilizandosqlite.model.Tarefa

interface ITarefaDAO {
    fun salvar(tarefa: Tarefa): Boolean
    fun atualizar(tarefa: Tarefa): Boolean
    fun remover(idTarefa: Int): Boolean
    fun listar(): List<Tarefa>
}