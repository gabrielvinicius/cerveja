package br.edu.ifba.gabrielvinicius.cerveja.View.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.orm.SugarRecord;

import java.util.List;

import br.edu.ifba.gabrielvinicius.cerveja.Controller.CestaDAO;
import br.edu.ifba.gabrielvinicius.cerveja.Model.Cesta;
import br.edu.ifba.gabrielvinicius.cerveja.R;
import br.edu.ifba.gabrielvinicius.cerveja.View.Holder.CestaHolder;

public class CestaAdapter extends RecyclerView.Adapter<CestaHolder>{

    private  List<Cesta> list;


    public CestaAdapter(List<Cesta> clientes) {
        this.list = clientes;
    }

    @Override
    public CestaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       final CestaHolder holder = new CestaHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_line_view, parent, false));

        holder.btnEditar.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.btnExcluir.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {

                final View view = v;
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Confirmação")
                        .setMessage("Tem certeza que deseja excluir este cliente?")
                        .setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                remove(list.get(holder.getAdapterPosition()));
                            }
                        })
                        .setNegativeButton("Cancelar", null)
                        .create()
                        .show();
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.get(holder.getAdapterPosition());
                RecyclerView mRecyclerView = getActivity(v).findViewById(R.id.recyclerView);
                mRecyclerView.removeAllViews();
                Cesta cesta = list.get(holder.getAdapterPosition());
                ItemAdapter adapter = new ItemAdapter(cesta);
                mRecyclerView.setAdapter(adapter);

            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(CestaHolder holder, int position) {
        holder.nomeCliente.setText(list.get(position).getData().toString());
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {

        FloatingActionButton fab = (FloatingActionButton) getActivity(recyclerView).findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecyclerView mRecyclerView = getActivity(v).findViewById(R.id.recyclerView);

                Cesta cesta = CestaDAO.getInstance().insert(new Cesta());

                adiciona(cesta);

            }
        });


        super.onAttachedToRecyclerView(recyclerView);
    }

    public void setList(List<Cesta> cestas) {
        this.list = cestas;
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    private Activity getActivity(View view) {
        Context context = view.getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity)context;
            }
            context = ((ContextWrapper)context).getBaseContext();
        }
        return null;
    }
    public void adiciona(SugarRecord target){

        list.add((((Cesta) target)));
        notifyItemInserted(getItemCount());
    }
    public void atualiza(SugarRecord target){
        list.set(list.indexOf((Cesta) target), CestaDAO.getInstance().update((Cesta) target));
        notifyItemChanged(list.indexOf((Cesta) target));
    }

    public void remove(SugarRecord target){
      CestaDAO.getInstance().delete((Cesta) target);
        int position = list.indexOf((Cesta) target);
        list.remove(position);
        notifyItemRemoved(position);
    }

}
