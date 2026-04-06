package com.fintech.finance.dtos;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponseDto {

	private Long id;
	private Boolean status;
	private String message;
	private List<UserDto> activeUsers;
	private List<UserDto> nonactiveUsers;
}
