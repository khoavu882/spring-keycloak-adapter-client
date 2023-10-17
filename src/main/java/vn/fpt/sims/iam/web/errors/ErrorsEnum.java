package vn.fpt.sims.iam.web.errors;

import lombok.Getter;
import vn.fpt.sims.iam.constant.EntitiesConstant;
import vn.fpt.sims.iam.constant.ErrorsKeyConstant;

@Getter
public enum ErrorsEnum {
    USER_ID_NOT_FOUND(EntitiesConstant.USER, ErrorsKeyConstant.ID_NOT_FOUND, ""),
    USER_NAME_HAD_BEEN_EXISTED(EntitiesConstant.USER, ErrorsKeyConstant.HAD_BEEN_EXISTED, ""),
    USER_INVALID(EntitiesConstant.USER, ErrorsKeyConstant.ID_INVALID, ""),
    USER_ID_NULL(EntitiesConstant.USER, ErrorsKeyConstant.ID_NULL, ""),
    WEBHOOK_ID_NOT_FOUND(EntitiesConstant.USER, ErrorsKeyConstant.ID_NOT_FOUND, ""),
    WEBHOOK_NAME_HAD_BEEN_EXISTED(EntitiesConstant.USER, ErrorsKeyConstant.HAD_BEEN_EXISTED, ""),
    WEBHOOK_INVALID(EntitiesConstant.USER, ErrorsKeyConstant.ID_INVALID, ""),
    WEBHOOK_ID_NULL(EntitiesConstant.USER, ErrorsKeyConstant.ID_NULL, "");

    // ... add more cases here ...
    private final String entityName;
    private final String errorKey;
    private String message;

    public String getMessage() {
        message = getEntityName() + "." + getErrorKey();
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    ErrorsEnum(String entityName, String errorKey, String message) {
        this.entityName = entityName;
        this.errorKey = errorKey;
        this.message = message;
    }
}
