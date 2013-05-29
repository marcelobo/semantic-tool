/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufjf.ontology.gnosis.ag.util;

import br.ufjf.ontology.gnosis.ag.domain.Cromossomo;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rooke
 */
public class Serializa {

    public static void serializaPopulacao(List<Cromossomo> cromossomos) {
        //Gera o arquivo para armazenar o objeto
        FileOutputStream arquivoGrav;
        try {
            arquivoGrav = new FileOutputStream("populacao.dat");

            //Classe responsavel por inserir os objetos
            ObjectOutputStream objGravar = new ObjectOutputStream(arquivoGrav);

            //Grava o objeto no arquivo
            objGravar.writeObject(cromossomos);

            objGravar.flush();

            objGravar.close();

            arquivoGrav.flush();

            arquivoGrav.close();

        } catch (Exception ex) {
            Logger.getLogger(Serializa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static List<Cromossomo> desserializaPopulacao() {

        //arquivo onde estao os dados serializados
        FileInputStream arqLeitura;
        ArrayList<Cromossomo> lista = null;
        try {
            arqLeitura = new FileInputStream("populacao.dat");

            //objeto que vai ler os dados do arquivo
            ObjectInputStream in = new ObjectInputStream(arqLeitura);

            //recupera o array list de Musicas
            lista = (ArrayList<Cromossomo>) in.readObject();


            arqLeitura.close();
            in.close();

        } catch (Exception ex) {
            Logger.getLogger(Serializa.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lista;

    }
}
