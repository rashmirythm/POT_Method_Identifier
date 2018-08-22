import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class MethodsAffected {

	public String FileName;
	public String FunctionName;
	public String FunctionParameter;
	
	public String getFileName() {
		return FileName;
	}
	public void setFileName(String fileName) {
		FileName = fileName;
	}
	public String getFunctionName() {
		return FunctionName;
	}
	public void setFunctionName(String functionName) {
		FunctionName = functionName;
	}
	
	@Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31). // two randomly chosen prime numbers
            // if deriving: appendSuper(super.hashCode()).
            append(FileName).
            append(FunctionName).
            append(FunctionParameter).
            toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
       if (!(obj instanceof MethodsAffected))
            return false;
        if (obj == this)
            return true;

        MethodsAffected rhs = (MethodsAffected) obj;
        return new EqualsBuilder().
            // if deriving: appendSuper(super.equals(obj)).
            append(FileName, rhs.FileName).
            append(FunctionName, rhs.FunctionName).
            append(FunctionParameter, rhs.FunctionParameter).
            isEquals();
    } 
	
}
