package br.edu.ifba.gabrielvinicius.cerveja.View.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.orm.SugarRecord;

import java.util.List;

import br.edu.ifba.gabrielvinicius.cerveja.Controller.SuperMercadoDAO;
import br.edu.ifba.gabrielvinicius.cerveja.Model.SuperMercado;
import br.edu.ifba.gabrielvinicius.cerveja.R;
import br.edu.ifba.gabrielvinicius.cerveja.View.Holder.SuperMercadoHolder;
import br.edu.ifba.gabrielvinicius.cerveja.View.SuperMercadoFragment;

public class SuperMercadoAdapter extends RecyclerView.Adapter<SuperMercadoHolder>{
private  List<SuperMercado> list;
private int position_;
public SuperMercadoAdapter (List<SuperMercado> list){
        this.list = list;
        }



    @NonNull
@Override
public SuperMercadoHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

    final SuperMercadoHolder holder = new SuperMercadoHolder(LayoutInflater.from(parent.getContext())
            .inflate(R.layout.main_line_view, parent, false));
    holder.btnEditar.setOnClickListener(new ImageButton.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentTransaction fragmentTransaction = ((FragmentActivity) getActivity(v)).getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.fragment_content,new SuperMercadoFragment(list.get(holder.getAdapterPosition())));
            getActivity(v).findViewById(R.id.recyclerView).setVisibility(View.INVISIBLE);
            fragmentTransaction.commit();
        }
    });


    holder.btnExcluir.setOnClickListener(new ImageButton.OnClickListener() {

        @Override
        public void onClick(View v) {
            final View view = v;
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setTitle("Confirmação")
                    .setMessage("Tem certeza que deseja excluir ?")
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
    return holder;
}

@Override
public void onBindViewHolder(@NonNull SuperMercadoHolder holder, int position) {

        holder.nome.setText(list.get(position).getNome());


}

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        FloatingActionButton fab = (FloatingActionButton) getActivity(recyclerView).findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity(v).findViewById(R.id.recyclerView).setVisibility(View.INVISIBLE);
                FragmentTransaction fragmentTransaction = ((FragmentActivity) getActivity(v)).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.fragment_content,new SuperMercadoFragment());
                fragmentTransaction.commit();
            }
        });
                super.onAttachedToRecyclerView(recyclerView);
    }

    public void setList(List<SuperMercado> list) {
                this.list = list;
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
        
    
    public void remove(SugarRecord target){
        SuperMercadoDAO.getInstance().delete((SuperMercado) target);
        int position= list.indexOf((SuperMercado) target);
        list.remove(position);
        notifyItemRemoved(position);
    }
    public void adiciona(SugarRecord target){
        list.add(SuperMercadoDAO.getInstance().insert((SuperMercado) target));
        notifyItemInserted(getItemCount());
    }
    public void atualiza(SugarRecord target){
        list.set(list.indexOf((SuperMercado) target), SuperMercadoDAO.getInstance().update((SuperMercado) target));
        notifyItemChanged(list.indexOf((SuperMercado) target));
    }
}
