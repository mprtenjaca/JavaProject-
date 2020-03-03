package hr.java.vjezbe.entitet;

import java.math.BigDecimal;

public interface Vozilo {

	default BigDecimal izracunajKw(BigDecimal ks) {
		BigDecimal kw = ks.multiply(new BigDecimal(0.73549875));
		return kw;
	}

	public BigDecimal izracunajGrupuOsiguranja();

	default BigDecimal izracunajCijenuOsiguranja() {
		
		BigDecimal tempGrupa = izracunajGrupuOsiguranja();
		int value = tempGrupa.intValue();

		
		int tempResult = 0;
		
		switch (value) {

		case 1:
			tempResult = 200;
			break;
		case 2:
			tempResult = 500;
			break;
		case 3:
			tempResult = 700;
			break;
		case 4:
			tempResult = 1000;
			break;
		case 5:
			tempResult = 1500;
			break;
		default:
			tempResult = 2000;
			break;
		};

		BigDecimal result = new BigDecimal(tempResult);

		return result;

	}

}
