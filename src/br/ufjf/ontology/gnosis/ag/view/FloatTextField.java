package br.ufjf.ontology.gnosis.ag.view;
/*
 *  UNIVERSIDADE FEDERAL DE JUIZ DE FORA
 *  CRIADO: 14/01/2011
 *  MODIFICADO: 14/01/2011
 *  AUTOR: Bruno Augusto Clemente de Assis
 *  CLASSE: FloatTextField
 *  FUN��O: Valida os valores inseridos, verifica se s�o float.
 * 
 */


import java.awt.*;


public class FloatTextField extends TextField
{
	private static final long serialVersionUID = 4843090395739673560L;

	public FloatTextField(float def, int size)
	{
		super(""+def, size);
	}

	// Verifica se o valor � um numero de ponto flutuante
	public boolean isValid()
	{
		@SuppressWarnings("unused")
		float value=0;
		try
		{
			value = Float.valueOf(getText().trim()).floatValue();
		}
		catch(NumberFormatException e)
		{
			requestFocus();
			return false;
		}
		return true;
	}

	public float getValue()
	{
		return  Float.valueOf(getText().trim()).floatValue();
	}

}
