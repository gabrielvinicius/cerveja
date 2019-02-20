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

import br.edu.ifba.gabrielvinicius.cerveja.Controller.CestaDAO;
import br.edu.ifba.gabrielvinicius.cerveja.Controller.ItemDAO;
import br.edu.ifba.gabrielvinicius.cerveja.Model.Cerveja;
import br.edu.ifba.gabrielvinicius.cerveja.Model.Cesta;
import br.edu.ifba.gabrielvinicius.cerveja.Model.Item;
import br.edu.ifba.gabrielvinicius.cerveja.Model.Volume;
import br.edu.ifba.gabrielvinicius.cerveja.R;
import br.edu.ifba.gabrielvinicius.cerveja.View.Holder.ItemHolder;
import br.edu.ifba.gabrielvinicius.cerveja.View.ItemFragment;

public class ItemAdapter extends RecyclerView.Adapter<ItemHolder> {
    private List<Item> list;
    private Cesta cesta;

    public ItemAdapter(Cesta cesta) {
        this.cesta = CestaDAO.getInstance().getById(cesta.getId());

        list = this.cesta.getListItens();
    }

    public Cesta getCesta() {
        return cesta;
    }

    public void setCesta(Cesta cesta) {
        this.cesta = CestaDAO.getInstance().getById(cesta.getId());
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        final ItemHolder holder = new ItemHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_line_item_view, parent, false));

        holder.btnEditar.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.FragmentTransaction fragmentTransaction = ((FragmentActivity) getActivity(v)).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.fragment_content, new ItemFragment(list.get(holder.getAdapterPosition())));
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

        return holder;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        FloatingActionButton fab = (FloatingActionButton) getActivity(recyclerView).findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((RecyclerView) getActivity(v).findViewById(R.id.recyclerView)).setVisibility(View.INVISIBLE);
                FragmentTransaction fragmentTransaction = ((FragmentActivity) getActivity(v)).getSupportFragmentManager().beginTransaction();
                Item item = new Item();
                item.setCesta(cesta);
                fragmentTransaction.add(R.id.fragment_content, new ItemFragment(item));
                fragmentTransaction.commit();

            }
        });

        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder itemHolder, int i) {
        Cerveja cerveja = list.get(i).getCerveja();
        itemHolder.supermercado.setText(list.get(i).getSuperMercado().getNome());
        Float preco = list.get(i).getPreco();
        Volume volume = list.get(i).getVolume();
        Float precoPorMl = (preco / volume.getVolume()) * 1000;
        itemHolder.cerveja.setText(String.format("%s %s %s", cerveja.getMarca().getNome(), cerveja.getNome(), volume.getNome()));
        itemHolder.preco.setText(String.format("R$ %s", String.valueOf(preco)));
        itemHolder.precoPorLitro.setText(String.format("R$ %s", String.valueOf(precoPorMl)));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void adiciona(Item target) {

        ItemDAO.getInstance().insert(target);
        list.clear();
        list.addAll(ItemDAO.getInstance().listByCesta(cesta));

        notifyDataSetChanged();
    }

    private Activity getActivity(View view) {
        Context context = view.getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }

    public void remove(SugarRecord target) {
        ItemDAO.getInstance().delete((Item) target);
        int position = list.indexOf((Item) target);
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void atualiza(SugarRecord target) {
        ItemDAO.getInstance().update((Item)target);
        list.clear();
        list.addAll(ItemDAO.getInstance().listByCesta(cesta));
        notifyDataSetChanged();
    }




}
