package com.goodee.gdlibrary.domain;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationDTO {
	private Long reservationNo;
	private Long memberNo;
	private Long seatNo;
	private Date reservationBeginDate;
	private Date reservationEndDate;
}
