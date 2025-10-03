/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.auth.service.impl;

import com.anasdidi.school.auth.AuthConstants;
import com.anasdidi.school.common.CommonConstants;
import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.core.annotation.NonNull;

@ConfigurationProperties(CommonConstants.PROP_PREFIX + AuthConstants.Event.AUTH_SIGN_IN)
record SignInServiceProps(@NonNull Long refreshTokenExpiredSecs) {}
