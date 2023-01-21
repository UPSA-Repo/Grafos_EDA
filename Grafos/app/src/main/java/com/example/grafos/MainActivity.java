package com.example.grafos;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grafos.models.Grafo;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Grafo grafo;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        grafo = new Grafo(this);
        textView = findViewById(R.id.tvPrincipal);
    }

    protected void cargarGrafo(){
        grafo.crearVertice("A");
        grafo.crearVertice("B");
        grafo.crearVertice("C");
        grafo.crearVertice("D");
        grafo.crearVertice("E");
        grafo.crearVertice("F");
        grafo.crearVertice("G");
        grafo.crearVertice("H");
        grafo.insertarArco("A", "B", 10);
        grafo.insertarArco("A", "C", 8);
        grafo.insertarArco("C", "B", 8);
        grafo.insertarArco("B", "D", 4);
        grafo.insertarArco("C", "E", 5);
        grafo.insertarArco("E", "D", 5);
        grafo.insertarArco("E", "F", 7);
        grafo.insertarArco("D", "F", 5);
        grafo.insertarArco("F", "G", 12);
        grafo.insertarArco("F", "H", 4);
        grafo.insertarArco("G", "C", 7);
        grafo.insertarArco("G", "H", 5);
        grafo.insertarArco("G", "E", 2);
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btnInsertarGrafo:
                cargarGrafo();
                break;
            case R.id.btnMostrarGrafo:
                textView.setText("");
                grafo.imprimir(textView);
                textView.setMovementMethod(new ScrollingMovementMethod());
                break;
            case R.id.btnDFS:
                EditText aux = findViewById(R.id.etVerticeInicial);
                if(!aux.getText().toString().isEmpty())
                    grafo.DFS(aux.getText().toString(), textView);
                else
                    Toast.makeText(this,"No existe vertice inicial",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnBFS:
                aux = findViewById(R.id.etVerticeInicial);
                if(!aux.getText().toString().isEmpty())
                    grafo.BFS(aux.getText().toString(), textView);
                else
                    Toast.makeText(this,"No existe vertice inicial",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnExisteCamino:
                EditText origen = findViewById(R.id.etVerticeInicial);
                EditText destino = findViewById(R.id.etVerticeFinal);
                if(!origen.getText().toString().isEmpty() && !destino.getText().toString().isEmpty()
                && grafo.existeCamino(origen.getText().toString().trim(), destino.getText().toString().trim()))
                    Toast.makeText(this,"Existe camino",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this,"No existe camino",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnPeso:
                Toast.makeText(this,"Peso: " + grafo.peso(),Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnCaminos:
                origen = findViewById(R.id.etVerticeInicial);
                destino = findViewById(R.id.etVerticeFinal);
                if(!origen.getText().toString().isEmpty() && !destino.getText().toString().isEmpty()){
                    int caminos = grafo.cantidadCaminos(origen.getText().toString().trim(), destino.getText().toString().trim());
                    if(caminos == -1)
                        Toast.makeText(this,"No existe referencia de los vertices",Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(this,"Existen " + caminos + " caminos",Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(this,"Debes ingresar vertice origen y destino",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnExamen:
                textView.setText("");
                origen = findViewById(R.id.etVerticeInicial);
                destino = findViewById(R.id.etVerticeFinal);
                grafo.mostrarCaminos(origen.getText().toString().toUpperCase(), destino.getText().toString().toUpperCase(), textView);
                break;
            case R.id.btnLimpiar:
                textView.setText("");
                origen = findViewById(R.id.etVerticeInicial);
                destino = findViewById(R.id.etVerticeFinal);
                origen.setText("");
                destino.setText("");
                grafo.limpiar();
                break;
        }
    }

}