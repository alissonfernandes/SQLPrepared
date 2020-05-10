
/**
 * @author Alisson F. Moreira
 * @version 1.0
 * 
 * Class: SQLPrepared
 * Classe responsável por gerar uma uma linguagem de consulta estruturada(SQL) para lidar com banco de dados relacional.
 * ATENÇÃO: esta classe não faz nenhuma consulta diretamente ao banco de dados, ela somente preparada a Linguagem de Consulta 
 * Estruturada(SQL) e retorna uma String de acordo com cada função.
 */
package prepared.sql;

import java.util.Map;

public class SQLPrepared {
    
    private String tableName = "", prefix="";
    
    /**
     *
     * @param tableName Nome da tabela do Banco de Dados
     * @param prefix Prefixo do nome da tabela do Banco de Dados
     */
    public SQLPrepared(String tableName, String prefix){
        this.tableName = tableName;
        this.prefix = prefix;
    }
    
    /**
     *
     * @param tableName Nome da tabela do Banco de Dados
     */
    public SQLPrepared(String tableName){
        this.tableName = tableName;
    }
    
    /**
     *
     * @param data Contem os nomes das colunas e respectivamente os dados que serão incluidas no banco de dados.
     * @return Retorna uma SQL que permite inserir dados no Banco de Dados.
     */
    public String insert(Map data){
        // Retirar chaves ( "["  "]" ) em keys e values
        String keysSQL = (data.keySet().toString().replace("[", "")).replace("]", "");
        String values = (data.values().toString().replace("[", "")).replace("]", "").replace("", "");
        
        // Coloca aspas simples entres os valores
        String valuesSQL = "'" + values.replace(", ", "', '") + "'";
        
        
        String sql = "INSERT INTO " + getTableName() + "(" + keysSQL + ") VALUES("+ valuesSQL +")";
        
        return sql;
    }
    
    /**
     *
     * @param columns Nomes das colunas do Banco de Dados, separada por virgula, a serem selecionados.
     * @param where Cláusula para filtar dados em um comando SQL.Essa cláusula deve ser seguida por uma expressão lógica.
     * @return Retorna uma SQL que permite buscar os dados no Bando de Dados;
     */
    public String select(String columns, String where){
        String sql = null;
        
        if(where.isEmpty()){
            sql = "SELECT " + columns + " FROM " + getTableName() ;
        }else{
            sql = "SELECT " + columns + " FROM " + getTableName() + " " + where;
        }
        
        return sql;
    }
    
    /**
     *
     * @param columns Nomes das colunas do Banco de Dados, separada por virgula, a serem selecionados.
     * @return Retorna uma SQL que permite buscar os dados no Bando de Dados.
     */
    public String select(String columns){
         return select(columns, "");
    }
    
    /**
     *
     * @return Retorna uma SQL que permite buscar os dados do Bando de Dados.
     */
    public String select(){
        return select("*", "");
    }
    
    /**
     *
     * @param data nomes das colunas do Banco de Dados e respectivamente os dados que serão atualizados no banco de dados.
     * @param where Cláusula para filtar os dados a serem atualizados no Banco de Dados. Essa cláusula deve ser seguida por uma expressão lógica.
     * @return Retorna uma SQL que permite atualizar os dados no Bando de Dados.
     */
    public String update(Map data, String where){
        
        String set = "";
        String sql = null;
        
         // Retirar chaves e espaços( "["  "]" " ") das keys e das values
        String keys = data.keySet().toString().replace("[", "").replace("]", "").replace(" ", "");
        String values = data.values().toString().replace("[", "").replace("]", "").replace("", "");
        
        // Cria duas arrays
        String[] keysSQL = keys.split(",");
        String[] valuesSQL = values.split(", ");
        
        // Prepara o SET
        for(int i=0; i<data.size(); i++){
           
            if(!(i== data.size()-1)){
              set += keysSQL[i] + "='" + valuesSQL[i] + "', ";
            }else{
                set += keysSQL[i] + "='" + valuesSQL[i] + "'";
            }
           
        }
        
        if(where.isEmpty()){
            sql = "UPDATE " + getTableName() + " SET " + set;
        }else{
            sql = "UPDATE " + getTableName() + " SET " + set + " " + where;
        }
        
        return sql;
    }
    
    /**
     *
     * @param where Cláusula para filtar os dados a serem excluídos no Banco de Dados. Essa cláusula deve ser seguida por uma expressão lógica.
     * @return Retorna uma SQL que permite deletar os dados no Bando de Dados.
     */
    public String delete(String where){
        
        String sql = "DELETE FROM " + getTableName() + " " + where;
        
        return sql;
    }
    
    // Retorna nome da tabela do database com o prefixo
    private String getTableName(){
        String tableName = null;
       
        if(!this.prefix.isEmpty()){
            return (this.prefix + "_" + this.tableName);
        }else{
           return this.tableName;
        }
    }
}
