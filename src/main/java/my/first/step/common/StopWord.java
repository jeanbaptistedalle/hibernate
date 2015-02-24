package my.first.step.common;

import java.util.ArrayList;
import java.util.List;

public class StopWord {

	private List<String> stopWords;

	public StopWord() {
		stopWords = new ArrayList<String>();
	}

	public StopWord(final List<String> stopWords) {
		this.stopWords = stopWords;
	}
	
	public boolean contains(final String word){
		return stopWords.contains(word);
	}
}
