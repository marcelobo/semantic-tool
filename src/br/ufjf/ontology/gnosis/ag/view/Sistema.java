package br.ufjf.ontology.gnosis.ag.view;
/*
 *  UNIVERSIDADE FEDERAL DE JUIZ DE FORA
 *  CRIADO: 14/01/2011
 *  MODIFICADO: 14/01/2011
 *  AUTOR: Bruno Augusto Clemente de Assis
 *  CLASSE: Sistema
 *  FUN��O: Applet para a resolu��o de um sistema com 3 equa��es pelo m�todo de Cramer.(Determinante)
 * 
 */

import java.awt.*;
import java.applet.Applet;

public class Sistema extends Applet
{	
	
	private static final long serialVersionUID = 7064477753520964690L;
	float[] A,B,C,D;
	FloatTextField[] aField,bField,cField,IgualField;	
	Color bkground,fground;
	TextField detField, xResposta,yResposta, zResposta;
	Label specialCase;
	
    public void init() 
	{	
    	/* Configura a interface */

    	this.setSize(320,280);
		A=new float[3];	B=new float[3];
		C=new float[3];	D=new float[3];
		aField = new FloatTextField[3];
		bField = new FloatTextField[3];
		cField = new FloatTextField[3];
		IgualField = new FloatTextField[3];
		
		Color cor = new Color(240, 240, 255);
		bkground=cor;
		fground=Color.black;
		setBackground(bkground);
		setForeground(fground);
		setLayout(new GridLayout(11,1));

		Label titulo1=new Label("Verifica se um sistema de", Label.CENTER);
		Label titulo2=new Label("tr�s vari�veis tem solu��o!", Label.CENTER);
		Label titulo3=new Label("A, B, C s�o as fun��es f1, f2, f3 ", Label.CENTER);
		Label titulo4=new Label("X, Y, Z s�o os valores de P1, P2, P3 respectivamente", Label.CENTER);
		titulo1.setFont(new Font("Dialog", Font.BOLD, 14));
		titulo2.setFont(new Font("Dialog", Font.BOLD, 14));
		add(titulo1);
		add(titulo2);
		add(titulo3);
		add(titulo4);
		
		
		/* Gera Campos de entrada de Texto */
		
		Panel[] inputPanel=new Panel[3];
		for(int i=0;i<3;i++)
		{	inputPanel[i]=new Panel();
			inputPanel[i].setLayout(new GridLayout(1,11));
			inputPanel[i].setBackground(bkground);
			inputPanel[i].setForeground(fground);
			aField[i] = new FloatTextField(0, 5);
			bField[i] = new FloatTextField(0, 5);
			cField[i] = new FloatTextField(0, 5);
			IgualField[i] = new FloatTextField(0, 5);
			inputPanel[i].add(new Label(""));
			inputPanel[i].add(aField[i]);
			inputPanel[i].add(new Label(" X +",Label.LEFT));
			inputPanel[i].add(bField[i]); 
			inputPanel[i].add(new Label(" Y +",Label.LEFT));
			inputPanel[i].add(cField[i]);
			inputPanel[i].add(new Label(" Z =",Label.LEFT));
			inputPanel[i].add(IgualField[i]);
			inputPanel[i].add(new Label(""));
			add(inputPanel[i]);
		}
		
		Button solveMe=new Button("Resolver");
		
		add(solveMe);
		specialCase=new Label("Resolver", Label.CENTER);
		specialCase.setFont(new Font("Dialog", Font.BOLD, 14));
		specialCase.setForeground(Color.blue);
		add(specialCase);
		
		/* Campos de respostas */
		
		Panel answerTop=new Panel();
		answerTop.add(new Label("Determinante",Label.RIGHT));
		detField=new TextField("",5);
		detField.setEditable(false);
		answerTop.add(detField);
		add(answerTop);
		Panel answerBottom=new Panel();
		answerBottom.add(new Label(" X=",Label.RIGHT));
		xResposta=new TextField("",5);
		xResposta.setEditable(false);
		answerBottom.add(xResposta);
		answerBottom.add(new Label(" Y=",Label.RIGHT));
		yResposta=new TextField("",5);
		yResposta.setEditable(false);
		answerBottom.add(yResposta);
		answerBottom.add(new Label(" Z=",Label.RIGHT));
		zResposta=new TextField("",5);
		zResposta.setEditable(false);
		answerBottom.add(zResposta);
		add(answerBottom);								
	 }

	public boolean action(Event evt, Object arg)
	{	if(arg.equals("Resolver"))
		{	
			xResposta.setText("");
			yResposta.setText("");
			zResposta.setText("");
			detField.setText("");
			
			/* Testa a entrada dos numeros */
			boolean TesteValido=true;
			for(int i=0;i<3;i++)
			{			
			TesteValido = TesteValido && aField[i].isValid() && bField[i].isValid() 
						&& cField[i].isValid() && IgualField[i].isValid();
			}
			if(TesteValido)
				resolver();			
			else
				specialCase.setText("Favor, corriga o valor digitado.");;
		}
		else return false;
		return true;
	}
		
	public void resolver()
	{	
		for(int i=0;i<3; i++)
		{	A[i]=aField[i].getValue();
			B[i]=bField[i].getValue();
			C[i]=cField[i].getValue();
			D[i]=IgualField[i].getValue();
		}
		
		/* Usa regra de cramer para resolver o determinante*/
		
		float aDeter=calcDeterminante(D,B,C);
		float bDeter=calcDeterminante(A,D,C);
		float cDeter=calcDeterminante(A,B,D);
		float dDeter=calcDeterminante(A,B,C);
		detField.setText(new Float(dDeter).toString());

		if(dDeter==0)
		{	if(aDeter==0 && bDeter==0 && cDeter==0)
			{	
				/*As equa��es s�o dependentes*/
				/*Existe um n�mero infinito de solu��es poss�veis*/
				specialCase.setText("Poss�vel e Indeterminado");
			}
			else
			{	
				/*N�o existe solu��o*/
				specialCase.setText("Imposs�vel");
			}
		}   
		else
			/*Existe solu��o e retorna os valores de x,y,z*/
		{	
			specialCase.setText("Poss�vel e determinado");
			xResposta.setText(new Float(aDeter/dDeter).toString());
			yResposta.setText(new Float(bDeter/dDeter).toString());
			zResposta.setText(new Float(cDeter/dDeter).toString());
		}
		repaint();
		return;
	}
	
	public float calcDeterminante(float[] a, float[] b,float[] c)
	{
		float det=0;
		det=a[0]*b[1]*c[2]
			+b[0]*c[1]*a[2]
			+c[0]*a[1]*b[2]
			-a[2]*b[1]*c[0]
			-b[2]*c[1]*a[0]
			-c[2]*a[1]*b[0];
		return det;
	}

	@SuppressWarnings("deprecation")
	public void start()
	{
		this.show();
    }
}
