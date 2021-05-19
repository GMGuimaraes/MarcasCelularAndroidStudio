package com.example.cadastro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class BDContatoHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "contato2.db";
    private static final String TABLE_NAME = "contato";
    private static final String COLUM_ID = "id";
    private static final String COLUM_NOME = "nome";
    private static final String COLUM_IDADE = "idade";
    private static final String COLUM_ENDERECO = "endereco";
    private static final String COLUM_TELEFONE = "telefone";
    SQLiteDatabase db;
    private static final String TABLE_CREATE = "create table contato " +
            "(id integer primary key autoincrement, nome text not null, idade integer, endereco text not null, telefone text not null);";
    public BDContatoHelper(Context context){
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        this.db = db;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS "+TABLE_NAME;
        db.execSQL(query);
        onCreate(db);
    }
    public long insert_Contato(Contato c){
        long retornoBD;
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUM_NOME,c.getNome());
        values.put(COLUM_IDADE,c.getIdade());
        values.put(COLUM_ENDERECO, c.getEndereco());
        values.put(COLUM_TELEFONE,c.getTelefone());
        retornoBD=db.insert(TABLE_NAME,null,values);
        String res=Long.toString(retornoBD);
        Log.i("BDContatoHelper",res);
        db.close();
        return retornoBD;
    }
    public ArrayList<Contato> selectAllContato(){
        String[] coluns={COLUM_ID, COLUM_NOME,COLUM_IDADE, COLUM_ENDERECO,
                COLUM_TELEFONE};
        Cursor cursor = getWritableDatabase().query(TABLE_NAME,
                coluns,null,null,null,
                null,"upper(nome)",null);
        ArrayList<Contato> listaContato = new ArrayList<Contato>();
        while(cursor.moveToNext()){
            Contato c = new Contato();
            c.setId(cursor.getInt(0));
            c.setNome(cursor.getString(1));
            c.setIdade(cursor.getInt(2));
            c.setEndereco(cursor.getString(3));
            c.setTelefone(cursor.getString(4));
            listaContato.add(c);
        }
        return listaContato;
    }
    public long updateContato(Contato c){
        long retornoBD;
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUM_NOME,c.getNome());
        values.put(COLUM_IDADE,c.getIdade());
        values.put(COLUM_ENDERECO, c.getEndereco());
        values.put(COLUM_TELEFONE,c.getTelefone());
        String[] args = {String.valueOf(c.getId())};
        retornoBD=db.update(TABLE_NAME,values,"id=?",args);
        db.close();
        return retornoBD;
    }
    public long deleteContato(Contato c){
        long retornoBD;
        db = this.getWritableDatabase();
        String[] args = {String.valueOf(c.getId())};
        retornoBD=db.delete(TABLE_NAME, COLUM_ID+"=?",args);
        return retornoBD;
    }
}
