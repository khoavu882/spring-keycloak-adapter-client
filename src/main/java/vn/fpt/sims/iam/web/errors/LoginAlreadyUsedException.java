package vn.fpt.sims.iam.web.errors;

import vn.fpt.sims.iam.constant.ErrorConstants;

public class LoginAlreadyUsedException extends BadRequestIamException {

    private static final long serialVersionUID = 1L;

    public LoginAlreadyUsedException() {
        super(ErrorConstants.LOGIN_ALREADY_USED_TYPE, "Login name already used!", "user_management", "user_exists");
    }
}
