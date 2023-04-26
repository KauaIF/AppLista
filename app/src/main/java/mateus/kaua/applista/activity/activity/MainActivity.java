package mateus.kaua.applista.activity.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import mateus.kaua.applista.R;

public class MainActivity extends AppCompatActivity {
    static int NEW_ITEM_REQUEST=1;
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
                startActivityForResult(i,NEW_ITEM_REQUEST);
            }
        });
    }
}