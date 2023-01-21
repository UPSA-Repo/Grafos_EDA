package com.example.grafos.models;

import android.content.Context;
import android.widget.Toast;

import com.example.grafos.widgets.CustomAlertDialog;

public class Lista {
    private static int max = 50;
    private Object[] v;
    private int n;
    private Context context;

    public Lista(Context context) {
        this.v = new Object[max];
        this.n = 0;
        this.context = context;
    }

    public Lista() {
        this.v = new Object[max];
        this.n = 0;
        this.context = null;
    }

    public void dim(int d) {
        n = d;
    }

    public int dim() {
        return n;
    }

    public void setElem(Object x, int pos) {
        v[pos] = x;
    }

    public Object getElem(int pos) {
        return v[pos];
    }

    boolean vacia() {
        return n == 0;
    }

    boolean llena() {
        return n == max;
    }

    public void insertar(Object x, int p) {
        if (llena()) {
            //JOptionPane.showConfirmDialog(null, "Lista::Lista llena...!!!");
            //customAlertDialog.onCreateDialog(null, "Lista::Lista llena");
            Toast.makeText(context,"Lista::Lista llena",Toast.LENGTH_SHORT).show();
        }
        if (p < 0 || p > n) {
            //customAlertDialog.onCreateDialog(null, "Lista::Posicion no valida");
            Toast.makeText(context,"Lista::Posicion no valida",Toast.LENGTH_SHORT).show();
        } else {
            int m = n - 1;
            while (m >= p) {
                v[m + 1] = v[m];
                m = m - 1;
            }
            v[p] = x;
            n++;
        }
    }

    public void insertarPri(Object x) {
        insertar(x, 0);
    }

    public void insertarUlt(Object x) {
        insertar(x, n);
    }

    public void eliminar(int pos) {
        if (vacia()) {
            //JOptionPane.showConfirmDialog(null, "Lista::Lista vacia...!!!");
            Toast.makeText(context,"Lista::Lista vacia",Toast.LENGTH_SHORT).show();
        }

        int k = pos + 1;
        while (k < n) {
            v[k - 1] = v[k];
            k = k + 1;
        }
        n = n - 1;
    }

    public void eliminarPri() {
        eliminar(0);
    }

    public void eliminarUlt() {
        eliminar(n - 1);
    }
}
