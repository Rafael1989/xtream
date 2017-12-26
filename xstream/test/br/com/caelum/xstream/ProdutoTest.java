package br.com.caelum.xstream;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.thoughtworks.xstream.XStream;

public class ProdutoTest {
	
	@Test
	public void deveGerarOXmlComONomePrecoEDescricaoAdequados() {
		String resultadoEsperado = "<produto codigo=\"1587\">\n" +
		        "  <nome>geladeira</nome>\n" +
		        "  <preco>1000.0</preco>\n" +
		        "  <descrição>geladeira duas portas</descrição>\n" +
		        "</produto>";
		Produto produto = new Produto("geladeira", 1000.0, "geladeira duas portas",1587);
		XStream xStream = new XStream();
		xStream.alias("produto", Produto.class);
		xStream.aliasField("descrição", Produto.class, "descricao");
		xStream.useAttributeFor(Produto.class,"codigo");
		String xmlGerado = xStream.toXML(produto);
		assertEquals(resultadoEsperado, xmlGerado);
	}

}
