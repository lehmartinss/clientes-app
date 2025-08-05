package br.senai.sp.jandira.clientesapp.service

import br.senai.sp.jandira.clientesapp.model.Cliente
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ClienteService {

    @POST("clientes")
    fun gravar(@Body cliente: Cliente): Call<Cliente>

    @GET("clientes")
    fun exibirTodos(): Call<List<Cliente>>

    @PUT("cliente")
    fun atualizar(@Body cliente: Cliente): Call<Cliente>

    @DELETE("cliente/{id}")
    fun excluir(@Path("id") id: Long): Call<Unit>

    @GET("cliente/{id}")
    fun exibirPorId(@Path("id") id:Long): Call<Cliente>
}

// No Long podia ser Int tbm (mesmma coisa so muda tamanho)
// Unit retorna nada, vazio