package vn.fpt.sims.iam.web.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;
import vn.fpt.sims.iam.constant.ErrorConstants;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class BadRequestIamException extends AbstractThrowableProblem {

    private static final long serialVersionUID = 1L;

    private final String entityName;

    private final String errorKey;

    public BadRequestIamException(String defaultMessage, String entityName, String errorKey) {
        this(ErrorConstants.DEFAULT_TYPE, defaultMessage, entityName, errorKey);
    }

    public BadRequestIamException(URI type, String defaultMessage, String entityName, String errorKey) {
        super(type, defaultMessage, Status.BAD_REQUEST, null, null, null, getAlertParameters(entityName, errorKey));
        this.entityName = entityName;
        this.errorKey = errorKey;
    }

    public BadRequestIamException(ErrorsEnum error) {
        super(null, null, Status.BAD_REQUEST, null, null, null, getAlertParameters(error.getEntityName(), error.getErrorKey()));
        this.entityName = error.getEntityName();
        this.errorKey = error.getErrorKey();
    }

    public String getEntityName() {
        return entityName;
    }

    public String getErrorKey() {
        return errorKey;
    }

    private static Map<String, Object> getAlertParameters(String entityName, String errorKey) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("message", "error." + entityName + errorKey);
        parameters.put("params", entityName);
        return parameters;
    }
}
