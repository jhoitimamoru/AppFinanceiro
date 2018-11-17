package com.jmksolutions.appfinanceiro.Util;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jmksolutions.appfinanceiro.Model.LoginModel;
import com.jmksolutions.appfinanceiro.R;

import java.util.List;

import android.app.Activity;
import android.graphics.Color;


/**
 * Created by Marcus on 27/03/2017.
 */

public class AdaptersConsulta extends BaseAdapter{

    private final List<LoginModel> ListaLogin;
    private final Activity act;
    private int selected = -1;

    public AdaptersConsulta(List<LoginModel> con, Activity act) {
        this.ListaLogin = con;
        this.act = act;
    }

    @Override
    public int getCount() {
        return ListaLogin.size();
    }

    @Override
    public Object getItem(int position) {
        return ListaLogin.get(position);
    }

    @Override
    public long getItemId(int position) {
        LoginModel conListagem1Int = ListaLogin.get(position);
        return conListagem1Int.getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = act.getLayoutInflater().inflate(R.layout.layout_listview, parent, false);
        LoginModel conListagem1Int = ListaLogin.get(position);

        //pegando as referências das Views
        TextView texto1 = (TextView)view.findViewById(R.id.txtListPer1View1);
        TextView texto2 = (TextView)view.findViewById(R.id.txtListPer1View2);
        TextView texto3 = (TextView)view.findViewById(R.id.txtListPer1View3);
        TextView texto4 = (TextView)view.findViewById(R.id.txtListPer1View4);

        if(selected != -1 && position == selected) {
            view.setBackgroundColor(Color.argb(180, 83, 131, 233));
        }

        //populando as Views
        texto1.setText("Id: "+String.valueOf(conListagem1Int.getId()));
        texto2.setText("Nome: "+String.valueOf(conListagem1Int.getLogin()));
        texto3.setText("Data: "+String.valueOf(conListagem1Int.getSexo()));
        texto4.setText("Nome: "+String.valueOf(conListagem1Int.getSenha()));
        return view;
    }

    public void select(int position) {
        this.selected = position;
        notifyDataSetChanged();
    }
}
