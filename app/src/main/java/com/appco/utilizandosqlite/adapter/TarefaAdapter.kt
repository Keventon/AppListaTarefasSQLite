package com.appco.utilizandosqlite.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.appco.utilizandosqlite.databinding.ItemTarefaBinding
import com.appco.utilizandosqlite.model.Tarefa

class TarefaAdapter(
    val onClickExcluir: (Int) -> Unit,
    val onCLickEditar: (Tarefa) -> Unit
) : RecyclerView.Adapter<TarefaAdapter.TarefaViewHolder>() {

    private var listaTarefas: List<Tarefa> = emptyList()

    fun adicionarLista( lista: List<Tarefa> ){
        this.listaTarefas = lista
        notifyDataSetChanged()
    }

    inner class TarefaViewHolder(itemBinding: ItemTarefaBinding)
        : RecyclerView.ViewHolder(itemBinding.root) {

        private val binding: ItemTarefaBinding

        init {
            binding = itemBinding
        }

        fun bind( tarefa: Tarefa ){
            binding.textDescricao.text = tarefa.descricao
            binding.textData.text = tarefa.dataCriacao

            binding.btnExcluir.setOnClickListener {
                onClickExcluir(tarefa.id)
            }

            binding.btnEditar.setOnClickListener {
                onCLickEditar(tarefa)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TarefaViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemTarefaBinding = ItemTarefaBinding.inflate(
            layoutInflater, parent, false
        )
        return TarefaViewHolder(itemTarefaBinding)
    }

    override fun onBindViewHolder(holder: TarefaViewHolder, position: Int) {
        val tarefa = listaTarefas[position]
        holder.bind( tarefa )
    }

    override fun getItemCount(): Int {
        return listaTarefas.size
    }

}