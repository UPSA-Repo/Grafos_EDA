package com.example.grafos.models;
import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Grafo {
    private Lista LVertices;
    private Context context;

    public Grafo(Context context) {
        this.LVertices = new Lista(context);
        this.context = context;
    }

    public void crearVertice(String nomV) {
        LVertices.insertarUlt(new Vertice(nomV));
    }

    public Vertice buscarVertice(String nomV) {
        Vertice vertice;
        int i = 0;
        while (i < LVertices.dim()) {
            vertice = (Vertice) LVertices.getElem(i);
            if (vertice.getNombre().equals(nomV))
                return vertice;
            i++;
        }
        return null;
    }

    public void limpiar(){
        LVertices = new Lista(context);
    }

    public void insertarArco(String X, String Y, float co) {
        Vertice vo = buscarVertice(X);
        Vertice vd = buscarVertice(Y);
        vo.insertarArco(new Arco(vd, co));
    }

    public void imprimir(TextView textView) {
        int i = 0;
        Vertice v;
        Arco a;
        while (i < LVertices.dim()) {
            v = (Vertice) LVertices.getElem(i);
            int j = 0;
            while (j < v.LArcos.dim()) {
                textView.append(v.getNombre());
                textView.append("-->");
                a = (Arco) v.LArcos.getElem(j); //Muestra el arco donde apunto
                textView.append(a.getNombreVertD() + "  " + a.getCosto());
                textView.append("\n");
                j++;
            }
            i++;
        }
    }

    //METODOS DE ENSEÑANZA
    public float peso() {
        int i = 0;
        Vertice v;
        float s = 0;
        while (i < LVertices.dim()) {
            v = (Vertice) LVertices.getElem(i);
            int j = 0;
            Arco a;
            while (j < v.LArcos.dim()) {
                a = (Arco) v.LArcos.getElem(j);
                s = s + a.getCosto();
                j++;
            }
            i++;
        }
        return s;
    }


    public void desmarcarTodos() {
        for (int i = 0; i < this.LVertices.dim(); i++) {
            Vertice v = (Vertice) this.LVertices.getElem(i);
            v.marcado = false;
        }
    }

    //Necesita ordenar los vertices y arcos
    public boolean iguales(Grafo G2) {
        if (this.LVertices.dim() != G2.LVertices.dim())
            return false;
        int i = 0, j;
        while (i < this.LVertices.dim()) {
            Vertice v1 = (Vertice) this.LVertices.getElem(i);
            Vertice v2 = (Vertice) G2.LVertices.getElem(i);
            if (!v1.getNombre().equals(v2.getNombre()) || v1.LArcos.dim() != v2.LArcos.dim())
                return false;
            else {
                j = 0;
                while (j < v1.LArcos.dim()) {
                    Arco a1 = (Arco) v1.LArcos.getElem(j);
                    Arco a2 = (Arco) v2.LArcos.getElem(j);
                    if (!a1.getNombreVertD().equals(a2.getNombreVertD()) || a1.getCosto() != a2.getCosto())
                        return false;
                    j++;
                }
            }
            i++;
        }
        return true;
    }

    public void ordenarVerticesAlf() {
        Vertice aux;
        Vertice v1;
        Vertice v2;
        for (int i = 0; i < LVertices.dim(); i++) {
            for (int j = 0; j < LVertices.dim() - 1; j++) {
                v1 = (Vertice) LVertices.getElem(j);
                v2 = (Vertice) LVertices.getElem(j + 1);
                if (v1.getNombre().compareTo(v2.getNombre()) > 0) {
                    aux = (Vertice) LVertices.getElem(j);
                    LVertices.setElem(v2, j);
                    LVertices.setElem(aux, j + 1);
                }
            }
        }
        for (int i = 0; i < LVertices.dim(); i++) {
            Vertice v = (Vertice) LVertices.getElem(i);
            v.ordenarArcosAlf();
        }
    }

    public void DFS(String A, TextView textView) {
        ordenarVerticesAlf();
        textView.append("DFS: ");
        desmarcarTodos();
        ordenarVerticesAlf();
        Vertice v = buscarVertice(A);
        dfs(v, textView);
        textView.append("\n");
    }

    private void dfs(Vertice v, TextView textView) {
        textView.append(v.getNombre() + " ");
        v.marcado = true;
        Arco a;
        for (int i = 0; i < v.LArcos.dim(); i++) {
            a = (Arco) v.LArcos.getElem(i);
            Vertice w = buscarVertice(a.getNombreVertD());
            if (!w.marcado)
                dfs(w, textView);
        }
    }


    public void BFS(String s, TextView textView) {
        desmarcarTodos();
        ordenarVerticesAlf();
        Arco a;
        Vertice v = buscarVertice(s), w;
        LinkedList<Vertice> C = new LinkedList<Vertice>();
        C.add(v);
        v.marcado = true;
        textView.append("BFS: ");
        do {
            v = C.pop();
            textView.append(v.getNombre() + " ");
            for (int i = 0; i < v.LArcos.dim(); i++) {
                a = (Arco) v.LArcos.getElem(i);
                w = buscarVertice(a.getNombreVertD());
                if (!w.marcado) {
                    C.add(w);
                    w.marcado = true;
                }
            }
        } while (!C.isEmpty());
        textView.append("\n");
    }


    public boolean existeCamino(String X, String Y) {
        if (buscarVertice(X) != null && buscarVertice(Y) != null) {
            desmarcarTodos();
            Vertice v = buscarVertice(X);
            return existeCamino(v, Y);
        }
        return false;
    }

    private boolean existeCamino(Vertice v, String Y) {
        v.marcado = true;
        for (int i = 0; i < v.LArcos.dim(); i++) {
            Arco a = (Arco) v.LArcos.getElem(i);
            Vertice v2 = buscarVertice(a.getNombreVertD());
            if (!v2.marcado) { //sino esta marcado (marcado==false)
                if (v2.getNombre().equals(Y))
                    return true;
                else if (existeCamino(v2, Y))
                    return true;
            } else if (v2.getNombre().equals(Y))  //Cuando se quiera a traves de otros vertices llegar al mismo vertice o del mismo al mismo
                return true;
        }
        return false;
    }

    public boolean esConexo() {
        if(LVertices.vacia())
            return false;

        for(int i=0; i<LVertices.dim(); i++){
            Vertice vo = (Vertice) LVertices.getElem(i);
            for(int j=0; j<LVertices.dim(); j++){
                Vertice vd = (Vertice) LVertices.getElem(j);
                if(!existeCamino(vo.getNombre(), vd.getNombre()))
                    return false;
            }
        }

        return true;
    }

    public int cantidadCaminos(String X, String Y) {
        if (buscarVertice(X) != null && buscarVertice(Y) != null) {
            desmarcarTodos();
            //Vertice v = buscarVertice(X);
            return cantidadCaminos(X, Y);
        }
        return -1;
    }

    private int cantidadCaminos(Vertice v, String y) {
        int i = 0, c = 0;
        v.marcado = true;
        while (i < v.LArcos.dim()) {
            //jta.append(v.getNombre());
            Arco a = (Arco) v.LArcos.getElem(i);
            Vertice v2 = buscarVertice(a.getNombreVertD());
            if (!v2.marcado) {
                if (v2.getNombre().equals(y)) {
                    //jta.append(v2.getNombre());
                    //jta.append("\n");
                    c = c + 1;
                } else {
                    c = c + cantidadCaminos(v2, y);
                    v2.marcado = false;
                }
            }
            i++;
        }
        return c;
    }

    //primera versión
    public boolean existeCaminoBFS(String X, String Y) {
        desmarcarTodos();
        ordenarVerticesAlf();
        boolean aux = false;
        Vertice v = buscarVertice(X);
        LinkedList<Vertice> C;
        C = new LinkedList<Vertice>();
        C.add(v);
        v.marcado = true;
        do {
            v = C.pop();
            for (int i = 0; i < v.LArcos.dim(); i++) {
                Arco a = (Arco) v.LArcos.getElem(i);
                Vertice w = buscarVertice(a.getNombreVertD());
                if (!w.marcado) {
                    C.add(w);
                    w.marcado = true;
                    if (w.getNombre().equals(Y))
                        aux = true;
                }
            }
        } while (!C.isEmpty());
        return aux;
    }

    //Segunda versión
    public boolean existeCaminoBFS1(String X, String Y) {
        desmarcarTodos();
        ordenarVerticesAlf();
        Vertice v = buscarVertice(X);
        LinkedList<Vertice> C = new LinkedList<Vertice>();
        C.add(v);
        v.marcado = true;
        do {
            v = C.pop();
            for (int i = 0; i < v.LArcos.dim(); i++) {
                Arco a = (Arco) v.LArcos.getElem(i);
                Vertice w = buscarVertice(a.getNombreVertD());
                if (!w.marcado) {
                    C.add(w);
                    w.marcado = true;
                }
            }
        } while (!C.isEmpty());

        return buscarVertice(Y).marcado;
    }

    public void arcoMayor() {
        if (LVertices.vacia()) {
            Toast.makeText(context, "No existe grafo", Toast.LENGTH_SHORT).show();
            return;
        }

        desmarcarTodos();
        ordenarVerticesAlf();
        float mayor = Integer.MIN_VALUE;
        for (int i = 0; i < LVertices.dim(); i++) {
            Vertice vertice = (Vertice) LVertices.getElem(i);
            for (int j = 0; j < vertice.getCantArcos(); j++) {
                Arco arco = (Arco) vertice.LArcos.getElem(j);
                if (arco.getCosto() > mayor)
                    mayor = arco.getCosto();
            }
        }

        Toast.makeText(context, "Arco mayor: " + mayor, Toast.LENGTH_SHORT).show();
    }

    // ---------------- METODOS PARCIAL ---------------- //

    public void mostrarCaminos(String origen, String destino, TextView textView){
        if(LVertices.vacia()){
            Toast.makeText(context,"No existe grafo",Toast.LENGTH_SHORT).show();
            return;
        }

        if(buscarVertice(origen) == null || buscarVertice(destino) == null){
            Toast.makeText(context,"No existe alguno",Toast.LENGTH_SHORT).show();
            return;
        }

        if(origen.equals(destino)){
            Toast.makeText(context,"Mismo vertice",Toast.LENGTH_SHORT).show();
            return;
        }

        dfsExamen(origen, destino, textView);
    }

    private void dfsExamen(String origen, String destino, TextView textView){
        ordenarVerticesAlf();
        desmarcarTodos();
        List<String> caminos = new ArrayList();
        dfsExamen(origen, destino, caminos, textView);
    }

    private void dfsExamen(String origen, String destino, List<String> caminos, TextView textView){
        Vertice or = buscarVertice(origen);
        or.marcado = true;
        caminos.add(or.getNombre());

        if(origen.equals(destino)){
            // imprimir caminos
            System.out.println(caminos);
            for(String str : caminos)
                textView.append(str + " -> ");
            textView.append("\n");
        } else {
            for(int i=0; i<or.getCantArcos(); i++){
//                Vertice aux = (Vertice) or.LArcos.getElem(i);
                Arco a = (Arco) or.LArcos.getElem(i);
                String str = a.getNombreVertD();
                Vertice aux = buscarVertice(str);
                if (!aux.marcado)
                    dfsExamen(aux.getNombre(), destino, caminos, textView);
            }
        }

        caminos.remove(caminos.size() - 1);
        or.marcado = false;
    }
}
