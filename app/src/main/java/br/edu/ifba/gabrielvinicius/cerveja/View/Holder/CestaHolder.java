package br.edu.ifba.gabrielvinicius.cerveja.View.Holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import br.edu.ifba.gabrielvinicius.cerveja.R;

public class CestaHolder extends RecyclerView.ViewHolder  {
    public TextView nomeCliente;
    public ImageButton btnEditar;
    public ImageButton btnExcluir;

    public CestaHolder(View itemView) {
        super(itemView);
        nomeCliente = (TextView) itemView.findViewById(R.id.main_line_title);
        btnEditar = (ImageButton) itemView.findViewById(R.id.item_Editar);
        btnExcluir = (ImageButton) itemView.findViewById(R.id.main_line_delete);
    }
}
