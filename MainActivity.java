package com.example.cadastro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView listContatos;
    private Button btnNovoCadastro;
    private Contato contato;
    BDContatoHelper contatoHelper;
    ArrayList<Contato> arrayListContato;
    ArrayAdapter<Contato> arrayAdapterContato;
    private int id1,id2; //menu item
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listContatos = findViewById(R.id.listContatos);
        registerForContextMenu(listContatos);
        btnNovoCadastro = findViewById(R.id.btnNovoCadastro);
        btnNovoCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this,CadastroContato.class);
                startActivity(it);
            }
        });
        listContatos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Contato contatoEnviada = (Contato) arrayAdapterContato.getItem(position);
                Intent it = new Intent(MainActivity.this,CadastroContato.class);
                it.putExtra("chave_contato",contatoEnviada);
                startActivity(it);
            }
        });
        listContatos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView,View view, int position, long id){
                contato = arrayAdapterContato.getItem(position);
                return false;
            }
        });
    }
    public void preencheLista(){
        contatoHelper = new BDContatoHelper(MainActivity.this);
        arrayListContato = contatoHelper.selectAllContato();
        contatoHelper.close();
        if(listContatos!=null){
            arrayAdapterContato = new ArrayAdapter<Contato>(MainActivity.this,
                    android.R.layout.simple_list_item_1,arrayListContato);
            listContatos.setAdapter(arrayAdapterContato);
        }
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        MenuItem mDelete = menu.add(Menu.NONE, id1, 1,"Deleta Registro");
        MenuItem mSair = menu.add(Menu.NONE, id2, 2,"Cancela");
        mDelete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                long retornoBD;
                contatoHelper = new BDContatoHelper(MainActivity.this);
                retornoBD = contatoHelper.deleteContato(contato);
                contatoHelper.close();
                if(retornoBD==-1){
                    alert("Erro de exclusão!");
                }
                else{
                    alert("Registro excluído com sucesso!");
                }
                preencheLista();
                return false;        }
        });
        super.onCreateContextMenu(menu, v, menuInfo);
    }
    @Override
    protected void onResume(){
        super.onResume();
        preencheLista();
    }
    private void alert(String s){
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }
}