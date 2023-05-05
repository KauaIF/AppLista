package mateus.kaua.applista.activity.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import mateus.kaua.applista.R;
import mateus.kaua.applista.activity.adapter.MyAdapter;
import mateus.kaua.applista.activity.model.MainActivityViewModel;
import mateus.kaua.applista.activity.model.MyItem;
import mateus.kaua.applista.activity.util.Util;

public class MainActivity extends AppCompatActivity {
    static int NEW_ITEM_REQUEST=1;
    //declarando um arquivo de adaptação
    MyAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*Passo 7 - Navegação Para Uma Activity com Retorno de Resultado*/
        FloatingActionButton fabAddItem = findViewById(R.id.fabAddNewItem);
        fabAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,NewItemActivity.class);
                //inicia um requisição que espera ser solucionada pela nova activity
                startActivityForResult(i,NEW_ITEM_REQUEST);
            }
        });
        //passo 12 administrando o recicler view

        RecyclerView rvItens = findViewById(R.id.rvItens);
        //adicionando o view model para salvar os dados
        MainActivityViewModel vm = new ViewModelProvider(this).get(MainActivityViewModel.class);
        //intermediando por uma lista os dados
        List<MyItem> itens = vm.getItens();
        //associando o adapter
        myAdapter = new MyAdapter(this,itens);
        rvItens.setAdapter(myAdapter);
        //declaro que o elemento é fixo
        rvItens.setHasFixedSize(true);

        //inicio um novo gerenciador de layout
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvItens.setLayoutManager(layoutManager);
        //defino a divisão decorativa dos itens
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvItens.getContext(),DividerItemDecoration.VERTICAL);
        rvItens.addItemDecoration(dividerItemDecoration);
    }

    /* Passo 11:
    validamos o request, caso validado a instancia myItem recebe os dados e é adicionada a lista*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode==NEW_ITEM_REQUEST){
            if (resultCode== Activity.RESULT_OK){
                //intermediando por uma lista os dados
                MyItem myItem = new MyItem();
                myItem.title = data.getStringExtra("title");
                myItem.description = data.getStringExtra("description");
                Uri selectedPhotoURI = data.getData();
                try {
                    //utilizo o bitmap para não depender de acesso aos arquivos do dispositivo
                    Bitmap photo = Util.getBitmap(MainActivity.this,selectedPhotoURI,100,100);
                    myItem.photo = photo;
                }catch (FileNotFoundException e){
                    e.printStackTrace();
                }
                MainActivityViewModel vm = new ViewModelProvider(this).get(MainActivityViewModel.class);
                //pegando os itens da lista auxiliar
                List<MyItem> itens = vm.getItens();
                itens.add(myItem);
                myAdapter.notifyItemInserted(itens.size()-1);
            }
        }
    }
}
