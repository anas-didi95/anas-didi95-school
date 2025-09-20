/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.user.controller;

import io.micronaut.http.HttpResponse;

public abstract class UserController {

  protected abstract HttpResponse<String> helloWorld();
}
