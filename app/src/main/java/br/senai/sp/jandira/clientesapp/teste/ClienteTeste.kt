package br.senai.sp.jandira.clientesapp.teste

import br.senai.sp.jandira.clientesapp.model.Cliente
import br.senai.sp.jandira.clientesapp.service.RetrofitFactory

fun main() {

    val c1 = Cliente(
        nome = "Leticia Bia",
        email = "leticiabia@email.com"

    )

    val retrofit = RetrofitFactory().getClienteService()
    val cliente = retrofit.gravar(c1)
}