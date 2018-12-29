import units.qual.*;

class GenericMethods {

    public <T> T idOne(T param) {
        return param;
    }

    public <T extends Number> T idTwo(T param) {
        T localVar = null;
        return param;
    }

    public <T> String mStringCatOne(T param) {
        // :: fixable-error: (method.invocation.invalid)
        return param.toString() + param;
    }

    public <T extends Object> String mStringCatTwo(T param) {
        return param.toString() + param;
    }

    // Issue: https://github.com/opprop/checker-framework-inference/issues/170
    // public <T extends Integer> Integer mInteger(T param){
    //     return param + param;
    // }

}
