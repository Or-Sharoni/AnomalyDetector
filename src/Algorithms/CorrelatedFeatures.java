package Algorithms;

public class CorrelatedFeatures {
	public String feature1;
	public String feature2;
	public float corrlation;
	public Line lin_reg;
	public Circle circle;
	public float threshold;
	int index1;
	int index2;

	public CorrelatedFeatures(String feature1, String feature2, float corrlation, int index1, int index2) {
		this.feature1 = feature1;
		this.feature2 = feature2;
		this.corrlation = corrlation;
		this.lin_reg = null;
		this.threshold = 0.0F;
		this.circle = null;
		this.index1 = index1;
		this.index2 = index2;
	}

	public CorrelatedFeatures(String feature1, String feature2, float corrlation, Line lin_reg, float threshold) {
		this.feature1 = feature1;
		this.feature2 = feature2;
		this.corrlation = corrlation;
		this.lin_reg = lin_reg;
		this.threshold = threshold;
		this.circle = null;
	}

	public CorrelatedFeatures(String feature1, String feature2, float corrlation, Circle circle, float threshold) {
		this.feature1 = feature1;
		this.feature2 = feature2;
		this.corrlation = corrlation;
		this.lin_reg = null;
		this.threshold = threshold;
		this.circle = circle;
	}
}