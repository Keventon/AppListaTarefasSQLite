package com.appco.utilizandosqlite.database

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.appco.utilizandosqlite.model.Tarefa

class TarefaDAO(context: Context): ITarefaDAO{

    private val escrita = DatabaseHelper(context).writableDatabase
    private val leitura = DatabaseHelper(context).readableDatabase

    override fun salvar(tarefa: Tarefa): Boolean {

        val conteudos = ContentValues()
        conteudos.put(DatabaseHelper.COLUNA_DESCRICAO, tarefa.descricao)

        try {
            escrita.insert(
                DatabaseHelper.NOME_TABELA_TAREFAS,
                null,
                conteudos
            )
            Log.i("info_db", "Sucesso ao salvar tarefa")
        }catch (e: Exception) {
            e.printStackTrace()
            Log.i("info_db", "Erro ao salvar tarefa")
            return false
        }
        return true
    }

    override fun atualizar(tarefa: Tarefa): Boolean {
        val args = arrayOf(tarefa.id.toString())
        val conteudo = ContentValues()
        conteudo.put(DatabaseHelper.COLUNA_DESCRICAO, tarefa.descricao)
        try {
            escrita.update(
                DatabaseHelper.NOME_TABELA_TAREFAS,
                conteudo,
                "${DatabaseHelper.COLUNA_ID} = ?",
                args
            )
            Log.i("info_db", "Sucesso ao remover tarefa")
        }catch (e: Exception) {
            e.printStackTrace()
            Log.i("info_db", "Erro ao remover tarefa")
            return false
        }

        return true
    }

    override fun remover(idTarefa: Int): Boolean {

        val args = arrayOf(idTarefa.toString())
        try {
            escrita.delete(
                DatabaseHelper.NOME_TABELA_TAREFAS,
                "${DatabaseHelper.COLUNA_ID} = ?",
                args
            )
            Log.i("info_db", "Sucesso ao remover tarefa")
        }catch (e: Exception) {
            e.printStackTrace()
            Log.i("info_db", "Erro ao remover tarefa")
            return false
        }

        return true
    }

    override fun listar(): List<Tarefa> {
        val listTarefas = mutableListOf<Tarefa>()

        val sql = "SELECT ${DatabaseHelper.COLUNA_ID}," +
                " ${DatabaseHelper.COLUNA_DESCRICAO}," +
                "strftime('%d/%m/%Y %H:%M', ${DatabaseHelper.COLUNA_DATA_CRIACAO}) ${DatabaseHelper.COLUNA_DATA_CRIACAO}" +
                " FROM ${DatabaseHelper.NOME_TABELA_TAREFAS}"

        val cursor = leitura.rawQuery(sql, null)

        val indiceId = cursor.getColumnIndex(DatabaseHelper.COLUNA_ID)
        val indiceDescricao = cursor.getColumnIndex(DatabaseHelper.COLUNA_DESCRICAO)
        val indiceDataCriacao = cursor.getColumnIndex(DatabaseHelper.COLUNA_DATA_CRIACAO)

        while (cursor.moveToNext()) {
            val id = cursor.getInt(indiceId)
            val descricao = cursor.getString(indiceDescricao)
            val dataCriacao = cursor.getString(indiceDataCriacao)

            listTarefas.add(Tarefa(id, descricao, dataCriacao))
        }

        return listTarefas
    }
}