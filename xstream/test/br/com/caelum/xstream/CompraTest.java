package br.com.caelum.xstream;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.NoTypePermission;
import com.thoughtworks.xstream.security.NullPermission;
import com.thoughtworks.xstream.security.PrimitiveTypePermission;

public class CompraTest {
	
	@Test
	public void deveSerializarCadaUmDosProdutosDeUmaCompra () {
		String resultadoEsperado = "<compra>\n"+
	            "  <id>15</id>\n"+
	            "  <produtos>\n"+
	            "    <produto codigo=\"1587\">\n"+
	            "      <nome>geladeira</nome>\n"+
	            "      <preco>1000.0</preco>\n"+
	            "      <descrição>geladeira duas portas</descrição>\n"+
	            "    </produto>\n"+
	            "    <produto codigo=\"1588\">\n"+
	            "      <nome>ferro de passar</nome>\n"+
	            "      <preco>100.0</preco>\n"+
	            "      <descrição>ferro com vaporizador</descrição>\n"+
	            "    </produto>\n"+
	            "  </produtos>\n"+
	            "</compra>";
		
		Compra compra = compraComGeladeiraEFerro();
		
		XStream xStream = new XStream();
		xStream.alias("compra", Compra.class);
		xStream.alias("produto", Produto.class);
		xStream.aliasField("descrição", Produto.class, "descricao");
		xStream.useAttributeFor(Produto.class, "codigo");
		String xmlGerado = xStream.toXML(compra);
		
		assertEquals(resultadoEsperado, xmlGerado);
	}

	private Compra compraComGeladeiraEFerro() {
		Produto geladeira = geladeira();
		Produto ferro = ferro();
		List<Produto> produtos = new ArrayList<>();
		produtos.add(geladeira);
		produtos.add(ferro);
		
		Compra compra = new Compra(15, produtos);
		return compra;
	}

	private Produto ferro() {
		return new Produto("ferro de passar", 100, "ferro com vaporizador", 1588);
	}

	private Produto geladeira() {
		return new Produto("geladeira", 1000, "geladeira duas portas", 1587);
	}
	
	@Test
	public void deveGerarUmaCompraComOsProdutosAdequados() {
		String xmlDeOrigem = "<compra>\n"+
	            "  <id>15</id>\n"+
	            "  <produtos>\n"+
	            "    <produto codigo=\"1587\">\n"+
	            "      <nome>geladeira</nome>\n"+
	            "      <preco>1000.0</preco>\n"+
	            "      <descrição>geladeira duas portas</descrição>\n"+
	            "    </produto>\n"+
	            "    <produto codigo=\"1588\">\n"+
	            "      <nome>ferro de passar</nome>\n"+
	            "      <preco>100.0</preco>\n"+
	            "      <descrição>ferro com vaporizador</descrição>\n"+
	            "    </produto>\n"+
	            "  </produtos>\n"+
	            "</compra>";
		
		XStream xStream = new XStream();
		
		xStream.addPermission(NoTypePermission.NONE);
		xStream.addPermission(NullPermission.NULL);
		xStream.addPermission(PrimitiveTypePermission.PRIMITIVES);
		xStream.allowTypeHierarchy(Collection.class);
		// allow any type from the same package
		xStream.allowTypesByWildcard(new String[] {
		    "br.com.caelum.xstream.**"
		});
		xStream.alias("compra", Compra.class);
		xStream.alias("produto", Produto.class);
		xStream.aliasField("descrição", Produto.class, "descricao");
		xStream.useAttributeFor(Produto.class,"codigo");
		
		Compra compra = (Compra) xStream.fromXML(xmlDeOrigem);
		
		Produto geladeira = geladeira();
		Produto ferro = ferro();
		List<Produto> produtos = new ArrayList<Produto>();
		produtos.add(geladeira);
		produtos.add(ferro);

		Compra compraEsperada = new Compra(15, produtos);
		assertEquals(compraEsperada, compra);
	}

}
