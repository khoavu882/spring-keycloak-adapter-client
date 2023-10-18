package vn.fpt.sims.iam.web.errors;

import vn.fpt.sims.iam.constant.ErrorConstants;

public class EmailAlreadyUsedException extends BadRequestIamException {

    private static final long serialVersionUID = 1L;

    public EmailAlreadyUsedException() {
        super(ErrorConstants.EMAIL_ALREADY_USED_TYPE, "Email is already in use!", "user_management", "emailexists");
    }
}
