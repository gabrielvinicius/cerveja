package br.edu.ifba.gabrielvinicius.cerveja.Controller;

import com.orm.SugarRecord;

import java.util.List;

import br.edu.ifba.gabrielvinicius.cerveja.Model.Cesta;
import br.edu.ifba.gabrielvinicius.cerveja.Model.Item;

public class ItemDAO {
    private static ItemDAO instance;
    private ItemDAO(){}
    public static ItemDAO getInstance(){
        if (instance == null){
            instance = new ItemDAO();
        }
        return instance;
    }
    public Item insert (Item target){
        return getById(target.save());
    }
    public Item update(Item target){
        return getById(target.update());
    }
    public boolean delete (Item target){
        return target.delete();
    }
    public Item getById(Long id){
        return SugarRecord.findById(Item.class,id);
    }
    public List<Item> listByCesta(Cesta cesta){
        return Item.find(Item.class,"cesta = ? ORDER BY preco_por_litro ASC",String.valueOf(cesta.getId()));
    }
    public List<Item> listAll(){
        return SugarRecord.listAll(Item.class);
    }
    public List<Item> listSort(Cesta cesta) {
        return Item.find(Item.class, "cesta = ? ORDER BY preco_por_litro ASC",String.valueOf(cesta.getId()));
    }
}
