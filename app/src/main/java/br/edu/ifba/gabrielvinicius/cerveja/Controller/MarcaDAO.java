package br.edu.ifba.gabrielvinicius.cerveja.Controller;

import com.orm.SugarRecord;

import java.util.List;

import br.edu.ifba.gabrielvinicius.cerveja.Model.Cerveja;
import br.edu.ifba.gabrielvinicius.cerveja.Model.Marca;

public class MarcaDAO {
    private static MarcaDAO instance;
    private MarcaDAO(){}
    public static MarcaDAO getInstance(){
        if (instance == null){
            instance = new MarcaDAO();
        }
        return instance;
    }
    public Marca insert (Marca target){
        target.save();
        Marca test = SugarRecord.first(Marca.class);
        return SugarRecord.first(Marca.class) ;
    }
    public Marca update(Marca target){
        return getById(target.update());
    }
    public boolean delete (Marca target){
        SugarRecord.deleteAll(Cerveja.class,"marca = ?",String.valueOf(target.getId()));
        return target.delete();
    }
    public Marca getById(Long id){
        Marca tmp = SugarRecord.findById(Marca.class, id);
        tmp.setList(SugarRecord.find(Cerveja.class,"marca = ?",String.valueOf(id)));
        return tmp;

    }
    public Marca getByNome(String nome){
        List<Marca> list = SugarRecord.find(Marca.class, " nome = ? ", nome);
        if(!list.isEmpty()){
            Marca marca = list.get(0);
            marca.setList(CervejaDAO.getInstance().listPorMarca(marca));
            return marca;
        }
        return null;
        //return  SugarRecord.find(Marca.class," nome = ? ",nome);
    }
    public List<Marca> listAll(){
        return SugarRecord.listAll(Marca.class);
    }
    public List<Marca> listLike(String nome){
        return SugarRecord.find(Marca.class," nome LIKE ? ",nome);
    }
}
