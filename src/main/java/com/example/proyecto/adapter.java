package com.example.proyecto;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.proyecto.R;

import java.util.List;

public class adapter extends ArrayAdapter<String> {

    private Context context;
    private List<String> dataList;

    public adapter(Context context, List<String> dataList) {
        super(context, R.layout.listaitem, dataList);
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.listaitem, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textViewItem = convertView.findViewById(R.id.textViewListItem);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final String item = dataList.get(position);
        viewHolder.textViewItem.setText(item);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí maneja el clic en el elemento de la lista
                // Puedes iniciar una nueva actividad o realizar otras acciones según tus necesidades
                // Ejemplo: iniciar una nueva actividad
                Intent intent = new Intent(context, mostrardetalles.class);
                intent.putExtra("selectedItem", item); // Pasa datos adicionales si es necesario
                context.startActivity(intent);
            }
        });

        return convertView;
    }

    static class ViewHolder {
        TextView textViewItem;
    }
}
