package Algorithms;

import java.util.List;

public interface Algorithms {
	void learnNormal(TimeSeries ts);
	List<AnomalyReport> detect(TimeSeries ts);
}
