package br.ufjf.ontology.gnosis.ag.genetic;

import java.util.List;
import java.util.Random;

import br.ufjf.ontology.gnosis.ag.domain.Cromossomo;
import br.ufjf.ontology.gnosis.ag.domain.Equacao;
import br.ufjf.ontology.gnosis.ag.domain.IFuncao;
import br.ufjf.ontology.gnosis.ag.domain.Gene;
import java.math.BigDecimal;

public class Operadores {

	/**
	 * Realiza reproducao com ponto de cruzamento √∫nico. 
	 * Pega 50% de cada um dos pais. Se tiver um n√∫mero √≠mpar de cromossomos (3 por exemplo, pega 2 de um e 1 de outro);
	 *  
	 * @param candidato1
	 * @param candidato2
	 * @return filho gerado
	 */
	public static Cromossomo reproducaoPontoCruzamento(Cromossomo candidato1, Cromossomo candidato2, int numGenes) {

		Cromossomo filho = new Cromossomo();
		filho.setNumeroDeGenes(numGenes);
		int res = numGenes % 2;
		int div = numGenes / 2;

		// numero par de genes
		if (res == 0) {

			for (int k = 0; k < (div); k++)
                                //Ent„o parece que essa È a parte critica!!!
                                //O gene do pai È passado como referencia!!!!
				//filho.addGene(candidato1.getGene(k));

                                filho.addGene(new Gene(candidato1.getGene(k).getValor()));

			for (int l = div; l < numGenes; l++) 
				//filho.addGene(candidato2.getGene(l));
                                filho.addGene(new Gene(candidato2.getGene(l).getValor()));

		// numero √≠mpar de genes
		} else {

			for (int k = 0; k < (div + 1); k++)
				//filho.addGene(candidato1.getGene(k));
                                filho.addGene(new Gene(candidato1.getGene(k).getValor()));

			for (int l = div + 1; l < numGenes; l++) 
				//filho.addGene(candidato2.getGene(l));
                                filho.addGene(new Gene(candidato2.getGene(l).getValor()));
		}
		
		return filho;
	}
	
	
	/**
	 * TODO fazer uma estrutura para guardar as equacoes e seus resultados!
	 *
	 * Realiza a soma dos genes em relacao as equacoes de entrada
	 * @param c
	 * @param equacoes
	 * @return
	 */
	public static BigDecimal avaliaCromossomo(Cromossomo c, List<IFuncao> equacoes, double resultadoEquacoes[]) {
		
		BigDecimal soma = new BigDecimal(0);
		Equacao equacaoAux;
		
		for (int i = 0; i < equacoes.size(); i++) {
			BigDecimal somaAux = new BigDecimal(0);
			equacaoAux = new Equacao(equacoes.get(i));

                        //Passa os pesos para a EquaÁ„o
			equacaoAux.setPesos(c);

                        //calcula o resultado da funÁ„o com os pesos aplicados
                        somaAux = equacoes.get(i).calcula();
			
			// Adiciona o valor do resultado da equa√ß√£o i no cromossomo.
			c.addResultado(somaAux);

			// Calcula a diferen√ßa da equa√ß√£o.
			c.setDiferencaEquacoes(somaAux.subtract(BigDecimal.valueOf(resultadoEquacoes[i])).abs());

			// Adiciona no somat√≥rio o valor encontrado na educa√ß√£o.
			soma = soma.add(somaAux);
		}

                //agora eu que eu ja fiz o calculo das diferenÁas eu posso colocar que eu ja fiz esse calculo no cromossomo
                c.setCalculado(true);

		return soma;
		
	}

        /**
         * A mutaÁ„o sempre soma a granularidade na posiÁ„o escolhida
         * Caso o gene possua valor maior que 1, subtraia
         *
         **/
	public static void mutacao(Cromossomo c, int num, double granularidade) {

		Random r2 = new Random();
                Gene g = null;
                while (g==null || g.getValor() == 1) {
                    g = c.getGene(r2.nextInt(num));
                }

                if (g.getValor() < 1)
                    g.addValor(granularidade);
                else
                    g.subtValor(granularidade);
	}
	
	
}
