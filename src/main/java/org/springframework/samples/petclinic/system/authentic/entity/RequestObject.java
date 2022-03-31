package org.springframework.samples.petclinic.system.authentic.entity;

import java.util.HashMap;
import java.util.Map;

public abstract class RequestObject {

	protected String url;

	protected HttpRequestMethodEnum httpMethod;

	protected Map<String, String> param = new HashMap<>();

}

enum HttpRequestMethodEnum {

	GET, POST, PUT, DELETE

}