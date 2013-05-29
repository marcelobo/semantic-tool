package br.ufjf.ontology.gnosis.ag;

import br.ufjf.ontology.gnosis.ag.domain.Cromossomo;
import br.ufjf.ontology.gnosis.ag.domain.FuncaoComposta;
import br.ufjf.ontology.gnosis.ag.domain.FuncaoSimples;
import br.ufjf.ontology.gnosis.ag.domain.IFuncao;
import br.ufjf.ontology.gnosis.ag.genetic.Gerador;
import br.ufjf.ontology.gnosis.ag.genetic.SimulaVida;
import br.ufjf.ontology.gnosis.ag.view.Printer;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.configuration.ConfigurationException;

public class Principal {

    public static void main(String args[]) {

        ArrayList<IFuncao> equacoes = new ArrayList<IFuncao>();

/*
        final FuncaoComposta fc1 = new FuncaoComposta();
        fc1.add(new FuncaoSimples(0.5));
        fc1.add(new FuncaoSimples(0.1));
        fc1.add(new FuncaoSimples(0.2));

        final FuncaoComposta fc2 = new FuncaoComposta();
        fc2.add(new FuncaoSimples(0.6));
        fc2.add(new FuncaoSimples(0.8));
        fc2.add(new FuncaoSimples(0.1));

        final FuncaoComposta fc3 = new FuncaoComposta();
        fc3.add(new FuncaoSimples(0.1));
        fc3.add(new FuncaoSimples(0.5));
        fc3.add(new FuncaoSimples(0.8));

        equacoes.add(fc1);
        equacoes.add(fc2);
        equacoes.add(fc3);
*/
        //Valor da soma de cada equaÃ§Ã£o em ordem 1Âª, 2Âª, 3Âª etc...
        final double resultadoDasEquacoes[] = {1, 1, 1};


        
        //Função K1 (fc3)
        final FuncaoSimples f1 = new FuncaoSimples(0.4);
        final FuncaoSimples f2 = new FuncaoSimples(0.1);
        final FuncaoSimples f3 = new FuncaoSimples(0.2);
        final FuncaoComposta fc1 = new FuncaoComposta();
        fc1.add(f1);
        fc1.add(f2);
        fc1.add(f3);

        final FuncaoSimples f4 = new FuncaoSimples(0.1);
        final FuncaoComposta fc2 = new FuncaoComposta();
        fc2.add(fc1);
        fc2.add(f4);

        final FuncaoSimples f5 = new FuncaoSimples(0.2);
        final FuncaoSimples f6 = new FuncaoSimples(0.4);
        final FuncaoComposta fc3 = new FuncaoComposta();
        fc3.add(f5);
        fc3.add(f6);
        fc3.add(fc2);

        //Função K2 (fc32)
        final FuncaoSimples f12 = new FuncaoSimples(0.3);
        final FuncaoSimples f22 = new FuncaoSimples(0.2);
        final FuncaoSimples f32 = new FuncaoSimples(0.4);
        final FuncaoComposta fc12 = new FuncaoComposta();
        fc12.add(f12);
        fc12.add(f22);
        fc12.add(f32);

        final FuncaoSimples f42 = new FuncaoSimples(0.7);
        final FuncaoComposta fc22 = new FuncaoComposta();
        fc22.add(fc12);
        fc22.add(f42);

        final FuncaoSimples f52 = new FuncaoSimples(0.1);
        final FuncaoSimples f62 = new FuncaoSimples(0.5);
        final FuncaoComposta fc32 = new FuncaoComposta();
        fc32.add(f52);
        fc32.add(f62);
        fc32.add(fc22);

        //Função K3 (fc33)
        final FuncaoSimples f13 = new FuncaoSimples(0.5);
        final FuncaoSimples f23 = new FuncaoSimples(0.1);
        final FuncaoSimples f33 = new FuncaoSimples(0.1);
        final FuncaoComposta fc13 = new FuncaoComposta();
        fc13.add(f13);
        fc13.add(f23);
        fc13.add(f33);

        final FuncaoSimples f43 = new FuncaoSimples(0.4);
        final FuncaoComposta fc23 = new FuncaoComposta();
        fc23.add(fc13);
        fc23.add(f43);

        final FuncaoSimples f53 = new FuncaoSimples(0.7);
        final FuncaoSimples f63 = new FuncaoSimples(0.2);
        final FuncaoComposta fc33 = new FuncaoComposta();
        fc33.add(f53);
        fc33.add(f63);
        fc33.add(fc23);

        equacoes.add(fc3);
        equacoes.add(fc32);
        equacoes.add(fc33);





        SimulaVida sv;
        try {
            long inicio = Calendar.getInstance().getTimeInMillis();
           // sv = new SimulaVida(new Gerador(equacoes, resultadoDasEquacoes), "genetics.properties");
            //sv.run();
           // long fim = Calendar.getInstance().getTimeInMillis();
            
            
            Cromossomo vencedor = null;
            
            for (int i=0; i<1; i++) {
                sv = new SimulaVida(new Gerador(equacoes, resultadoDasEquacoes), "genetics.properties");
                Cromossomo aux = sv.runGRASP2();
                if (vencedor == null)
                    vencedor = aux;
                else 
                    vencedor = vencedor.getDiferenca().compareTo(aux.getDiferenca()) < 0  ? vencedor : aux;
            }
            
            long fim = Calendar.getInstance().getTimeInMillis();
            long dif = fim-inicio;
            System.out.println(String.format("%02d segundos e %02d milisegundos.", dif/60, dif%60));
            Printer.imprimeFim(vencedor);
 
            
        } catch (ConfigurationException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }




        /*

        //Apagar esse trecho (sÃ³ para testes)

        for(int contador=0;contador < Gfinal.getPopulacaoFinal().size();contador++) {
        System.out.println("Cromossomo " + contador);
        Cromossomo zezim = Gfinal.getPopulacaoFinal().get(contador);
        zezim.mostraTodosResultados();
        }


        System.out.print("Foram Mutados: " + Gfinal.getQtosForamMutados());
        System.out.print(" cromossomos. ");
        System.out.println("Quantidade de geraÃ§Ãµes " + (i));
         */

    }
}
