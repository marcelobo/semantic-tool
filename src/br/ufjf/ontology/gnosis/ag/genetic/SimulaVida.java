package br.ufjf.ontology.gnosis.ag.genetic;

import br.ufjf.ontology.gnosis.ag.domain.ArrayPesosGranulares;
import br.ufjf.ontology.gnosis.ag.domain.ComparatorCromossomo;
import br.ufjf.ontology.gnosis.ag.domain.Cromossomo;
import br.ufjf.ontology.gnosis.ag.domain.Equacao;
import br.ufjf.ontology.gnosis.ag.view.Printer;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class SimulaVida {

	private List<Cromossomo> populacao;
	private Configuration parametros;
	private Gerador gerador;
        private Cromossomo _vencedor;
        private ArrayPesosGranulares pesos;
        private int buscaLocal;
        
	//Vari√°veis para debugTela.
	private int __contMutacao = 0;
	private int __contReproducao = 0;
        private int __contAvaliacoes = 0;

	public SimulaVida(Gerador g, String parametro) throws ConfigurationException  {
		this.gerador = g;
		this.parametros = new PropertiesConfiguration(parametro);
                this._vencedor = null;

		//Cria a popula√ß√£o inicial;
                this.pesos = new ArrayPesosGranulares(this.parametros.getDouble("ag.granularidade"));
                List<Cromossomo> populacaoAux = this.gerador.criaPopulacao(this.parametros.getInt("ag.populacao.inicial"), pesos);

                //this.populacao = Serializa.desserializaPopulacao();
                this.populacao = populacaoAux;

		//Seta n√≠vel de informa√ß√£o que ser√° exibida no console
		Printer.debugTela = this.parametros.getInt("ag.debug.level");
		Printer.debugArquivo = this.parametros.getInt("ag.debug.log");

		//Seta tipo de compara√ß√£o que ser√° feita entre os cromossomos
		ComparatorCromossomo.tipoComparacao = this.parametros.getInt("ag.tipo.comparacao");

                //Seta de quanto em quanto tempo ser· realizada a busca local
                this.buscaLocal = this.parametros.getInt("ag.buscalocal.periodicidade");

	}
	

	public Cromossomo runGRASP2() {
		//Printer.imprimeInicio(this.parametros.getInt("ag.populacao.inicial"), this.parametros.getInt("ag.geracoes"));

                Cromossomo vencedorAntigo = null; 
                int nGer=0;

		do {
                        vencedorAntigo = this.populacao.get(0);
        		Printer.imprimeInformacoesInicioGeracao(++nGer);
                	BigDecimal fitnessTotal = avaliaPopulacao();
                        fazDiversificacaoIntensificacao();
			this._vencedor = this.populacao.get(0);

			Printer.imprimeFim(this.populacao.get(0));
		} while (vencedorAntigo != this._vencedor);
		
                
                
		System.out.println("N˙mero de geraÁıes utilizadas no GRASP: " + nGer);

                //retorna a equacoes j· alteradas
                Equacao.getEquacoesComPesos(gerador.getEquacoes(), this.populacao.get(0));

                return this.populacao.get(0);
	}
	
	public Cromossomo runGRASP() {
        	Printer.imprimeInformacoesInicioGeracao(1);
		BigDecimal fitnessTotal = avaliaPopulacao();
                fazDiversificacaoIntensificacao();
		this._vencedor = this.populacao.get(0);
		Printer.imprimeInformacoesFimGeracao(
				this.populacao.size(), 
				0, 
				this.__contMutacao, 
				this.__contReproducao, 
				fitnessTotal.doubleValue(),
				this._vencedor);
		
		//Printer.imprimeFim(this.populacao.get(0));

                //retorna a equacoes j· alteradas
                Equacao.getEquacoesComPesos(gerador.getEquacoes(), this.populacao.get(0));

                return this.populacao.get(0);
	}
	
	public void run() {
		Printer.imprimeInicio(this.parametros.getInt("ag.populacao.inicial"), this.parametros.getInt("ag.geracoes"));

		int nGer = 0;
		while (++nGer <= this.parametros.getInt("ag.geracoes")) {

			Printer.imprimeInformacoesInicioGeracao(nGer);
			
			Collections.shuffle(this.populacao); // Embaralha a popula√ß√£o para evitar privil√©gios
			List<Cromossomo> newPopulacao = new ArrayList<Cromossomo>();
			__contMutacao = 0; __contReproducao = 0;
			
			BigDecimal fitnessTotal = avaliaPopulacao();
			
			float tentativasCruzamento = this.populacao.size()*this.parametros.getFloat("ag.porcentagem.selecao");
			for (int i=0; i<=tentativasCruzamento; i++) {			
				Cromossomo[] casal = fazSelecao(fitnessTotal);
				newPopulacao.addAll(fazCruzamento(casal[0], casal[1]));
			}
			atualizaPopulacao(newPopulacao);

                        ComparatorCromossomo cpCrom = new ComparatorCromossomo(false);
			Collections.sort(this.populacao, cpCrom);

                        if ((nGer % this.buscaLocal) == 0)
                            fazDiversificacaoIntensificacao();

			this._vencedor = this.populacao.get(0);

			Printer.imprimeInformacoesFimGeracao(
					this.populacao.size(), 
					(int)tentativasCruzamento, 
					this.__contMutacao, 
					this.__contReproducao, 
					fitnessTotal.doubleValue(),
					this._vencedor);
		}
		
		Printer.imprimeFim(this.populacao.get(0));

                //retorna a equacoes j· alteradas
                Equacao.getEquacoesComPesos(gerador.getEquacoes(), this.populacao.get(0));
                
                System.out.println("N˙mero de avaliaÁıes " + this.__contAvaliacoes);                
	}
	
	/**
	 * Cria a nova popula√ß√£o, deixando uma porcentagem dos melhores e dos piores indiv√≠duos da populacao anterior.
	 * O resto, troca os individuos da populacao antiga e troca pelos melhores indiv√≠duos da popula√ß√£o nova.
	 * @param popNova
	 */
	private void atualizaPopulacao(List<Cromossomo> popNova) {

		List<Cromossomo> novaGeracao = new ArrayList<Cromossomo>();
                int tamanhoInicialPop = this.populacao.size();
                int ultimoMelhorIndividuo = (int) (tamanhoInicialPop*this.parametros.getFloat("ag.porcentagem.individuos.otimos"));
                int primeiroPiorIndividuo = tamanhoInicialPop - (int)(tamanhoInicialPop*this.parametros.getFloat("ag.porcentagem.individuos.ruins"));

		// Ordena a populacao. Os melhores indiv√≠duos ficam no topo e os piores no fundo
		Collections.sort(this.populacao, new ComparatorCromossomo(false));
	
		Printer.imprimePopulacao(this.populacao);

                // Mata os individuos velhos e ruins da populaÁ„o pai
		for (int i = tamanhoInicialPop-1; i >= primeiroPiorIndividuo; i--)
                    if(this.populacao.get(i).getGeracao() >= this.parametros.getInt("ag.mortalidade"))
                        this.populacao.remove(i);

		// Preservar, na nova gera√ß√£o, os melhores indiv√≠duos da gera√ß√£o anterior
		for (int i = 0; i < ultimoMelhorIndividuo; i++){
                    this.populacao.get(i).atualizaGeracao();
                    novaGeracao.add(this.populacao.get(i));
                }
		
		// Preservar, na nova gera√ß√£o, os piores indiv√≠duos da gera√ß√£o anterior
                int tamanhoAtualPop = this.populacao.size();
                int j;
		for (j = 1; j <= tamanhoInicialPop-primeiroPiorIndividuo; j++){
                    this.populacao.get(tamanhoAtualPop-j).atualizaGeracao();
                    novaGeracao.add(this.populacao.get(tamanhoAtualPop-j));
                }

		// Preservar os melhores individuos em compara√ß√£o com as duas popula√ß√µes
                int ultimoPiorEscolhido = tamanhoAtualPop-j;
		for (int i = ultimoMelhorIndividuo; i <= ultimoPiorEscolhido; i++) 
			popNova.add(this.populacao.get(i));
                Collections.sort(popNova, new ComparatorCromossomo(false));
		for (int i = 0; i<primeiroPiorIndividuo - ultimoMelhorIndividuo; i++){
                    popNova.get(i).atualizaGeracao();
                    novaGeracao.add(popNova.get(i));
                }
		this.populacao = novaGeracao;
	}
	

	/**
	 * Avalia o fitness da popula√ß√£o
	 */
	private BigDecimal avaliaPopulacao() {
		
		BigDecimal soma = new BigDecimal(0);
                //calcular a diferenÁa de equaÁıes apenas para os indivÌduos novos
		for(Cromossomo c : this.populacao){
                        if(c.isCalculado()){
                            soma = soma.add(c.getSoma());
                            this.__contAvaliacoes++;

                        }else{
                            soma = soma.add(avaliaCromossomo(c));
                        }
                }
		return soma;
	}
		
	/**
	 * 
	 * @return dois cromossomos para cruzar
	 */
	private Cromossomo[] fazSelecao(BigDecimal fitness) {
		Cromossomo[] crom = new Cromossomo[2];
		
		crom[0] = this.getEscolhidoRoleta(fitness);
		do {
			crom[1] = this.getEscolhidoRoleta(fitness);
		} while (crom[1] == crom[0]);

		return crom;		
	}
	
	private Cromossomo getEscolhidoRoleta(BigDecimal fitness) {
		
		double roleta = Math.random() * fitness.doubleValue();
		double soma = 0;
		int i = 0;

		while (soma < roleta) 
			soma += this.populacao.get(i++).getSoma().doubleValue();
		
		return this.populacao.get(i-1);
	}

	/***
	 * Realiza a tentativa de cruzamento entre os indiv√≠duos. Caso haja cruzamento, s√£o gerados 2 filhos.
	 * @param c1
	 * @param c2
	 * @return
	 */
	private List<Cromossomo> fazCruzamento(Cromossomo c1, Cromossomo c2) {
		
		List<Cromossomo> filhos = new ArrayList<Cromossomo>();

		if (Math.random() <= this.parametros.getFloat("ag.taxa.cruzamento")) {
			Cromossomo filho = Operadores.reproducaoPontoCruzamento(c1, c2, this.gerador.getNumeroDeGenes());
			fazMutacao(filho);
			avaliaCromossomo(filho);
			filhos.add(filho);

			filho = Operadores.reproducaoPontoCruzamento(c2, c1, this.gerador.getNumeroDeGenes());
			fazMutacao(filho);			
			avaliaCromossomo(filho);
			filhos.add(filho);
			
			__contReproducao += 2;
		}
		
		return filhos;
	}
	
	private BigDecimal avaliaCromossomo(Cromossomo c) {
            
                this.__contAvaliacoes++;
            
		c.limpaCadaResultado();
		c.setSoma(Operadores.avaliaCromossomo(c, gerador.getEquacoes(), gerador.getResultadoEquacoes()));
		c.setDiferenca(this.gerador.getSomatorioEquacoes().subtract(c.getSoma()).abs());
		return c.getSoma();
	}
	
	private void fazMutacao(Cromossomo c) {
		if (Math.random() <= this.parametros.getFloat("ag.taxa.mutacao")) {
			Operadores.mutacao(
                                c,
                                this.gerador.getMenorNumeroDeGenes(),
                                this.parametros.getDouble("ag.granularidade"));
			//TODO n√£o √© mais necess√°rio desse m√©todo... retirar da classe Cromossomo
			c.setFoiMutado(true);
			
			__contMutacao += 1;
		}
		
	}

    private void fazDiversificacaoIntensificacao() {

        Printer.imprimeDiversificacaoIntensificacaoInicio();

        double granularidade = this.parametros.getDouble("ag.granularidade");
        
        //Cria colecao com solucoes variadas a partir do vencedor
        Cromossomo venc = this.populacao.get(0);

        ArrayList<Cromossomo> al = new ArrayList<Cromossomo>();
        for (int i = 0; i < venc.getNumeroDeGenes(); i++) {
            //Cria cromossomo com um gene somado de granularidade
            Cromossomo cAux = venc.clone();
            cAux.getGene(i).addValor(granularidade);
            avaliaCromossomo(cAux);
            al.add(cAux);

            //Cria cromossomo com um gene subtraÌdo de granularidade
            if (venc.getGene(i).getValor() >= granularidade ) {
                cAux = venc.clone();
                cAux.getGene(i).subtValor(granularidade);
                avaliaCromossomo(cAux);
                al.add(cAux);
            }
         }

        //Faz a busca local, ou seja, verifica se alguma soluÁ„o na vizinhanÁa È melhor que a soluÁ„o inicial
        ComparatorCromossomo cc = new ComparatorCromossomo(false);
        Collections.sort(al, cc);

        Printer.imprimeDiversificacaoIntensificacao(venc, al);

        // Caso o primeiro individuo seja melhor que o vencedor atual, insira-o na populaÁ„o
        if (cc.compare(venc, al.get(0)) > 0) 
            this.populacao.add(0, al.get(0));

        // Insere algumas das soluÁıes de vizinhanÁa na populaÁ„o
        if (this.parametros.getInt("ag.buscalocal.insercao") != 0) {
            int qtd = (int) (al.size() / this.parametros.getInt("ag.buscalocal.insercao"));
            for(int i = 1; i < qtd; i++)
                this.populacao.add(al.get(i));
        }

    }
	
}
