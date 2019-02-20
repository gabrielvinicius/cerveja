package br.edu.ifba.gabrielvinicius.cerveja.View.Holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import br.edu.ifba.gabrielvinicius.cerveja.R;

public class ItemHolder extends RecyclerView.ViewHolder  {
    public TextView supermercado;
    public TextView cerveja;
    public TextView preco;
    public TextView precoPorLitro;
    public ImageButton btnEditar;
    public ImageButton btnExcluir;

    public ItemHolder(View itemView) {
        super(itemView);
        supermercado = (TextView) itemView.findViewById(R.id.item_SuperMercado_View);
        cerveja = (TextView) itemView.findViewById(R.id.item_Marca_Cerveja_Volume_View);
        preco = (TextView) itemView.findViewById(R.id.item_Preço_View);
        precoPorLitro = (TextView) itemView.findViewById(R.id.item_Preço_Por_Litro_View);
        btnEditar = (ImageButton) itemView.findViewById(R.id.item_Editar);
        btnExcluir = (ImageButton) itemView.findViewById(R.id.item_Deletar);
    }
}
