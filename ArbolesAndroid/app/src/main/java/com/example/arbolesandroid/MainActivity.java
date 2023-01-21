package com.example.arbolesandroid;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.arbolesandroid.models.Arbol;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Arbol arbol;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arbol = new Arbol();
        textView = (TextView) findViewById(R.id.tvArbol);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fabAddNode:
                showInputDialog();
                break;
            case R.id.btnPreOrder:
                if(arbol != null) {
                    textView.setText("");
                    arbol.preOrden(textView);
                } else
                    textView.setText("No hay arbol");
                break;
            case R.id.btnInOrder:
                if(arbol != null) {
                    textView.setText("");
                    arbol.inOrden(textView);
                } else
                    textView.setText("No hay arbol");
                break;
            case R.id.btnPostOrder:
                if(arbol != null) {
                    textView.setText("");
                    arbol.postOrden(textView);
                } else
                    textView.setText("No hay arbol");
                break;
            case R.id.btnLimpiar:
                arbol.limpiar();
                textView.setText("");
                break;
        }
    }

    private void showInputDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
        builder.setView(input);
        builder.setTitle("Insertar nodo");
        builder.setPositiveButton("Ingresar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(!input.getText().toString().matches(""))
                    arbol.insertar(Integer.parseInt(input.getText().toString()));
            }
        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }
}