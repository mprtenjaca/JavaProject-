package hr.java.vjezbe.entitet;

import java.math.BigDecimal;

public class Automobil extends Artikl implements Vozilo {

	public BigDecimal snagaKs;

	public Automobil(String naslov, String opis, BigDecimal snagaKs,BigDecimal cijena) {
		super(naslov, opis, cijena);
		this.snagaKs = snagaKs;
	}

	@Override
	public BigDecimal izracunajGrupuOsiguranja() {
		double tempSnagaKs = snagaKs.doubleValue();
		int izracunGrupe = 0;
		
		if(tempSnagaKs < 55) {
			izracunGrupe = 1;
		}
		if(tempSnagaKs >= 55 && tempSnagaKs < 65) {
			izracunGrupe = 1;
		}
		if(tempSnagaKs >= 65 && tempSnagaKs < 85) {
			izracunGrupe = 2;
		}
		if(tempSnagaKs >= 85 && tempSnagaKs < 110) {
			izracunGrupe = 3;
		}
		if(tempSnagaKs >= 110 && tempSnagaKs < 150) {
			izracunGrupe = 4;
		}
		if(tempSnagaKs >= 150 && tempSnagaKs < 200) {
			izracunGrupe = 5;
		}
		if(tempSnagaKs >= 200) {
			izracunGrupe = 6;
		}
		
		
		BigDecimal result = new BigDecimal(izracunGrupe);
		
		return result;
	}

	@Override
	public String tekstOglasa() {
		
		return String.format("Naslov automobila: %s\n"
				+ "Opis automobila: %s\n"
				+ "Snaga automobila: %.0f\n"
				+ "Izračun osiguranja automobila: %.0f\n"
				+ "Cijena automobila: %.0f", getNaslov(), getOpis(), izracunajKw(snagaKs), izracunajCijenuOsiguranja(), getCijena());
	}

	public BigDecimal getSnagaKs() {
		return snagaKs;
	}

	public void setSnagaKs(BigDecimal snagaKs) {
		this.snagaKs = snagaKs;
	}

}
