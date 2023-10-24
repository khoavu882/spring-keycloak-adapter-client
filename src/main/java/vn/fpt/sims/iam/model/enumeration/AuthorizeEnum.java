package vn.fpt.sims.iam.model.enumeration;

import lombok.Getter;

@Getter
public enum AuthorizeEnum {
    APP_ADMIN("APP_ADMIN"),
    APP_USER("APP_USER"),
    SUPER_ADMIN("SUPER_ADMIN");

    private final String value;

    AuthorizeEnum(String value) {
        this.value = value;
    }
}
