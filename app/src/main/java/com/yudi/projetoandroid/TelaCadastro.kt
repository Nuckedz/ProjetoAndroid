package com.yudi.projetoandroid

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.yudi.projetoandroid.databinding.ActivityTelaCadastroBinding

class TelaCadastro : AppCompatActivity() {

    private lateinit var voltar: ActivityTelaCadastroBinding
    private lateinit var bancoDeDados: MeuBancoDeDados

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        voltar = ActivityTelaCadastroBinding.inflate(layoutInflater)
        setContentView(voltar.root)

        // Instanciar o banco de dados
        bancoDeDados = MeuBancoDeDados(this)

        voltar.txtJaCadastrei.setOnClickListener {
            val navegarTelaLogin = Intent(this, MainActivity::class.java)
            startActivity(navegarTelaLogin)
        }

        voltar.btSalvar.setOnClickListener {
            val email = voltar.editEmail.text.toString()
            val senha = voltar.editSenha.text.toString()
            val confirmarSenha = voltar.editConfirmarSenha.text.toString()
            val cpf = voltar.editCpf.text.toString()
            val telefone = voltar.editTelefone.text.toString()

            when {
                email.isEmpty() -> {
                    voltar.editEmail.error = "Preencha o E-mail!"
                }
                senha.isEmpty() -> {
                    voltar.editSenha.error = "Preencha a Senha!"
                }
                confirmarSenha.isEmpty() -> {
                    voltar.editConfirmarSenha.error = "Confirme a Senha!"
                }
                cpf.isEmpty() -> {
                    voltar.editCpf.error = "Preencha o CPF!"
                }
                telefone.isEmpty() -> {
                    voltar.editTelefone.error = "Preencha o Telefone!"
                }
                !email.contains("@gmail.com") -> {
                    val snackbar = Snackbar.make(it, "E-mail inválido!", Snackbar.LENGTH_SHORT)
                    snackbar.show()
                }
                senha.length <= 5 -> {
                    val snackbar = Snackbar.make(it, "A senha deve ter pelo menos 6 caracteres!", Snackbar.LENGTH_SHORT)
                    snackbar.show()
                }
                confirmarSenha != senha -> {
                    val snackbar = Snackbar.make(it, "As senhas não coincidem!", Snackbar.LENGTH_SHORT)
                    snackbar.show()
                }
                else -> {
                    Cadastrar(it, email, senha, cpf, telefone)
                }
            }
        }
    }

    private fun Cadastrar(view: View, email: String, senha: String, cpf: String, telefone: String) {
        val resultado = bancoDeDados.inserirUsuario(email, email, senha, cpf, telefone)

        if (resultado != -1L) {
            // Cadastro com sucesso
            val snackbar = Snackbar.make(view, "Cadastro realizado com sucesso!", Snackbar.LENGTH_SHORT)
            snackbar.show()
            navegarTelaPrincipal()
        } else {
            // Falha no cadastro
            val snackbar = Snackbar.make(view, "Erro ao cadastrar!", Snackbar.LENGTH_SHORT)
            snackbar.show()
        }
    }

    private fun navegarTelaPrincipal() {
        val intent = Intent(this, TelaPrincipal::class.java)
        startActivity(intent)
    }
}
