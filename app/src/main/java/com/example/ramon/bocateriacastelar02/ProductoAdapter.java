package com.example.ramon.bocateriacastelar02;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;
import dto.Producto;

/**
 * Created by RAMON on 12/01/2015.
 */
public class ProductoAdapter extends BaseAdapter {

    private Activity activity;
    private List<Producto> items;

    public ProductoAdapter(Activity activity, List<Producto> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int posicion) {
        return items.get(posicion);
    }

    @Override
    public long getItemId(int posicion) {
        return items.get(posicion).getId();
    }

    @Override
    public View getView(int posicion, View converView, ViewGroup parent) {
        View vi=converView;

        if(vi==null){
            LayoutInflater inflater=(LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            vi=inflater.inflate(R.layout.producto,null);


        }
        Producto item=items.get(posicion);

        ImageView foto=(ImageView) vi.findViewById(R.id.foto);
        int imageResource=activity.getResources().getIdentifier("drawable/"+item.getNombreFoto(),null,activity.getPackageName());
        foto.setImageDrawable(activity.getResources().getDrawable(imageResource));

        TextView tvDescripcion=(TextView) vi.findViewById(R.id.descripcion);
        tvDescripcion.setText(item.getNombre());
        TextView tvPrecio=(TextView)vi.findViewById(R.id.precio);
        tvPrecio.setText(Double.toString(item.getPvp()));


        return vi;
    }
}
