package com.java.springBoot.app.Class;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@NoArgsConstructor
@ToString

public class Response <T>  {
	 private int resultCode;
     private String resultDescription;
     private String message;
     @JsonInclude(JsonInclude.Include.NON_NULL) // Include only if not null
  	 @JsonProperty("data")
     private T data;
     @JsonInclude(JsonInclude.Include.NON_NULL) // Include only if not null
 	 @JsonProperty("list")
     private List<T> dataList;


	public static <T> Response<T> error(int resultCode, String resultDescription) {
		Response<T> response = new Response<>();
		response.setResultCode(resultCode);
		response.setResultDescription(resultDescription);
		response.setMessage("");
		return response;
	}

		// Regular success method for generic data
		public static <T> Response<T> success(T data) {
			Response<T> response = new Response<>();
			response.setResultCode(200);
			response.setResultDescription("Success");
			response.setMessage("");
			response.setData(data);
			return response;
		}

		// Overloaded method for success when no data is returned (Void)
		public static Response<Void> success() {
			Response<Void> response = new Response<>();
			response.setResultCode(200);
			response.setResultDescription("Success");
			response.setMessage("");
			return response;
		}

	}


