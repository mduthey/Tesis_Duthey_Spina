package generic;

public class XmiReference implements XmiReferenceInterface{

	XmiReferenceInterface parent = null;
	String reference = null;
	
	@Override
	public void setReference(String container, int position, XmiReferenceInterface parent) {
		this.parent = parent;
		this.reference = "@"+container;
		if(position >= 0)
			this.reference += "."+position;
	}

	@Override
	public String getReference() {
		String out = this.reference;
		if(this.parent != null){
			out = this.parent.getReference()+"/"+out;
		}
		return out;
	}
	
}
