package it.polito.tdp.PremierLeague.model;

public class Adiacenza {
	
	private Match m1;
	private Match m2;
	private int peso;
	
	public Adiacenza(Match m1, Match m2, int peso) {
		super();
		this.m1 = m1;
		this.m2 = m2;
		this.peso = peso;
	}

	public Match getM1() {
		return m1;
	}

	public Match getM2() {
		return m2;
	}

	public int getPeso() {
		return peso;
	}
	
}
