package com.appco.utilizandosqlite.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.appco.utilizandosqlite.adapter.TarefaAdapter
import com.appco.utilizandosqlite.database.TarefaDAO
import com.appco.utilizandosqlite.databinding.ActivityMainBinding
import com.appco.utilizandosqlite.model.Tarefa

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var listaTarefas = emptyList<Tarefa>()
    private var tarefaAdapter: TarefaAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            fabAdicionar.setOnClickListener {
                startActivity(Intent(applicationContext, AdicionarTarefaActivity::class.java))
            }

            //Recycle View
            tarefaAdapter = TarefaAdapter(
                { id -> confirmarExclusao(id)},
                {tarefa ->  editarTarefa(tarefa)}
            )
            recycleTarefas.adapter = tarefaAdapter
            recycleTarefas.layoutManager = LinearLayoutManager(applicationContext)
        }
    }

    private fun editarTarefa(tarefa: Tarefa) {
        val intent = Intent(this, AdicionarTarefaActivity::class.java)
        intent.putExtra("tarefa", tarefa)
        startActivity(intent)
    }

    private fun confirmarExclusao(id: Int) {
        val alertBuilder = AlertDialog.Builder(this)

        alertBuilder.setTitle("Confirmar exclusão")
        alertBuilder.setMessage("Deseja realmente excluir essa tarefa?")
        alertBuilder.setPositiveButton("Sim") { _, _ ->
            val tarefaDAO = TarefaDAO(this)

            if (tarefaDAO.remover(id)) {
                atualizarListaTarefas()
                Toast.makeText(applicationContext, "Tarefa removida", Toast.LENGTH_SHORT).show()
            }else {
                Toast.makeText(applicationContext, "Erro ao remover tarefa", Toast.LENGTH_SHORT).show()
            }
        }
        alertBuilder.setNegativeButton("Não") { _, _ -> }

        alertBuilder.create().show()
    }

    private fun atualizarListaTarefas() {
        val tarefaDAO = TarefaDAO(applicationContext)
        listaTarefas = tarefaDAO.listar()
        tarefaAdapter?.adicionarLista(listaTarefas)
    }

    override fun onStart() {
        super.onStart()
        atualizarListaTarefas()
    }
}