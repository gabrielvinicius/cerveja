package br.edu.ifba.gabrielvinicius.cerveja.View.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.orm.SugarRecord;

import java.util.List;

import br.edu.ifba.gabrielvinicius.cerveja.Controller.CervejaDAO;
import br.edu.ifba.gabrielvinicius.cerveja.Model.Cerveja;
import br.edu.ifba.gabrielvinicius.cerveja.R;
import br.edu.ifba.gabrielvinicius.cerveja.View.CervejaFragment;
import br.edu.ifba.gabrielvinicius.cerveja.View.Holder.CervejaHolder;

public class CervejaAdapter extends RecyclerView.Adapter<CervejaHolder>{
private  List<Cerveja> list;



public CervejaAdapter (List<Cerveja> list){
    this.list = list;
}
    @NonNull
    @Override
    public CervejaHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        final CervejaHolder holder = new CervejaHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_line_view, parent, false));

        holder.btnEditar.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.FragmentTransaction fragmentTransaction = ((FragmentActivity) getActivity(v)).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.fragment_content,new CervejaFragment(list.get(holder.getAdapterPosition())));
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
        return  holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CervejaHolder holder, int position) {
        Cerveja cerveja = list.get(holder.getPosition());
    holder.nome.setText("Marca: "+cerveja.getMarca().getNome()+" Nome: "+cerveja.getNome());
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {

        FloatingActionButton fab = (FloatingActionButton) getActivity(recyclerView).findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.FragmentTransaction fragmentTransaction = ((FragmentActivity) getActivity(v)).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.fragment_content,new CervejaFragment());
                getActivity(v).findViewById(R.id.recyclerView).setVisibility(View.INVISIBLE);
                fragmentTransaction.commit();
            }
        });
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void setList(List<Cerveja> list) {
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

    public void adiciona(SugarRecord target){

        list.add(CervejaDAO.getInstance().insert((Cerveja) target));
        notifyItemInserted(getItemCount());
    }
    public void atualiza(SugarRecord target){
        list.set(list.indexOf((Cerveja) target), CervejaDAO.getInstance().update((Cerveja) target));
         notifyItemChanged(list.indexOf((Cerveja) target));
    }

    public void remove(SugarRecord target){
        CervejaDAO.getInstance().delete((Cerveja) target);
        int position = list.indexOf((Cerveja) target);
        list.remove(position);
        notifyItemRemoved(position);
    }



}
