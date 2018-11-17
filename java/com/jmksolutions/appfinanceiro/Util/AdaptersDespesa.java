package com.jmksolutions.appfinanceiro.Util;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jmksolutions.appfinanceiro.Model.DespesaModel;
import com.jmksolutions.appfinanceiro.Model.ReceitaModel;
import com.jmksolutions.appfinanceiro.R;

import java.util.List;

public class AdaptersDespesa  extends BaseAdapter {
    private final List<DespesaModel> ListaDespesa;
    private final Activity act;
    private int selected = -1;

    public AdaptersDespesa(List<DespesaModel> con, Activity act) {
        this.ListaDespesa = con;
        this.act = act;
    }

    @Override
    public int getCount() {
        return ListaDespesa.size();
    }

    @Override
    public Object getItem(int position) {
        return ListaDespesa.get(position);
    }

    @Override
    public long getItemId(int position) {
        DespesaModel conListagem1Int = ListaDespesa.get(position);
        return conListagem1Int.getId_pag();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = act.getLayoutInflater().inflate(R.layout.layout_listview, parent, false);
        DespesaModel conListagem1Int = ListaDespesa.get(position);

        //pegando as referências das Views
        TextView texto1 = (TextView)view.findViewById(R.id.txtListPer1View1);
        TextView texto2 = (TextView)view.findViewById(R.id.txtListPer1View2);
        TextView texto3 = (TextView)view.findViewById(R.id.txtListPer1View3);
        TextView texto4 = (TextView)view.findViewById(R.id.txtListPer1View4);


        if(selected != -1 && position == selected) {
            view.setBackgroundColor(Color.argb(180, 83, 131, 233));
        }

        //populando as Views

        //texto1.setText("Descrição: "+String.valueOf(conListagem1Int.getdesc_rec()));
        texto1.setText("                    DATA: "+String.valueOf(conListagem1Int.getDt_desp()));
        texto2.setText("  VALOR:  "+String.valueOf(conListagem1Int.getVl_pag()));
        //texto3.setText("  Data: "+String.valueOf(conListagem1Int.getDt_rec()));
        texto3.setText("  CONTA:  "+String.valueOf(conListagem1Int.getdesc_conta()));
        texto4.setText("  DESCRIÇÃO:  "+String.valueOf(conListagem1Int.getdesc_pag()));
        return view;
    }

    public void select(int position) {
        this.selected = position;
        notifyDataSetChanged();
    }
}
