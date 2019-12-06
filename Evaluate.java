package WTE;

public class Evaluate {
private String UID;
private Double point[];

public String getid() {
	return UID;
}

public Double getpoint(int j) {
	return point[j];
}

public Evaluate(User p,Double po[]) {
	this.UID = p.getid();
	point = new Double[po.length];
	for(int i=0;i<po.length;i++)
		point[i] = po[i];
}
}
