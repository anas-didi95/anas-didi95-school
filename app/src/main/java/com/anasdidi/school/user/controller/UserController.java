/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.user.controller;

import com.anasdidi.school.user.dto.HelloWorldReqDTO;
import com.anasdidi.school.user.dto.HelloWorldResDTO;
import io.micronaut.http.HttpResponse;

public abstract class UserController {

  protected abstract HttpResponse<HelloWorldResDTO> helloWorld(HelloWorldReqDTO body);
}
