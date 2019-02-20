package br.edu.ifba.gabrielvinicius.cerveja.Controller;

import com.orm.SugarRecord;

import java.util.List;

import br.edu.ifba.gabrielvinicius.cerveja.Model.Cesta;

public class CestaDAO {
    private static CestaDAO instance;
    private CestaDAO(){}
    public static CestaDAO getInstance(){
        if (instance == null){
            instance = new CestaDAO();
        }
        return instance;
    }
    public Cesta insert (Cesta target){

        if(!target.getListItens().isEmpty()){
            SugarRecord.saveInTx(target.getListItens());
        }
        return getById(target.save());
    }
    public Cesta update(Cesta target){
        SugarRecord.updateInTx(target.getListItens());
        return getById(target.save());
    }
    public boolean delete (Cesta target){
        SugarRecord.deleteInTx(target.getListItens());
        return target.delete();
    }
    public Cesta getById(Long id){
        Cesta cesta = SugarRecord.findById(Cesta.class,id);

        cesta.setListItens(ItemDAO.getInstance().listSort(cesta));
        return cesta;
    }
    public List<Cesta> listAll(){
        return SugarRecord.listAll(Cesta.class);
    }

}
