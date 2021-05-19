package com.example.cadastro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CadastroContato extends AppCompatActivity {
    private EditText edtNome, edtIdade, edtEndereco, edtTelefone;
    private Button btnVariavel;
    Contato contato, altContato;
    BDContatoHelper contatoHelper;
    long retornoBD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_contato);
        Intent it=getIntent();
        altContato = (Contato) it.getSerializableExtra("chave_contato");
        contato = new Contato();
        contatoHelper = new BDContatoHelper(CadastroContato.this);
        edtNome = findViewById(R.id.edtNome);
        edtIdade = findViewById(R.id.edtIdade);
        edtEndereco = findViewById(R.id.edtEndereco);
        edtTelefone = findViewById(R.id.edtTelefone);
        btnVariavel = findViewById(R.id.btnVariavel);
        if(altContato != null){
            btnVariavel.setText("ALTERAR");
            edtNome.setText(altContato.getNome());
            edtIdade.setText(altContato.getIdade()+"");
            edtEndereco.setText(altContato.getEndereco());
            edtTelefone.setText(altContato.getTelefone());
            contato.setId(altContato.getId());
        }
        else{
            btnVariavel.setText("SALVAR");
        }
        btnVariavel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome = edtNome.getText().toString();
                String idade = edtIdade.getText().toString();
                String endereco = edtEndereco.getText().toString();
                String telefone = edtTelefone.getText().toString();
                long retornoBD;
                contato.setNome(nome);
                contato.setIdade(Integer.parseInt(idade));
                contato.setEndereco(endereco);
                contato.setTelefone(telefone);
                if(btnVariavel.getText().toString().equals("SALVAR")) {
                    retornoBD=contatoHelper.insert_Contato(contato);
                    if(retornoBD==-1){
                        alert("Erro ao Cadastrar!");
                    }
                    else{
                        alert("Cadastro realizado com sucesso!");
                    }
                }else{
                    contatoHelper.updateContato(contato);
                    contatoHelper.close();
                }
                finish();
            }
        });
    }
    private void alert(String s){
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }
}