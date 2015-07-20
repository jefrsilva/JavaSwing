package modelo;

public class ContaAPagar {

	private String categoria;
	private String descricao;
	private double valor;
	private String vencimento;
	
	public ContaAPagar(String categoria, String descricao, double valor,
			String vencimento) {
		this.categoria = categoria;
		this.descricao = descricao;
		this.valor = valor;
		this.vencimento = vencimento;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public String getVencimento() {
		return vencimento;
	}

	public void setVencimento(String vencimento) {
		this.vencimento = vencimento;
	}
	
}
