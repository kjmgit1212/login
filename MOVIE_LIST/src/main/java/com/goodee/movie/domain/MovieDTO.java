package com.goodee.movie.domain;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieDTO {
		private Long movieNo;
		private String movieTitle;
		private String movieGenre;
		private String movieInfo;
		private Date movieOpenDt;
		
}
