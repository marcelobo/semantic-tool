package br.ufjf.ontology.gnosis.ag.genetic;

import br.ufjf.ontology.gnosis.ag.domain.ArrayPesosGranulares;
import java.util.ArrayList;
import java.util.List;

import br.ufjf.ontology.gnosis.ag.domain.Cromossomo;
import br.ufjf.ontology.gnosis.ag.domain.Equacao;
import br.ufjf.ontology.gnosis.ag.domain.IFuncao;
import java.math.BigDecimal;

public class Gerador {

	// Variáveis
	private int numeroDeGenes = 0;
	private int menorNumeroDeGenes = 0;
	private BigDecimal Ez = new BigDecimal(0); // somatório das N funções.
	private double resultadoEquacoes[];

	// Arrays
	private ArrayList<IFuncao> equacoes = new ArrayList<IFuncao>();

	public Gerador(ArrayList<IFuncao> equacoes, double somatorio[]) {

		int numeroDeGenes = 0;
		Equacao aux;

		// Verifica o somatório
		//TODO Fazer isso dentro da Estrutura de Equacoes, para fazer somente 1 vez.
		resultadoEquacoes = somatorio;
		for (int i = 0; i < somatorio.length; i++) {
			Ez = Ez.add(BigDecimal.valueOf(somatorio[i]));
		}

		// Encontra o número de genes necessários nos cromossomos.
		//TODO colocar isso também dentro da estrutura de dados de equacoes!

                Equacao equacao1 = new Equacao();
                equacao1.setFuncao(equacoes.get(0));
                
                menorNumeroDeGenes = equacao1.numeroDeFuncoes();

                for (int i = 0; i < equacoes.size(); i++) {
                        aux = new Equacao(equacoes.get(i));
			if (aux.numeroDeFuncoes() < menorNumeroDeGenes) {
				menorNumeroDeGenes = aux.numeroDeFuncoes();
			}
			if (aux.numeroDeFuncoes() > numeroDeGenes) {
				numeroDeGenes = aux.numeroDeFuncoes();
			}
		}

		setNumeroDeGenes(numeroDeGenes); // pega o nº máximo de genes necessarios, no caso x,y,z .... w
		setEquacoes(equacoes);
	}

	// Gera valores randomicos entre 0 e 1 para a população e a cria.
	public List<Cromossomo> criaPopulacao(int qtd, ArrayPesosGranulares pesos) {
		ArrayList<String> niveis = new ArrayList<String>();
		this.getNiveisFuncoes(equacoes.get(0), "", niveis);

                ArrayList<Cromossomo> popInicial = new ArrayList<Cromossomo>();
		//if (qtd < 2) {
		//	System.out.println("O valor mínimo para a função é 2");
		//	System.exit(0);
		//}
		
		for (int j = 0; j < qtd; j++) 
			popInicial.add(new Cromossomo(getNumeroDeGenes(), pesos, niveis));

		return popInicial;
	}

        /**
         * Esse m�todo retorna o caminho para cada n� da estrutura de fun��es.
         * O caminho � denotado por uma sequ�ncia, onde cada algarismo significa a posi��o do filho no n�vel. A abordagem � parecida com um caminhamento digital na �rvore.
         * @param raiz
         * @param alt
         * @param niveis
         */
        private void getNiveisFuncoes(IFuncao raiz, String alt, List niveis) {

            if (raiz == null) return;

            List<IFuncao> filhos = raiz.getFilhos();
            for(int i=0; filhos!= null && i<filhos.size(); i++)
                getNiveisFuncoes(filhos.get(i), alt+i, niveis);
            
            if (!"".equals(alt)) niveis.add(alt);
        }


	/*
	 * 
	 * ************************************
	 * *********** GETs e SETs ************
	 * ************************************
	 */

	//TODO apagar GETs e SETs que não estão sendo usados (estão como comentários)
	
	public double[] getResultadoEquacoes() { return this.resultadoEquacoes; }
	
	/**
	public ArrayList<double[]> getResultadoEquacao() {
		ArrayList<double[]> aux = new ArrayList<double[]>();
		aux.add(this.resultado);
		return aux;
	}

	public double[] getResultado() {
		return this.resultado;
	}
*/
	public void setEquacoes(ArrayList<IFuncao> eq) {
		this.equacoes = eq;
	}
/**
	public void copiaPopulacaoDePara(ArrayList<Cromossomo> de, ArrayList<Cromossomo> para) {
		for (int i = 0; i < de.size(); i++) {
			para.add(de.get(i));
		}
	}
*/
	public ArrayList<IFuncao> getEquacoes() {
		return this.equacoes;
	}

	public BigDecimal getSomatorioEquacoes() {
		return this.Ez;
	}
/**	
	public double[] getRes() {
		return this.resultado;
	}
*/
	public void setNumeroDeGenes(int numeroDeGenes) {
		this.numeroDeGenes = numeroDeGenes;
	}

	
	public int getNumeroDeGenes() {
		return this.numeroDeGenes;
	}

	/**
	public void setNumeroDeEquacoes(int numeroDeEquacoes) {
		this.numeroDeEquacoes = numeroDeEquacoes;
	}
	public int getNumeroDeEquacoes() {
		return this.numeroDeEquacoes;
	}
*/
	public int getMenorNumeroDeGenes() {
		return this.menorNumeroDeGenes;
	}
/**
	public int getQtosForamMutados() {
		int qtd = 0;
		for (int i = 0; i < this.popCruzada.size(); i++) {
			if (this.popCruzada.get(i).getFoiMutado()) {
				qtd++;
			}
		}
		return qtd;
	}

	public ArrayList<Cromossomo> getPopulacaoFinal() {
		return this.popFinal;
	}
   **/
}
