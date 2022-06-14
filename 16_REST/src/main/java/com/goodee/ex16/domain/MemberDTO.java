package com.goodee.ex16.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDTO {
		private Long memberNo;
		private String id;
		private String name;
		private String gender;
		private String address;
}
