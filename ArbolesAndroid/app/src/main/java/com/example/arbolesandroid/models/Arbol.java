package com.example.arbolesandroid.models;

import android.widget.TextView;

public class Arbol {
    private Nodo raiz;

    public Arbol() {
        this.raiz = null;
    }

    private boolean esHoja(Nodo pr) {
        return pr.getHI() == null && pr.getHD() == null;
    }

    public void insertar(int x) {
        Nodo q = new Nodo(x);
        if (this.raiz == null) {
            this.raiz = q;
            return;
        }
        Nodo nodo = this.raiz;
        Nodo nodoAnterior = null;
        while (nodo != null) {
            nodoAnterior = nodo;
            if (x < nodo.getElem()) {
                nodo = nodo.getHI();
            } else if (x > nodo.getElem()) {
                nodo = nodo.getHD();
            } else {
                return;
            }
        }
        if (x < nodoAnterior.getElem()) {
            nodoAnterior.setHI(q);
        } else {
            nodoAnterior.setHD(q);
        }
    }

    private void preOrden(Nodo nodo, TextView textView) {
        if (nodo != null){
            textView.append(nodo.getElem() + " ");
            preOrden(nodo.getHI(), textView);
            preOrden(nodo.getHD(), textView);
        }
    }

    public void preOrden(TextView textView) {
        if(this.raiz == null){
            textView.setText("No existe arbol");
            return;
        }
        preOrden(this.raiz, textView);
    }

    private void inOrden(Nodo nodo, TextView textView) {
        if (nodo != null) {
            inOrden(nodo.getHI(), textView);
            textView.append(nodo.getElem() + " ");
            inOrden(nodo.getHD(), textView);
        }
    }

    public void inOrden(TextView textView) {
        if(this.raiz == null){
            textView.setText("No existe arbol");
            return;
        }
        inOrden(this.raiz, textView);
    }

    private void postOrden(Nodo nodo, TextView textView) {
        if (nodo != null) {
            postOrden(nodo.getHI(), textView);
            postOrden(nodo.getHD(), textView);
            textView.append(nodo.getElem() + " ");
        }
    }

    public void postOrden(TextView textView) {
        if(this.raiz == null){
            textView.setText("No existe arbol");
            return;
        }
        postOrden(raiz, textView);
    }

    public void limpiar(){
        this.raiz = null;
    }
}
