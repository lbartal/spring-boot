package sample.data.jpa.domain;

public class Quote {
	String type;
	Value value;

	class Value{
		long id;
		String quote;

		public Value(){}

		public long getId() {
			return id;
		}

		public String getQuote() {
			return quote;
		}
	}

	public Quote(){}

	public String getType() {
		return type;
	}

	public Value getValue(){
		return value;
	}
}
