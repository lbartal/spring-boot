package sample.data.jpa.domain;

import java.util.Objects;

public class Quote {
	String type;
	Value value;

	class Value {
		long id;
		String quote;

		public Value() {
		}

		public Value(long id, String quote) {
			this.id = id;
			this.quote = quote;
		}

		public long getId() {
			return id;
		}

		public String getQuote() {
			return quote;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			Value value = (Value) o;
			return id == value.id &&
					Objects.equals(quote, value.quote);
		}

		@Override
		public int hashCode() {

			return Objects.hash(id, quote);
		}

	}

	public Quote() {
	}

	public Quote(String type, long id, String quote) {
		this.type = type;
		this.value = new Value(id, quote);
	}

	public String getType() {
		return type;
	}

	public Value getValue() {
		return value;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Quote quote = (Quote) o;
		return Objects.equals(type, quote.type) &&
				Objects.equals(value, quote.value);
	}

	@Override
	public int hashCode() {

		return Objects.hash(type, value);
	}
}
