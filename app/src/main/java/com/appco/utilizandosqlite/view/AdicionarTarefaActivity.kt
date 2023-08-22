package com.appco.utilizandosqlite.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.appco.utilizandosqlite.R
import com.appco.utilizandosqlite.database.TarefaDAO
import com.appco.utilizandosqlite.databinding.ActivityAdicionarTarefaBinding
import com.appco.utilizandosqlite.model.Tarefa

class AdicionarTarefaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdicionarTarefaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdicionarTarefaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            imageVoltar.setOnClickListener { finish() }

            var tarefa: Tarefa? = null
            val bundle = intent.extras

            if (bundle != null) {
                tarefa = bundle.getSerializable("tarefa") as Tarefa
                textInputTarefa.setText(tarefa.descricao)
                buttonSalvarTarefa.text = "Atualizar tarefa"
            }

            buttonSalvarTarefa.setOnClickListener {

                if (textInputTarefa.text.toString().isNotEmpty()) {
                    if (tarefa != null) {
                        editar(tarefa)
                    }else {
                        salvar()
                    }

                }else {
                    Toast.makeText(applicationContext, "Digite sua tarefa!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun editar(tarefa: Tarefa) {
        val nomeTarefa = binding.textInputTarefa.text.toString()
        val tarefaAtualizar = Tarefa(tarefa.id, nomeTarefa, "default")
        val tarefaDAO = TarefaDAO(this)

        if (tarefaDAO.atualizar(tarefaAtualizar)) {
            Toast.makeText(this, "Tarefa atualizada com sucesso", Toast.LENGTH_SHORT).show()
            finish()
        }else {
            Toast.makeText(this, "Erro ao atualizar tarefa", Toast.LENGTH_SHORT).show()
        }
    }

    private fun salvar() {
        val nomeTarefa = binding.textInputTarefa.text.toString()
        val tarefa = Tarefa(-1, nomeTarefa, "default")
        val tarefaDAO = TarefaDAO(applicationContext)

        if (tarefaDAO.salvar(tarefa)) {
            Toast.makeText(applicationContext, "Tarefa salva com sucesso", Toast.LENGTH_SHORT)
                .show()
            finish()
        } else {
            Toast.makeText(applicationContext, "Erro ao salvar tarefa", Toast.LENGTH_SHORT).show()
        }
    }
}