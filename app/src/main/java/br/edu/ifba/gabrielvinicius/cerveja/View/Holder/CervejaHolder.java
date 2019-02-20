package br.edu.ifba.gabrielvinicius.cerveja.View.Holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import br.edu.ifba.gabrielvinicius.cerveja.R;

public class CervejaHolder extends RecyclerView.ViewHolder  {
    public TextView nome;
    public ImageButton btnEditar;
    public ImageButton btnExcluir;

    public CervejaHolder(View itemView) {
        super(itemView);
        nome = (TextView) itemView.findViewById(R.id.main_line_title);
        btnEditar = (ImageButton) itemView.findViewById(R.id.item_Editar);
        btnExcluir = (ImageButton) itemView.findViewById(R.id.main_line_delete);
        


    }
}
