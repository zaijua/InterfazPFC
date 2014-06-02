package listasExpandibles;

import java.util.ArrayList;
import java.util.List;

import pfc.obj.Resultado;

public class GrupoDeItems {
	public String string;
	public final List<Resultado> children = new ArrayList<Resultado>();

	public GrupoDeItems(String string) {
		this.string = string;
	}
}
