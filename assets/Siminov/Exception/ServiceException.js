
function ServiceException(className, methodName, message) {

    var className = className;
    var methodName = methodName;
    var message = message;


    this.getClassName = function() {
        return className;
    }

    this.setClassName = function(val) {
        className = val;
    }

    this.getMethodName = function() {
        return methodName;
    }

    this.setMethodName = function(val) {
        methodName = val;
    }

    this.getMessage = function() {
        return message;
    }

    this.setMessage = function(val) {
        message = val;
    }
}

