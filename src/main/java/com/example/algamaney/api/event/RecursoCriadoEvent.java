package com.example.algamaney.api.event;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEvent;

public class RecursoCriadoEvent extends ApplicationEvent{

	private static final long serialVersionUID = 5991238397367781813L;

	private HttpServletResponse response;
	private Long codigo;
	
	public RecursoCriadoEvent(Object source,HttpServletResponse response,Long codigo) {
		super(source);
		this.codigo=codigo;
		this.response=response;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public Long getCodigo() {
		return codigo;
	}

}
