package com.appco.utilizandosqlite.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper(context: Context): SQLiteOpenHelper(
    context, NOME_BANCO_DADOS, null, VERSAO_BANCO_DADOS) {

    companion object {
        const val NOME_BANCO_DADOS = "ListaTarefas.db"
        const val VERSAO_BANCO_DADOS = 1
        const val NOME_TABELA_TAREFAS = "tarefas"
        const val COLUNA_ID = "id"
        const val COLUNA_DESCRICAO = "descricao"
        const val COLUNA_DATA_CRIACAO = "dataCriacao"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val sql = "CREATE TABLE IF NOT EXISTS $NOME_TABELA_TAREFAS(" +
                "$COLUNA_ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "$COLUNA_DESCRICAO VARCHAR(70)," +
                "$COLUNA_DATA_CRIACAO DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP);"

        try {
            db?.execSQL(sql)
            Log.i("info_db", "Sucesso ao criar tabela")
        }catch (e: Exception) {
            e.printStackTrace()
            Log.i("info_db", "Erro ao criar tabela")
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}