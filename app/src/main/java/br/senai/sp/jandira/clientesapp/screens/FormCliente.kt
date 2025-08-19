package br.senai.sp.jandira.clientesapp.screens

import android.app.AlertDialog
import android.util.Patterns
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import br.senai.sp.jandira.clientesapp.model.Cliente
import br.senai.sp.jandira.clientesapp.service.RetrofitFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.await

@Composable
fun FormCliente(navController: NavHostController?) {

    // VARIAVEIS DE ESTADO PARA UTILIZAR NO OUTLINED
    var nomeCliente by remember {
        mutableStateOf("")
    }
    var emailCliente by remember {
        mutableStateOf("")
    }

    // VARIAVEL QUE VAI EXIBIR A CAIXA DE DIALOGO
    var mostrarTelaSucesso by remember {
        mutableStateOf(false)
    }

    // CRIAR UMA INSTANCIA DO RETROFITFACTORY
    val clienteApi = RetrofitFactory().getClienteService()

   // VARIAVEIS DE ESTADO PARA VALIDAR A ENTRADA DO USUARIO
    var isNomeError by remember { mutableStateOf(false) }
    var isEmailError by remember { mutableStateOf(false) }

   fun validar():Boolean{
    isNomeError = nomeCliente.length < 1
    isEmailError = !Patterns.EMAIL_ADDRESS.matcher(emailCliente).matches()
    return !isNomeError && !isEmailError
   }
    
    Column (
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ){
        Row {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Icone do cadastro"
            )
            Text(
                text = "Novo Cliente",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        OutlinedTextField(
            value = nomeCliente,
            onValueChange = {
                nome -> nomeCliente = nome
            },
            label = {
                Text(text = "Nome do cliente ")
            },
            supportingText = {
                if (isNomeError){
                    Text(text = "Nome do cliente é obrigatório")
                }
            },
            trailingIcon = {
                if (isNomeError){
                    Icon(imageVector = Icons.Default.Info,
                        contentDescription = "Erro")
                }
            },
            isError = isNomeError,
            modifier = Modifier
                .fillMaxWidth()
        )
        OutlinedTextField(
            value = emailCliente,
            onValueChange = {
                email -> emailCliente = email
            },
            label = {
                Text(text = "Email do cliente ")
            },
            supportingText = {
                if (isEmailError){
                    Text(text = "Email do cliente é obrigatório")
                }
            },
            trailingIcon = {
                if (isEmailError){
                    Icon(imageVector = Icons.Default.Info,
                        contentDescription = "Erro")
                }
            },
            isError = isEmailError,
            modifier = Modifier
                .fillMaxWidth()
        )
        Button(
            onClick = {
                // CRIAR UM CLIENTE COM OS DADOS INFORMADOS
                if(validar())
                {
                    val cliente = Cliente(
                        nome = nomeCliente,
                        email = emailCliente
                    )
                    // REQUISICAO POST PARA API
                    GlobalScope.launch(Dispatchers.IO){
                        val novoCliente = clienteApi.gravar(cliente).await()
                      mostrarTelaSucesso = true
                    }
                }else {
                    println("******** Os dados estao incorretos")
                }

            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp)
        ) {
            Text(text = "Gravar cliente")
        }
        if (mostrarTelaSucesso){
            AlertDialog(
                onDismissRequest = {},
                title = { Text(text = "Sucesso") },
                text = { Text(text = "Cliente gravado com sucesso!") },
                confirmButton = {
                    Button(
                        onClick = {navController!!.navigate(route = "Home")}
                    ) {
                        Text(text = "Ok")
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FormClientePreview(){
    FormCliente(null)
}